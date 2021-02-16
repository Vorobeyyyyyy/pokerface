package by.bsuir.pokerface.entity.room;

public interface RoomState {
    default void action(Room room) {}

    default void start(Room room) {}

    default void end(Room room) {}

    default RoomState nextState() {return RoomStateStorage.WAITING;}
}
