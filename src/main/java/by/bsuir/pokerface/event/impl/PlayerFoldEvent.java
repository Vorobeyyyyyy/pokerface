package by.bsuir.pokerface.event.impl;

import by.bsuir.pokerface.event.AbstractGameEvent;
import by.bsuir.pokerface.event.EventName;

public class PlayerFoldEvent extends AbstractGameEvent {
    public int chairId;
    public int currentBank;

    public PlayerFoldEvent(int chairId, int currentBank) {
        event = EventName.PLAYER_FOLD;
        this.chairId = chairId;
        this.currentBank = currentBank;
    }
}
