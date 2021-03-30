package by.bsuir.pokerface.event.impl;

import by.bsuir.pokerface.entity.room.Room;
import by.bsuir.pokerface.event.AbstractGameEvent;
import by.bsuir.pokerface.event.EventName;

public class RoomStateEvent extends AbstractGameEvent {
    public Room room;

    public RoomStateEvent(Room room) {
        event = EventName.ROOM_STATE;
        this.room = room;
    }
}
