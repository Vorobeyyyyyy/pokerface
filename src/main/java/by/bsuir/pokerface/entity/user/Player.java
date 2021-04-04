package by.bsuir.pokerface.entity.user;

import by.bsuir.pokerface.entity.card.Card;
import by.bsuir.pokerface.entity.card.Combination;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@JsonIgnoreProperties({"cards", "makeTurn", "combination"})
public class Player {
    private String nickname;
    private Card[] cards;
    private boolean folded;
    private boolean makeTurn;
    private int bank;
    private int bet;
    private Combination combination;

    public Player(String nickname, boolean folded, int bank, int bet) {
        this.nickname = nickname;
        this.folded = folded;
        this.bank = bank;
        this.bet = bet;
    }

    public Player(String nickname) {
        this.nickname = nickname;
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

    public boolean isMakeTurn() {
        return makeTurn;
    }

    public void setMakeTurn(boolean makeTurn) {
        this.makeTurn = makeTurn;
    }

    public Combination getCombination() {
        return combination;
    }

    public void setCombination(Combination combination) {
        this.combination = combination;
    }
}
