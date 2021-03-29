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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Combination that = (Combination) o;

        if (strength != that.strength) return false;
        return type == that.type;
    }

    @Override
    public int hashCode() {
        int result = type != null ? type.hashCode() : 0;
        result = 31 * result + strength;
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Combination{");
        sb.append("type=").append(type);
        sb.append(", strength=").append(strength);
        sb.append('}');
        return sb.toString();
    }
}
