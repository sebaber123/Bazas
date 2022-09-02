package com.example.bazas.game;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.example.bazas.DatabaseHelper;
import com.example.bazas.FragmentWithBackPress;
import com.example.bazas.R;
import com.example.bazas.model.Game;

public class Activity_Game extends AppCompatActivity {

    Game theGame;

    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__game);
        theGame = (Game)getIntent().getSerializableExtra("theGame");

        //

        if (theGame.isGameFinished()){

            continueButton("finished");
        }
        else{

            continueButton("round");
        }

        dbHelper = DatabaseHelper.getInstance(this);


    }

    //set the fragment depending the text get as parameter
    public void continueButton(String fragmentText) {
        TextView title = findViewById(R.id.game_title);
        Fragment fragment = null;
        switch (fragmentText) {

            //advance to the next round fragment
            case "round":
                fragment = new Fragment_Round();

                title.setText("Ronda: "+(theGame.getActual_round()+1));
                break;

            //change to the fragment of "add bets"
            case "add bets":
                fragment = new Fragment_Add_Bets();
                break;

            //change to the fragment of "add dones"
            case "add dones":
                fragment = new Fragment_Add_Dones();
                break;

            //change to the fragment of "results"
            case "results":
                title.setText("Resultados");
                fragment = new Fragment_Results();
                break;

            //advance to the next round fragment
            case "finished":
                fragment = new Fragment_Finished_Game();

                title.setText("Resultados finales");
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

    //advance to the next round of the game
    public void nextRound() {

        //advance to the next round
        getTheGame().nextRound();

        //update the database
        dbHelper.updateGame(getTheGame());

        if (!getTheGame().isGameFinished()){

            continueButton("round");
        }else {

            continueButton("finished");

        }

    }

    //behavior of back button
    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.home_fragment);
        ((FragmentWithBackPress) fragment).onBackPressed();
    }

    //behavior of the "back" button
    public void backButton(String fragmentText) {
        TextView title = findViewById(R.id.game_title);
        switch (fragmentText) {

            //back to the round fragment if there is other fragment in the view
            case "no round":

                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.home_fragment,  new Fragment_Round(), null)
                        .commit();

                title.setText("Ronda: "+(theGame.getActual_round()+1));
                break;

            case "round":

                //show a confirm dialog to return to the menu
                //create the Dialog window
                Dialog d = new Dialog(this);

                //set the layout of the dialog
                d.setContentView(R.layout.dialog_return_to_main_menu);

                //behaviour of "cancel" button
                Button cancelButton = (Button) d.findViewById(R.id.button_cancel);
                cancelButton.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v) {
                        d.dismiss();
                    }
                });

                //behaviour of the "accept" button
                Button acceptButton = (Button) d.findViewById(R.id.button_accept);
                acceptButton.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v) {

                        finish();
                        //behavior of the onClick

                    }
                });

                Window window = d.getWindow();
                window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                d.show();

                break;

            case "finished game":

                finish();

                break;

        }
    }

}