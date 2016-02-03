package org.drmod;

import com.pi4j.io.gpio.GpioFactory;
import org.drmod.listener.Listener;
import org.drmod.representation.Notificator;
import org.drmod.representation.display.ConsoleView;
import org.drmod.representation.display.RPiPentaLedView;

import java.io.IOException;

/**
 * Created on 5/4/2015
 *
 * @author drmod
 */
public class EntryPoint {

    public static void main(String[] args) throws IOException {
        Notificator notificator = new Notificator();
        new ConsoleView(notificator);
        new RPiPentaLedView(notificator, GpioFactory.getInstance());
        new Thread(new Listener(GpioFactory.getInstance())).start();
        new Run(notificator).start();
    }

}
