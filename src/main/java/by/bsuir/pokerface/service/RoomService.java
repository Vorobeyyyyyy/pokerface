package by.bsuir.pokerface.service;

import by.bsuir.pokerface.entity.room.Room;
import by.bsuir.pokerface.entity.user.Player;
import by.bsuir.pokerface.event.impl.RoomStateEvent;
import by.bsuir.pokerface.exception.ServiceException;

public interface RoomService {
    default Room createRoom(String name) throws ServiceException {return null;}

    default void enterRoom(int roomId, Player player) throws ServiceException { }

    default void leaveRoom(int roomId, Player player) throws ServiceException { }

    default void sitDown(int roomId, int chairId, Player player) throws ServiceException { }

    default void getUp(int roomId, int chairId, Player player) throws ServiceException { }

    default void startGame(int roomId) throws ServiceException { }

    RoomStateEvent takeRoomStateEvent(int roomId, Player player) throws ServiceException;
}
