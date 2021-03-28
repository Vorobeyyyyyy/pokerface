package by.bsuir.pokerface.factory;

import by.bsuir.pokerface.entity.card.Card;
import by.bsuir.pokerface.entity.card.Combination;

import java.util.List;
import java.util.Optional;

public interface CombinationFactory {
    Optional<Combination> createCombination(List<Card> cards);
}
