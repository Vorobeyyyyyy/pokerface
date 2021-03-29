package by.bsuir.pokerface.service.impl;

import by.bsuir.pokerface.dao.RoomDao;
import by.bsuir.pokerface.dao.impl.RoomDaoImpl;
import by.bsuir.pokerface.entity.room.Room;
import by.bsuir.pokerface.entity.room.RoomNotifier;
import by.bsuir.pokerface.entity.user.Player;
import by.bsuir.pokerface.event.impl.*;
import by.bsuir.pokerface.exception.ServiceException;
import by.bsuir.pokerface.service.RoomService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class RoomServiceImpl implements RoomService {
    private final static Logger logger = LogManager.getLogger();
    private final static RoomDao ROOM_DAO = RoomDaoImpl.getInstance();

    @Override
    public Room createRoom(String name) throws ServiceException {
        Optional<Room> optionalRoom = ROOM_DAO.createRoom(name);
        if (optionalRoom.isPresent()) {
            return optionalRoom.get();
        } else {
            throw new ServiceException("Cant create room with name " + name);
        }
    }

    @Override
    public void enterRoom(int roomId, Player player) throws ServiceException {
        Room room = roomByIdOrThrow(roomId);
        if (room.getPlayers().contains(player)) {
            throw new ServiceException("Player " + player.getNickname() + " already in room " + roomId);
        }
        room.addPlayer(player);
        SseEmitter emitter = player.getEmitter();
        Runnable callback = () -> room.removePlayer(player);
        emitter.onCompletion(callback);
        emitter.onTimeout(callback);
        PlayerJoinEvent event = new PlayerJoinEvent(player.getNickname());
        RoomNotifier.notifyPlayers(room, event);
        logger.log(Level.INFO, "Player {} entered room {}", player.getNickname(), roomId);
    }

    @Override
    public void leaveRoom(int roomId, Player player) throws ServiceException {
        Room room = roomByIdOrThrow(roomId);
        if (room.getPlayers().contains(player)) {
            room.removePlayer(player);
            PlayerLeaveEvent event = new PlayerLeaveEvent(player.getNickname());
            RoomNotifier.notifyPlayers(room, event);
            logger.log(Level.INFO, "Player {} leave from room {}", player.getNickname(), roomId);
        }
    }

    @Override
    public void sitDown(int roomId, int chairId, Player player) throws ServiceException {
        Room room = roomByIdOrThrow(roomId);
        if (!room.getPlayers().contains(player)) {
            throw new ServiceException("Player " + player.getNickname() + " not in room " + roomId);
        }
        if (room.getSitedPlayers().contains(player)) {
            throw new ServiceException("Player " + player.getNickname() + "already sited");
        }
        if (room.getSitedPlayers().get(chairId) != null) {
            throw new ServiceException("Chair " + chairId + " is busy in room " + roomId);
        }
        room.sitDown(player, chairId);
        player.setBank(1000);
        PlayerSitEvent event = new PlayerSitEvent(chairId, player.getNickname(), player.getBank());
        RoomNotifier.notifyPlayers(room, event);
        logger.log(Level.INFO, "Player {} sit on {} chair in {} room", player.getNickname(), chairId, roomId);
    }

    @Override
    public void getUp(int roomId, int chairId, Player player) throws ServiceException {
        Room room = roomByIdOrThrow(roomId);
        if (!room.getPlayers().contains(player)) {
            throw new ServiceException("Player " + player.getNickname() + " not in room " + roomId);
        }
        if (room.getSitedPlayers().get(chairId) != player) {
            throw new ServiceException("Player " + player.getNickname() + " not sit in chair " + chairId);
        }
        room.getUp(chairId);
        PlayerGetUpEvent event = new PlayerGetUpEvent(player.getNickname(), chairId);
        RoomNotifier.notifyPlayers(room, event);
    }

    @Override
    public void startGame(int roomId) throws ServiceException {
        Room room = roomByIdOrThrow(roomId);
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(room.getExecutor(), 0, 1, TimeUnit.SECONDS);
        StartGameEvent event = new StartGameEvent();
        RoomNotifier.notifyPlayers(room, event);
    }


    private Room roomByIdOrThrow(int roomId) throws ServiceException {
        Optional<Room> optionalRoom = ROOM_DAO.findRoomById(roomId);
        if (optionalRoom.isEmpty()) {
            throw new ServiceException("Room " + roomId + " not exists");
        }
        return  optionalRoom.get();
    }
}