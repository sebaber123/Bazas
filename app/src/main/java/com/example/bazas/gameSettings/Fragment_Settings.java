package com.example.bazas.gameSettings;

import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bazas.FragmentWithBackPress;
import com.example.bazas.R;
import com.example.bazas.gameSettings.Adapters.Adapter_SetRounds;
import com.example.bazas.model.Round;

import org.w3c.dom.Text;

import java.util.Random;


public class Fragment_Settings extends Fragment implements FragmentWithBackPress {



    public Fragment_Settings() {
        // Required empty public constructor
    }


    public static Fragment_Settings newInstance(String param1, String param2) {
        Fragment_Settings fragment = new Fragment_Settings();
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
        View view = inflater.inflate(R.layout.fragment__settings, container, false);

        //behaviour of the "continue" button
        Button continueButton = view.findViewById(R.id.continue_button);
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity_GameSettings)getActivity()).continueButton(0);
            }
        });

        //behaviour of the "back" button
        Button backButton = view.findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity_GameSettings)getActivity()).backPressed(0);
            }
        });

        //behaviour of the "edit point if done" button
        Button pointsIfDoneEditButton = view.findViewById(R.id.edit_points_if_done);
        pointsIfDoneEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                createEditDialog(view, 50, 5, "Puntos que dara cumplir la apuesta", 0);
            }
        });

        //behaviour of the "edit point per bet if done" button
        Button pointsPerBetIfDoneEditButton = view.findViewById(R.id.edit_points_per_bet_if_done);
        pointsPerBetIfDoneEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                createEditDialog(view, 10, 1, "Puntos que dara por baza cuando cumple la apuesta", 1);
            }
        });

        //behaviour of the "edit point per bet if not done" button
        Button pointsPerBetIfNotDoneEditButton = view.findViewById(R.id.edit_points_per_bet_if_not_done);
        pointsPerBetIfNotDoneEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                createEditDialog(view, 10, 1, "Puntos que dara por baza cuando no cumple la apuesta", 2);
            }
        });

        //behaviour of the "edit point per bet if not done" button
        Button betZeroLimitEditButton = view.findViewById(R.id.edit_bet_0_limit);
        betZeroLimitEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                createEditDialog(view, 5, 0, "Cantidad de rondas que se puede apostar 0 seguidas", 3);
            }
        });


        return view;
    }

    //behaviour of back press
    @Override
    public void onBackPressed() {
        ((Activity_GameSettings)getActivity()).backPressed(0);
    }

    public void createEditDialog(View view, int maxValue, int minValue, String titleText, int option){

        //create the dialog
        Dialog d = new Dialog((Activity_GameSettings)getActivity());

        //set the layout of the dialog
        d.setContentView(R.layout.number_picker_dialog_settings);

        //create the number picker
        final NumberPicker np = (NumberPicker) d.findViewById(R.id.numberPicker);

        np.setMaxValue(maxValue);
        np.setMinValue(minValue);
        np.setWrapSelectorWheel(false);

        TextView title = d.findViewById(R.id.text_title);
        title.setText(titleText);

        //behaviour of the "cancel" button of the dialog
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

                int numberPickerValue = np.getValue();
                TextView textViewEditted = null;


                switch (option){


                    //editing points if done
                    case 0:
                        textViewEditted = view.findViewById(R.id.points_if_done_text);
                        ((Activity_GameSettings)getActivity()).getTheGame().setPointsIfDone(numberPickerValue);
                        break;

                    //editing points per bet if done
                    case 1:
                        textViewEditted = view.findViewById(R.id.points_per_bet_if_done_text);
                        ((Activity_GameSettings)getActivity()).getTheGame().setPointsPerDone(numberPickerValue);
                        break;

                    //editing points per bet if not done
                    case 2:
                        textViewEditted = view.findViewById(R.id.points_per_bet_if_not_done_text);
                        ((Activity_GameSettings)getActivity()).getTheGame().setPointsPerDoneIfNotAchieveTheBet(numberPickerValue);
                        break;

                    //editing bet 0 limit
                    case 3:
                        textViewEditted = view.findViewById(R.id.bet_0_limit_text);

                        //if zero limit bet is 0 then there is no bet zero limit
                        if(numberPickerValue != 0) {
                            ((Activity_GameSettings)getActivity()).getTheGame().setQuantityOfLimitZeroBets(numberPickerValue);
                            ((Activity_GameSettings)getActivity()).getTheGame().setLimitZeroBets(true);
                        }
                        else{
                            ((Activity_GameSettings)getActivity()).getTheGame().setLimitZeroBets(false);
                        }

                        break;

                }

                textViewEditted.setText(String.valueOf(numberPickerValue));



                Toast toast = Toast.makeText(getActivity().getApplicationContext(), "Editado con exito", Toast.LENGTH_SHORT);
                toast.show();
                d.dismiss();
            }
        });


        Window window = d.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        d.show();
    }
}