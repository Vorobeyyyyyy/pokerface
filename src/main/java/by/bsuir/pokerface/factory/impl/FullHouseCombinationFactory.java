package by.bsuir.pokerface.factory.impl;

import by.bsuir.pokerface.comparator.CardComparator;
import by.bsuir.pokerface.entity.card.Card;
import by.bsuir.pokerface.entity.card.Combination;
import by.bsuir.pokerface.entity.card.CombinationConstant;
import by.bsuir.pokerface.entity.card.CombinationType;
import by.bsuir.pokerface.factory.CombinationFactory;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class FullHouseCombinationFactory implements CombinationFactory {
    private static final int DOUBLE_CARD_FREQUENCY = 2;
    private static final int TRIPLE_CARD_FREQUENCY = 3;

    @Override
    public Optional<Combination> createCombination(List<Card> cards) {
        Optional<Card> tripleCard = cards.stream().distinct().filter(card -> Collections.frequency(cards, card) == TRIPLE_CARD_FREQUENCY).findFirst();
        List<Card> doubleCards = cards.stream().distinct().filter(card -> Collections.frequency(cards, card) == DOUBLE_CARD_FREQUENCY).collect(Collectors.toList());
        if (tripleCard.isEmpty() || doubleCards.isEmpty()) {
            return Optional.empty();
        }
        int strength = tripleCard.get().getValue().getStrength() * CombinationConstant.COMBINATION_STRENGTH +
                doubleCards.stream().max(CardComparator.HIGH_TO_LOW).get().getValue().getStrength();
        return Optional.of(new Combination(CombinationType.FULL_HOUSE, strength));
    }
}
