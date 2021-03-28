package by.bsuir.pokerface.service.impl;

import by.bsuir.pokerface.entity.card.Card;
import by.bsuir.pokerface.entity.card.Combination;
import by.bsuir.pokerface.entity.card.Deck;
import by.bsuir.pokerface.exception.ServiceException;
import by.bsuir.pokerface.factory.CombinationFactory;
import by.bsuir.pokerface.factory.CombinationFactoryProvider;
import by.bsuir.pokerface.service.CardService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class CardServiceImpl implements CardService {
    private static final int PLAYER_CARDS_COUNT = 3;
    private static final int BOARD_CARDS_COUNT = 5;

    @Override
    public void shuffle(Deck deck) {
        Collections.shuffle(deck.getCards());
    }

    @Override
    public Combination findBestCombination(List<Card> playerCards, List<Card> boardCards) throws ServiceException {
        if (playerCards.size() != PLAYER_CARDS_COUNT || boardCards.size() != BOARD_CARDS_COUNT) {
            throw new ServiceException("Wrong cards count");
        }
        List<Card> cards = new ArrayList<>();
        cards.addAll(playerCards);
        cards.addAll(boardCards);

        Optional<Combination> optionalCombination;
        int i = 0;
        CombinationFactory[] combinationFactories = CombinationFactoryProvider.values();
        do {
            optionalCombination = combinationFactories[i++].createCombination(cards);
        } while (optionalCombination.isEmpty() && i < combinationFactories.length);
        return optionalCombination.orElseThrow(() -> new ServiceException("Cant define combination"));
    }
}
