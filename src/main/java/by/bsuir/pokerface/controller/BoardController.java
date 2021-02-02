package by.bsuir.pokerface.controller;

import by.bsuir.pokerface.event.TestEvent;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
@RequestMapping("/board/")
public class BoardController {


    @GetMapping("socket/{boardId}")
    public SseEmitter takeSseEmitter(@PathVariable(value = "boardId") String id, HttpSession session) {
        SseEmitter emitter = new SseEmitter(60 * 1000L);
        System.out.println("SSSSSS!");
        new Thread(() -> {
            while (true) {
                TestEvent event = new TestEvent();
                event.body = "шрек";
                event.key = "2";
                try {
                    emitter.send(event);
                    System.out.println("send!");
                } catch (IOException exception) {
                    //TODO: Чё то сделать надо тут
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException exception) {
                    exception.printStackTrace();
                }
            }
        }).start();
        return emitter;
    }
}
