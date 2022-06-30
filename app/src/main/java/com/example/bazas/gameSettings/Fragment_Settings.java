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

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment_Settings() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static Fragment_Settings newInstance(String param1, String param2) {
        Fragment_Settings fragment = new Fragment_Settings();
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
        View view = inflater.inflate(R.layout.fragment__settings, container, false);

        Button continueButton = view.findViewById(R.id.continue_button);
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity_GameSettings)getActivity()).continueButton(0);
            }
        });

        Button backButton = view.findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity_GameSettings)getActivity()).backPressed(0);
            }
        });

        Button pointsIfDoneEditButton = view.findViewById(R.id.edit_points_if_done);
        pointsIfDoneEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog d = new Dialog((Activity_GameSettings)getActivity());
                d.setContentView(R.layout.number_picker_dialog_generate_rounds);
                final NumberPicker np = (NumberPicker) d.findViewById(R.id.numberPicker);
                np.setMaxValue(50);
                np.setMinValue(5);
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

                        int pointsIfDone = np.getValue();
                        TextView pointsIfDoneText = view.findViewById(R.id.points_if_done_text);
                        pointsIfDoneText.setText(String.valueOf(pointsIfDone));
                        ((Activity_GameSettings)getActivity()).getTheGame().setPointsIfDone(pointsIfDone);


                        Toast toast = Toast.makeText(getActivity().getApplicationContext(), "Editado con exito", Toast.LENGTH_SHORT);
                        toast.show();
                        d.dismiss();
                    }
                });


                Window window = d.getWindow();
                window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                d.show();
            }
        });


        return view;
    }

    @Override
    public void onBackPressed() {
        ((Activity_GameSettings)getActivity()).backPressed(0);
    }
}