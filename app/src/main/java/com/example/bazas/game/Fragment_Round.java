package com.example.bazas.game;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.bazas.FragmentWithBackPress;
import com.example.bazas.R;


public class Fragment_Round extends Fragment implements FragmentWithBackPress {



    public Fragment_Round() {
        // Required empty public constructor
    }


    public static Fragment_Round newInstance(String param1, String param2) {
        Fragment_Round fragment = new Fragment_Round();
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
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment__round, container, false);

        //set the quantity of cards text
        TextView quantityOfCards = view.findViewById(R.id.quantity_of_cards);
        quantityOfCards.setText(String.valueOf(((Activity_Game)getActivity()).getTheGame().getRounds().get(((Activity_Game)getActivity()).getTheGame().getActual_round()).getQuantity_of_cards()));

        //set the dealer name text
        TextView dealer = view.findViewById(R.id.dealer_text_line);
        dealer.setText(((Activity_Game)getActivity()).getTheGame().getPlayers().get(((Activity_Game)getActivity()).getTheGame().getPositionOfFirstPlayerOfTheRound()).getName());

        //set the visibility text (yes or no are the options)
        TextView visibility = view.findViewById(R.id.visibility_text_line);
        if (((Activity_Game)getActivity()).getTheGame().getRounds().get(((Activity_Game)getActivity()).getTheGame().getPositionOfFirstPlayerOfTheRound()).isVisibility()){
            visibility.setText("Si");
        }else{
            visibility.setText("No");
        }

        //behavior of the "addBets" button
        Button addBetsButton = view.findViewById(R.id.add_bets_button);
        addBetsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //change the fragment to the fragment of add bets
                ((Activity_Game)getActivity()).continueButton("add bets");
            }
        });

        //behavior of the "addDones" button
        Button addDonesButton = view.findViewById(R.id.add_dones_button);
        addDonesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //change the fragment to the fragment of add dones
                ((Activity_Game)getActivity()).continueButton("add dones");
            }
        });

        //behavior of "finishRound" button
        Button finishRoundButton = view.findViewById(R.id.continue_button);
        finishRoundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //advance to the next round
                ((Activity_Game)getActivity()).nextRound();
            }
        });

        //check if the buttons must be enable or not
        checkButtons(addDonesButton, finishRoundButton);

        //behaviour of the result button
        Button resultButton = view.findViewById(R.id.results_button);
        resultButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //change the fragment to the fragment of results
                ((Activity_Game)getActivity()).continueButton("results");

            }
        });

        return view;
    }

    //check if the buttons must be enable or not
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void checkButtons(Button addDonesButton, Button finishRoundButton) {

        //if all the bets are loaded enable the "addDones" button
        if (((Activity_Game)getActivity()).getTheGame().areAllBetsLoaded()){
            addDonesButton.setEnabled(true);
        } else {
            addDonesButton.setEnabled(false);
        }

        //if all the dones are loaded enable the "finish" button
        if (((Activity_Game)getActivity()).getTheGame().allDonesLoaded()){
            finishRoundButton.setEnabled(true);
        } else {
            finishRoundButton.setEnabled(false);
        }
    }

    //behavior of "back" button
    @Override
    public void onBackPressed() {
        ((Activity_Game)getActivity()).backButton("round");
    }
}