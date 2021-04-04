package by.bsuir.pokerface.entity.room.impl;

import by.bsuir.pokerface.entity.room.Room;
import by.bsuir.pokerface.entity.room.RoomState;
import by.bsuir.pokerface.entity.room.RoomStateStorage;
import by.bsuir.pokerface.event.impl.ClearBoardCardsEvent;

public class WaitingState implements RoomState {
    @Override
    public void onStart(Room room) {
    }

    @Override
    public RoomState nextState() {
        return RoomStateStorage.PREFLOP;
    }
}
