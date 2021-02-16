package by.bsuir.pokerface.entity.room.impl;

import by.bsuir.pokerface.entity.room.Room;
import by.bsuir.pokerface.entity.room.RoomState;
import by.bsuir.pokerface.entity.room.RoomStateStorage;
import by.bsuir.pokerface.entity.user.Player;
import by.bsuir.pokerface.event.SetPlayerCardEvent;

import java.io.IOException;
import java.util.List;

public class PreflopState implements RoomState {
    @Override
    public void action(Room room) {

    }

    @Override
    public void start(Room room) {
        List<Player> players = room.getPlayers();
        players.forEach(o -> {
            SetPlayerCardEvent event = new SetPlayerCardEvent();
            event.firstCard = "korolb bubna";
            event.secondCard = "tuz chirva";
            try {
                o.getEmitter().send(event);
            } catch (IOException e) {
                //TODO: hz poka chto
            }
        });
    }

    @Override
    public void end(Room room) {

    }

    @Override
    public RoomState nextState() {
        return RoomStateStorage.FLOP;
    }
}
