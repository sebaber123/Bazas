package com.example.bazas.gameSettings;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.bazas.R;
import com.example.bazas.model.Game;
import com.example.bazas.model.Player;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class Activity_GameSettings extends AppCompatActivity {

    public Game theGame = new Game();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_settings);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.home_fragment, new Fragment_Players())
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
        switch (number){
            case 1:
                fragment = new Fragment_Select_deck();

                settings.setText("Seleccione el mazo de cartas");
                break;
            case 2:
                fragment = new Fragment_Set_Rounds();
                settings.setText("Agregue la cantidad de cartas por ronda");
                break;
        }
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.home_fragment, fragment,null)
                .commit();
    }

    @Override public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.home_fragment);
        ((FragmentWithBackPress) fragment).onBackPressed();
    }

    public void backPressed(Integer number) {
        TextView settings = findViewById(android.R.id.content).getRootView().findViewById(R.id.settings);
        switch (number){
            case 1:
                finish();
                break;
            case 2:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.home_fragment, new Fragment_Players(),null)
                        .commit();
                settings.setText("Agregue los jugadores en el orden que quiera jugar");
                break;
        }
    }
}

