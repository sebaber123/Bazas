package com.example.bazas.model;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
//import java.util.stream.Collectors;

public class Game implements Serializable {
    private int quantity_of_rounds;
    private int actual_round = 0;
    private int deck = 0;//0 no seleccionado aun, 40 baraja española sin 8 y 9 , 48 baraja española con 8 y 9, 52 mazo de poker
    private ArrayList<Player> players = new ArrayList<>();
    private ArrayList<Round> rounds = new ArrayList<>();
    private Date startDate = Calendar.getInstance().getTime();
    private boolean limitZeroBets = true;
    private int quantityOfLimitZeroBets = 3;
    private int pointsPerDone = 3;
    private int pointsIfDone = 10;
    private int pointsPerDoneIfNotAchieveTheBet = 1;
    private long id = 0;

    
    //getter
    public long getId() {
        return id;
    }

    //setter
    public void setId(long id) {
        this.id = id;
    }

    //getter
    public Date getStartDate() {
        return startDate;
    }

    //setter
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    //getter
    public int getQuantity_of_rounds() {
        return quantity_of_rounds;
    }

    //setter
    public void setQuantity_of_rounds(int quantity_of_rounds) {
        this.quantity_of_rounds = quantity_of_rounds;
    }

    //getter
    public int getActual_round() {
        return actual_round;
    }

    //setter
    public void setActual_round(int actual_round) {
        this.actual_round = actual_round;
    }

    //getter
    public int getDeck() {
        return deck;
    }

    //setter
    public void setDeck(int deck) {
        this.deck = deck;
    }

    //add a Player to the list of players of the game
    public void addPlayer (String name){
        Player player = new Player(name);
        players.add(player);
    }

    //add a Round to the list of rounds of the game
    public void addRound (Round round){
        rounds.add(round);
    }

    //getter
    public ArrayList<Round> getRounds() {
        return rounds;
    }

    //getter
    public ArrayList<Player> getPlayers() {
        return players;
    }

    //delete an specific player from the list of players of the game
    public void deletePlayer(Player player) {
        players.remove(player);
    }

    //getter
    public int getMaxQuantityOfCardPerRound() {
        return (( this.getDeck() - 1 )/ players.size());
    }

    //delete an specific round from the list of rounds of the game
    public void deleteRound(Round round) {
        rounds.remove(round);
    }

    //delete all the rounds from the list of rounds of the game
    public void deleteAllRounds() {
        rounds.clear();
    }

    //getter
    public boolean isLimitZeroBets() {
        return limitZeroBets;
    }

    //setter
    public void setLimitZeroBets(boolean limitZeroBets) {
        this.limitZeroBets = limitZeroBets;
    }

    //getter
    public int getQuantityOfLimitZeroBets() {
        return quantityOfLimitZeroBets;
    }

    //setter
    public void setQuantityOfLimitZeroBets(int quantityOfLimitZeroBets) {
        this.quantityOfLimitZeroBets = quantityOfLimitZeroBets;
    }

    //getter
    public int getPointsPerDone() {
        return pointsPerDone;
    }

    //setter
    public void setPointsPerDone(int pointsPerDone) {
        this.pointsPerDone = pointsPerDone;
    }

    //getter
    public int getPointsIfDone() {
        return pointsIfDone;
    }

    //setter
    public void setPointsIfDone(int pointsIfDone) {
        this.pointsIfDone = pointsIfDone;
    }

    //getter
    public int getPointsPerDoneIfNotAchieveTheBet() {
        return pointsPerDoneIfNotAchieveTheBet;
    }

    //setter
    public void setPointsPerDoneIfNotAchieveTheBet(int pointsPerDoneIfNotAchieveTheBet) {
        this.pointsPerDoneIfNotAchieveTheBet = pointsPerDoneIfNotAchieveTheBet;
    }

    //return the position of the first player of the round in the list of players of the game
    public int getPositionOfFirstPlayerOfTheRound() {
        return Math.abs((getActual_round())%(getPlayers().size()));
    }

    //get a position
    //return the position of the player in the round on the list of players of the game in the position that got as parameter
    public int getPositionOfPlayerOnActualRound(int position) {
        return Math.abs((position + getActual_round())%(getPlayers().size()));
    }

    //get a position
    //return the PlayerRound of the player in the round on the list of players of the game in the position that got as parameter
    public PlayerRound getPlayerRoundOfPositionOnActualRound(int position) {
        return getPlayers().get(getPositionOfPlayerOnActualRound(position)).getPlayerRounds().get(actual_round);
    }

