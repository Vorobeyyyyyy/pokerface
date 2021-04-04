package by.bsuir.pokerface.factory.impl;

import by.bsuir.pokerface.comparator.CardComparator;
import by.bsuir.pokerface.entity.card.*;
import by.bsuir.pokerface.factory.CombinationFactory;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TwoPairCombinationFactory implements CombinationFactory {
    private static final int DISTINCT_CARD_COUNT = 5;
    private static final int CARD_FREQUENCY = 2;

    @Override
    public Optional<Combination> createCombination(List<Card> cards) {
        List<Value> values = cards.stream().map(Card::getValue).collect(Collectors.toList());
        if (values.stream().distinct().count() != DISTINCT_CARD_COUNT) {
            return Optional.empty();
        }
        List<Value> pairValues = values.stream().filter(value -> Collections.frequency(values, value) == CARD_FREQUENCY).collect(Collectors.toList());
        int strength = pairValues.stream().mapToInt(value -> value.getStrength() * CombinationConstant.COMBINATION_STRENGTH).sum();
        strength += values.stream().filter(value -> !pairValues.contains(value)).mapToInt(Value::getStrength).max().orElse(0);
        return Optional.of(new Combination(CombinationType.TWO_PAIR, strength));
    }
}
