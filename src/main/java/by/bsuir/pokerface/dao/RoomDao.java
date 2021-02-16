package by.bsuir.pokerface.dao;

import by.bsuir.pokerface.entity.room.Room;

import java.util.Optional;

public interface RoomDao {
    Optional<Room> findRoomById(int id);

    Optional<Room> createRoom(String name);
}
