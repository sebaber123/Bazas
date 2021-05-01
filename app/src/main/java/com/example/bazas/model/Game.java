package com.example.bazas.model;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.stream.Collectors;

public class Game implements Serializable {
    private int quantity_of_rounds;
    private int actual_round = 0;
    private int deck = 0;//0 no seleccionado aun, 40 baraja española sin 8 y 9 , 48 baraja española con 8 y 9, 52 mazo de poker
    private ArrayList<Player> players = new ArrayList<Player>();
    private ArrayList<Round> rounds = new ArrayList<Round>();
    private Date startDate = Calendar.getInstance().getTime();
    private boolean limitZeroBets = true;
    private int quantityOfLimitZeroBets = 3;


    public int getQuantity_of_rounds() {
        return quantity_of_rounds;
    }

    public void setQuantity_of_rounds(int quantity_of_rounds) {
        this.quantity_of_rounds = quantity_of_rounds;
    }

    public int getActual_round() {
        return actual_round;
    }

    public void setActual_round(int actual_round) {
        this.actual_round = actual_round;
    }

    public int getDeck() {
        return deck;
    }

    public void setDeck(int deck) {
        this.deck = deck;
    }

    public void addPlayer (String name){
        Player player = new Player(name);
        players.add(player);
    }

    public void addRound (Round round){
        rounds.add(round);
    }

    public ArrayList<Round> getRounds() {
        return rounds;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }


    public void deletePlayer(Player player) {
        players.remove(player);
    }

    public int getMaxQuantityOfCardPerRound() {
        return (( this.getDeck() - 1 )/ players.size());
    }

    public void deleteRound(Round round) {
        rounds.remove(round);
    }

    public void deleteAllRounds() {
        rounds.clear();
    }

    public boolean isLimitZeroBets() {
        return limitZeroBets;
    }

    public void setLimitZeroBets(boolean limitZeroBets) {
        this.limitZeroBets = limitZeroBets;
    }

    public int getQuantityOfLimitZeroBets() {
        return quantityOfLimitZeroBets;
    }

    public void setQuantityOfLimitZeroBets(int quantityOfLimitZeroBets) {
        this.quantityOfLimitZeroBets = quantityOfLimitZeroBets;
    }

    public int positionOfFirstPlayerOfTheRound() {
        return Math.abs((getActual_round())%(getPlayers().size()));
    }

    public int playerOfPositionOnActualRound(int position) {
        return Math.abs((position + getActual_round())%(getPlayers().size()));
    }

    public PlayerRound getPlayerRoundOfPositionOnActualRound(int position) {
        return getPlayers().get(playerOfPositionOnActualRound(position)).getPlayerRounds().get(actual_round);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public ArrayList<String> getQuantitiesThatCanBetThePlayer(int position){

        ArrayList<String> numbersThatCanBet = new ArrayList<String>();

        for (int i = 0; i <= getRounds().get(getActual_round()).getQuantity_of_cards() ; i++) {
            numbersThatCanBet.add(String.valueOf(i));
        }

        if(position == getPlayers().size()-1){

            int quantity_of_bets = getPlayers().stream()
                    .map(player -> player.getPlayerRounds().get(actual_round))
                    .filter(playerRound -> playerRound.isLoaded())
                    .map(playerRound -> playerRound.getBet())
                    .mapToInt(Integer::intValue)
                    .sum();

            if (getPlayers().get(playerOfPositionOnActualRound(position)).getPlayerRounds().get(getActual_round()).isLoaded()){
                quantity_of_bets -= getPlayers().get(playerOfPositionOnActualRound(position)).getPlayerRounds().get(getActual_round()).getBet();
            }

            if ((getRounds().get(getActual_round()).getQuantity_of_cards() - quantity_of_bets) >= 0){
                numbersThatCanBet.remove(String.valueOf(getRounds().get(getActual_round()).getQuantity_of_cards() - quantity_of_bets));
            }
        }

        if (isLimitZeroBets() && getActual_round() > getQuantityOfLimitZeroBets() && !players.get(playerOfPositionOnActualRound(position)).canBetZero(getActual_round(), getQuantityOfLimitZeroBets())){
            numbersThatCanBet.remove(String.valueOf(0));
        }

        if (numbersThatCanBet.size()==0){
            numbersThatCanBet.add("0");
        }



        return numbersThatCanBet;
    }


    public void StartGame() {
        for (Player player : players){
            player.setPlayerRounds(getRounds().size());
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public boolean allBetsLoaded() {
        return getPlayers().stream()
                .map(player -> player.getPlayerRounds().get(getActual_round()))
                .filter(playerRound -> playerRound.isLoaded())
                .collect(Collectors.toList())
                .size() == getPlayers().size();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public boolean lastPlayerCantBet(int position) {
        return getPlayers().stream()
                .map(player -> player.getPlayerRounds().get(getActual_round()))
                .filter(playerRound -> playerRound.isLoaded())
                .collect(Collectors.toList())
                .size() < getPlayers().size() - 1;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void checkThatTheLastBetIsAvailable() {
        if(     getPlayers().stream()
                    .map(player -> player.getPlayerRounds().get(getActual_round()))
                    .filter(playerRound -> playerRound.isLoaded())
                    .collect(Collectors.toList())
                    .size() == getPlayers().size()
                &&
                getPlayers().stream()
                    .map(player -> player.getPlayerRounds().get(actual_round))
                    .filter(playerRound -> playerRound.isLoaded())
                    .map(playerRound -> playerRound.getBet())
                    .mapToInt(Integer::intValue)
                    .sum() == rounds.get(getActual_round()).getQuantity_of_cards())
        {
            getPlayers().get(playerOfPositionOnActualRound(players.size()-1)).getPlayerRounds().get(getActual_round()).setLoaded(false);
            getPlayers().get(playerOfPositionOnActualRound(players.size()-1)).getPlayerRounds().get(getActual_round()).setBet(0);
        }
    }
}
