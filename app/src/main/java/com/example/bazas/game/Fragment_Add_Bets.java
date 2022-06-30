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
import com.example.bazas.gameSettings.Activity_GameSettings;
import com.example.bazas.gameSettings.Adapters.Adapter_SetRounds;

import java.util.stream.Collectors;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_Add_Bets#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_Add_Bets extends Fragment implements FragmentWithBackPress {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment_Add_Bets() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_Add_Bets.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_Add_Bets newInstance(String param1, String param2) {
        Fragment_Add_Bets fragment = new Fragment_Add_Bets();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment__add__bets, container, false);

        ListView listview = (ListView) view.findViewById(R.id.players_list);
        listview.setAdapter(new Adapter_Add_Bets(this, ((Activity_Game)getActivity()).getTheGame().getPlayers()));

        Button finishBets = view.findViewById(R.id.continue_button);
        finishBets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity_Game)getActivity()).continueButton("round");
            }
        });

        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void checkContinueButton() {
        Button finishBets = getView().findViewById(R.id.continue_button);

        if(((Activity_Game)getActivity()).getTheGame().allBetsLoaded()){

            finishBets.setEnabled(true);
        }
        else{

            finishBets.setEnabled(false);
        }

    }

    @Override
    public void onBackPressed() {
        ((Activity_Game)getActivity()).backButton("no round");
    }
}