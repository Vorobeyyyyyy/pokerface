package by.bsuir.pokerface.controller;

import by.bsuir.pokerface.entity.user.Player;
import by.bsuir.pokerface.exception.ServiceException;
import by.bsuir.pokerface.service.RoomService;
import by.bsuir.pokerface.service.impl.RoomServiceImpl;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/")
public class PageController {
    private final static Logger logger = LogManager.getLogger();
    private final static RoomService ROOM_SERVICE = new RoomServiceImpl(); //todo getInstance()

    @GetMapping("/main")
    public String getMainPage() {
        return "main.html";
    }

    @GetMapping("/test")
    public String test(){
        return "test/test.html";
    }

    @GetMapping("id{roomId}")
    public String enterRoom(@PathVariable(value = "roomId") int roomId, HttpSession session) {
        Player player = (Player) session.getAttribute(SessionAttributeName.PLAYER);
        try {
            ROOM_SERVICE.enterRoom(roomId, player);
            session.setAttribute(SessionAttributeName.ROOM_ID, roomId);
        } catch (ServiceException exception) {
            logger.log(Level.ERROR, exception);
        }
        return "test/test";
    }
}
