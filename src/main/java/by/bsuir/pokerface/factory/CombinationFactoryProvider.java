package by.bsuir.pokerface.factory;

import by.bsuir.pokerface.entity.card.Card;
import by.bsuir.pokerface.entity.card.Combination;
import by.bsuir.pokerface.factory.impl.*;

import java.util.List;
import java.util.Optional;

public enum CombinationFactoryProvider implements CombinationFactory {
    ROYAL_FLASH(new RoyalFlashCombinationFactory()),
    STRAIGHT_FLASH(new StraightFlashCombinationFactory()),
    FOUR_OF_A_KIND(new FourOfKindCombinationFactory()),
    FULL_HOUSE(new FullHouseCombinationFactory()),
    FLUSH(new FlushCombinationFactory()),
    STRAIGHT(new StraightCombinationFactory()),
    THREE_OF_A_KIND(new ThreeOfKindCombinationFactory()),
    TWO_PAIR(new TwoPairCombinationFactory()),
    PAIR(new PairCombinationFactory()),
    HIGH_CARD(new HighCardCombinationFactory());

    private CombinationFactory combinationFactory;

    CombinationFactoryProvider(CombinationFactory combinationFactory) {
        this.combinationFactory = combinationFactory;
    }

    @Override
    public Optional<Combination> createCombination(List<Card> cards) {
        return combinationFactory.createCombination(cards);
    }
}
