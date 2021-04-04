package by.bsuir.pokerface.entity.room.impl;

import by.bsuir.pokerface.entity.card.Card;
import by.bsuir.pokerface.entity.room.Room;
import by.bsuir.pokerface.entity.room.RoomExecutor;
import by.bsuir.pokerface.entity.room.RoomState;
import by.bsuir.pokerface.entity.room.RoomStateStorage;
import by.bsuir.pokerface.event.impl.AddBoardCardEvent;

public class RiverState implements RoomState {
    @Override
    public void onStart(Room room) {
        RoomExecutor executor = room.getExecutor();
        Card card = executor.getDeck().takeTopCard();
        room.addCard(card);
        executor.notifyPlayers(new AddBoardCardEvent(card));
    }

    @Override
    public RoomState nextState() {
        return RoomStateStorage.FINAL;
    }
}
