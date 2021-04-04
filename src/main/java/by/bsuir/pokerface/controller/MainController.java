package by.bsuir.pokerface.controller;

import by.bsuir.pokerface.entity.user.Player;
import by.bsuir.pokerface.event.impl.PlayerFoldEvent;
import by.bsuir.pokerface.event.impl.RoomPotEvent;
import by.bsuir.pokerface.request.impl.LoginRequest;
import by.bsuir.pokerface.service.WebSocketService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/user")
public class MainController {
    private final static Logger logger = LogManager.getLogger();

    @Autowired
    private WebSocketService webSocketService;

    @Deprecated
    @PostMapping("/login")
    public void login(@RequestBody LoginRequest request, HttpSession session) {
        Player player = new Player(request.nickname);
        session.setAttribute(SessionAttributeName.PLAYER, player);
        logger.log(Level.INFO, player.getNickname());
    }

    @PostMapping("/test")
    public void a() {
        webSocketService.notifyPlayers(1, new RoomPotEvent(20));
        webSocketService.notifySinglePlayer(1, 1, new PlayerFoldEvent(1, 2));
    }

    @PostMapping("/getLogin")
    public String getLogin(HttpSession session) {
        Player player = (Player)session.getAttribute(SessionAttributeName.PLAYER);
        logger.log(Level.INFO, player.getNickname());
        return player.getNickname();
    }
}
