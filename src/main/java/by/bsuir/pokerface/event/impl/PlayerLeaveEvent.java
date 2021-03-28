package by.bsuir.pokerface.event.impl;

import by.bsuir.pokerface.event.AbstractGameEvent;
import by.bsuir.pokerface.event.EventName;

public class PlayerLeaveEvent extends AbstractGameEvent {
    public final String nickname;

    public PlayerLeaveEvent(String nickname) {
        event = EventName.PLAYER_LEAVE;
        this.nickname = nickname;
    }
}
