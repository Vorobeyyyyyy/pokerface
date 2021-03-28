package by.bsuir.pokerface.service.impl;

import by.bsuir.pokerface.entity.card.Card;
import by.bsuir.pokerface.entity.card.Suit;
import by.bsuir.pokerface.entity.card.Value;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.*;

public class CardServiceImplTest {

    @Test
    public void testFindBestCombination() {
        List<Card> cards = List.of(
                new Card(Suit.CLUB, Value.KING),
                new Card(Suit.CLUB, Value.KING),
                new Card(Suit.CLUB, Value.KING),
                new Card(Suit.CLUB, Value.KING),
                new Card(Suit.CLUB, Value.KING),
                new Card(Suit.CLUB, Value.KING),
                new Card(Suit.CLUB, Value.KING)
        );

    }
}