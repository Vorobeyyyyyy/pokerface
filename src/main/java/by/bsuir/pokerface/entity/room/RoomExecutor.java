package by.bsuir.pokerface.entity.room;

import by.bsuir.pokerface.entity.card.Deck;
import by.bsuir.pokerface.entity.user.Player;
import by.bsuir.pokerface.event.impl.*;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Objects;

public class RoomExecutor implements Runnable {
    private static final Logger logger = LogManager.getLogger();
    private static final int INITIAL_TURN_TIME = 40;
    public static final int INITIAL_BLIND = 50;
    private Room room;
    private int currentButton;
    private int currentChair;
    private int lastRaised;
    private int bet = 0;
    private int pot = 0;
    private int currentBlind = INITIAL_BLIND;
    private int timeToTurn = INITIAL_TURN_TIME;
    private int pauseTime = 0;
    private boolean firstTurn = true;
    private Deck deck = new Deck();

    public RoomExecutor(Room room) {
        this.room = room;
    }

    @Override
    public void run() {
        if (pauseTime > 0) {
            pauseTime--;
            return;
        }

        if (room.getRoomState() == RoomStateStorage.WAITING) {
            nextState();
            pauseTime = 1;
            return;
        }

        if (nextChair() == currentChair) {
            win(room.getSitedPlayers().get(currentChair));
        }

        if (timeToTurn <= 0) {
            fold(room.getSitedPlayers().get(currentChair));
        }

        timeToTurn--;
        TurnTimeEvent event = new TurnTimeEvent(currentChair, timeToTurn);
        RoomNotifier.notifyPlayers(room, event);
    }

    public void fold(Player player) {
        player.setFolded(true);
        RoomNotifier.notifyPlayers(room, new PlayerFoldEvent(currentChair, player.getBank()));
        endTurn();
    }

    public void check(Player player) {
        if (bet != player.getBet()) {
            return;
        }
        RoomNotifier.notifyPlayers(room, new PlayerCheckEvent(currentChair, player.getBank()));
        endTurn();
    }

    public void call(Player player) {
        int playerBank = player.getBank();
        int callValue = bet - player.getBet();
        if (playerBank >= callValue) {
            player.setBank(playerBank - callValue);
            pot += callValue;
        }
        logger.log(Level.INFO, "Chair {} called", room.getSitedPlayers().indexOf(player));
        RoomNotifier.notifyPlayers(room, new PlayerCallEvent(currentChair, player.getBank()));
        RoomNotifier.notifyPlayers(room, new RoomPotEvent(pot));
        endTurn();
    }

    public void raise(Player player, int value) {
        int playerBank = player.getBank();
        value -= player.getBet();
        if (value <= bet || playerBank < value) {
            return;
        }

        bet = value;
        pot += value;
        player.setBet(player.getBet() + value);
        player.setBank(playerBank - value);
        if (!firstTurn) {
            lastRaised = currentChair;
        }
        room.getSitedPlayers().stream().filter(Objects::nonNull).forEach(p -> p.setMakeTurn(false));
        logger.log(Level.INFO, "Chair {} raised", room.getSitedPlayers().indexOf(player));
        RoomNotifier.notifyPlayers(room, new RoomPotEvent(pot));
        RoomNotifier.notifyPlayers(room, new PlayerRaiseEvent(currentChair, value, player.getBank()));
        endTurn();
    }

    private void endTurn() {
        if (!firstTurn) {
            room.getSitedPlayers().get(currentChair).setMakeTurn(true);
        } else {
            firstTurn = false;
        }
        currentChair = nextChair();
        logger.log(Level.INFO, "Current chair {}, firstTurn = {}, lastRaised = {}", currentChair, firstTurn, lastRaised);
        if (isAllMakeTurn()) {
            nextState();
        }
        timeToTurn = INITIAL_TURN_TIME;
    }

    private void nextState() {
        room.getSitedPlayers().stream().filter(Objects::nonNull).forEach(player -> player.setMakeTurn(false));
        firstTurn = true;
        currentChair = currentButton;
        bet = 0;
        lastRaised = -1;
        room.setRoomState(room.getRoomState().nextState());
        logger.log(Level.INFO, "In room {} new state {}", room.getId(), room.getRoomState());
        room.getRoomState().onStart(room);
    }

    public int nextChair() {
        int result = currentChair;
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

    private void win(Player player) {
        logger.log(Level.INFO, "In room {} WIN player {}", room.getId(), player.getNickname());
        player.setBank(player.getBank() + pot);
        RoomNotifier.notifyPlayers(room, new PlayerWinEvent(currentChair, player.getBank()));
        room.setRoomState(RoomStateStorage.WAITING);
        room.clearCards();
        pot = 0;
        room.getSitedPlayers().forEach(p -> {
            if (p != null) {
                p.setFolded(false);
            }
        });
    }

    private boolean isAllMakeTurn() {
        return room.getSitedPlayers().stream().filter(Objects::nonNull).allMatch(Player::isMakeTurn);
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public int getCurrentButton() {
        return currentButton;
    }

    public void setCurrentButton(int currentButton) {
        this.currentButton = currentButton;
    }

    public int getCurrentChair() {
        return currentChair;
    }

    public void setCurrentChair(int currentChair) {
        this.currentChair = currentChair;
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

    public int getPot() {
        return pot;
    }

    public void setPot(int pot) {
        this.pot = pot;
    }

    public int getBet() {
        return bet;
    }

    public void setBet(int bet) {
        this.bet = bet;
    }

    public Deck getDeck() {
        return deck;
    }

    public void setDeck(Deck deck) {
        this.deck = deck;
    }
}
