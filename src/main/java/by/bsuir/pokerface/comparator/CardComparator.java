package by.bsuir.pokerface.comparator;

import by.bsuir.pokerface.entity.card.Card;

import java.util.Comparator;

public enum CardComparator implements Comparator<Card> {
    LOW_TO_HIGH((Comparator.comparing(card -> card.getValue().getStrength()))),
    HIGH_TO_LOW(LOW_TO_HIGH.reversed());


    private final Comparator<Card> comparator;

    CardComparator(Comparator<Card> comparator) {
        this.comparator = comparator;
    }

    @Override
    public int compare(Card o1, Card o2) {
        return comparator.compare(o1, o2);
    }
}
