package com.example.bazas.game.adapters;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.example.bazas.R;
import com.example.bazas.game.Activity_Game;
import com.example.bazas.game.Fragment_Add_Bets;
import com.example.bazas.model.Player;
import com.example.bazas.model.PlayerRound;

import java.util.ArrayList;

public class Adapter_Add_Bets extends BaseAdapter implements ListAdapter {
    private ArrayList<Player> list = new ArrayList<Player>();
    private Fragment_Add_Bets context;

    //constructor
    public Adapter_Add_Bets(Fragment_Add_Bets context, ArrayList<Player> playersList) {
        this.list = playersList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int pos) {
        return list.get(pos);
    }

    @Override
    public long getItemId(int pos) {
        return 0;
        //just return 0 if your list items do not have an Id variable.
    }


    //construct the view
    @SuppressLint("ResourceAsColor")
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = null;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_item_add_bets, null);
        }

        context.checkContinueButton();

        //set the name of the player
        TextView playerName = view.findViewById(R.id.player_name);
        playerName.setText(getPlayerByPosition(position).getName());

        //set the text of how much the player has bet if the bet is loaded
        TextView playerBet= view.findViewById(R.id.player_bet);
        if (getPlayerRoundByPosition(position).isLoaded()){
            playerBet.setText(String.valueOf(getPlayerRoundByPosition(position).getBet()));
        } else {
            playerBet.setText("");
        }

        //set the visibility of the edit button and check if the last player can edit the bet
        ImageButton editButton = view.findViewById(R.id.button_edit);
        if (position == ((Activity_Game)context.getActivity()).getTheGame().getPlayers().size() - 1 && ((Activity_Game)context.getActivity()).getTheGame().lastPlayerCantBet(position)){
            editButton.setEnabled(false);
            editButton.setVisibility(View.INVISIBLE);
        }

        //add the onClickListener to open the dialog to select the bet
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNumberPickerDialog(position);

            }
        });

        return view;
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void openNumberPickerDialog(int position) {

        //get the quantities that the player can bet
        ArrayList<String> numberThatCanBet = ((Activity_Game)context.getActivity()).getTheGame().getQuantitiesThatCanBetThePlayer(position);

        //transform the arrayList to an array
        String[] numberThatCanBetToDisplay = numberThatCanBet.toArray(new String[0]);

        //create the Dialog window
        Dialog d = new Dialog((Activity_Game)context.getActivity());

        //set the layout of the dialog
        d.setContentView(R.layout.number_picker_dialog_add_bets);

        //set the title of the dialog
        TextView title = d.findViewById(R.id.text_title);
        title.setText("Ingrese la apuesta de " + getPlayerByPosition(position).getName());

        //create the number picker
        final NumberPicker np = (NumberPicker) d.findViewById(R.id.numberPicker);

        //set the numbers to display
        np.setDisplayedValues(numberThatCanBetToDisplay);

        np.setMaxValue(numberThatCanBetToDisplay.length - 1);
        np.setMinValue(0);

        //if the player is editing the bet set the selected value in the number picker
        if (getPlayerRoundByPosition(position).isLoaded()){
            np.setValue(numberThatCanBet.indexOf(String.valueOf(getPlayerRoundByPosition(position).getBet())));

        //else set the value of 0
        } else{
            np.setValue(0);
        }

        np.setWrapSelectorWheel(false);

        //behaviour of the "cancel" button
        Button cancelButton = (Button) d.findViewById(R.id.button_cancel);
        cancelButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                d.dismiss();
            }
        });

        //behaviour of the "edit" button
        Button addRoundsButton = (Button) d.findViewById(R.id.button_edit_bet);
        addRoundsButton.setText("Editar");
        addRoundsButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {

                editOrNextDialog ( d, position, numberThatCanBet, np  );
            }
        });

        //behaviour of the "next" button
        Button nextBetButton = (Button) d.findViewById(R.id.button_next_bet);
        if (position == (((Activity_Game)context.getActivity()).getTheGame().getPlayers().size()-1)){
            nextBetButton.setEnabled(false);
        }
        nextBetButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {

                //behavior of the click
                editOrNextDialog ( d, position, numberThatCanBet, np  );

                //open the Dialog of the next player
                openNumberPickerDialog(position+1);
            }
        });


        //show the dialog
        Window window = d.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        d.show();
    }

    //get a Player by the position of the round
    private Player getPlayerByPosition(int position){
        return list.get(((Activity_Game)context.getActivity()).getTheGame().getPositionOfPlayerOnActualRound(position));
    }

    //get a PlayerRound by the position of the round
    private PlayerRound getPlayerRoundByPosition(int position){
        return (((Activity_Game)context.getActivity()).getTheGame().getPlayerRoundOfPositionOnActualRound(position));
    }

    //update the view and check if the continue button must be enable or disable
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void update(){
        notifyDataSetChanged();
        context.checkContinueButton();
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void editOrNextDialog (Dialog d, int position, ArrayList<String> numberThatCanBet, NumberPicker np  ){

        //set the values selected to the PlayerRound
        getPlayerRoundByPosition(position).setBet(Integer.valueOf(numberThatCanBet.get(np.getValue())));
        getPlayerRoundByPosition(position).setLoaded(true);

        //close the dialog
        d.dismiss();

        //if a player edit his bet then check if the last bet is available (because the amount of cards cant
        // be equal to the amount of bets)
        if (position != ((Activity_Game)context.getActivity()).getTheGame().getPlayers().size() - 1){

            if (((Activity_Game)context.getActivity()).getTheGame().checkThatTheLastBetIsAvailable()){

                ((Activity_Game)context.getActivity()).getTheGame().removeLastBet();

                //show a confirm dialog to return to the menu
                //create the Dialog window
                Dialog d2 = new Dialog(context.getActivity());

                //set the layout of the dialog
                d2.setContentView(R.layout.dialog_last_bet_removed);



                //behaviour of the "accept" button
                Button acceptButton = (Button) d2.findViewById(R.id.button_accept);
                acceptButton.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v) {

                        d2.dismiss();
                        //behavior of the onClick

                    }
                });

                Window window = d.getWindow();
                window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                d2.show();

            }


        }

        //update the view
        update();

        //create a toast message
        Toast toast = Toast.makeText(((Activity_Game)context.getActivity()).getApplicationContext(), "Apuesta editada con exito", Toast.LENGTH_SHORT);
        toast.show();



    }

}
