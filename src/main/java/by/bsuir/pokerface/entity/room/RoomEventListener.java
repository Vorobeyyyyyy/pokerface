package by.bsuir.pokerface.entity.room;

import by.bsuir.pokerface.event.AbstractGameEvent;

public interface RoomEventListener {
    void handleSinglePlayerEvent(Room room, int chairId, AbstractGameEvent event);

    void handleRoomEvent(Room room, AbstractGameEvent event);
}
