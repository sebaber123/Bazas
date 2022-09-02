package com.example.bazas.gameSettings;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.example.bazas.FragmentWithBackPress;
import com.example.bazas.R;
import com.example.bazas.gameSettings.Adapters.Adapter_SelectDeck;
import com.example.bazas.model.Game;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_Select_deck#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_Select_deck extends Fragment implements FragmentWithBackPress {

    public Fragment_Select_deck() {
        // Required empty public constructor
    }

    public static Fragment_Select_deck newInstance(String param1, String param2) {
        Fragment_Select_deck fragment = new Fragment_Select_deck();
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

    //construct the view
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment__select_deck, container, false);

        //set the adapter of the ListView
        ListView listview2 = (ListView) view.findViewById(R.id.decks_list);
        listview2.setAdapter(new Adapter_SelectDeck(this));

        //behaviour of the "back" button
        Button backButton = view.findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity_GameSettings)getActivity()).backPressed(2);
            }
        });

        //behaviour of the "continue" button
        Button continueButton = view.findViewById(R.id.continue_button);
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity_GameSettings)getActivity()).continueButton(2);
            }
        });

        //check if the deck is selected to enable the "continue" button
        if (((Activity_GameSettings)getActivity()).getTheGame().getDeck()!=0){
            continueButton.setEnabled(true);
        }else{
            continueButton.setEnabled(false);
        }

        return view;


    }

    //return the game
    public Game getTheGame() {
        return ((Activity_GameSettings)getActivity()).getTheGame();
    }

    //behaviour of back press
    @Override
    public void onBackPressed() {
        ((Activity_GameSettings)getActivity()).backPressed(2);
    }

    //check if the deck is selected to enable the "continue" button
    public void checkContinueButton() {
        Button button = getView().findViewById(R.id.continue_button);
        if (((Activity_GameSettings)getActivity()).getTheGame().getDeck()!=0){
            button.setEnabled(true);
        }else{
            button.setEnabled(false);
        }
    }
}