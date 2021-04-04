package by.bsuir.pokerface.factory.impl;

import by.bsuir.pokerface.comparator.CardComparator;
import by.bsuir.pokerface.entity.card.*;
import by.bsuir.pokerface.factory.CombinationFactory;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class StraightCombinationFactory implements CombinationFactory {
    private static final int NEEDED_PASS_COUNT = 4;

    @Override
    public Optional<Combination> createCombination(List<Card> cards) {
        int passCount = 0;
        List<Value> sortedValues = cards.stream().sorted(CardComparator.LOW_TO_HIGH).map(Card::getValue).distinct().collect(Collectors.toList());
        if (sortedValues.size() < CombinationConstant.CARD_COUNT) {
            return Optional.empty();
        }

        int strength = 0;
        for (int i = sortedValues.size() - 1; i > 0; i--) {
            if (sortedValues.get(i).ordinal() - 1 == sortedValues.get(i - 1).ordinal()) {
                passCount++;
                if (strength == 0) {
                    strength = sortedValues.get(i).getStrength();
                }
            } else {
                passCount = 0;
            }
            if (passCount == NEEDED_PASS_COUNT) {
                break;
            }
        }
        if (passCount == NEEDED_PASS_COUNT) {
            return Optional.of(new Combination(CombinationType.STRAIGHT, strength));
        } else {
            return Optional.empty();
        }
    }
}
