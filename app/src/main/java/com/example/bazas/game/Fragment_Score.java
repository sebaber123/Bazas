package com.example.bazas.game;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.example.bazas.FragmentWithBackPress;
import com.example.bazas.R;
import com.example.bazas.game.adapters.Adapter_Results_Spinner;
import com.example.bazas.game.adapters.Adapter_Round_Results;
import com.example.bazas.game.adapters.Adapter_Total_Results;
import com.example.bazas.model.Player;

import java.util.ArrayList;
import java.util.stream.Collectors;


public class Fragment_Score extends Fragment {



    public Fragment_Score() {
        // Required empty public constructor
    }


    public static Fragment_Score newInstance() {
        Fragment_Score fragment = new Fragment_Score();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment__score, container, false);

        //create the array list of rounds
        ArrayList<String> rounds = new ArrayList<String>();

        //add the total result to the list of rounds
        rounds.add("Resultados totales");

        //add each round of the game to the list of rounds
        for (int i = 1; i <= ((Activity_Game)getActivity()).getTheGame().getRounds().size(); i++) {
            rounds.add("Ronda" + String.valueOf(i));
        }


        RelativeLayout resultHeading = view.findViewById(R.id.result_heading);
        RelativeLayout totalResultHeading = view.findViewById(R.id.total_result_heading);

        //set up the spinner
        final Spinner spinner = (Spinner) view.findViewById(R.id.result_spinner);
        final Adapter_Results_Spinner adapter = new Adapter_Results_Spinner(getActivity(), rounds);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        Fragment_Score theThis = this;

        //define the listview, so later i can set the adapter depending on the selection of the spinner
        ListView listview = (ListView) view.findViewById(R.id.results_list_view);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                //if position == 0 the option selected is "total results"
                if (position == 0){

                    totalResultHeading.setVisibility(View.VISIBLE);
                    resultHeading.setVisibility(View.INVISIBLE);

                    ArrayList<Player> playersOrderByPosition = ((Activity_Game)getActivity()).getTheGame().getPlayers().stream()
                            .sorted((player1, player2) -> ((Activity_Game)getActivity()).getTheGame().whoHasMorePoints(player1, player2))
                            .collect(Collectors.toCollection(ArrayList::new));

                    /*playersOrderByPosition.size();
                    ArrayList<Player> players = ((Activity_Game)getActivity()).getTheGame().getPlayers();
                    players.size();*/

                    //set the adapter
                    listview.setAdapter(new Adapter_Total_Results(theThis, playersOrderByPosition));

                }

                else{

                    resultHeading.setVisibility(View.VISIBLE);
                    totalResultHeading.setVisibility(View.INVISIBLE);

                    //set the adapter
                    listview.setAdapter(new Adapter_Round_Results(theThis, ((Activity_Game)getActivity()).getTheGame().getPlayers(), position-1));

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //
            }
        });

        // Inflate the layout for this fragment
        return view;
    }


}