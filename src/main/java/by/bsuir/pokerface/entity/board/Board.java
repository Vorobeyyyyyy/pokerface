package by.bsuir.pokerface.entity.board;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Board {
    private int id;

    private final List<SseEmitter> emitters = new ArrayList<>();

    private final List<Chair> chairs;

    public Board(int chairCount) {
        chairs = Arrays.asList(new Chair[chairCount]);
    }

    public void addEmitter(SseEmitter emitter) {
        emitter.onCompletion(() -> emitters.remove(emitter));
        emitter.onTimeout(() -> emitters.remove(emitter));
        emitters.add(emitter);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
