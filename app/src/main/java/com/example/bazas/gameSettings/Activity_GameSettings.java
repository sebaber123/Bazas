package com.example.bazas.gameSettings;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.bazas.FragmentWithBackPress;
import com.example.bazas.R;
import com.example.bazas.model.Game;
import com.example.bazas.model.Player;
import com.example.bazas.model.Round;

import java.util.ArrayList;

public class Activity_GameSettings extends AppCompatActivity {

    //create the game
    public Game theGame = new Game();

    //construct the view
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //set the layout
        setContentView(R.layout.activity_game_settings);

        //set the fragment
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.home_fragment, new Fragment_Settings())
                .commit();
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.home_fragment);

    }

    //delete a player from the game
    public void deletePlayer(Player player) {
        theGame.deletePlayer(player);
    }

    //add a player to the game
    public void addPlayer(String name) {
        theGame.addPlayer(name);
    }

    //return the list of players
    public ArrayList<Player> getPlayers() {
        return theGame.getPlayers();
    }

    //return the game
    public Game getTheGame() {
        return theGame;
    }

    //handle the continue button depending on which fragment is the settings
    public void continueButton(Integer number) {

        //text view of the title of the layout
        TextView settings = findViewById(android.R.id.content).getRootView().findViewById(R.id.settings);
        Fragment fragment = null;

        //receive a number depending on the fragment that the configuration as parameter
        switch (number) {
            //advance from the fragment of the settings to the fragment of the players
            case 0:
                fragment = new Fragment_Players();

                settings.setText("Agregue los jugadores en el orden que quiera jugar");
                break;

            //advance from the fragment of the players to the fragment of the selection of the deck
            case 1:
                fragment = new Fragment_Select_deck();

                settings.setText("Seleccione el mazo de cartas");
                break;

            //advance from the fragment of the selection of the deck to the fragment of the rounds
            case 2:
                fragment = new Fragment_Set_Rounds();
                settings.setText("Agregue la cantidad de cartas por ronda");
                break;

            //advance from the fragment of the rounds to the view of the game finish the configuration and advance
            //to the game
            case 3:
                Intent intent = new Intent();
                intent.putExtra("theGame", theGame);
                setResult(2, intent);
                finish();
                break;
        }
        if (number != 3) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.home_fragment, fragment, null)
                    .commit();
        }
    }

    //onBackPress behaviour (handle by the fragment)
    @Override public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.home_fragment);
        ((FragmentWithBackPress) fragment).onBackPressed();
    }

    public void backPressed(Integer number) {

        //text view of the title of the layout
        TextView settings = findViewById(android.R.id.content).getRootView().findViewById(R.id.settings);

        //receive a number depending on the fragment that the configuration as parameter
        switch (number){

            //return from the settings fragment to the starting screen
            case 0:
                Intent intent = new Intent();
                setResult(1, intent);
                finish();
                break;

            //return from the players fragment fragment to the settings fragment
            case 1:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.home_fragment, new Fragment_Settings(),null)
                        .commit();

                settings.setText("Configure las reglas del juego");
                break;

            //return from the select deck fragment fragment to the players fragment
            case 2:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.home_fragment, new Fragment_Players(),null)
                        .commit();
                settings.setText("Agregue los jugadores en el orden que quiera jugar");
                break;

            //return from the rounds fragment fragment to the select deck fragment
            case 3:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.home_fragment, new Fragment_Select_deck(),null)
                        .commit();
                settings.setText("Seleccione el mazo de cartas");
                break;
        }
    }

    //delete a round from the game
    public void deleteRound(Round round) {
        theGame.deleteRound(round);
    }

    //delete all the rounds of the game
    public void deleteAllRounds() {
        theGame.deleteAllRounds();
    }
}

