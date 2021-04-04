package by.bsuir.pokerface.event.impl;

import by.bsuir.pokerface.event.AbstractGameEvent;
import by.bsuir.pokerface.event.EventName;

public class PlayerGetUpEvent extends AbstractGameEvent {
    public final int chairId;

    public PlayerGetUpEvent(int chairId) {
        event = EventName.PLAYER_GET_UP;
        this.chairId = chairId;
    }
}
