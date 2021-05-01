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
import com.example.bazas.gameSettings.Activity_GameSettings;
import com.example.bazas.model.Player;
import com.example.bazas.model.PlayerRound;

import java.util.ArrayList;

public class Adapter_Add_Bets extends BaseAdapter implements ListAdapter {
    private ArrayList<Player> list = new ArrayList<Player>();
    private Fragment_Add_Bets context;


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

        TextView playerName = view.findViewById(R.id.player_name);
        playerName.setText(getPlayerByPosition(position).getName());

        TextView playerBet= view.findViewById(R.id.player_bet);
        if (getPlayerRoundByPosition(position).isLoaded()){
            playerBet.setText(String.valueOf(getPlayerRoundByPosition(position).getBet()));
        } else {
            playerBet.setText("");
        }

        ImageButton editButton = view.findViewById(R.id.button_edit);
        if (position == ((Activity_Game)context.getActivity()).getTheGame().getPlayers().size() - 1 && ((Activity_Game)context.getActivity()).getTheGame().lastPlayerCantBet(position)){
            editButton.setEnabled(false);
            editButton.setVisibility(View.INVISIBLE);
        }
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

        ArrayList<String> numberThatCanBet = ((Activity_Game)context.getActivity()).getTheGame().getQuantitiesThatCanBetThePlayer(position);

        String[] numberThatCanBetToDisplay = numberThatCanBet.toArray(new String[0]);

        Dialog d = new Dialog((Activity_Game)context.getActivity());

        d.setContentView(R.layout.number_picker_dialog_add_bets);

        final NumberPicker np = (NumberPicker) d.findViewById(R.id.numberPicker);
        TextView title = d.findViewById(R.id.text_title);
        title.setText("Ingrese la apuesta de " + getPlayerByPosition(position).getName());

        np.setDisplayedValues(numberThatCanBetToDisplay);

        np.setMaxValue(numberThatCanBetToDisplay.length - 1);

        np.setMinValue(0);

        if (getPlayerRoundByPosition(position).isLoaded()){
            np.setValue(numberThatCanBet.indexOf(String.valueOf(getPlayerRoundByPosition(position).getBet())));
        } else{
            np.setValue(0);
        }
        np.setWrapSelectorWheel(false);
        Button cancelButton = (Button) d.findViewById(R.id.button_cancel);
        cancelButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                d.dismiss();
            }
        });


        Button addRoundsButton = (Button) d.findViewById(R.id.button_edit_bet);
        addRoundsButton.setText("Editar");
        addRoundsButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                getPlayerRoundByPosition(position).setBet(Integer.valueOf(numberThatCanBet.get(np.getValue())));
                getPlayerRoundByPosition(position).setLoaded(true);

                if (position != ((Activity_Game)context.getActivity()).getTheGame().getPlayers().size() - 1){

                    ((Activity_Game)context.getActivity()).getTheGame().checkThatTheLastBetIsAvailable();
                }

                update();

                Toast toast = Toast.makeText(((Activity_Game)context.getActivity()).getApplicationContext(), "Apuesta editada con exito", Toast.LENGTH_SHORT);
                toast.show();

                d.dismiss();
            }
        });

        Button nextBetButton = (Button) d.findViewById(R.id.button_next_bet);
        if (position == (((Activity_Game)context.getActivity()).getTheGame().getPlayers().size()-1)){
            nextBetButton.setEnabled(false);
        }
        nextBetButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                getPlayerRoundByPosition(position).setBet(Integer.valueOf(numberThatCanBet.get(np.getValue())));
                getPlayerRoundByPosition(position).setLoaded(true);

                if (position != ((Activity_Game)context.getActivity()).getTheGame().getPlayers().size() - 1){

                    ((Activity_Game)context.getActivity()).getTheGame().checkThatTheLastBetIsAvailable();
                }

                update();

                Toast toast = Toast.makeText(((Activity_Game)context.getActivity()).getApplicationContext(), "Apuesta editada con exito", Toast.LENGTH_SHORT);
                toast.show();

                d.dismiss();
                openNumberPickerDialog(position+1);
            }
        });



        Window window = d.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        d.show();
    }

    private Player getPlayerByPosition(int position){
        return list.get(((Activity_Game)context.getActivity()).getTheGame().playerOfPositionOnActualRound(position));
    }

    private PlayerRound getPlayerRoundByPosition(int position){
        return (((Activity_Game)context.getActivity()).getTheGame().getPlayerRoundOfPositionOnActualRound(position));
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void update(){
        notifyDataSetChanged();
        context.checkContinueButton();
    }

}
