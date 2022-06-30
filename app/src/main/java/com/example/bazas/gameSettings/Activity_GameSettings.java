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

    public Game theGame = new Game();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_settings);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.home_fragment, new Fragment_Settings())
                .commit();
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.home_fragment);

    }

    public void deletePlayer(Player player) {
        theGame.deletePlayer(player);
    }

    public void addPlayer(String name) {
        theGame.addPlayer(name);
    }

    public ArrayList<Player> getPlayers() {
        return theGame.getPlayers();
    }


    public Game getTheGame() {
        return theGame;
    }

    public void continueButton(Integer number) {
        TextView settings = findViewById(android.R.id.content).getRootView().findViewById(R.id.settings);
        Fragment fragment = null;
        switch (number) {
            case 0:
                fragment = new Fragment_Players();

                settings.setText("Agregue los jugadores en el orden que quiera jugar");
                break;
            case 1:
                fragment = new Fragment_Select_deck();

                settings.setText("Seleccione el mazo de cartas");
                break;
            case 2:
                fragment = new Fragment_Set_Rounds();
                settings.setText("Agregue la cantidad de cartas por ronda");
                break;
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

    @Override public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.home_fragment);
        ((FragmentWithBackPress) fragment).onBackPressed();
    }

    public void backPressed(Integer number) {
        TextView settings = findViewById(android.R.id.content).getRootView().findViewById(R.id.settings);
        switch (number){
            case 0:
                Intent intent = new Intent();
                setResult(1, intent);
                finish();
                break;
            case 1:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.home_fragment, new Fragment_Settings(),null)
                        .commit();

                settings.setText("Configure las reglas del juego");
                break;
            case 2:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.home_fragment, new Fragment_Players(),null)
                        .commit();
                settings.setText("Agregue los jugadores en el orden que quiera jugar");
                break;
            case 3:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.home_fragment, new Fragment_Select_deck(),null)
                        .commit();
                settings.setText("Seleccione el mazo de cartas");
                break;
        }
    }

    public void deleteRound(Round round) {
        theGame.deleteRound(round);
    }

    public void deleteAllRounds() {
        theGame.deleteAllRounds();
    }
}

