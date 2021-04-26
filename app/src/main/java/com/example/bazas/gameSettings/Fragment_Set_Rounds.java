package com.example.bazas.gameSettings;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.example.bazas.R;
import com.example.bazas.adapter.Adapter_AddPlayers;
import com.example.bazas.adapter.Adapter_SelectDeck;
import com.example.bazas.adapter.Adapter_SetRounds;
import com.example.bazas.model.Game;
import com.example.bazas.model.Round;

import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_Set_Rounds#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_Set_Rounds extends Fragment implements FragmentWithBackPress {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment_Set_Rounds() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_Set_Rounds.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_Set_Rounds newInstance(String param1, String param2) {
        Fragment_Set_Rounds fragment = new Fragment_Set_Rounds();
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

        View view = inflater.inflate(R.layout.fragment__set__rounds, container, false);

        ListView listview = (ListView) view.findViewById(R.id.rounds_list);
        listview.setAdapter(new Adapter_SetRounds(this, ((Activity_GameSettings)getActivity()).getTheGame().getRounds()));


        Button deleteAllButton = view.findViewById(R.id.button_delete_all);
        deleteAllButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity_GameSettings)getActivity()).deleteAllRounds();
                ((Adapter_SetRounds)listview.getAdapter()).update();
            }
        });

        Button generateRoundsButton = view.findViewById(R.id.button_generate_rounds);
        generateRoundsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog d = new Dialog((Activity_GameSettings)getActivity());
                d.setContentView(R.layout.number_picker_dialog_generate_rounds);
                final NumberPicker np = (NumberPicker) d.findViewById(R.id.numberPicker);
                np.setMaxValue(20);
                np.setMinValue(1);
                np.setWrapSelectorWheel(false);
                Button cancelButton = (Button) d.findViewById(R.id.button_cancel);
                cancelButton.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v) {
                        d.dismiss();
                    }
                });


                Button addRoundsButton = (Button) d.findViewById(R.id.button_add_rounds);
                addRoundsButton.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v) {
                        Random r = new Random();
                        int max = ((Activity_GameSettings)getActivity()).getTheGame().getMaxQuantityOfCardPerRound();
                        int min = 1;
                        int quantityOfCardToAdd;
                        for (int i = 1; i <= np.getValue(); ++i) {
                            quantityOfCardToAdd = r.nextInt((max - min) + 1) + min;
                            ((Activity_GameSettings)getActivity()).getTheGame().addRound(new Round(quantityOfCardToAdd));
                        }


                        ((Adapter_SetRounds)listview.getAdapter()).update();
                        Toast toast = Toast.makeText(getActivity().getApplicationContext(), "Rondas agregadas con exito", Toast.LENGTH_SHORT);
                        toast.show();
                        d.dismiss();
                    }
                });


                Window window = d.getWindow();
                window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                d.show();
            }
        });


        Button addRoundButton = view.findViewById(R.id.button_add_round);
        addRoundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog d = new Dialog((Activity_GameSettings)getActivity());
                d.setContentView(R.layout.number_picker_dialog_add_round);
                final NumberPicker np = (NumberPicker) d.findViewById(R.id.numberPicker);
                np.setMaxValue(((Activity_GameSettings)getActivity()).getTheGame().getMaxQuantityOfCardPerRound());
                np.setMinValue(1);
                np.setWrapSelectorWheel(false);
                Button cancelButton = (Button) d.findViewById(R.id.button_cancel);
                cancelButton.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v) {
                        d.dismiss();
                    }
                });

                Button addRoundButton = (Button) d.findViewById(R.id.button_add_round);
                addRoundButton.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v) {
                        ((Activity_GameSettings)getActivity()).getTheGame().addRound(new Round(np.getValue()));
                        np.setValue(1);
                        ((Adapter_SetRounds)listview.getAdapter()).update();
                        Toast toast = Toast.makeText(getActivity().getApplicationContext(), "Ronda agregada con exito", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                });


                Window window = d.getWindow();
                window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                d.show();
            }
        });



        Button backButton = view.findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity_GameSettings)getActivity()).backPressed(3);
            }
        });

        Button continueButton = view.findViewById(R.id.continue_button);
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity_GameSettings)getActivity()).continueButton(3);
            }
        });

        if (((Activity_GameSettings)getActivity()).getTheGame().getRounds().size()>0){
            continueButton.setEnabled(true);
        }else{
            continueButton.setEnabled(false);
        }

        return view;
    }

    @Override
    public void onBackPressed() {
        ((Activity_GameSettings)getActivity()).backPressed(3);
    }
    //TO DO
    public void checkContinueButton() {
        Button button = getView().findViewById(R.id.continue_button);
        if (((Activity_GameSettings)getActivity()).getTheGame().getRounds().size()>0){
            button.setEnabled(true);
        }else{
            button.setEnabled(false);
        }
    }



    public void deleteRound(Round round) {
        ((Activity_GameSettings)getActivity()).deleteRound(round);
    }
}