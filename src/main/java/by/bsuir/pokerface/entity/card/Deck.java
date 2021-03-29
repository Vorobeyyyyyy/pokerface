package by.bsuir.pokerface.entity.card;

import java.util.List;

public class Deck {
    private List<Card> cards;

    public Deck() {
        cards = CardStorage.getCards();
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public Card takeTopCard() {
        return cards.remove(cards.size() - 1);
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Deck deck = (Deck) o;

        return cards != null ? cards.equals(deck.cards) : deck.cards == null;
    }

    @Override
    public int hashCode() {
        return cards != null ? cards.hashCode() : 0;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Deck{");
        sb.append("cards=").append(cards);
        sb.append('}');
        return sb.toString();
    }
}
