package by.bsuir.pokerface.entity.user;

import by.bsuir.pokerface.entity.card.Card;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

public class Player {
    private String nickname;
    private SseEmitter emitter;
    private Card[] cards;
    private boolean folded;
    private int bank;
    private int bet;

    public Player(String nickname, SseEmitter emitter) {
        this.nickname = nickname;
        this.emitter = emitter;
    }

    public Player(SseEmitter emitter) {
        this.emitter = emitter;
    }

    public SseEmitter getEmitter() {
        return emitter;
    }

    public void setEmitter(SseEmitter emitter) {
        this.emitter = emitter;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Card[] getCards() {
        return cards;
    }

    public void setCards(Card[] cards) {
        this.cards = cards;
    }

    public boolean isFolded() {
        return folded;
    }

    public void setFolded(boolean folded) {
        this.folded = folded;
    }

    public int getBank() {
        return bank;
    }

    public void setBank(int bank) {
        this.bank = bank;
    }


    public int getBet() {
        return bet;
    }

    public void setBet(int bet) {
        this.bet = bet;
    }
}
