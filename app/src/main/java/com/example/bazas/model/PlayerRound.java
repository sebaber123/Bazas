package com.example.bazas.model;

import java.io.Serializable;

public class PlayerRound implements Serializable {
    private int bet;
    private int done;

    public int getBet() {
        return bet;
    }

    public void setBet(int bet) {
        this.bet = bet;
    }

    public int getDone() {
        return done;
    }

    public void setDone(int done) {
        this.done = done;
    }
}
