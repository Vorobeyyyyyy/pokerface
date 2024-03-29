package by.bsuir.pokerface.controller;

import by.bsuir.pokerface.entity.room.Room;
import by.bsuir.pokerface.entity.user.Player;
import by.bsuir.pokerface.event.AbstractGameEvent;
import by.bsuir.pokerface.event.impl.RoomStateEvent;
import by.bsuir.pokerface.exception.ControllerException;
import by.bsuir.pokerface.exception.ServiceException;
import by.bsuir.pokerface.request.impl.CreateRoomRequest;
import by.bsuir.pokerface.request.impl.EnterRoomRequest;
import by.bsuir.pokerface.request.impl.SitDownRequest;
import by.bsuir.pokerface.request.impl.StartGameRequest;
import by.bsuir.pokerface.responce.AbstractResponse;
import by.bsuir.pokerface.responce.CreateRoomResponse;
import by.bsuir.pokerface.responce.ResponseWithStatus;
import by.bsuir.pokerface.service.RoomService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/room/")
public class RoomController {
    private final static Logger logger = LogManager.getLogger();
    private RoomService roomService;

    @Autowired
    public void setRoomService(RoomService roomService) {
        this.roomService = roomService;
    }

    @PostMapping(value = "create")
    @ResponseBody
    public ResponseEntity<CreateRoomResponse> createRoom(@RequestBody CreateRoomRequest request) throws ControllerException {
        Room room;
        try {
            room = roomService.createRoom(request.name);
        } catch (ServiceException exception) {
            logger.log(Level.ERROR, exception.getMessage());
            throw new ControllerException(exception);
        }
        CreateRoomResponse createRoomResponse = new CreateRoomResponse();
        createRoomResponse.id = room.getId();
        logger.log(Level.INFO, "NEW ROOM {}", room);
        return ResponseEntity.ok(createRoomResponse);
    }

    @PostMapping(value = "enter")
    @ResponseBody
    public ResponseEntity<AbstractGameEvent> enterRoom(@RequestBody EnterRoomRequest request, HttpSession session) {
        int roomId = request.roomId;
        Player player = (Player) session.getAttribute(SessionAttributeName.PLAYER);
        logger.log(Level.INFO, "Player {} entering room {}", player.getNickname(), roomId);
        RoomStateEvent event;
        try {
            roomService.enterRoom(roomId, player);
            event = roomService.takeRoomStateEvent(roomId, player);
            session.setAttribute(SessionAttributeName.ROOM_ID, roomId);
        } catch (ServiceException exception) {
            logger.log(Level.ERROR, exception.getMessage());
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(event);
    }

    @PostMapping(value = "sit")
    @ResponseBody
    public AbstractResponse sitDown(@RequestBody SitDownRequest request, HttpSession session) throws ControllerException {
        Integer roomId = (Integer) session.getAttribute(SessionAttributeName.ROOM_ID);
        Player player = (Player) session.getAttribute(SessionAttributeName.PLAYER);
        try {
            roomService.sitDown(roomId, request.chairId, player);
        } catch (ServiceException exception) {
            logger.log(Level.ERROR, exception.getMessage());
            throw new ControllerException(exception);
        }
        return new ResponseWithStatus("ok");
    }

    @PostMapping(value = "getup")
    @ResponseBody
    public ResponseEntity<AbstractGameEvent> getUp(HttpSession session) throws ControllerException {
        Integer roomId = (Integer) session.getAttribute(SessionAttributeName.ROOM_ID);
        Player player = (Player) session.getAttribute(SessionAttributeName.PLAYER);
        if (roomId == null || player == null) {
            return ResponseEntity.badRequest().build();
        }
        try {
            roomService.getUp(roomId, player);
        } catch (ServiceException exception) {
            logger.log(Level.ERROR, exception.getMessage());
            throw new ControllerException(exception);
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "start")
    @ResponseBody
    public AbstractResponse startRoom(@RequestBody StartGameRequest request) throws ControllerException {
        try {
            roomService.startGame(request.roomId);
        } catch (ServiceException exception) {
            throw new ControllerException(exception);
        }
        return new ResponseWithStatus("ok");
    }
}
