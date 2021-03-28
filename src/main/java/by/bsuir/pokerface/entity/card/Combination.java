package by.bsuir.pokerface.entity.card;

public class Combination {
    private CombinationType type;
    private int strength;

    public Combination() {
        this.type = CombinationType.HIGH_CARD;
        this.strength = 0;
    }

    public Combination(CombinationType type) {
        this.type = type;
        this.strength = 0;
    }

    public Combination(CombinationType type, int strength) {
        this.type = type;
        this.strength = strength;
    }

    public CombinationType getType() {
        return type;
    }

    public void setType(CombinationType type) {
        this.type = type;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }
}
