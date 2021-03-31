package by.bsuir.pokerface.controller;

import by.bsuir.pokerface.entity.user.Player;
import by.bsuir.pokerface.exception.ControllerException;
import by.bsuir.pokerface.exception.ServiceException;
import by.bsuir.pokerface.request.impl.RaiseRequest;
import by.bsuir.pokerface.responce.AbstractResponse;
import by.bsuir.pokerface.responce.ResponseWithStatus;
import by.bsuir.pokerface.service.GameService;
import by.bsuir.pokerface.service.impl.GameServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/game/")
public class GameController {
    private final static Logger logger = LogManager.getLogger();
    private static final GameService GAME_SERVICE = GameServiceImpl.getInstance();

    @PostMapping("check")
    public AbstractResponse check(HttpSession session) throws ControllerException {
        Player player = (Player) session.getAttribute(SessionAttributeName.PLAYER);
        Integer roomId = (Integer) session.getAttribute(SessionAttributeName.ROOM_ID);
        try {
            GAME_SERVICE.check(roomId, player);
        } catch (ServiceException exception) {
            logger.log(Level.ERROR, exception);
            return new ResponseWithStatus(exception.getMessage());
        }
        return new ResponseWithStatus("ok");
    }

    @PostMapping("call")
    public AbstractResponse call(HttpSession session) throws ControllerException {
        Player player = (Player) session.getAttribute(SessionAttributeName.PLAYER);
        Integer roomId = (Integer) session.getAttribute(SessionAttributeName.ROOM_ID);
        try {
            GAME_SERVICE.call(roomId, player);
        } catch (ServiceException exception) {
            logger.log(Level.ERROR, exception.getMessage());
            return new ResponseWithStatus(exception.getMessage());
        }
        return new ResponseWithStatus("ok");
    }

    @PostMapping("raise")
    public AbstractResponse raise(@RequestBody RaiseRequest request, HttpSession session) throws ControllerException {
        Player player = (Player) session.getAttribute(SessionAttributeName.PLAYER);
        Integer roomId = (Integer) session.getAttribute(SessionAttributeName.ROOM_ID);
        try {
            GAME_SERVICE.raise(roomId, player, request.value);
        } catch (ServiceException exception) {
            logger.log(Level.ERROR, exception);
            return new ResponseWithStatus(exception.getMessage());
        }
        return new ResponseWithStatus("ok");
    }

    @PostMapping("fold")
    public AbstractResponse fold(HttpSession session) throws ControllerException {
        Player player = (Player) session.getAttribute(SessionAttributeName.PLAYER);
        Integer roomId = (Integer) session.getAttribute(SessionAttributeName.ROOM_ID);
        try {
            GAME_SERVICE.fold(roomId, player);
        } catch (ServiceException exception) {
            logger.log(Level.ERROR, exception);
            return new ResponseWithStatus(exception.getMessage());
        }
        return new ResponseWithStatus("ok");
    }
}
