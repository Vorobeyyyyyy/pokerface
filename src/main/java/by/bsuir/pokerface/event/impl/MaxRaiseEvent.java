package by.bsuir.pokerface.event.impl;

import by.bsuir.pokerface.event.AbstractGameEvent;
import by.bsuir.pokerface.event.EventName;

public class MaxRaiseEvent extends AbstractGameEvent {
    public int minRaise;
    public int maxRaise;

    public MaxRaiseEvent(int minRaise, int maxRaise) {
        event = EventName.MAX_RAISE;
        this.minRaise = minRaise;
        this.maxRaise = maxRaise;
    }
}
