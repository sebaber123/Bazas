package com.example.bazas.model;

import java.util.ArrayList;

public class Player {
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
}
