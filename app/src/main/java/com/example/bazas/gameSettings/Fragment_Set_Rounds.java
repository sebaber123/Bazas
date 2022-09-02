package com.example.bazas.gameSettings;

import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.example.bazas.FragmentWithBackPress;
import com.example.bazas.R;
import com.example.bazas.gameSettings.Adapters.Adapter_SetRounds;
import com.example.bazas.model.Round;

import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_Set_Rounds#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_Set_Rounds extends Fragment implements FragmentWithBackPress {

    //constructor
    public Fragment_Set_Rounds() {
        // Required empty public constructor
    }

    public static Fragment_Set_Rounds newInstance(String param1, String param2) {
        Fragment_Set_Rounds fragment = new Fragment_Set_Rounds();
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
        View view = inflater.inflate(R.layout.fragment__set__rounds, container, false);

        //set the adapter to the listview
        ListView listview = (ListView) view.findViewById(R.id.rounds_list);
        listview.setAdapter(new Adapter_SetRounds(this, ((Activity_GameSettings)getActivity()).getTheGame().getRounds()));

        //behaviour of the "delete all" button
        Button deleteAllButton = view.findViewById(R.id.button_delete_all);
        deleteAllButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //delete all the rounds of the game
                ((Activity_GameSettings)getActivity()).deleteAllRounds();

                //update the adapter
                ((Adapter_SetRounds)listview.getAdapter()).update();
            }
        });

        //behaviour of the "generate rounds" button
        Button generateRoundsButton = view.findViewById(R.id.button_generate_rounds);
        generateRoundsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //create a dialog
                Dialog d = new Dialog((Activity_GameSettings)getActivity());

                //set the layout of the dialog
                d.setContentView(R.layout.number_picker_dialog_generate_rounds);

                //create a number picker
                final NumberPicker np = (NumberPicker) d.findViewById(R.id.numberPicker);

                np.setMaxValue(20);
                np.setMinValue(1);
                np.setWrapSelectorWheel(false);

                //behaviour of the "cancel" button of the dialog (close the dialog)
                Button cancelButton = (Button) d.findViewById(R.id.button_cancel);
                cancelButton.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v) {
                        d.dismiss();
                    }
                });


                //behaviour of the "add rounds" button of the dialog
                Button addRoundsButton = (Button) d.findViewById(R.id.button_add_rounds);
                addRoundsButton.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v) {

                        //create a random generator
                        Random r = new Random();

                        //max value of that can return the random (max quantity of cards per round)
                        int max = ((Activity_GameSettings)getActivity()).getTheGame().getMaxQuantityOfCardPerRound();

                        //min value that can return the random
                        int min = 1;

                        //var to save the return of the random
                        int quantityOfCardToAdd;

                        //generate the quantity of rounds selected in the number picker
                        for (int i = 1; i <= np.getValue(); ++i) {

                            //generate the random number
                            quantityOfCardToAdd = r.nextInt((max - min) + 1) + min;

                            //add the random number to the rounds
                            ((Activity_GameSettings)getActivity()).getTheGame().addRound(new Round(quantityOfCardToAdd));
                        }

                        //update the view
                        ((Adapter_SetRounds)listview.getAdapter()).update();

                        //toast message
                        Toast toast = Toast.makeText(getActivity().getApplicationContext(), "Rondas agregadas con exito", Toast.LENGTH_SHORT);
                        toast.show();

                        //close the dialog
                        d.dismiss();
                    }
                });

                //get the window
                Window window = d.getWindow();
                window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                //show the dialog
                d.show();
            }
        });

        //behaviour of the "add round" button
        Button addRoundButton = view.findViewById(R.id.button_add_round);
        addRoundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //create a dialog
                Dialog d = new Dialog((Activity_GameSettings)getActivity());

                //set the layout of the dialog
                d.setContentView(R.layout.number_picker_dialog_add_round);

                //create a number picker
                final NumberPicker np = (NumberPicker) d.findViewById(R.id.numberPicker);


                np.setMaxValue(((Activity_GameSettings)getActivity()).getTheGame().getMaxQuantityOfCardPerRound());
                np.setMinValue(1);
                np.setWrapSelectorWheel(false);

                //behaviour of the "cancel" button of the dialog (close the dialog)
                Button cancelButton = (Button) d.findViewById(R.id.button_cancel);
                cancelButton.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v) {
                        d.dismiss();
                    }
                });

                //behaviour of the "add round" button of the dialog
                Button addRoundButton = (Button) d.findViewById(R.id.button_add_round);
                addRoundButton.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v) {

                        //get the value of the number picker
                        ((Activity_GameSettings)getActivity()).getTheGame().addRound(new Round(np.getValue()));

                        //set the value of the np to 1 (so the player can add another round)
                        np.setValue(1);

                        //update the listview
                        ((Adapter_SetRounds)listview.getAdapter()).update();

                        //toast message
                        Toast toast = Toast.makeText(getActivity().getApplicationContext(), "Ronda agregada con exito", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                });

                //get the window
                Window window = d.getWindow();
                window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                //show the dialog
                d.show();
            }
        });


        //behaviour of "back" button
        Button backButton = view.findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity_GameSettings)getActivity()).backPressed(3);
            }
        });

        //behaviour of the "continue" button
        Button continueButton = view.findViewById(R.id.continue_button);
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity_GameSettings)getActivity()).continueButton(3);
            }
        });

        //check if the continue button must be enable
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


    //check if the continue button must be enable
    public void checkContinueButton() {
        Button button = getView().findViewById(R.id.continue_button);
        if (((Activity_GameSettings)getActivity()).getTheGame().getRounds().size()>0){
            button.setEnabled(true);
        }else{
            button.setEnabled(false);
        }
    }


    //delete a round from the game
    public void deleteRound(Round round) {
        ((Activity_GameSettings)getActivity()).deleteRound(round);
    }
}