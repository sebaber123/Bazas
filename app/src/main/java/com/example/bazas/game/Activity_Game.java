package com.example.bazas.game;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.bazas.FragmentWithBackPress;
import com.example.bazas.R;
import com.example.bazas.gameSettings.Fragment_Players;
import com.example.bazas.gameSettings.Fragment_Select_deck;
import com.example.bazas.gameSettings.Fragment_Set_Rounds;
import com.example.bazas.model.Game;

import java.io.Serializable;

public class Activity_Game extends AppCompatActivity {

    Game theGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__game);
        theGame = (Game)getIntent().getSerializableExtra("theGame");

        continueButton("round");


    }

    public void continueButton(String fragmentText) {
        TextView title = findViewById(R.id.game_title);
        Fragment fragment = null;
        switch (fragmentText) {
            case "round":
                fragment = new Fragment_Round();

                title.setText("Ronda: "+(theGame.getActual_round()+1));
                break;

            case "add bets":
                fragment = new Fragment_Add_Bets();
                break;

            case "add dones":
                fragment = new Fragment_Add_Dones();
                break;

            case "results":
                title.setText("Resultados");
                fragment = new Fragment_Results();
                break;
        }
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.home_fragment, fragment, null)
                .commit();
    }

    public Game getTheGame() {
        return theGame;
    }

    public void nextRound() {

        if (!getTheGame().isLastRound()){
            getTheGame().nextRound();

            //ACA TENGO QUE HACER EL GUARDADO

            continueButton("round");
        }else {

            //ACA CAMBIO A LA PANTALLA DE PARTIDA FINALIZADA

        }

    }

    @Override public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.home_fragment);
        ((FragmentWithBackPress) fragment).onBackPressed();
    }

    public void backButton(String fragmentText) {
        TextView title = findViewById(R.id.game_title);
        switch (fragmentText) {
            case "no round":

                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.home_fragment,  new Fragment_Round(), null)
                        .commit();

                title.setText("Ronda: "+(theGame.getActual_round()+1));
                break;

            case "round":

                break;


        }
    }

}