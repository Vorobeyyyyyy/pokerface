package by.bsuir.pokerface.factory.impl;

import by.bsuir.pokerface.comparator.CardComparator;
import by.bsuir.pokerface.entity.card.*;
import by.bsuir.pokerface.factory.CombinationFactory;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class FullHouseCombinationFactory implements CombinationFactory {
    private static final int DOUBLE_CARD_FREQUENCY = 2;
    private static final int TRIPLE_CARD_FREQUENCY = 3;

    @Override
    public Optional<Combination> createCombination(List<Card> cards) { //fixme
        List<Value> values = cards.stream().map(Card::getValue).collect(Collectors.toList());
        Optional<Value> tripleValue = values.stream().distinct().filter(value -> Collections.frequency(values, value) == TRIPLE_CARD_FREQUENCY).findFirst();
        List<Value> doubleValues = values.stream().distinct().filter(value -> Collections.frequency(values, value) == DOUBLE_CARD_FREQUENCY).collect(Collectors.toList());
        if (tripleValue.isEmpty() || doubleValues.isEmpty()) {
            return Optional.empty();
        }
        int strength = tripleValue.get().getStrength() * CombinationConstant.COMBINATION_STRENGTH +
                doubleValues.stream().max(Comparator.naturalOrder()).get().getStrength();
        return Optional.of(new Combination(CombinationType.FULL_HOUSE, strength));
    }
}
