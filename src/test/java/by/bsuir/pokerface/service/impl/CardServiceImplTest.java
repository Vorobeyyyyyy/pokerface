package by.bsuir.pokerface.service.impl;

import by.bsuir.pokerface.entity.card.Card;
import by.bsuir.pokerface.entity.card.Combination;
import by.bsuir.pokerface.entity.card.Suit;
import by.bsuir.pokerface.entity.card.Value;
import by.bsuir.pokerface.entity.room.Room;
import by.bsuir.pokerface.entity.user.Player;
import by.bsuir.pokerface.exception.ServiceException;
import by.bsuir.pokerface.service.CardService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.spi.mapper.JacksonMappingProvider;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.*;

public class CardServiceImplTest {
    private final CardService cardService = CardServiceImpl.getInstance();

    @Test
    public void testFindBestCombination() throws JsonProcessingException {
        Room room = new Room("SSd");
        ObjectMapper mapper = new ObjectMapper();
        System.out.println(mapper.writeValueAsString(room));
        List<Card> cards = List.of(
                new Card(Suit.CLUB, Value.ACE),
                new Card(Suit.DIAMOND, Value.ACE),
                new Card(Suit.HEART, Value.FIVE),
                new Card(Suit.SPADE, Value.KING),
                new Card(Suit.CLUB, Value.KING),
                new Card(Suit.SPADE, Value.NINE),
                new Card(Suit.CLUB, Value.QUEEN)
        );
        System.out.println(findBestCombination(cards));
    }

    Combination findBestCombination(List<Card> cards) {
        List<Card> playerCards = cards.subList(0, 2);
        List<Card> boardCards = cards.subList(2, 7);
        try {
            return cardService.findBestCombination(playerCards, boardCards);
        } catch (ServiceException exception) {
            return new Combination();
        }
    }
}