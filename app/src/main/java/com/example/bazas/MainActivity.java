package com.example.bazas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;

import com.example.bazas.game.Activity_Game;
import com.example.bazas.gameSettings.Activity_GameSettings;
import com.example.bazas.model.Game;
import com.google.gson.Gson;

import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void iniciarPartida(View view) {
        Intent intent = new Intent(this, Activity_GameSettings.class);
        startActivityForResult(intent, 2);
        //startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2
        if(requestCode==2)
        {
            Game theGame = (Game)data.getSerializableExtra("theGame");
            theGame.getRounds();
            SharedPreferences sharedpreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            Gson gson = new Gson();
            String json = gson.toJson(theGame);
            Set<String> games = new HashSet<String>(sharedpreferences.getStringSet("games", new HashSet<String>()));
            games.add(json);
            sharedpreferences.edit().putStringSet("games", games).apply();
            //Game theFirstGame = gson.fromJson(games.iterator().next(), Game.class);
            //theFirstGame.getRounds();

            Intent intent = new Intent(this, Activity_Game.class);
            intent.putExtra("theGame", theGame);
            startActivity(intent);
        }
    }
}