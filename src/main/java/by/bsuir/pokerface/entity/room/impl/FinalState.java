package by.bsuir.pokerface.entity.room.impl;

import by.bsuir.pokerface.entity.room.RoomState;
import by.bsuir.pokerface.entity.room.RoomStateStorage;

public class FinalState implements RoomState {

    @Override
    public RoomState nextState() {
        return RoomStateStorage.WAITING;
    }
}
