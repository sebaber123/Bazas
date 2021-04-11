package com.example.bazas.gameSettings;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.bazas.R;
import com.example.bazas.adapter.Adapter_AddPlayers;
import com.example.bazas.adapter.Adapter_SelectDeck;
import com.example.bazas.model.Game;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_Select_deck#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_Select_deck extends Fragment implements FragmentWithBackPress {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment_Select_deck() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_Select_deck.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_Select_deck newInstance(String param1, String param2) {
        Fragment_Select_deck fragment = new Fragment_Select_deck();
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

        View view = inflater.inflate(R.layout.fragment__select_deck, container, false);


        ListView listview2 = (ListView) view.findViewById(R.id.decks_list);
        listview2.setAdapter(new Adapter_SelectDeck(this));

        Button backButton = view.findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity_GameSettings)getActivity()).backPressed(2);
            }
        });

        Button continueButton = view.findViewById(R.id.continue_button);
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity_GameSettings)getActivity()).continueButton(2);
            }
        });

        if (((Activity_GameSettings)getActivity()).getTheGame().getDeck()!=0){
            continueButton.setEnabled(true);
        }else{
            continueButton.setEnabled(false);
        }

        return view;


    }

    public Game getTheGame() {
        return ((Activity_GameSettings)getActivity()).getTheGame();
    }

    @Override
    public void onBackPressed() {
        ((Activity_GameSettings)getActivity()).backPressed(2);
    }

    public void checkContinueButton() {
        Button button = getView().findViewById(R.id.continue_button);
        if (((Activity_GameSettings)getActivity()).getTheGame().getDeck()!=0){
            button.setEnabled(true);
        }else{
            button.setEnabled(false);
        }
    }
}