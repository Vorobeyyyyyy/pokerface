package by.bsuir.pokerface.entity.room.impl;

import by.bsuir.pokerface.entity.card.Card;
import by.bsuir.pokerface.entity.room.Room;
import by.bsuir.pokerface.entity.room.RoomNotifier;
import by.bsuir.pokerface.entity.room.RoomState;
import by.bsuir.pokerface.entity.room.RoomStateStorage;
import by.bsuir.pokerface.event.impl.AddBoardCard;

public class FlopState implements RoomState {
    private final static int FLOP_CARD_COUNT = 3;

    @Override
    public void onStart(Room room) {
        for (int i = 0; i < FLOP_CARD_COUNT; i++) {
            Card card = room.getExecutor().getDeck().takeTopCard();
            room.addCard(card);
            RoomNotifier.notifyPlayers(room, new AddBoardCard(card));
        }
    }

    @Override
    public RoomState nextState() {
        return RoomStateStorage.TURN;
    }
}
