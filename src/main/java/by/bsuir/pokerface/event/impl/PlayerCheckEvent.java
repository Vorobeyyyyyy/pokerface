package by.bsuir.pokerface.event.impl;

import by.bsuir.pokerface.event.AbstractGameEvent;
import by.bsuir.pokerface.event.EventName;

public class PlayerCheckEvent extends AbstractGameEvent {
    public int chairId;
    public int currentBank;

    public PlayerCheckEvent(int chairId, int currentBank) {
        event = EventName.PLAYER_CHECK;
        this.chairId = chairId;
        this.currentBank = currentBank;
    }
}
