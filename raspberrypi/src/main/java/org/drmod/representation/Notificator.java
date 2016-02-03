package org.drmod.representation;

import org.drmod.domain.Led;

import java.util.ArrayList;
import java.util.List;

public class Notificator {

    private List<Observer> observers = new ArrayList<>();
    private Led status;

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    public void notifyObservers() {
        for(Observer observer : observers) {
            observer.update(status);
        }
    }

    public void setLedStatus(Led status) {
        this.status = status;
    }
}
