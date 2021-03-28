package by.bsuir.pokerface.entity.card;

public enum Value {
    TWO(2),
    THREE(4),
    FOUR(8),
    FIVE(16),
    SIX(32),
    SEVEN(64),
    EIGHT(128),
    NINE(256),
    TEN(512),
    JACK(1024),
    QUEEN(2048),
    KING(4096),
    ACE(8192);

    private final int strength;

    Value(int strength) {
        this.strength = strength;
    }

    public int getStrength() {
        return strength;
    }
}
