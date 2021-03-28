package by.bsuir.pokerface.factory.impl;

import by.bsuir.pokerface.entity.card.Card;
import by.bsuir.pokerface.entity.card.Combination;
import by.bsuir.pokerface.factory.CombinationFactory;

import java.util.List;
import java.util.Optional;

public class RoyalFlashCombinationFactory implements CombinationFactory {
    @Override
    public Optional<Combination> createCombination(List<Card> cards) {

        return Optional.empty();
    }
}
