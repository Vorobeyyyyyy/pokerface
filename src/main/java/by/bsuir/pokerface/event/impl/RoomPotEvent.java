package by.bsuir.pokerface.event.impl;

import by.bsuir.pokerface.event.AbstractGameEvent;
import by.bsuir.pokerface.event.EventName;

public class RoomPotEvent extends AbstractGameEvent {
    public int value;

    public RoomPotEvent(int value) {
        event = EventName.ROOM_POT;
        this.value = value;
    }
}
