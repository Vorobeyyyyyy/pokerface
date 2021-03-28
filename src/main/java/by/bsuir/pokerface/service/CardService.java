package by.bsuir.pokerface.service;

import by.bsuir.pokerface.entity.card.Card;
import by.bsuir.pokerface.entity.card.Combination;
import by.bsuir.pokerface.entity.card.Deck;
import by.bsuir.pokerface.exception.ServiceException;

import java.util.List;

public interface CardService {
    void shuffle(Deck deck);

    Combination findBestCombination(List<Card> playerCards, List<Card> boardCards) throws ServiceException;
}
