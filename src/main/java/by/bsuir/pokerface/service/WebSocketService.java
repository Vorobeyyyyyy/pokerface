package by.bsuir.pokerface.service;

import by.bsuir.pokerface.entity.room.Room;
import by.bsuir.pokerface.event.AbstractGameEvent;

public interface WebSocketService {
    void notifyPlayers(Room room, AbstractGameEvent event);

    void notifySinglePlayer(Room room, int chairId, AbstractGameEvent event);

    void notifyPlayers(int roomId, AbstractGameEvent event);

    void notifySinglePlayer(int roomId, int chairId, AbstractGameEvent event);
}
