package by.bsuir.pokerface.entity.room.impl;

import by.bsuir.pokerface.entity.room.Room;
import by.bsuir.pokerface.entity.room.RoomState;
import by.bsuir.pokerface.entity.room.RoomStateStorage;

public class WaitingState implements RoomState {
    @Override
    public void action(Room room) {
    }

    @Override
    public void end(Room room) {
    }

    @Override
    public RoomState nextState() {
        return RoomStateStorage.PREFLOP;
    }
}
