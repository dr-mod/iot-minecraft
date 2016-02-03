package org.drmod.listener;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

public class Listener implements Runnable {

    private static byte[] HART_BEAT_CODE = {0b00010000};
    private static int SOCKET_READ_TIMEOUT = 500;

    private GpioController gpio;
    private ServerSocket serverSocket;
    private volatile Socket connectionSocket;

    public Listener(GpioController gpio) throws IOException {
        this.gpio = gpio;
        this.serverSocket = new ServerSocket(9993);
        System.out.println("Hi from the Listener's constructor");
    }

    @Override
    public void run() {
        try {
            GpioPinDigitalInput gpioPinDigitalInput = gpio.provisionDigitalInputPin(RaspiPin.GPIO_03, PinPullResistance.PULL_UP);
            gpioPinDigitalInput.addListener(new ButtonListener());
            while (true) {
                checkConnection();
                Thread.sleep(500);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    private boolean isConnectionAlive(){
        boolean isAlive = false;
        if (connectionSocket != null)
            try {
                connectionSocket.getOutputStream().write(HART_BEAT_CODE);
                connectionSocket.getOutputStream().flush();
                byte[] hartBeatResponse = new byte[1];
                connectionSocket.getInputStream().read(hartBeatResponse);
                if(Arrays.equals(HART_BEAT_CODE, hartBeatResponse)) {
                    isAlive = true;
                }
            } catch (IOException e) {
                // nothing is here as isAlive is false by default
            }
        return isAlive;
    }

    synchronized private void checkConnection() throws IOException {
        if (!isConnectionAlive()) {
            System.out.println("No connection for the button");
            connectionSocket = serverSocket.accept();
            connectionSocket.setSoTimeout(SOCKET_READ_TIMEOUT);
            System.out.println("Accepted for the button: " + connectionSocket.getInetAddress().toString());
        }
    }

    synchronized private void sendMessage() {
        System.out.println("In sendMessage method");
        try {
            connectionSocket.getOutputStream().write(new byte[]{0b00000001});
            connectionSocket.getOutputStream().flush();
            System.out.println("Flashed");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    class ButtonListener implements GpioPinListenerDigital {

        @Override
        public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
            System.out.println("Button event " + event.getState().toString());
            System.out.println(Thread.currentThread().toString());
            if (event.getState().isHigh())
                return;
            if (event.getState().isLow())
                sendMessage();
            System.out.println("After button event " + event.getState().toString());
        }

    }

}
