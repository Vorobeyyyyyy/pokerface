package by.bsuir.pokerface.entity.room;

import by.bsuir.pokerface.entity.user.Player;
import by.bsuir.pokerface.event.AbstractGameEvent;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;

public class RoomNotifier {
    private static final Logger logger = LogManager.getLogger();

    private RoomNotifier() {
    }

    public static void notifyPlayers(Room room, AbstractGameEvent event) {
        room.getPlayers().forEach(player -> {
            SseEmitter emitter = player.getEmitter();
            try {
                emitter.send(event);
                logger.log(Level.DEBUG, "Send {} to {} in room {}", event, player.getNickname(), room.getId());
            } catch (IOException exception) {
                logger.log(Level.ERROR, "Error when sending {} to {} in room {}", event.getClass(), player.getNickname(), room.getId());
                room.removePlayer(player);
            }
        });
    }

    public static void notifySinglePlayer(Room room, int chairId, AbstractGameEvent event) {
        List<Player> playerList = room.getSitedPlayers();
        Player player = playerList.get(chairId);
        if (player == null) {
            logger.log(Level.WARN, "In chair {} of room {} player is NULL", chairId, room);
            return;
        }
        SseEmitter emitter = player.getEmitter();
        try {
            emitter.send(event);
            logger.log(Level.DEBUG, "Send {} to {} in room {}", event, player.getNickname(), room.getId());
        } catch (IOException exception) {
            logger.log(Level.ERROR, "Error when sending {} to {} in room {}", event.getClass(), player.getNickname(), room.getId());
            room.removePlayer(player);
        }
    }

    public static void notifySinglePlayer(Player player, AbstractGameEvent event) {
        SseEmitter emitter = player.getEmitter();
        try {
            emitter.send(event);
            logger.log(Level.DEBUG, "Send {} to {}", event, player.getNickname());
        } catch (IOException exception) {
            logger.log(Level.ERROR, "Error when sending {} to {}", event.getClass().toString(), player.getNickname());
        }
    }
}
