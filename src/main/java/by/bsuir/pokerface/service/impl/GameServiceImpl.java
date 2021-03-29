package by.bsuir.pokerface.service.impl;

import by.bsuir.pokerface.dao.RoomDao;
import by.bsuir.pokerface.dao.impl.RoomDaoImpl;
import by.bsuir.pokerface.entity.room.Room;
import by.bsuir.pokerface.entity.room.RoomExecutor;
import by.bsuir.pokerface.entity.user.Player;
import by.bsuir.pokerface.exception.ServiceException;
import by.bsuir.pokerface.service.GameService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

public class GameServiceImpl implements GameService {
    private final static Logger logger = LogManager.getLogger();
    private final static RoomDao ROOM_DAO = RoomDaoImpl.getInstance();

    @Override
    public void check(int roomId, Player player) throws ServiceException {
        Room room = roomByIdOrThrow(roomId);
        if (!isPlayerTurn(room, player)) {
            throw new ServiceException("Not turn of player " + player.getNickname());
        }
        if (room.getExecutor().getBet() != player.getBet()) {
            throw new ServiceException("Cant check");
        }
        room.getExecutor().check(player);
    }

    @Override
    public void call(int roomId, Player player) throws ServiceException {
        Room room = roomByIdOrThrow(roomId);
        if (!isPlayerTurn(room, player)) {
            throw new ServiceException("Not turn of player " + player.getNickname());
        }
        room.getExecutor().call(player);
    }

    @Override
    public void raise(int roomId, Player player, int value) throws ServiceException {
        Room room = roomByIdOrThrow(roomId);
        if (!isPlayerTurn(room, player)) {
            throw new ServiceException("Not turn of player " + player.getNickname());
        }
        if (player.getBank() <= value) {
            throw new ServiceException("Not enough, player " + player.getNickname() + " have " + player.getBank() + ", and try to raise " + value);
        }
        room.getExecutor().raise(player, value);
    }

    @Override
    public void fold(int roomId, Player player) throws ServiceException {
        Room room = roomByIdOrThrow(roomId);
        if (!isPlayerTurn(room, player)) {
            throw new ServiceException("Not turn of player " + player.getNickname());
        }
        room.getExecutor().fold(player);
    }

    private boolean isPlayerTurn(Room room, Player player) {
        RoomExecutor executor = room.getExecutor();
        int chair = executor.getCurrentChair();
        return room.getSitedPlayers().get(chair) == player;
    }

    private Room roomByIdOrThrow(int roomId) throws ServiceException {
        Optional<Room> optionalRoom = ROOM_DAO.findRoomById(roomId);
        if (optionalRoom.isEmpty()) {
            throw new ServiceException("Room " + roomId + " not exists");
        }
        return optionalRoom.get();
    }
}
