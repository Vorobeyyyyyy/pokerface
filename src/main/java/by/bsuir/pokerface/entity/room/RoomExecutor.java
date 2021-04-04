package by.bsuir.pokerface.entity.room;

import by.bsuir.pokerface.entity.card.Deck;
import by.bsuir.pokerface.entity.user.Player;
import by.bsuir.pokerface.event.AbstractGameEvent;
import by.bsuir.pokerface.event.impl.*;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class RoomExecutor implements Runnable {
    private static final Logger logger = LogManager.getLogger();
    private static final int INITIAL_TURN_TIME = 40;
    public static final int INITIAL_BLIND = 50;
    private Room room;

    private int currentBlind = INITIAL_BLIND;
    private int timeToTurn = INITIAL_TURN_TIME;
    private int pauseTime = 0;
    private boolean blindTurn = true;
    private Deck deck = new Deck();

    private RoomEventListener roomEventListener;

    public void setRoomEventListener(RoomEventListener roomEventListener) {
        this.roomEventListener = roomEventListener;
    }

    public RoomExecutor(Room room) {
        this.room = room;
    }

    @Override
    public void run() {
        if (pauseTime > 0) {
            notifyPlayers(new TurnTimeEvent(-1, pauseTime));
            pauseTime--;
            return;
        }

        if (room.getRoomState() == RoomStateStorage.WAITING) {
            nextState();
            pauseTime = 1;
            return;
        }

        if (nextChair() == room.getCurrentChair()) {
            win(room.getSitedPlayers().get(room.getCurrentChair()));
            resetRoom();
        }

        if (timeToTurn <= 0) {
            Player player = room.getSitedPlayers().get(room.getCurrentChair());
            if (room.getBet() <= player.getBet()) {
                check(player);
            } else {
                fold(player);
            }
        }
        TurnTimeEvent event = new TurnTimeEvent(room.getCurrentChair(), timeToTurn);
        notifyPlayers(event);
        timeToTurn--;
    }

    public void fold(Player player) {
        player.setFolded(true);
        notifyPlayers(new PlayerFoldEvent(room.getCurrentChair(), player.getBank()));
        endTurn();
    }

    public void check(Player player) {
        if (room.getBet() != player.getBet()) {
            return;
        }
        notifyPlayers(new PlayerCheckEvent(room.getCurrentChair(), player.getBet(), player.getBank()));
        endTurn();
    }

    public void call(Player player) {
        int playerBank = player.getBank();
        int callValue = Integer.min(room.getBet() - player.getBet(), playerBank);
        if (playerBank >= callValue) {
            player.setBank(playerBank - callValue);
            player.setBet(player.getBet() + callValue);
            room.setPot(room.getPot() + callValue);
        }
        logger.log(Level.INFO, "Chair {} called {}", room.getSitedPlayers().indexOf(player), callValue);
        notifyPlayers(new PlayerCallEvent(room.getCurrentChair(), player.getBet(), player.getBank()));
        notifyPlayers(new RoomPotEvent(room.getPot()));
        endTurn();
    }

    public void raise(Player player, int value) {
        logger.info("{} raising", player.getNickname());
        int playerBank = player.getBank();
        int newRoomBet = value;
        value -= player.getBet();
        logger.log(Level.INFO, "value = {} bet = {}", value, room.getBet());
        if (value <= 0 || playerBank < value) {
            return;
        }

        room.setBet(newRoomBet);
        room.setPot(room.getPot() + value);
        player.setBet(player.getBet() + value);
        player.setBank(playerBank - value);
        room.getSitedPlayers().stream().filter(Objects::nonNull).forEach(p -> p.setMakeTurn(false));
        logger.log(Level.INFO, "Chair {} raised", room.getSitedPlayers().indexOf(player));
        notifyPlayers(new RoomPotEvent(room.getPot()));
        notifyPlayers(new PlayerRaiseEvent(room.getCurrentChair(), player.getBet(), player.getBank()));
        endTurn();
    }

    private void endTurn() {
        if (!blindTurn) {
            room.getSitedPlayers().get(room.getCurrentChair()).setMakeTurn(true);
        } else {
            blindTurn = false;
        }
        room.setCurrentChair(nextChair());
        MaxRaiseEvent maxRaiseEvent = new MaxRaiseEvent(room.getBet(), room.getSitedPlayers().stream().filter(Objects::nonNull).mapToInt(player -> player.getBet() + player.getBank()).min().orElse(room.getBet()));
        notifySinglePlayer(room.getCurrentChair(), maxRaiseEvent);
        logger.log(Level.INFO, "Current chair {}, firstTurn = {}", room.getCurrentChair(), blindTurn);
        if (isAllMakeTurn()) {
            nextState();
        }
        timeToTurn = INITIAL_TURN_TIME;
    }

    private void nextState() {
        room.getSitedPlayers().stream().filter(Objects::nonNull).forEach(player -> {
            player.setMakeTurn(false);
            player.setBet(0);
            PlayerCheckEvent playerCheckEvent = new PlayerCheckEvent(findChair(player), player.getBet(), player.getBank());
            notifyPlayers(playerCheckEvent);
        });
        blindTurn = true;
        room.setCurrentChair(room.getCurrentButton());
        if (room.getRoomState() != RoomStateStorage.WAITING) {
            room.setCurrentChair(nextChair());
            blindTurn = false;
        }
        room.setBet(0);
        room.setRoomState(room.getRoomState().nextState());
        logger.log(Level.INFO, "In room {} new state {}", room.getId(), room.getRoomState());
        room.getRoomState().onStart(room);
    }

    public int nextChair() {
        int result = room.getCurrentChair();
        List<Player> sitedPlayers = room.getSitedPlayers();
        do {
            if (++result >= Room.ROOM_SIZE) {
                result = 0;
            }
        } while (sitedPlayers.get(result) == null || sitedPlayers.get(result).isFolded());
        return result;
    }

    public int findChair(Player player) {
        return room.getSitedPlayers().indexOf(player);
    }

    public void win(List<Player> players) {
        int winValue = room.getPot() / players.size();
        logger.log(Level.INFO, "In room {} WIN player(s) {}", room.getId(), players.stream().map(Player::getNickname).collect(Collectors.toList()));
        players.forEach(player -> {
            player.setBank(player.getBank() + winValue);
            notifyPlayers(new PlayerWinEvent(findChair(player), player.getBank()));
        });
    }

    public void win(Player player) {
        logger.log(Level.INFO, "In room {} WIN player {}", room.getId(), player.getNickname());
        player.setBank(player.getBank() + room.getPot());
        notifyPlayers(new PlayerWinEvent(findChair(player), player.getBank()));
        room.setRoomState(RoomStateStorage.WAITING);
    }

    public void resetRoom() {
        room.setRoomState(RoomStateStorage.WAITING);
        room.getExecutor().notifyPlayers(new ClearBoardCardsEvent());
        room.setCurrentChair(-1);
        room.clearCards();
        room.setPot(0);
        room.getSitedPlayers().forEach(p -> {
            if (p != null) {
                p.setFolded(false);
            }
        });
    }

    private boolean isAllMakeTurn() {
        return room.getSitedPlayers().stream().filter(Objects::nonNull).allMatch(Player::isMakeTurn);
    }

    public void notifyPlayers(AbstractGameEvent event) {
        roomEventListener.handleRoomEvent(room, event);

    }

    public void notifySinglePlayer(int chairId, AbstractGameEvent event) {
        roomEventListener.handleSinglePlayerEvent(room, chairId, event);
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public int getCurrentBlind() {
        return currentBlind;
    }

    public void setCurrentBlind(int currentBlind) {
        this.currentBlind = currentBlind;
    }

    public int getTimeToTurn() {
        return timeToTurn;
    }

    public void setTimeToTurn(int timeToTurn) {
        this.timeToTurn = timeToTurn;
    }

    public int getPauseTime() {
        return pauseTime;
    }

    public void setPauseTime(int pauseTime) {
        this.pauseTime = pauseTime;
    }

    public Deck getDeck() {
        return deck;
    }

    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    public boolean isBlindTurn() {
        return blindTurn;
    }

    public void setBlindTurn(boolean blindTurn) {
        this.blindTurn = blindTurn;
    }
}
