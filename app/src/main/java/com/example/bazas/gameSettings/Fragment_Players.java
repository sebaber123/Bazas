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

import com.example.bazas.FragmentWithBackPress;
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



    public Fragment_Players() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static Fragment_Players newInstance(String param1, String param2) {
        Fragment_Players fragment = new Fragment_Players();
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

        //set the layout
        View view = inflater.inflate(R.layout.fragment_players, container, false);

        //get the players of the game
        players = ((Activity_GameSettings)getActivity()).getPlayers();

        //set the listview adapter
        ListView listview2 = (ListView) view.findViewById(R.id.players_list);
        listview2.setAdapter(new Adapter_AddPlayers(players, this));

        //add player button behaviour
        Button addPlayer = view.findViewById(R.id.añadir);
        addPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //get the edit text of the view
                EditText editText = (EditText)view.findViewById(R.id.nombre);

                //transform the text to lowerCase
                String name = editText.getText().toString().toLowerCase();

                //check if there is a name to add
                if (!name.equals("")){

                    //delete the game rounds because there is more players on the game
                    ((Activity_GameSettings)getActivity()).getTheGame().deleteAllRounds();

                    //add the player to the game
                    ((Activity_GameSettings)getActivity()).addPlayer(name);

                    //set the text of the player to add as empty
                    editText.setText("");

                    //toast text
                    Toast toast = Toast.makeText(getActivity().getApplicationContext(), "Jugador agregado con exito", Toast.LENGTH_SHORT);
                    toast.show();

                    //update the view
                    checkContinueButton();

                } else {

                    //toast text
                    Toast toast = Toast.makeText(getActivity().getApplicationContext(), "Debe ingresar el nombre de un jugador", Toast.LENGTH_SHORT);
                    toast.show();
                }

                //update the adapter
                ((Adapter_AddPlayers)listview2.getAdapter()).update();
            }
        });

        //behaviour of the continue button
        Button continueButton = view.findViewById(R.id.continue_button);
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //continue to the next fragment
                ((Activity_GameSettings)getActivity()).continueButton(1);
            }
        });

        //if the quantity of players is more than 2 the game can continue to the next fragment
        if (((Activity_GameSettings)getActivity()).getTheGame().getPlayers().size()>2){
            continueButton.setEnabled(true);
        }else{
            continueButton.setEnabled(false);
        }

        //behaviour of the back button
        Button backButton = view.findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override

            //return to the previous fragment
            public void onClick(View v) {
                ((Activity_GameSettings)getActivity()).backPressed(1);
            }
        });

        return view;
    }

    //delete a player from the game
    public void deletePlayer(Player player) {
        ((Activity_GameSettings)getActivity()).deletePlayer(player);
    }

    //check if the continue button must be enable
    public void checkContinueButton(){
        Button button = getView().findViewById(R.id.continue_button);

        //if the quantity of players is more than 2 the game can continue to the next fragment
        if (((Activity_GameSettings)getActivity()).getTheGame().getPlayers().size()>2){
            button.setEnabled(true);
        }else{
            button.setEnabled(false);
        }
    }

    //backPress behaviour
    @Override
    public void onBackPressed() {
        ((Activity_GameSettings)getActivity()).backPressed(1);
    }
}