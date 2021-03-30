package by.bsuir.pokerface.event.impl;

import by.bsuir.pokerface.event.AbstractGameEvent;
import by.bsuir.pokerface.event.EventName;

public class PlayerRaiseEvent extends AbstractGameEvent {
    public int chairId;
    public int bet;
    public int currentBank;

    public PlayerRaiseEvent(int chairId, int bet, int currentBank) {
        event = EventName.PLAYER_RAISE;
        this.chairId = chairId;
        this.bet = bet;
        this.currentBank = currentBank;
    }
}
