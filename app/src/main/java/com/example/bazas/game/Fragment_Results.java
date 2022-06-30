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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_Results#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_Results extends Fragment implements FragmentWithBackPress {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment_Results() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_Results.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_Results newInstance(String param1, String param2) {
        Fragment_Results fragment = new Fragment_Results();
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

        View view = inflater.inflate(R.layout.fragment__results, container, false);

        ArrayList<String> rounds = new ArrayList<String>();

        rounds.add("Resultados totales");
        for (int i = 1; i <= ((Activity_Game)getActivity()).getTheGame().getRounds().size(); i++) {
            rounds.add("Ronda" + String.valueOf(i));
        }

        RelativeLayout resultHeading = view.findViewById(R.id.result_heading);
        RelativeLayout totalResultHeading = view.findViewById(R.id.total_result_heading);

        final Spinner spinner = (Spinner) view.findViewById(R.id.result_spinner);

        final Adapter_Results_Spinner adapter = new Adapter_Results_Spinner(getActivity(), rounds);

        Fragment_Results theThis = this;
        ListView listview = (ListView) view.findViewById(R.id.results_list_view);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position == 0){

                    totalResultHeading.setVisibility(View.VISIBLE);
                    resultHeading.setVisibility(View.INVISIBLE);
                    ArrayList<Player> playersOrderByPosition = ((Activity_Game)getActivity()).getTheGame().getPlayers().stream().sorted((player1, player2) -> ((Activity_Game)getActivity()).getTheGame().whoHasMorePoints(player1, player2)).collect(Collectors
                            .toCollection(ArrayList::new));
                    /*playersOrderByPosition.size();
                    ArrayList<Player> players = ((Activity_Game)getActivity()).getTheGame().getPlayers();
                    players.size();*/

                    listview.setAdapter(new Adapter_Total_Results(theThis, playersOrderByPosition));





                }

                else{

                    resultHeading.setVisibility(View.VISIBLE);
                    totalResultHeading.setVisibility(View.INVISIBLE);
                    listview.setAdapter(new Adapter_Round_Results(theThis, ((Activity_Game)getActivity()).getTheGame().getPlayers(), position-1));

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //
            }
        });

        Button returnButton = view.findViewById(R.id.return_button);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity_Game)getActivity()).continueButton("round");
            }
        });

        return view;
    }

    @Override
    public void onBackPressed() {
        ((Activity_Game)getActivity()).backButton("no round");
    }
}