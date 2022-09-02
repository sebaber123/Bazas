package com.example.bazas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;

import com.example.bazas.game.Activity_Game;
import com.example.bazas.gameSettings.Activity_GameSettings;
import com.example.bazas.loadGame.Activity_Load_Game;
import com.example.bazas.model.Game;
import com.google.gson.Gson;

import java.util.HashSet;
import java.util.Set;

public class Activity_Main extends AppCompatActivity {

    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = DatabaseHelper.getInstance(this);


    }

    //start the game
    public void startGame(View view) {

        //start the activity of game settings
        Intent intent = new Intent(this, Activity_GameSettings.class);
        startActivityForResult(intent, 2);
        //startActivity(intent);
    }

    public void loadGame(View view){
        //start the activity of game settings
        Intent intent = new Intent(this, Activity_Load_Game.class);
        startActivityForResult(intent, 3);
    }

    //return of the setting activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        // check if the request code is same as what is passed  here it is 2

        //if the request code is 2 (it means that is ok)
        switch(requestCode) {

            //return of game settings
            case 2:
                switch (resultCode) {

                    //if the result code is 2 the game must start
                    case 2:

                        //get the game
                        Game theGame = (Game) data.getSerializableExtra("theGame");

                        //start the game
                        theGame.StartGame();

                        //save the game
                        /*SharedPreferences sharedpreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        Gson gson = new Gson();
                        String json = gson.toJson(theGame);
                        Set<String> games = new HashSet<String>(sharedpreferences.getStringSet("games", new HashSet<String>()));
                        games.add(json);
                        sharedpreferences.edit().putStringSet("games", games).apply();
                        //Game theFirstGame = gson.fromJson(games.iterator().next(), Game.class);
                        //theFirstGame.getRounds();
                        */

                        dbHelper.addGame(theGame);



                        //start the activity of the game
                        Intent intent = new Intent(this, Activity_Game.class);
                        intent.putExtra("theGame", theGame);
                        startActivity(intent);
                        break;
                }
                break;

            //return of load game
            case 3:
                switch (resultCode) {

                    //if the result code is 2 the game must start
                    case 2:
                        //get the game
                        Game theGame = (Game) data.getSerializableExtra("theGame");
                        Intent intent = new Intent(this, Activity_Game.class);
                        intent.putExtra("theGame", theGame);
                        startActivity(intent);

                        break;
                }
        }
    }
}