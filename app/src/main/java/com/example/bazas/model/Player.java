package com.example.bazas.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Player implements Serializable {
    private String name;
    private int total_points;
    private ArrayList<PlayerRound> playerRounds = new ArrayList<PlayerRound>();

    public Player(String name) {
        setName(name);
    }

    public Player() {
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTotal_points() {
        return total_points;
    }

    public void setTotal_points(int total_points) {
        this.total_points = total_points;
    }

    public ArrayList<PlayerRound> getPlayerRounds() {
        return playerRounds;
    }

    public void addPlayerRound(PlayerRound playerRound){
        playerRounds.add(playerRound);
    }

    public void setPlayerRounds(int size) {
        for (int i = 0; i < size; i++) {
            addPlayerRound(new PlayerRound());
        }
    }

    public boolean canBetZero(int actual_round, int quantityOfLimitZeroBets) {
        boolean booleanToReturn = false;

        for (int i = actual_round-quantityOfLimitZeroBets; i < actual_round; i++) {
            if (getPlayerRounds().get(i).getBet() != 0){
                booleanToReturn = true;
            }
        }


        return booleanToReturn;

    }
}
