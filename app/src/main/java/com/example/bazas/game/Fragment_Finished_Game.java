package com.example.bazas.game;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.bazas.FragmentWithBackPress;
import com.example.bazas.R;


public class Fragment_Finished_Game extends Fragment implements FragmentWithBackPress {


    public Fragment_Finished_Game() {
        // Required empty public constructor
    }

    public static Fragment_Finished_Game newInstance() {
        Fragment_Finished_Game fragment = new Fragment_Finished_Game();
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

        View view = inflater.inflate(R.layout.fragment__finished__game, container, false);

        getFragmentManager()
                .beginTransaction()
                .replace(R.id.score_fragment, new Fragment_Score(), null)
                .commit();

        //behavior of the "back" button
        Button returnButton = view.findViewById(R.id.return_button);
        returnButton.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                ((Activity_Game)getActivity()).backButton("finished game");
            }
        });

        return view;
    }

    //behavior of "back" button
    @Override
    public void onBackPressed() {


        ((Activity_Game)getActivity()).backButton("finished game");
    }
}