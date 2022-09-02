package com.example.bazas.loadGame.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.example.bazas.R;
import com.example.bazas.gameSettings.Fragment_Players;
import com.example.bazas.loadGame.Activity_Load_Game;
import com.example.bazas.model.Game;
import com.example.bazas.model.Player;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class Adapter_LoadGame extends BaseAdapter implements ListAdapter {
    private ArrayList<Game> list = new ArrayList<Game>();
    private Activity_Load_Game context;


    public Adapter_LoadGame(ArrayList<Game> list, Activity_Load_Game context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Game getItem(int pos) {
        return list.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return 0;
        //just return 0 if your list items do not have an Id variable.
    }

    //construct the view
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = null;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_item_load_game, null);
        }

        //convert date of the game to string
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = dateFormat.format(getItem(position).getStartDate());

        //set the date text
        TextView dateText = view.findViewById(R.id.date_game_text);
        dateText.setText(strDate);

        //set the quantity of players text
        TextView PlayersText = view.findViewById(R.id.players_quantity_game_text);
        PlayersText.setText(String.valueOf(getItem(position).getPlayers().size()));

        //set the quantity of rounds text
        TextView roundsText = view.findViewById(R.id.rounds_quantity_game_text);
        roundsText.setText(
                String.valueOf(getItem(position).getActual_round()) +
                " de "+
                String.valueOf(getItem(position).getRounds().size())+
                " jugadas");

        //behavior of load button
        Button loadGameButton = view.findViewById(R.id.load_button);
        loadGameButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {

                //load the game
                context.loadGame(getItem(position));
            }
        });




        return view;
    }

    //update the view
    public void update(){
        notifyDataSetChanged();
    }
}
