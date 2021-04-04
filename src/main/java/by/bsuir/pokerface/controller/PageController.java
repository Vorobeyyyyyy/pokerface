package by.bsuir.pokerface.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/")
public class PageController {
    private final static Logger logger = LogManager.getLogger();

    @GetMapping("/main")
    public String getMainPage() {
        return "index.html";
    }

    @GetMapping("/test")
    public String test(){
        return "test/test.html";
    }

    @GetMapping("id{roomId}")
    public String room(@PathVariable(value = "roomId") int roomId) {
        return "game.html";
    }
}
