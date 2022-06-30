package com.example.bazas.model;

import java.io.Serializable;

public class PlayerRound implements Serializable {
    private int bet;
    private int done;
    private boolean betLoaded = false;
    private boolean doneLoaded = false;

    public int getBet() {
        return bet;
    }

    public void setBet(int bet) {

        this.bet = bet;

        if (isDoneLoaded()){
            setDoneLoaded(false);
        }
    }

    public int getDone() {
        return done;
    }

    public void setDone(int done) {
        this.done = done;
    }

    public boolean isLoaded() {
        return betLoaded;
    }

    public void setLoaded(boolean loaded) {
        this.betLoaded = loaded;
    }

    public boolean isDoneLoaded() {
        return doneLoaded;
    }

    public void setDoneLoaded(boolean doneLoaded) {
        this.doneLoaded = doneLoaded;
    }

}
