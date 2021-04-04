package by.bsuir.pokerface.event.impl;

import by.bsuir.pokerface.event.AbstractGameEvent;
import by.bsuir.pokerface.event.EventName;

public class ClearBoardCardsEvent extends AbstractGameEvent {
    public ClearBoardCardsEvent() {
        event = EventName.CLEAR_BOARD_CARDS;
    }
}
