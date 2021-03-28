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

public class TwoPairCombinationFactory implements CombinationFactory {
    private static final int DISTINCT_CARD_COUNT = 5;
    private static final int KICKER_COUNT = 1;
    private static final int CARD_FREQUENCY = 2;

    @Override
    public Optional<Combination> createCombination(List<Card> cards) {
        if (cards.stream().distinct().count() != DISTINCT_CARD_COUNT) {
            return Optional.empty();
        }
        int strength = 0;
        List<Card> listPairCards = cards.stream().filter(c -> Collections.frequency(cards, c) == CARD_FREQUENCY).collect(Collectors.toList());
        strength += listPairCards.stream().mapToInt(card -> card.getValue().getStrength() * CombinationConstant.COMBINATION_STRENGTH).sum();
        strength += cards.stream().filter(card -> !listPairCards.contains(card)).sorted(CardComparator.HIGH_TO_LOW).limit(KICKER_COUNT).mapToInt(c -> c.getValue().getStrength()).sum();
        return Optional.of(new Combination(CombinationType.TWO_PAIR, strength));
    }
}
