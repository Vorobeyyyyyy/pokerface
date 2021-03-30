package by.bsuir.pokerface.factory.impl;

import by.bsuir.pokerface.comparator.CardComparator;
import by.bsuir.pokerface.entity.card.*;
import by.bsuir.pokerface.factory.CombinationFactory;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ThreeOfKindCombinationFactory implements CombinationFactory {
    private static final int DISTINCT_CARD_COUNT = 5;
    private static final int KICKER_COUNT = 2;
    private static final int CARD_FREQUENCY = 3;

    @Override
    public Optional<Combination> createCombination(List<Card> cards) {
        List<Value> values = cards.stream().map(Card::getValue).collect(Collectors.toList());
        if (values.stream().distinct().count() != DISTINCT_CARD_COUNT) {
            return Optional.empty();
        }
        int strength = 0;
        Optional<Value> optionalDoubleValue = values.stream().filter(value -> Collections.frequency(values, value) == CARD_FREQUENCY).findFirst();
        if (optionalDoubleValue.isEmpty()) {
            return Optional.empty();
        }
        Value doubleValue = optionalDoubleValue.get();
        strength += doubleValue.getStrength() * CombinationConstant.COMBINATION_STRENGTH;
        strength += values.stream().filter(v -> v != doubleValue).sorted(Comparator.comparing(Value::getStrength).reversed()).limit(KICKER_COUNT).mapToInt(Value::getStrength).sum();
        return Optional.of(new Combination(CombinationType.THREE_OF_A_KIND, strength));
    }
}
