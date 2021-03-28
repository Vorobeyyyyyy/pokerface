package by.bsuir.pokerface.event.impl;

import by.bsuir.pokerface.event.AbstractGameEvent;
import by.bsuir.pokerface.event.EventName;

public class StartGameEvent extends AbstractGameEvent {
    public StartGameEvent() {
        event = EventName.START_GAME;
    }
}
