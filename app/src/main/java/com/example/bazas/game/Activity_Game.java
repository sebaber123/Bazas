package com.example.bazas.game;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.widget.TextView;

import com.example.bazas.R;
import com.example.bazas.gameSettings.Fragment_Players;
import com.example.bazas.model.Game;

import java.io.Serializable;

public class Activity_Game extends AppCompatActivity {

    Game theGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__game);
        theGame = (Game)getIntent().getSerializableExtra("theGame");

        TextView title = findViewById(R.id.game_title);
        title.setText("Ronda: "+(theGame.getActual_round()+1));

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.home_fragment, new Fragment_Round())
                .commit();
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.home_fragment);


    }

    public Game getTheGame() {
        return theGame;
    }
}