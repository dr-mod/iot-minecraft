package org.drmod.representation;

import org.drmod.domain.Led;

/**
 * Created on 5/4/2015
 * @author drmod
 */
public abstract class Observer {

    public Observer(Notificator notificator){
        notificator.addObserver(this);
    }

    public abstract void update(Led status);

}
