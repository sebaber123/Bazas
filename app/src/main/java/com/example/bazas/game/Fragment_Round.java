package com.example.bazas.game;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bazas.R;
import com.example.bazas.gameSettings.Activity_GameSettings;

import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_Round#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_Round extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment_Round() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_Round.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_Round newInstance(String param1, String param2) {
        Fragment_Round fragment = new Fragment_Round();
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
        View view = inflater.inflate(R.layout.fragment__round, container, false);

        TextView quantityOfCards = view.findViewById(R.id.quantity_of_cards);
        quantityOfCards.setText(String.valueOf(((Activity_Game)getActivity()).getTheGame().getRounds().get(((Activity_Game)getActivity()).getTheGame().getActual_round()).getQuantity_of_cards()));

        TextView delaer = view.findViewById(R.id.dealer_text_line);
        delaer.setText(((Activity_Game)getActivity()).getTheGame().getPlayers().get(((Activity_Game)getActivity()).getTheGame().positionOfFirstPlayerOfTheRound()).getName());

        TextView visibility = view.findViewById(R.id.visibility_text_line);
        if (((Activity_Game)getActivity()).getTheGame().getRounds().get(((Activity_Game)getActivity()).getTheGame().positionOfFirstPlayerOfTheRound()).isVisibility()){
            visibility.setText("Si");
        }else{
            visibility.setText("No");
        }


        return view;
    }

}