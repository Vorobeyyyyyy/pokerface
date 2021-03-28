package by.bsuir.pokerface.event.impl;

import by.bsuir.pokerface.event.AbstractGameEvent;
import by.bsuir.pokerface.event.EventName;

public class PlayerSitEvent extends AbstractGameEvent {
    public final int chair;
    public final String nickname;
    public final int wallet;

    public PlayerSitEvent(int chair, String nickname, int wallet) {
        event = EventName.PLAYER_SIT_DOWN;
        this.chair = chair;
        this.nickname = nickname;
        this.wallet = wallet;
    }
}
