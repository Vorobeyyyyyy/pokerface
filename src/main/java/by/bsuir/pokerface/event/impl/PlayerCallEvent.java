package by.bsuir.pokerface.event.impl;

import by.bsuir.pokerface.event.AbstractGameEvent;
import by.bsuir.pokerface.event.EventName;

public class PlayerCallEvent extends AbstractGameEvent {
    public int chairId;
    public int bet;
    public int currentBank;

    public PlayerCallEvent(int chairId, int bet, int currentBank) {
        event = EventName.PLAYER_CALL;
        this.chairId = chairId;
        this.bet = bet;
        this.currentBank = currentBank;
    }
}
