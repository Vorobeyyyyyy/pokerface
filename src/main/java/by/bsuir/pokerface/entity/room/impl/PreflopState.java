package by.bsuir.pokerface.entity.room.impl;

import by.bsuir.pokerface.entity.card.Card;
import by.bsuir.pokerface.entity.card.Deck;
import by.bsuir.pokerface.entity.room.*;
import by.bsuir.pokerface.entity.user.Player;
import by.bsuir.pokerface.event.impl.SetPlayerCardEvent;
import by.bsuir.pokerface.service.CardService;
import by.bsuir.pokerface.service.impl.CardServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PreflopState implements RoomState {
    private final static Logger logger = LogManager.getLogger();
    private final CardService cardService = CardServiceImpl.getInstance();

    @Override
    public void onStart(Room room) {
        RoomExecutor executor = room.getExecutor();

        executor.setDeck(new Deck());
        cardService.shuffle(executor.getDeck());

        room.setCurrentChair(0);
        room.setCurrentButton(executor.nextChair());
        room.setCurrentChair(room.getCurrentButton());
        room.setCurrentChair(room.getExecutor().nextChair());
        executor.raise(room.getSitedPlayers().get(room.getCurrentChair()), executor.getCurrentBlind());
        executor.setBlindTurn(true);
        executor.raise(room.getSitedPlayers().get(room.getCurrentChair()), executor.getCurrentBlind() * 2);

        for (int i = 0; i < room.getSitedPlayers().size(); i++) {
            Player player = room.getSitedPlayers().get(i);
            if (player != null && !player.isFolded()) {
                Card[] cards = new Card[]{executor.getDeck().takeTopCard(), executor.getDeck().takeTopCard()};
                player.setCards(cards);
                SetPlayerCardEvent event = new SetPlayerCardEvent(i, cards[0], cards[1]);
                RoomNotifier.notifySinglePlayer(room, i, event);
            }
        }
    }

    @Override
    public RoomState nextState() {
        return RoomStateStorage.FLOP;
    }
}
