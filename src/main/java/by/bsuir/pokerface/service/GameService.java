package by.bsuir.pokerface.service;

import by.bsuir.pokerface.entity.user.Player;
import by.bsuir.pokerface.exception.ServiceException;

public interface GameService {
    default void check(int roomId, Player player) throws ServiceException {
    }

    default void call(int roomId, Player player) throws ServiceException {
    }

    default void raise(int roomId, Player player, int value) throws ServiceException {
    }

    default void fold(int roomId, Player player) throws ServiceException {
    }
}
