package org.drmod;

import org.drmod.domain.Led;
import org.drmod.representation.Notificator;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created on 5/3/2015
 *
 * @author drmod
 */
public class Run {

    private Notificator notificator;

    private ServerSocket serverSocket;

    public Run(Notificator notificator) throws IOException {
        this.serverSocket = new ServerSocket(9992);
        this.notificator = notificator;
    }

    public void start() {
        while (true) {
            try {
                System.out.println("Waiting for a connection");
                Socket connectionSocket = serverSocket.accept();
                System.out.println("Accepted: " + connectionSocket.getInetAddress().toString());
                InputStream inputStream = connectionSocket.getInputStream();
                byte[] buffer = new byte[2];
                inputStream.read(buffer);
                connectionSocket.close();
                notificator.setLedStatus(new Led(buffer));
                notificator.notifyObservers();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
