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
import android.widget.TextView;
import android.widget.Toast;

import com.example.bazas.FragmentWithBackPress;
import com.example.bazas.R;
import com.example.bazas.game.adapters.Adapter_Add_Bets;
import com.example.bazas.game.adapters.Adapter_Add_Dones;


public class Fragment_Add_Dones extends Fragment implements FragmentWithBackPress {

    public Fragment_Add_Dones() {
        // Required empty public constructor
    }


    public static Fragment_Add_Dones newInstance(String param1, String param2) {
        Fragment_Add_Dones fragment = new Fragment_Add_Dones();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment__add__dones, container, false);

        //set the adapter of the listView
        ListView listview = (ListView) view.findViewById(R.id.players_list);
        listview.setAdapter(new Adapter_Add_Dones(this, ((Activity_Game)getActivity()).getTheGame().getPlayers()));

        //check if the "continue" button must be enable or not
        Button finishDones = view.findViewById(R.id.continue_button);
        if(((Activity_Game)getActivity()).getTheGame().allDonesLoaded()){

            finishDones.setEnabled(true);
        }
        else{

            finishDones.setEnabled(false);
        }

        //set the "continue" button behavior
        finishDones.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                ((Activity_Game)getActivity()).continueButton("round");
            }
        });

        TextView missing_text = view.findViewById(R.id.missing_text);
        missing_text.setText("Faltan cargar "+ String.valueOf(((Activity_Game)getActivity()).getTheGame().missingDones()) + " bazas");


        return view;
    }

    //check if all the dones are loaded to set the enable status of the "continue" button
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void checkContinueButton() {
        Button finishDones = getView().findViewById(R.id.continue_button);

        if(((Activity_Game)getActivity()).getTheGame().allDonesLoaded()){

            finishDones.setEnabled(true);
        }
        else{

            finishDones.setEnabled(false);
        }

    }

    //behavior of "back" button
    @Override
    public void onBackPressed() {
        ((Activity_Game)getActivity()).backButton("no round");
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void updateMissingText() {

        int missingDones = ((Activity_Game)getActivity()).getTheGame().missingDones();
        TextView missing_text = getView().findViewById(R.id.missing_text);

        if (missingDones != 0) {

            missing_text.setText("Faltan cargar " + String.valueOf(missingDones) + " bazas");
        }
        else{

            missing_text.setText("Bazas cargadas con éxito");


        }

    }
}