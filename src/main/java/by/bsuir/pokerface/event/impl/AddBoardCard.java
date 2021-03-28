package by.bsuir.pokerface.event.impl;

import by.bsuir.pokerface.event.AbstractGameEvent;
import by.bsuir.pokerface.event.EventName;

public class AddBoardCard extends AbstractGameEvent {
    public String card;

    public AddBoardCard(String card) {
        event = EventName.ADD_BOARD_CARD;
        this.card = card;
    }
}
