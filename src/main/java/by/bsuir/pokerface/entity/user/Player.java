package by.bsuir.pokerface.entity.user;

import by.bsuir.pokerface.entity.card.Card;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.servlet.http.HttpSession;

public class Player extends User {
    private SseEmitter emitter;
    private HttpSession session;
    private Card[] cards;

    public Player(SseEmitter emitter, HttpSession session) {
        this.emitter = emitter;
        this.session = session;
    }

    public SseEmitter getEmitter() {
        return emitter;
    }

    public void setEmitter(SseEmitter emitter) {
        this.emitter = emitter;
    }

    public HttpSession getSession() {
        return session;
    }

    public void setSession(HttpSession session) {
        this.session = session;
    }

    public Card[] getCards() {
        return cards;
    }

    public void setCards(Card[] cards) {
        this.cards = cards;
    }
}
