package by.bsuir.pokerface.event.impl;

import by.bsuir.pokerface.event.AbstractGameEvent;
import by.bsuir.pokerface.event.EventName;

public class PlayerRaiseEvent extends AbstractGameEvent {
    public int chairId;
    public int value;
    public int currentBank;

    public PlayerRaiseEvent(int chairId, int value, int currentBank) {
        event = EventName.PLAYER_RAISE;
        this.chairId = chairId;
        this.value = value;
        this.currentBank = currentBank;
    }
}
