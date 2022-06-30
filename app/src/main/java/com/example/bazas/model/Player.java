package com.example.bazas.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Player implements Serializable {
    private String name;
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

    public int getTotalPointsToRound(int pointsPerDone, int pointsIfDone, int pointsPerDoneIfNotAchiveTheBet, int totalRounds){

        int total = 0;

        for (int i = 0; i < totalRounds; i++) {

            if(playerRounds.get(i).isDoneLoaded()){
                if (playerRounds.get(i).getBet()==playerRounds.get(i).getDone()){
                    total +=  (pointsIfDone + playerRounds.get(i).getBet() * pointsPerDone);
                }else{
                    total += (playerRounds.get(i).getDone() * pointsPerDoneIfNotAchiveTheBet);
                }
            }
        }

        return total;
    }

    public int hasMorePointsThan(Player player2, int pointsPerDone, int pointsIfDone, int pointsPerDoneIfNotAchiveTheBet) {

        int valueToReturn = 0;

        if (getTotalPointsToRound(pointsPerDone, pointsIfDone, pointsPerDoneIfNotAchiveTheBet, getPlayerRounds().size()) >= player2.getTotalPointsToRound(pointsPerDone, pointsIfDone, pointsPerDoneIfNotAchiveTheBet, getPlayerRounds().size())){
            valueToReturn = -1;
        }else{
            valueToReturn = 1;
        }

        return valueToReturn;

    }

}
