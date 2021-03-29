package by.bsuir.pokerface.entity.room;

import by.bsuir.pokerface.entity.card.Card;
import by.bsuir.pokerface.entity.user.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Room {
    public final static Integer INVALID_ROOM_ID = -1;
    public final static int ROOM_SIZE = 8;
    private final static int CARD_COUNT = 8;

    private int id;
    private final String name;
    private final List<Player> players = new ArrayList<>();
    private final Player[] chairs = new Player[ROOM_SIZE];
    private final Card[] cards = new Card[CARD_COUNT];
    private RoomState roomState = RoomStateStorage.WAITING;
    private final RoomExecutor executor = new RoomExecutor(this);

    public Room(String name) {
        this.name = name;
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public void removePlayer(Player player) {
        players.remove(player);

    }

    public void sitDown(Player player, int chairId) {
        chairs[chairId] = player;
    }

    public void getUp(int chairId) {
        chairs[chairId] = null;
    }

    public List<Player> getSitedPlayers() {
        return Arrays.asList(chairs);
    }

    public List<Player> getPlayers() {
        return List.copyOf(players);
    }

    public RoomState getRoomState() {
        return roomState;
    }

    public void setRoomState(RoomState roomState) {
        this.roomState = roomState;
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

    public RoomExecutor getExecutor() {
        return executor;
    }

    public List<Card> getCards() {
        return Arrays.asList(cards);
    }

    public void addCard(Card card) {
        for (int i = 0; i < cards.length; i++) {
            if (cards[i] == null) {
                cards[i] = card;
            }
        }
    }

    public void clearCards() {
        Arrays.fill(cards, null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Room room = (Room) o;

        if (id != room.id) return false;
        if (!Objects.equals(name, room.name)) return false;
        if (!players.equals(room.players)) return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        if (!Arrays.equals(chairs, room.chairs)) return false;
        return Objects.equals(roomState, room.roomState);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + players.hashCode();
        result = 31 * result + Arrays.hashCode(chairs);
        result = 31 * result + (roomState != null ? roomState.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Room{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", players=").append(players);
        sb.append(", chairs=").append(Arrays.toString(chairs));
        sb.append(", roomState=").append(roomState);
        sb.append('}');
        return sb.toString();
    }
}
