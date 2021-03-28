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

public class ThreeOfKindCombinationFactory implements CombinationFactory {
    private static final int DISTINCT_CARD_COUNT = 5;
    private static final int KICKER_COUNT = 2;
    private static final int CARD_FREQUENCY = 3;

    @Override
    public Optional<Combination> createCombination(List<Card> cards) {
        if (cards.stream().distinct().count() != DISTINCT_CARD_COUNT) {
            return Optional.empty();
        }
        int strength = 0;
        Optional<Card> optionalTripleCard = cards.stream().filter(c -> Collections.frequency(cards, c) == CARD_FREQUENCY).findFirst();
        if (optionalTripleCard.isEmpty()) {
            return Optional.empty();
        }
        Card tripleCard = optionalTripleCard.get();
        strength += tripleCard.getValue().getStrength() * CombinationConstant.COMBINATION_STRENGTH;
        strength += cards.stream().filter(c -> !c.equals(tripleCard)).sorted(CardComparator.HIGH_TO_LOW).limit(KICKER_COUNT).mapToInt(c -> c.getValue().getStrength()).sum();
        return Optional.of(new Combination(CombinationType.THREE_OF_A_KIND, strength));
    }
}
