package by.bsuir.pokerface.service.impl;

import by.bsuir.pokerface.dao.RoomDao;
import by.bsuir.pokerface.dao.impl.RoomDaoImpl;
import by.bsuir.pokerface.entity.room.Room;
import by.bsuir.pokerface.entity.user.Player;
import by.bsuir.pokerface.exception.ServiceException;
import by.bsuir.pokerface.service.GameService;
import by.bsuir.pokerface.service.RoomService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Objects;
import java.util.Optional;

public class GameServiceImpl implements GameService {
    private final static Logger logger = LogManager.getLogger();
    private final static RoomDao ROOM_DAO = RoomDaoImpl.getInstance();

    private static final GameService INSTANCE = new GameServiceImpl();
    private GameServiceImpl(){}

    public static GameService getInstance() {
        return INSTANCE;
    }

    @Override
    public void check(int roomId, Player player) throws ServiceException {
        Room room = roomByIdOrThrow(roomId);
        if (!isPlayerTurn(room, player)) {
            throw new ServiceException("Not turn of player " + player.getNickname());
        }
        if (room.getBet() != player.getBet()) {
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
        int maxRaise = room.getSitedPlayers().stream().filter(Objects::nonNull).filter(p -> !p.isFolded()).mapToInt(p -> p.getBet() + p.getBank()).min().orElse(0);
        logger.log(Level.INFO, "Max raise: {}", maxRaise);
        if (value > maxRaise) {
            throw new ServiceException("Cant raise, max raise is %s, but %s provided".formatted(maxRaise, value));
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
        int chair = room.getCurrentChair();
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
