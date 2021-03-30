package by.bsuir.pokerface.controller;

import by.bsuir.pokerface.entity.user.Player;
import by.bsuir.pokerface.request.impl.LoginRequest;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.servlet.http.HttpSession;
import java.io.IOException;

@RestController
@RequestMapping("/user")
public class MainController {
    private final static Logger logger = LogManager.getLogger();

    @PostMapping("/login")
    public void login(@RequestBody LoginRequest request, HttpSession session) throws IOException {
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
        emitter.send("Ok");
        Player player = new Player(request.nickname, emitter);
        session.setAttribute(SessionAttributeName.PLAYER, player);
        logger.log(Level.INFO, player.getNickname());
    }

    @PostMapping("/getLogin")
    public String getLogin(HttpSession session) {
        Player player = (Player)session.getAttribute(SessionAttributeName.PLAYER);
        logger.log(Level.INFO, player.getNickname());
        return player.getNickname();
    }
}
