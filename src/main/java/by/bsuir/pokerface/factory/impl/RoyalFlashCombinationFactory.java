package by.bsuir.pokerface.factory.impl;

import by.bsuir.pokerface.comparator.CardComparator;
import by.bsuir.pokerface.entity.card.*;
import by.bsuir.pokerface.factory.CombinationFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class RoyalFlashCombinationFactory implements CombinationFactory {
    @Override
    public Optional<Combination> createCombination(List<Card> cards) {
        List<Card> sameSuitCards = cards.stream()
                .collect(Collectors.groupingBy(Card::getSuit)).values().stream()
                .filter(list -> list.size() >= CombinationConstant.CARD_COUNT)
                .findFirst().orElse(new ArrayList<>());
        if (sameSuitCards.isEmpty()) {
            return Optional.empty();
        }
        sameSuitCards = sameSuitCards.stream().sorted(CardComparator.HIGH_TO_LOW).limit(CombinationConstant.CARD_COUNT).collect(Collectors.toList());
        if (sameSuitCards.get(0).getValue() == Value.ACE && sameSuitCards.get(4).getValue() == Value.TEN) {
            return Optional.of(new Combination(CombinationType.ROYAL_FLASH));
        }
        return Optional.empty();
    }
}
