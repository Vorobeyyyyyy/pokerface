package by.bsuir.pokerface.event.impl;

import by.bsuir.pokerface.event.AbstractGameEvent;
import by.bsuir.pokerface.event.EventName;

public class PlayerWinEvent extends AbstractGameEvent {
    public int chairId;
    public int currentBank;

    public PlayerWinEvent(int chairId, int currentBank) {
        event = EventName.PLAYER_WIN;
        this.chairId = chairId;
        this.currentBank = currentBank;
    }
}
