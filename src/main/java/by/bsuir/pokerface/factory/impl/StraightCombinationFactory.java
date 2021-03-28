package by.bsuir.pokerface.factory.impl;

import by.bsuir.pokerface.comparator.CardComparator;
import by.bsuir.pokerface.entity.card.Card;
import by.bsuir.pokerface.entity.card.Combination;
import by.bsuir.pokerface.entity.card.CombinationType;
import by.bsuir.pokerface.factory.CombinationFactory;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class StraightCombinationFactory implements CombinationFactory {
    private static final int NEEDED_PASS_COUNT = 4;

    @Override
    public Optional<Combination> createCombination(List<Card> cards) {
        int passCount = 0;
        List<Card> sortedCards = cards.stream().sorted(CardComparator.LOW_TO_HIGH).collect(Collectors.toList());
        int strength = 0;
        for (int i = sortedCards.size() - 1; i > 0; i--) {
            if (sortedCards.get(i).getValue().ordinal() - 1 == sortedCards.get(i - 1).getValue().ordinal()) {
                passCount++;
                if (strength == 0) {
                    strength = sortedCards.get(i).getValue().getStrength();
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
