package by.bsuir.pokerface.entity.room;

import by.bsuir.pokerface.entity.user.Player;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

public class Room {
    @Id
    @GeneratedValue
    private int id;

    private final String name;

    private final List<Player> players = new ArrayList<>();

    private RoomState roomState =  RoomStateStorage.WAITING;

    public Room(String name) {
        this.name = name;
    }

    public void addPlayer(HttpSession session, SseEmitter emitter) {
        Player player = new Player(emitter, session);
        emitter.onCompletion(() -> players.remove(player));
        emitter.onTimeout(() -> players.remove(player));
        players.add(player);
    }

    public List<Player> getPlayers() {
        return List.copyOf(players);
    }

    public RoomState getRoomState() {
        return roomState;
    }

    public void setRoomState(RoomState roomState) {
        roomState.end(this);
        this.roomState = roomState;
        roomState.start(this);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
}
