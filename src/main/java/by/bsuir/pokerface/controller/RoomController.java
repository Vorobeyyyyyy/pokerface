package by.bsuir.pokerface.controller;

import by.bsuir.pokerface.entity.room.Room;
import by.bsuir.pokerface.entity.user.Player;
import by.bsuir.pokerface.exception.ControllerException;
import by.bsuir.pokerface.exception.ServiceException;
import by.bsuir.pokerface.request.impl.CreateRoomRequest;
import by.bsuir.pokerface.request.impl.SitDownRequest;
import by.bsuir.pokerface.request.impl.StartGameRequest;
import by.bsuir.pokerface.responce.AbstractResponse;
import by.bsuir.pokerface.responce.CreateRoomResponse;
import by.bsuir.pokerface.responce.ResponseWithStatus;
import by.bsuir.pokerface.service.RoomService;
import by.bsuir.pokerface.service.impl.RoomServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/room/")
public class RoomController {
    private final static Logger logger = LogManager.getLogger();
    private final static RoomService ROOM_SERVICE = new RoomServiceImpl(); //todo getInstance()

    @GetMapping("getEmitter")
    public ResponseEntity<SseEmitter> takeSseEmitter(HttpSession session) {
        Player player = (Player) session.getAttribute(SessionAttributeName.PLAYER);
        if (player == null) {
            return ResponseEntity.badRequest().build();
        }
        logger.log(Level.INFO, "Player {} get his emitter ({})", player.getNickname(), player.getEmitter());
        return ResponseEntity.ok(player.getEmitter());
    }

    @PostMapping(value = "create")
    @ResponseBody
    public ResponseEntity<CreateRoomResponse> createRoom(@RequestBody CreateRoomRequest request) throws ControllerException {
        Room room;
        try {
            room = ROOM_SERVICE.createRoom(request.name);
        } catch (ServiceException exception) {
            logger.log(Level.ERROR, exception.getMessage());
            throw new ControllerException(exception);
        }
        CreateRoomResponse createRoomResponse = new CreateRoomResponse();
        createRoomResponse.id = room.getId();
        logger.log(Level.INFO, "NEW ROOM {}", room);
        return ResponseEntity.ok(createRoomResponse);
    }

    @PostMapping(value = "sit")
    @ResponseBody
    public AbstractResponse sitDown(@RequestBody SitDownRequest request, HttpSession session) throws ControllerException {
        Integer roomId = (Integer) session.getAttribute(SessionAttributeName.ROOM_ID);
        Player player = (Player) session.getAttribute(SessionAttributeName.PLAYER);
        try {
            ROOM_SERVICE.sitDown(roomId, request.chairId, player);
        } catch (ServiceException exception) {
            logger.log(Level.ERROR, exception);
            throw new ControllerException(exception);
        }
        return new ResponseWithStatus("ok");
    }

    @PostMapping(value = "start")
    @ResponseBody
    public AbstractResponse startRoom(@RequestBody StartGameRequest request) throws ControllerException {
        try {
            ROOM_SERVICE.startGame(request.roomId);
        } catch (ServiceException exception) {
            throw new ControllerException(exception);
        }
        return new ResponseWithStatus("ok");
    }
}
