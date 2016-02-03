package org.drmod.representation.display;

import org.drmod.domain.Led;
import org.drmod.representation.Notificator;

/**
 * Created on 5/4/2015
 *
 * @author drmod
 */
public class ConsoleView extends AbstractView {

    public ConsoleView(Notificator notificator) {
        super(notificator);
    }

    @Override
    public void update(Led status) {
        System.out.println("Stauts: " + status.getNumber() + " " + status.isHigh());
    }
}
