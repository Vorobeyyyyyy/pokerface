package by.bsuir.pokerface.event.impl;

import by.bsuir.pokerface.event.AbstractGameEvent;
import by.bsuir.pokerface.event.EventName;

public class TurnTimeEvent extends AbstractGameEvent {
    public int chair;
    public int time;

    public TurnTimeEvent(int chair, int time) {
        event = EventName.PLAYER_TURN_TIME;
        this.chair = chair;
        this.time = time;
    }
}
