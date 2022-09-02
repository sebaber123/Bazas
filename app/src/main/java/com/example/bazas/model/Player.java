package com.example.bazas.model;

import java.io.Serializable;
import java.util.ArrayList;

public class Player implements Serializable {
    private String name;
    private ArrayList<PlayerRound> playerRounds = new ArrayList<PlayerRound>();

    //constructor
    public Player(String name) {
        setName(name);
    }

    //constructor
    public Player() {
    }

    //getter
    public String getName() {
        return name;
    }

    //setter
    public void setName(String name) {
        this.name = name;
    }

    //getter
    public ArrayList<PlayerRound> getPlayerRounds() {
        return playerRounds;
    }

    //add a PlayerRound to the arrayList of PlayerRound
    public void addPlayerRound(PlayerRound playerRound){
        playerRounds.add(playerRound);
    }

    //setter
    public void setPlayerRounds(int size) {
        for (int i = 0; i < size; i++) {
            addPlayerRound(new PlayerRound());
        }
    }

    //check if a player can bet 0 depending if the zero limit is enable
    public boolean canBetZero(int actual_round, int quantityOfLimitZeroBets) {

        boolean booleanToReturn = false;

        //check if in the last N (quantityOfLimitZeroBets) rounds the player has bet only 0
        for (int i = actual_round-quantityOfLimitZeroBets; i < actual_round; i++) {
            if (getPlayerRounds().get(i).getBet() != 0){
                booleanToReturn = true;
            }
        }

        return booleanToReturn;

    }

    //calculate the points of a player in a round and return it
    public int getTotalPointsToRound(int pointsPerDone, int pointsIfDone, int pointsPerDoneIfNotAchiveTheBet, int totalRounds){

        int total = 0;

        for (int i = 0; i < totalRounds; i++) {

            //if the player load how much dones has done
            if(playerRounds.get(i).isDoneLoaded()){

                //if the player achieve the quantity of dones that the player has bet
                if (playerRounds.get(i).getBet()==playerRounds.get(i).getDone()){
                    total +=  (pointsIfDone + playerRounds.get(i).getBet() * pointsPerDone);

                //else
                }else{
                    total += (playerRounds.get(i).getDone() * pointsPerDoneIfNotAchiveTheBet);
                }
            }
        }

        return total;
    }

    //check if the player has more points than other player
    //return 1 if the player2 has more points or -1 if the player1 has more points
    public int hasMorePointsThan(Player player2, int pointsPerDone, int pointsIfDone, int pointsPerDoneIfNotAchiveTheBet) {

        int valueToReturn = 0;

        //player 1 has more points
        if (getTotalPointsToRound(pointsPerDone, pointsIfDone, pointsPerDoneIfNotAchiveTheBet, getPlayerRounds().size()) >= player2.getTotalPointsToRound(pointsPerDone, pointsIfDone, pointsPerDoneIfNotAchiveTheBet, getPlayerRounds().size())){
            valueToReturn = -1;

        //player 2 has more points
        }else{
            valueToReturn = 1;
        }

        return valueToReturn;

    }

}
