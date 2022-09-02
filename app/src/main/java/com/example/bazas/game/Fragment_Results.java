package com.example.bazas.game;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.example.bazas.FragmentWithBackPress;
import com.example.bazas.R;
import com.example.bazas.game.adapters.Adapter_Add_Dones;
import com.example.bazas.game.adapters.Adapter_Results_Spinner;
import com.example.bazas.game.adapters.Adapter_Round_Results;
import com.example.bazas.game.adapters.Adapter_Total_Results;
import com.example.bazas.model.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class Fragment_Results extends Fragment implements FragmentWithBackPress {



    public Fragment_Results() {
        // Required empty public constructor
    }

    public static Fragment_Results newInstance(String param1, String param2) {
        Fragment_Results fragment = new Fragment_Results();
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

        View view = inflater.inflate(R.layout.fragment__results, container, false);

        getFragmentManager()
                .beginTransaction()
                .replace(R.id.score_fragment, new Fragment_Score(), null)
                .commit();

        //behavior of the "back" button
        Button returnButton = view.findViewById(R.id.return_button);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity_Game)getActivity()).continueButton("round");
            }
        });

        return view;
    }

    //behavior of "back" button
    @Override
    public void onBackPressed() {
        ((Activity_Game)getActivity()).backButton("no round");
    }
}