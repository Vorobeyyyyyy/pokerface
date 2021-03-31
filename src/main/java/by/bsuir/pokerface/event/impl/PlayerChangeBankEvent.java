package by.bsuir.pokerface.event.impl;

import by.bsuir.pokerface.event.AbstractGameEvent;

public class PlayerChangeBankEvent extends AbstractGameEvent {
    public int chairId;
    public int bet;
    public int currentBank;

    public PlayerChangeBankEvent(int chairId, int bet, int currentBank) {
        this.chairId = chairId;
        this.bet = bet;
        this.currentBank = currentBank;
    }
}