    ////get a position
    //return a List of the quantities that the player of the position that got as parameter can bet
    @RequiresApi(api = Build.VERSION_CODES.N)
    public ArrayList<String> getQuantitiesThatCanBetThePlayer(int position){

        //Create the List
        ArrayList<String> numbersThatCanBet = new ArrayList<>();

        //fill the list with all the quantities that a player can bet (from 0 to the amount of cards of the Round)
        for (int i = 0; i <= getRounds().get(getActual_round()).getQuantity_of_cards() ; i++) {
            numbersThatCanBet.add(String.valueOf(i));
        }

        //if the player that is betting is the last player of the round is necessary to check how much he cannot bet
        //because the sum of the bets can't be equal to the amount of cards of the round otherwise exist the possibility that everyone win in the round
        if(position == getPlayers().size()-1){

            //calculate the sum of all the bets in the round
            int quantity_of_bets = getPlayers().stream()
                    .map(player -> player.getPlayerRounds().get(actual_round))
                    .filter(PlayerRound::isLoaded)
                    .map(PlayerRound::getBet)
                    .mapToInt(Integer::intValue)
                    .sum();

            //if this is the case that the user is editing the amount bet of the last player the amount that the last player of the round
            //bet must be subtracted from the sum of the bets
            if (getPlayers().get(getPositionOfPlayerOnActualRound(position)).getPlayerRounds().get(getActual_round()).isLoaded()){
                quantity_of_bets -= getPlayers().get(getPositionOfPlayerOnActualRound(position)).getPlayerRounds().get(getActual_round()).getBet();
            }

            //if the subtraction of the quantity of cards in the round and the sum of bets of the round is greater than or equal 0
            //the last player cant bet the result of that subtraction
            if ((getRounds().get(getActual_round()).getQuantity_of_cards() - quantity_of_bets) >= 0){

                //remove the result of that subtraction from the numbers that the player can bet
                numbersThatCanBet.remove(String.valueOf(getRounds().get(getActual_round()).getQuantity_of_cards() - quantity_of_bets));
            }
        }

        //check if the player can beat 0 (if there is a zero limit in the game)
        if (isLimitZeroBets() && getActual_round()+1 > getQuantityOfLimitZeroBets() && !players.get(getPositionOfPlayerOnActualRound(position)).canBetZero(getActual_round(), getQuantityOfLimitZeroBets())){

            //remove 0 from the numbers that the player can bet
            numbersThatCanBet.remove(String.valueOf(0));
        }

        //if there are no bets that the player can do the player can bet 0
        if (numbersThatCanBet.size()==0){
            numbersThatCanBet.add("0");
        }

        return numbersThatCanBet;
    }

    //create the PlayerRound objects for each player
    public void StartGame() {
        for (Player player : players){
            player.setPlayerRounds(getRounds().size());
        }
    }

    //check if all bets of the round are loaded and return the result
    @RequiresApi(api = Build.VERSION_CODES.N)
    public boolean areAllBetsLoaded() {
        return getPlayers().stream()
                .map(player -> player.getPlayerRounds().get(getActual_round()))
                .filter(PlayerRound::isLoaded).count() == getPlayers().size();
    }

    //check if the last player can bet (because the game need all the bets of each player to calculate how much the last player
    // can bet) and return the result
    @RequiresApi(api = Build.VERSION_CODES.N)
    public boolean lastPlayerCantBet(int position) {
        return getPlayers().stream()
                .map(player -> player.getPlayerRounds().get(getActual_round()))
                .filter(PlayerRound::isLoaded).count() < getPlayers().size() - 1;
    }

    //if a player changes his bet and the last player has bet check that the new sum of bets is not
    // equal to the quantity of cards in the round
    @RequiresApi(api = Build.VERSION_CODES.N)
    public boolean checkThatTheLastBetIsAvailable() {

        //if each player has bet and the quantity of bets is equal to the quantity of cards in the round
        return  getPlayers().stream()
                    .map(player -> player.getPlayerRounds().get(getActual_round()))
                    .filter(PlayerRound::isLoaded).count() == getPlayers().size()
                &&
                getPlayers().stream()
                    .map(player -> player.getPlayerRounds().get(actual_round))
                    .filter(PlayerRound::isLoaded)
                    .map(PlayerRound::getBet)
                    .mapToInt(Integer::intValue)
                    .sum() == rounds.get(getActual_round()).getQuantity_of_cards();

    }

