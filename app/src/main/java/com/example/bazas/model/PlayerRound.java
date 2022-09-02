package com.example.bazas.model;

import java.io.Serializable;

public class PlayerRound implements Serializable {
    private int bet;
    private int done;
    private boolean betLoaded = false;
    private boolean doneLoaded = false;

    //getter
    public int getBet() {
        return bet;
    }

    //setter
    public void setBet(int bet) {

        this.bet = bet;

        if (isDoneLoaded()){
            setDoneLoaded(false);
        }
    }

    //getter
    public int getDone() {
        return done;
    }

    //setter
    public void setDone(int done) {
        this.done = done;
    }

    //getter
    public boolean isLoaded() {
        return betLoaded;
    }

    //setter
    public void setLoaded(boolean loaded) {
        this.betLoaded = loaded;
    }

    //getter
    public boolean isDoneLoaded() {
        return doneLoaded;
    }

    //setter
    public void setDoneLoaded(boolean doneLoaded) {
        this.doneLoaded = doneLoaded;
    }

}
