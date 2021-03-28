package by.bsuir.pokerface.event.impl;

import by.bsuir.pokerface.event.AbstractGameEvent;
import by.bsuir.pokerface.event.EventName;

public class SetPlayerCardEvent extends AbstractGameEvent {
    public int chairId;
    public String firstCard;
    public String secondCard;

    public SetPlayerCardEvent(int chairId, String firstCard, String secondCard) {
        event = EventName.SET_CARDS;
        this.chairId = chairId;
        this.firstCard = firstCard;
        this.secondCard = secondCard;
    }
}
