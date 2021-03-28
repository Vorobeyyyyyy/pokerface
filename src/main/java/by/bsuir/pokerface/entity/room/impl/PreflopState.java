package by.bsuir.pokerface.entity.room.impl;

import by.bsuir.pokerface.entity.card.Card;
import by.bsuir.pokerface.entity.room.*;
import by.bsuir.pokerface.entity.user.Player;
import by.bsuir.pokerface.event.impl.SetPlayerCardEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PreflopState implements RoomState {
    private final static Logger logger = LogManager.getLogger();

    @Override
    public void onStart(Room room) {
        RoomExecutor executor = room.getExecutor();

        executor.setCurrentChair(0);
        executor.setCurrentButton(executor.nextChair());
        executor.setCurrentChair(executor.getCurrentButton());
        executor.raise(room.getSitedPlayers().get(executor.getCurrentChair()), executor.getCurrentBlind());

        for (int i = 0; i < room.getSitedPlayers().size(); i++) {
            Player player = room.getSitedPlayers().get(i);
            if (player != null && !player.isFolded()) {
                player.setCards(new Card[]{new Card("korol"), new Card("dwa")});
                SetPlayerCardEvent event = new SetPlayerCardEvent(i, "b2", "c1");
                RoomNotifier.notifySinglePlayer(room, i, event);
            }
        }
    }

    @Override
    public RoomState nextState() {
        return RoomStateStorage.FLOP;
    }
}
