package com.example.bazas.loadGame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.example.bazas.DatabaseHelper;
import com.example.bazas.R;
import com.example.bazas.gameSettings.Adapters.Adapter_SelectDeck;
import com.example.bazas.loadGame.Adapters.Adapter_LoadGame;
import com.example.bazas.model.Game;

import java.util.ArrayList;

public class Activity_Load_Game extends AppCompatActivity {

    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_game);

        dbHelper = DatabaseHelper.getInstance(this);

        ArrayList<Game> GamesList = dbHelper.getAllGames();


        //set the adapter of the ListView
        ListView listview2 = (ListView) findViewById(R.id.games_list);
        listview2.setAdapter(new Adapter_LoadGame(GamesList,this));


    }

    //back button behaviour
    public void backButton(View view){

        Intent intent = new Intent();
        setResult(1, intent);
        finish();

    }

    //load game
    public void loadGame(Game game){

        Intent intent = new Intent();

        //send the game to the main activity to load it
        intent.putExtra("theGame", game);

        //set result code
        setResult(2, intent);

        //finish the activity
        finish();

    }
}