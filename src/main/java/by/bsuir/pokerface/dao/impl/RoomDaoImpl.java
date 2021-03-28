package by.bsuir.pokerface.dao.impl;

import by.bsuir.pokerface.dao.RoomDao;
import by.bsuir.pokerface.entity.room.Room;
import by.bsuir.pokerface.warehouse.RoomWarehouse;

import java.util.Comparator;
import java.util.Optional;

public class RoomDaoImpl implements RoomDao {
    private final static RoomWarehouse roomWarehouse = RoomWarehouse.getInstance();

    private final static RoomDaoImpl instance = new RoomDaoImpl();

    private RoomDaoImpl() {}

    public static RoomDaoImpl getInstance() {
        return instance;
    }

    @Override
    public Optional<Room> findRoomById(int id) {
        return roomWarehouse.stream().filter(o -> o.getId() == id).findFirst();
    }

    @Override
    public Optional<Room> createRoom(String name) {
        Optional<Room> optionalRoom;
        if (roomWarehouse.stream().noneMatch(o -> o.getName().equals(name))) {
            Room room = new Room(name);
            int id = roomWarehouse.stream().mapToInt(Room::getId).max().orElse(0) + 1;
            room.setId(id);
            roomWarehouse.add(room);
            optionalRoom = Optional.of(room);
        } else {
            optionalRoom = Optional.empty();
        }
        return optionalRoom;
    }
}
