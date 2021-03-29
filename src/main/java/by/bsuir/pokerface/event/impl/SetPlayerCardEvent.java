package by.bsuir.pokerface.event.impl;

import by.bsuir.pokerface.entity.card.Card;
import by.bsuir.pokerface.event.AbstractGameEvent;
import by.bsuir.pokerface.event.EventName;

public class SetPlayerCardEvent extends AbstractGameEvent {
    public int chairId;
    public Card firstCard;
    public Card secondCard;

    public SetPlayerCardEvent(int chairId, Card firstCard, Card secondCard) {
        event = EventName.SET_CARDS;
        this.chairId = chairId;
        this.firstCard = firstCard;
        this.secondCard = secondCard;
    }
}
