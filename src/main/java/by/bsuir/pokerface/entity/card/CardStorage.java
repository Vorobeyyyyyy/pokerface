package by.bsuir.pokerface.entity.card;

import java.util.ArrayList;
import java.util.List;

public class CardStorage {
    private final static List<Card> CARDS = new ArrayList<>();
    static  {
        for (Suit suit : Suit.values()) {
            for (Value value : Value.values()) {
                CARDS.add(new Card(suit, value));
            }
        }
    }

    public static List<Card> getCards() {
        return List.copyOf(CARDS);
    }
}
