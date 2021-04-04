package by.bsuir.pokerface.service.impl;

import by.bsuir.pokerface.dao.RoomDao;
import by.bsuir.pokerface.dao.impl.RoomDaoImpl;
import by.bsuir.pokerface.entity.room.Room;
import by.bsuir.pokerface.entity.room.RoomEventListener;
import by.bsuir.pokerface.entity.user.Player;
import by.bsuir.pokerface.event.AbstractGameEvent;
import by.bsuir.pokerface.event.impl.*;
import by.bsuir.pokerface.exception.ServiceException;
import by.bsuir.pokerface.service.RoomService;
import by.bsuir.pokerface.service.WebSocketService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Service
public class RoomServiceImpl implements RoomService {
    private final static Logger logger = LogManager.getLogger();
    private final static RoomDao ROOM_DAO = RoomDaoImpl.getInstance();
    private static final int MIN_PLAYERS_COUNT = 2;

    private WebSocketService webSocketService;

    @Autowired
    public void setWebSocketService(WebSocketService webSocketService) {
        this.webSocketService = webSocketService;
    }


    class WebSocketRoomEventListener implements RoomEventListener {
        @Override
        public void handleSinglePlayerEvent(Room room, int chairId, AbstractGameEvent event) {
            webSocketService.notifySinglePlayer(room, chairId, event);
        }

        @Override
        public void handleRoomEvent(Room room, AbstractGameEvent event) {
            webSocketService.notifyPlayers(room, event);
        }
    }
    private final WebSocketRoomEventListener webSocketRoomEventListener = new WebSocketRoomEventListener();

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
            logger.log(Level.WARN, "Player {} already in room {}", player.getNickname(), roomId);
            return;
        }
        room.addPlayer(player);
        PlayerJoinEvent playerJoinEvent = new PlayerJoinEvent(player.getNickname());
        webSocketService.notifyPlayers(room, playerJoinEvent);
        logger.log(Level.INFO, "Player {} entered room {}", player.getNickname(), roomId);
    }

    @Override
    public void leaveRoom(int roomId, Player player) throws ServiceException {
        Room room = roomByIdOrThrow(roomId);
        if (room.getPlayers().contains(player)) {
            room.removePlayer(player);
            PlayerLeaveEvent event = new PlayerLeaveEvent(player.getNickname());
            webSocketService.notifyPlayers(room, event);
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
            throw new ServiceException("Player " + player.getNickname() + " already sited");
        }
        if (room.getSitedPlayers().get(chairId) != null) {
            throw new ServiceException("Chair " + chairId + " is busy in room " + roomId);
        }
        room.sitDown(player, chairId);
        player.setBank(1000);
        PlayerSitEvent event = new PlayerSitEvent(chairId, player.getNickname(), player.getBank());
        webSocketService.notifyPlayers(room, event);
        logger.log(Level.INFO, "Player {} sit on {} chair in {} room", player.getNickname(), chairId, roomId);
    }

    @Override
    public void getUp(int roomId, Player player) throws ServiceException {
        Room room = roomByIdOrThrow(roomId);
        if (!room.getPlayers().contains(player)) {
            throw new ServiceException("Player " + player.getNickname() + " not in room " + roomId);
        }
        int chairId = room.getExecutor().findChair(player);
        if (chairId == -1) {
            throw new ServiceException("Player " + player.getNickname() + " not sit in chair");
        }
        room.getUp(chairId);
        PlayerGetUpEvent event = new PlayerGetUpEvent(chairId);
        webSocketService.notifyPlayers(room, event);
    }

    @Override
    public void startGame(int roomId) throws ServiceException {
        Room room = roomByIdOrThrow(roomId);
        if (room.getSitedPlayers().stream().filter(Objects::nonNull).count() < MIN_PLAYERS_COUNT) {
            throw new ServiceException("Not enough players to start the game");
        }
        room.getExecutor().setRoomEventListener(webSocketRoomEventListener);
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(room.getExecutor(), 0, 1, TimeUnit.SECONDS);
        StartGameEvent event = new StartGameEvent();
        webSocketService.notifyPlayers(room, event);
    }

    @Override
    public RoomStateEvent takeRoomStateEvent(int roomId, Player player) throws ServiceException {
        Room room = roomByIdOrThrow(roomId);
        return new RoomStateEvent(room, room.getExecutor().findChair(player), player.getCards());
    }

    private Room roomByIdOrThrow(int roomId) throws ServiceException {
        Optional<Room> optionalRoom = ROOM_DAO.findRoomById(roomId);
        if (optionalRoom.isEmpty()) {
            throw new ServiceException("Room " + roomId + " not exists");
        }
        return optionalRoom.get();
    }
}