    public void removeLastBet(){
        //change the last player bet to not loaded
        getPlayers().get(getPositionOfPlayerOnActualRound(players.size()-1)).getPlayerRounds().get(getActual_round()).setLoaded(false);
        getPlayers().get(getPositionOfPlayerOnActualRound(players.size()-1)).getPlayerRounds().get(getActual_round()).setBet(0);

    }

    //calculate the points of the a player in the round
    public int calculatePoints(Integer bet, Integer done) {
        if (bet==done){
            return  (getPointsIfDone() + bet * getPointsPerDone());
        }else{
            return (done * getPointsPerDoneIfNotAchieveTheBet());
        }
    }

    //get the position of a player in the round
    //return the possibles quantities that can have done the player
    //example: if the round has 5 cards and the first player has done 2 then the second player could have
    // done 0, 1, 2 or 3
    @RequiresApi(api = Build.VERSION_CODES.N)
    public ArrayList<String> getQuantitiesThatCanDoneThePlayer(int position) {

        //create the arraylist
        ArrayList<String> numbersThatCanDone = new ArrayList<>();

        //get max available quantity of dones
        int maxAvailableDones = getAvailableDones(position);

        //fill the array with the values from 0 to maxAvailableDones
        for (int i = 0; i <= maxAvailableDones; i++) {
            numbersThatCanDone.add(String.valueOf(i));
        }

        return numbersThatCanDone;
    }

    //sum the quantities of dones and return the subtraction of the quantity of cards of the round
    //and the quantity of dones loaded
    @RequiresApi(api = Build.VERSION_CODES.N)
    private int getAvailableDones(int position) {

        int quantityOfDones = 0;

        //sum all the dones loaded in the round
        for (int i = 0; i < getPlayers().size(); i++) {
            if (getPositionOfPlayerOnActualRound(position)!= i && getPlayers().get(i).getPlayerRounds().get(getActual_round()).isDoneLoaded()){
                quantityOfDones += getPlayers().get(i).getPlayerRounds().get(getActual_round()).getDone();
            }
        }

        //return the subtraction of the quantity of cards of the round and the quantity of dones loaded
        return ( getRounds().get(getActual_round()).getQuantity_of_cards() - quantityOfDones);
    }

    //check if all the dones are loaded and return a boolean with the result
    @RequiresApi(api = Build.VERSION_CODES.N)
    public boolean allDonesLoaded() {

                //if all the players has loaded his dones
        return ((getPlayers().stream()
                        .map(player -> player.getPlayerRounds().get(getActual_round()))
                        .filter(PlayerRound::isDoneLoaded).count() == getPlayers().size())

                &&

                //if the sum of dones is equal to the quantity of cards in the round
                (getPlayers().stream()
                        .map(player -> player.getPlayerRounds().get(getActual_round()))
                        .filter(PlayerRound::isDoneLoaded)
                        .map(PlayerRound::getDone)
                        .mapToInt(Integer::intValue)
                        .sum() == getRounds().get(getActual_round()).getQuantity_of_cards())


        );
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public int missingDones() {
        int dones = getPlayers().stream()
                        .map(player -> player.getPlayerRounds().get(getActual_round()))
                        .filter(PlayerRound::isDoneLoaded).map(playerRound -> playerRound.getDone()).reduce(0, (a, b) -> a + b);

        int quantityOfCards = rounds.get(getActual_round()).getQuantity_of_cards();

        return quantityOfCards - dones;
    }

    //advance the game to the next round
    public void nextRound() {
        setActual_round(getActual_round() + 1);
    }

    //check if the actual round is the last of the game
    public boolean isGameFinished() {
        return (getRounds().size() == getActual_round());
    }

    //return 1 if the player2 has more points or -1 if the player1 has more points
    public int whoHasMorePoints(Player player1, Player player2) {
        return player1.hasMorePointsThan(player2, pointsPerDone, pointsIfDone, pointsPerDoneIfNotAchieveTheBet);
    }

    //return the total points of a player
    public int getTotalPointOfPlayer (Player player){
        return player.getTotalPointsToRound(pointsPerDone, pointsIfDone, pointsPerDoneIfNotAchieveTheBet, player.getPlayerRounds().size());
    }

    //return the sum of points of a player until a specific round
    public int getTotalPointOfPlayerToRound(Player player, int round) {
        return player.getTotalPointsToRound(pointsPerDone, pointsIfDone, pointsPerDoneIfNotAchieveTheBet, round);
    }
}
