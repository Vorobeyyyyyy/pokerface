package by.bsuir.pokerface.factory.impl;

import by.bsuir.pokerface.comparator.CardComparator;
import by.bsuir.pokerface.entity.card.Card;
import by.bsuir.pokerface.entity.card.Combination;
import by.bsuir.pokerface.entity.card.CombinationConstant;
import by.bsuir.pokerface.entity.card.CombinationType;
import by.bsuir.pokerface.factory.CombinationFactory;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class FlushCombinationFactory implements CombinationFactory {

    @Override
    public Optional<Combination> createCombination(List<Card> cards) {
        List<Card> sameSuitCards = cards.stream().filter(card -> cards.stream().filter(card1 -> card.getSuit() == card1.getSuit()).count() >= CombinationConstant.CARD_COUNT)
                .collect(Collectors.toList());
        if (sameSuitCards.isEmpty()) {
            return Optional.empty();
        } else {
            int strength = sameSuitCards.stream().sorted(CardComparator.HIGH_TO_LOW).limit(CombinationConstant.CARD_COUNT).mapToInt(c -> c.getValue().getStrength()).sum();
            return Optional.of(new Combination(CombinationType.FLUSH, strength));
        }

    }
}
