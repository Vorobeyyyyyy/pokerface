package by.bsuir.pokerface.event.impl;

import by.bsuir.pokerface.event.AbstractGameEvent;
import by.bsuir.pokerface.event.EventName;

public class PlayerJoinEvent extends AbstractGameEvent {
    public String nickname;

    public PlayerJoinEvent(String nickname) {
        event = EventName.PLAYER_JOIN;
        this.nickname = nickname;
    }
}
