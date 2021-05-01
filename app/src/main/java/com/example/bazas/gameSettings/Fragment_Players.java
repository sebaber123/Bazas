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
import com.example.bazas.gameSettings.Adapters.Adapter_AddPlayers;
import com.example.bazas.model.Player;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_Players#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_Players extends Fragment implements FragmentWithBackPress {

    public ArrayList<Player> players;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment_Players() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Players.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_Players newInstance(String param1, String param2) {
        Fragment_Players fragment = new Fragment_Players();
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
        View view = inflater.inflate(R.layout.fragment_players, container, false);



        players = ((Activity_GameSettings)getActivity()).getPlayers();
        ListView listview2 = (ListView) view.findViewById(R.id.players_list);
        listview2.setAdapter(new Adapter_AddPlayers(players, this));


        Button addPlayer = view.findViewById(R.id.añadir);
        addPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editText = (EditText)view.findViewById(R.id.nombre);
                String name = editText.getText().toString().toLowerCase();
                if (!name.equals("")){
                    ((Activity_GameSettings)getActivity()).getTheGame().deleteAllRounds();
                    ((Activity_GameSettings)getActivity()).addPlayer(name);
                    editText.setText("");
                    Toast toast = Toast.makeText(getActivity().getApplicationContext(), "Jugador agregado con exito", Toast.LENGTH_SHORT);
                    checkContinueButton();
                    toast.show();
                } else {
                    Toast toast = Toast.makeText(getActivity().getApplicationContext(), "Debe ingresar el nombre de un jugador", Toast.LENGTH_SHORT);
                    toast.show();
                }
                ((Adapter_AddPlayers)listview2.getAdapter()).update();
            }
        });

        Button continueButton = view.findViewById(R.id.continue_button);
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Activity_GameSettings)getActivity()).continueButton(1);
            }
        });
        if (((Activity_GameSettings)getActivity()).getTheGame().getPlayers().size()>2){
            continueButton.setEnabled(true);
        }else{
            continueButton.setEnabled(false);
        }

        return view;
    }

    public void deletePlayer(Player player) {
        ((Activity_GameSettings)getActivity()).deletePlayer(player);
    }

    public void checkContinueButton(){
        Button button = getView().findViewById(R.id.continue_button);
        if (((Activity_GameSettings)getActivity()).getTheGame().getPlayers().size()>2){
            button.setEnabled(true);
        }else{
            button.setEnabled(false);
        }
    }


    @Override
    public void onBackPressed() {
        ((Activity_GameSettings)getActivity()).backPressed(1);
    }
}