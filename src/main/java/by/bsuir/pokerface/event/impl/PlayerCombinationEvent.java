package by.bsuir.pokerface.event.impl;

import by.bsuir.pokerface.entity.card.Combination;
import by.bsuir.pokerface.event.AbstractGameEvent;
import by.bsuir.pokerface.event.EventName;

public class PlayerCombinationEvent extends AbstractGameEvent {
    public int chairId;
    public Combination combination;

    public PlayerCombinationEvent(int chairId, Combination combination) {
        event = EventName.PLAYER_COMBINATION;
        this.chairId = chairId;
        this.combination = combination;
    }
}
