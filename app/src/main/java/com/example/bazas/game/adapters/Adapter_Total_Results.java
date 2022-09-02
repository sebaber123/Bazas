package com.example.bazas.game.adapters;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.bazas.R;
import com.example.bazas.game.Activity_Game;
import com.example.bazas.game.Fragment_Add_Dones;
import com.example.bazas.game.Fragment_Results;
import com.example.bazas.model.Player;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class Adapter_Total_Results extends BaseAdapter implements ListAdapter {
    private ArrayList<Player> list = new ArrayList<Player>();
    private Fragment context;


    public Adapter_Total_Results(Fragment context, ArrayList<Player> playersList) {
        this.list = playersList;
        this.context = context;
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
            view = inflater.inflate(R.layout.list_item_total_results, null);
        }

        //set the player position text
        TextView playerPosition = view.findViewById(R.id.player_position_text);
        playerPosition.setText(String.valueOf(position+1));

        //set the player name text
        TextView playerName = view.findViewById(R.id.player_name_text);
        playerName.setText(list.get(position).getName());

        //set the total player points text
        TextView playerTotalPoint = view.findViewById(R.id.total_text);
        playerTotalPoint.setText(String.valueOf(((Activity_Game)context.getActivity()).getTheGame().getTotalPointOfPlayer(list.get(position))));

        return view;
    }
}
