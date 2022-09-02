package com.example.bazas.game;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.example.bazas.FragmentWithBackPress;
import com.example.bazas.R;
import com.example.bazas.game.adapters.Adapter_Add_Bets;


public class Fragment_Add_Bets extends Fragment implements FragmentWithBackPress {


    public Fragment_Add_Bets() {
        // Required empty public constructor
    }

    public static Fragment_Add_Bets newInstance(String param1, String param2) {
        Fragment_Add_Bets fragment = new Fragment_Add_Bets();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment__add__bets, container, false);

        //set the listView of add bets with the adapter
        ListView listview = (ListView) view.findViewById(R.id.players_list);
        listview.setAdapter(new Adapter_Add_Bets(this, ((Activity_Game)getActivity()).getTheGame().getPlayers()));

        //behavior of "continue" button
        Button finishBets = view.findViewById(R.id.continue_button);
        finishBets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity_Game)getActivity()).continueButton("round");
            }
        });

        return view;
    }

    //check if all the bets are loaded to set the enable status of the "continue" button
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void checkContinueButton() {
        Button finishBets = getView().findViewById(R.id.continue_button);

        if(((Activity_Game)getActivity()).getTheGame().areAllBetsLoaded()){

            finishBets.setEnabled(true);
        }
        else{

            finishBets.setEnabled(false);
        }

    }

    //behavior of "back" button
    @Override
    public void onBackPressed() {
        ((Activity_Game)getActivity()).backButton("no round");
    }
}