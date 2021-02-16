package by.bsuir.pokerface.command.impl;

import by.bsuir.pokerface.command.GameCommand;
import by.bsuir.pokerface.dao.RoomDao;
import by.bsuir.pokerface.dao.impl.RoomDaoImpl;
import by.bsuir.pokerface.entity.room.Room;
import by.bsuir.pokerface.entity.room.RoomStateStorage;
import by.bsuir.pokerface.exception.ControllerException;
import by.bsuir.pokerface.responce.AbstractResponse;
import by.bsuir.pokerface.util.SessionAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

public class StartCommand implements GameCommand {
    RoomDao roomDao = RoomDaoImpl.getInstance();

    @Override
    public AbstractResponse perform(HttpServletRequest request) throws ControllerException {
        HttpSession session = request.getSession();
        Integer roomId = (Integer) session.getAttribute(SessionAttribute.ROOM_ID);
        Optional<Room> optionalRoom = roomDao.findRoomById(roomId);
        if (optionalRoom.isEmpty()) {
            throw new ControllerException("This room does not exists!");
        }
        Room room = optionalRoom.get();
        if (room.getRoomState() != RoomStateStorage.WAITING) {
            throw new ControllerException("Game is already started!");
        }
        room.setRoomState(room.getRoomState().nextState());
        return new AbstractResponse() {};
    }
}
