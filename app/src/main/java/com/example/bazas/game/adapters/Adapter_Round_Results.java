package com.example.bazas.game.adapters;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.bazas.R;
import com.example.bazas.game.Activity_Game;
import com.example.bazas.game.Fragment_Results;
import com.example.bazas.model.Player;
import com.example.bazas.model.PlayerRound;

import java.util.ArrayList;

public class Adapter_Round_Results extends BaseAdapter implements ListAdapter {
    private ArrayList<Player> list = new ArrayList<Player>();
    private Fragment context;
    private int round;


    public Adapter_Round_Results(Fragment context, ArrayList<Player> playersList, int round) {
        this.list = playersList;
        this.context = context;
        this.round = round;
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int pos) {
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
            LayoutInflater inflater = (LayoutInflater) context.getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_item_results_of_round, null);
        }

        //set the player name text
        TextView playerName = view.findViewById(R.id.player_name_text);
        playerName.setText(list.get(position).getName());

        TextView playerBet = view.findViewById(R.id.bet_text);
        TextView playerDone = view.findViewById(R.id.done_text);
        TextView roundPoints = view.findViewById(R.id.points_of_round_text);
        TextView subtotalPoints = view.findViewById(R.id.subtotal_points_text);

        //set the subtotal points text with the amount of points
        subtotalPoints.setText(String.valueOf(((Activity_Game) context.getActivity()).getTheGame().getTotalPointOfPlayerToRound(list.get(position), round + 1)));

        //if the playerRounds are loaded
        if (list.get(position).getPlayerRounds().get(round).isLoaded()) {

            //set the player bet text of the player
            playerBet.setText(String.valueOf(list.get(position).getPlayerRounds().get(round).getBet()));

            //if the done is loaded
            if (list.get(position).getPlayerRounds().get(round).isDoneLoaded()) {

                //set the player done text
                playerDone.setText(String.valueOf(list.get(position).getPlayerRounds().get(round).getDone()));

                //set the round points text with the calculation of the points that the player had made that round
                roundPoints.setText(String.valueOf(((Activity_Game) context.getActivity()).getTheGame().calculatePoints(list.get(position).getPlayerRounds().get(round).getBet(), list.get(position).getPlayerRounds().get(round).getDone())));

            //done not loaded
            }else {
                playerDone.setText("-");

                roundPoints.setText("-");
            }

        //round didn't play
        }else {
            playerBet.setText("-");

            playerDone.setText("-");

            roundPoints.setText("-");
        }

        return view;
    }


}
