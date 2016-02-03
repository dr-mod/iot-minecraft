package org.drmod.representation.display;

import org.drmod.domain.Led;
import org.drmod.representation.Notificator;
import org.drmod.representation.Observer;

/**
 * Created on 5/8/2015
 *
 * @author drmod
 */
abstract class AbstractView extends Observer {

    public AbstractView(final Notificator notificator) {
        super(notificator);
    }

    @Override
    public void update(final Led status) {
    }
}
