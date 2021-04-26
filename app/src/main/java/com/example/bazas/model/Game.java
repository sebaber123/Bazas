package com.example.bazas.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Game implements Serializable {
    private int quantity_of_rounds;
    private int actual_round = 0;
    private int deck = 0;//0 no seleccionado aun, 40 baraja española sin 8 y 9 , 48 baraja española con 8 y 9, 52 mazo de poker
    private ArrayList<Player> players = new ArrayList<Player>();
    private ArrayList<Round> rounds = new ArrayList<Round>();
    private Date startDate = Calendar.getInstance().getTime();


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

    public int positionOfFirstPlayerOfTheRound() {
        return Math.abs((getActual_round())%(getPlayers().size()));
    }
}
