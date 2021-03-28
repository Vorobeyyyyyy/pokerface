package by.bsuir.pokerface.entity.room;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public interface RoomState {
    Logger logger = LogManager.getLogger();
    default void onStart(Room room) {}

    default RoomState nextState() {
        return RoomStateStorage.WAITING;}
}
