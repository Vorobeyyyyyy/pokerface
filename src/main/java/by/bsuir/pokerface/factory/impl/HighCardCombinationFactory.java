package by.bsuir.pokerface.factory.impl;

import by.bsuir.pokerface.comparator.CardComparator;
import by.bsuir.pokerface.entity.card.Card;
import by.bsuir.pokerface.entity.card.Combination;
import by.bsuir.pokerface.entity.card.CombinationConstant;
import by.bsuir.pokerface.entity.card.CombinationType;
import by.bsuir.pokerface.factory.CombinationFactory;

import java.util.List;
import java.util.Optional;

public class HighCardCombinationFactory implements CombinationFactory {
    @Override
    public Optional<Combination> createCombination(List<Card> cards) {
        int strength = cards.stream().sorted(CardComparator.HIGH_TO_LOW).limit(CombinationConstant.CARD_COUNT).mapToInt(card -> card.getValue().getStrength()).sum();
        return Optional.of(new Combination(CombinationType.HIGH_CARD, strength));
    }
}
