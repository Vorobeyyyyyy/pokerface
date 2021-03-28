package by.bsuir.pokerface.entity.room.impl;

import by.bsuir.pokerface.entity.card.Card;
import by.bsuir.pokerface.entity.room.Room;
import by.bsuir.pokerface.entity.room.RoomNotifier;
import by.bsuir.pokerface.entity.room.RoomState;
import by.bsuir.pokerface.entity.room.RoomStateStorage;
import by.bsuir.pokerface.event.impl.AddBoardCard;

public class RiverState implements RoomState {
    @Override
    public void onStart(Room room) {
        Card card = new Card("river");
        room.addCard(card);
        RoomNotifier.notifyPlayers(room, new AddBoardCard(card.getCard()));
    }

    @Override
    public RoomState nextState() {
        return RoomStateStorage.FINAL;
    }
}
