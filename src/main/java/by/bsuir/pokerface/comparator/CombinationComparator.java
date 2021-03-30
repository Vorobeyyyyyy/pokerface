package by.bsuir.pokerface.comparator;

import by.bsuir.pokerface.entity.card.Combination;

import java.util.Comparator;

public enum CombinationComparator implements Comparator<Combination> {
    NATURAL(Comparator.comparing(Combination::getType).thenComparing(Combination::getStrength));

    private final Comparator<Combination> comparator;

    @Override
    public int compare(Combination o1, Combination o2) {
        return comparator.compare(o1, o2);
    }

    CombinationComparator(Comparator<Combination> comparator) {
        this.comparator = comparator;
    }
}
