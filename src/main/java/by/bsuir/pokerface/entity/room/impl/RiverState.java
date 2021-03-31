package by.bsuir.pokerface.entity.room.impl;

import by.bsuir.pokerface.entity.card.Card;
import by.bsuir.pokerface.entity.room.Room;
import by.bsuir.pokerface.entity.room.RoomNotifier;
import by.bsuir.pokerface.entity.room.RoomState;
import by.bsuir.pokerface.entity.room.RoomStateStorage;
import by.bsuir.pokerface.event.impl.AddBoardCardEvent;

public class RiverState implements RoomState {
    @Override
    public void onStart(Room room) {
        Card card = room.getExecutor().getDeck().takeTopCard();
        room.addCard(card);
        RoomNotifier.notifyPlayers(room, new AddBoardCardEvent(card));
    }

    @Override
    public RoomState nextState() {
        return RoomStateStorage.FINAL;
    }
}
