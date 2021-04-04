package by.bsuir.pokerface.entity.room.impl;

import by.bsuir.pokerface.comparator.CombinationComparator;
import by.bsuir.pokerface.entity.card.Combination;
import by.bsuir.pokerface.entity.room.Room;
import by.bsuir.pokerface.entity.room.RoomExecutor;
import by.bsuir.pokerface.entity.room.RoomState;
import by.bsuir.pokerface.entity.room.RoomStateStorage;
import by.bsuir.pokerface.entity.user.Player;
import by.bsuir.pokerface.event.impl.PlayerCombinationEvent;
import by.bsuir.pokerface.event.impl.RevealCardEvent;
import by.bsuir.pokerface.event.impl.SetPlayerCardEvent;
import by.bsuir.pokerface.exception.ServiceException;
import by.bsuir.pokerface.service.CardService;
import by.bsuir.pokerface.service.impl.CardServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class FinalState implements RoomState {
    private final static Logger logger = LogManager.getLogger();
    private final CardService cardService = CardServiceImpl.getInstance();
    private final static int AFTER_WIN_PAUSE = 8;

    @Override
    public void onStart(Room room) {
        RoomExecutor executor = room.getExecutor();
        List<Player> checkedPlayers = room.getSitedPlayers().stream().filter(Objects::nonNull).filter(player -> !player.isFolded()).collect(Collectors.toList());
        checkedPlayers.forEach(player -> {
            try {
                player.setCombination(cardService.findBestCombination(Arrays.asList(player.getCards()), room.getCards()));
            } catch (ServiceException exception) {
                logger.log(Level.ERROR, exception);
            }
        });
        Optional<Combination> optionalMaxCombination = checkedPlayers.stream().map(Player::getCombination).max(CombinationComparator.NATURAL);
        if (optionalMaxCombination.isEmpty()) {
            logger.log(Level.ERROR, "Max combination is empty");
            return;
        }
        checkedPlayers.forEach(player -> {
            int chair = executor.findChair(player);
            RevealCardEvent revealCardEvent = new RevealCardEvent(chair, player.getCards()[0], player.getCards()[1]);
            PlayerCombinationEvent playerCombinationEvent = new PlayerCombinationEvent(chair, player.getCombination());
            executor.notifyPlayers(revealCardEvent);
            executor.notifyPlayers(playerCombinationEvent);
        });
        Combination maxCombination = optionalMaxCombination.get();
        logger.log(Level.INFO, "Max combination is {}", maxCombination);
        List<Player> winPlayers = checkedPlayers.stream()
                .filter(player -> player.getCombination().equals(maxCombination)).collect(Collectors.toList());
        executor.win(winPlayers);
        executor.setPauseTime(AFTER_WIN_PAUSE);
        executor.resetRoom();
    }

    @Override
    public RoomState nextState() {
        return RoomStateStorage.WAITING;
    }
}
