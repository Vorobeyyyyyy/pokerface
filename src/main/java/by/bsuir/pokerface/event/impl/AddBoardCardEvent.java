package by.bsuir.pokerface.event.impl;

import by.bsuir.pokerface.entity.card.Card;
import by.bsuir.pokerface.event.AbstractGameEvent;
import by.bsuir.pokerface.event.EventName;

public class AddBoardCardEvent extends AbstractGameEvent {
    public Card card;

    public AddBoardCardEvent(Card card) {
        event = EventName.ADD_BOARD_CARD;
        this.card = card;
    }
}
