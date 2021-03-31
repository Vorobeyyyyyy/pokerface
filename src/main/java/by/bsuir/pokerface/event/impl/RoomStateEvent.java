package by.bsuir.pokerface.event.impl;

import by.bsuir.pokerface.entity.card.Card;
import by.bsuir.pokerface.entity.room.Room;
import by.bsuir.pokerface.entity.room.RoomStateStorage;
import by.bsuir.pokerface.event.AbstractGameEvent;
import by.bsuir.pokerface.event.EventName;

public class RoomStateEvent extends AbstractGameEvent {
    public Room room;
    boolean gameStarted;
    int chair;
    Card[] cards;

    public RoomStateEvent(Room room) {
        event = EventName.ROOM_STATE;
        this.room = room;
        gameStarted = room.getRoomState() != RoomStateStorage.WAITING;
    }

    public RoomStateEvent(Room room, int chair, Card[] cards) {
        this(room);
        this.chair = chair;
        this.cards = cards;
    }
}
