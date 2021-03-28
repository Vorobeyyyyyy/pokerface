package by.bsuir.pokerface.factory.impl;

import by.bsuir.pokerface.entity.card.Card;
import by.bsuir.pokerface.entity.card.Combination;
import by.bsuir.pokerface.entity.card.CombinationConstant;
import by.bsuir.pokerface.entity.card.CombinationType;
import by.bsuir.pokerface.factory.CombinationFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class StraightFlashCombinationFactory implements CombinationFactory {
    private static final int ORDINAL_DIFFERENCE = 4;

    @Override
    public Optional<Combination> createCombination(List<Card> cards) {
        List<Card> sameSuitCards = cards.stream()
                .collect(Collectors.groupingBy(Card::getSuit)).values().stream()
                .filter(list -> list.size() >= CombinationConstant.CARD_COUNT)
                .findFirst().orElse(new ArrayList<>());
        if (sameSuitCards.isEmpty()) {
            return Optional.empty();
        }
        int strength = 0;
        for (int i = sameSuitCards.size() - 1; i - CombinationConstant.CARD_COUNT > 0; i--) {
            if (sameSuitCards.get(i).getValue().ordinal() - sameSuitCards.get(i - CombinationConstant.CARD_COUNT).getValue().ordinal() == ORDINAL_DIFFERENCE) {
                strength = sameSuitCards.get(i).getValue().getStrength();
                break;
            }
        }
        if (strength == 0) {
            return Optional.empty();
        } else {
            return Optional.of(new Combination(CombinationType.STRAIGHT_FLASH, strength));
        }
    }
}
