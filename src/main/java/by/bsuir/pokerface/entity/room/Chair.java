package by.bsuir.pokerface.entity.room;

import by.bsuir.pokerface.entity.user.User;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public class Chair {
    private User user;
    private SseEmitter sseEmitter;

    public Chair(User user, SseEmitter sseEmitter) {
        this.user = user;
        this.sseEmitter = sseEmitter;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
