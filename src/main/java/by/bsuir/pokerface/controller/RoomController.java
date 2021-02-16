package by.bsuir.pokerface.controller;

import by.bsuir.pokerface.command.GameCommand;
import by.bsuir.pokerface.command.GameCommandProvider;
import by.bsuir.pokerface.dao.RoomDao;
import by.bsuir.pokerface.dao.impl.RoomDaoImpl;
import by.bsuir.pokerface.entity.room.Room;
import by.bsuir.pokerface.exception.ControllerException;
import by.bsuir.pokerface.request.CreateRoomRequest;
import by.bsuir.pokerface.responce.AbstractResponse;
import by.bsuir.pokerface.responce.CreateRoomResponse;
import by.bsuir.pokerface.responce.StartGameResponse;
import by.bsuir.pokerface.util.SessionAttribute;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
@RequestMapping("/room/")
public class RoomController {
    private final static RoomDao roomDao = RoomDaoImpl.getInstance();

    @GetMapping("getSocket/{boardId}")
    public SseEmitter takeSseEmitter(@PathVariable(value = "boardId") String id, HttpSession session) {
        SseEmitter emitter = new SseEmitter(60 * 1000L);

        return emitter;
    }

    @GetMapping("create")
    public CreateRoomResponse createRoom(@RequestBody CreateRoomRequest request) throws ControllerException {
        Optional<Room> optionalRoom = roomDao.createRoom(request.name);
        if (optionalRoom.isEmpty()) {
            throw new ControllerException();
        }
        Room room = optionalRoom.get();
        CreateRoomResponse createRoomResponse = new CreateRoomResponse();
        createRoomResponse.id = room.getId();
        return createRoomResponse;
    }

    @PostMapping("action-{actionId}")
    public AbstractResponse userGameAction(HttpServletRequest request, @PathVariable(value = "actionId") String commandId) throws ControllerException {
        Optional<GameCommand> optionalGameCommand = GameCommandProvider.defineCommand(commandId);
        if (optionalGameCommand.isEmpty()) {
            throw new ControllerException("No such command!");
        }
        GameCommand command = optionalGameCommand.get();
        return command.perform(request);
    }
}
