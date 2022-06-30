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
import com.example.bazas.game.Fragment_Add_Dones;
import com.example.bazas.model.Player;
import com.example.bazas.model.PlayerRound;

import java.util.ArrayList;

public class Adapter_Add_Dones extends BaseAdapter implements ListAdapter {
    private ArrayList<Player> list = new ArrayList<Player>();
    private Fragment_Add_Dones context;


    public Adapter_Add_Dones(Fragment_Add_Dones context, ArrayList<Player> playersList) {
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

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = null;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_item_add_dones, null);
        }

        TextView playerName = view.findViewById(R.id.player_name);
        playerName.setText(getPlayerByPosition(position).getName());

        TextView playerPointsDone= view.findViewById(R.id.points_done_text);
        TextView playerDones= view.findViewById(R.id.dones_text);
        if (getPlayerRoundByPosition(position).isDoneLoaded()){
            playerDones.setText(String.valueOf(getPlayerRoundByPosition(position).getDone()));
            playerPointsDone.setText(String.valueOf(((Activity_Game)context.getActivity()).getTheGame().calculatePoints(getPlayerRoundByPosition(position).getBet(), getPlayerRoundByPosition(position).getDone())));
        }else{
            playerDones.setText("-");
            playerPointsDone.setText("-");
        }

        ImageButton editButton = view.findViewById(R.id.button_edit);
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

        ArrayList<String> numberThatCanDone = ((Activity_Game)context.getActivity()).getTheGame().getQuantitiesThatCanDoneThePlayer(position);

        if (numberThatCanDone.size()==1){
            Toast toast = Toast.makeText(((Activity_Game)context.getActivity()).getApplicationContext(), "Solo puede cargar 0. No restan bazas!", Toast.LENGTH_SHORT);
            toast.show();
        }

        String[] numberThatCanBetToDisplay = numberThatCanDone.toArray(new String[0]);

        Dialog d = new Dialog((Activity_Game)context.getActivity());

        d.setContentView(R.layout.number_picker_dialog_add_dones);

        final NumberPicker np = (NumberPicker) d.findViewById(R.id.numberPicker);
        TextView title = d.findViewById(R.id.text_title);
        title.setText("Bazas hechas por " + getPlayerByPosition(position).getName() + "\n (Pidio: " + String.valueOf(getPlayerRoundByPosition(position).getBet()) + ")");

        np.setDisplayedValues(numberThatCanBetToDisplay);

        np.setMaxValue(numberThatCanBetToDisplay.length - 1);

        np.setMinValue(0);

        if (getPlayerRoundByPosition(position).isDoneLoaded()){
            np.setValue(numberThatCanDone.indexOf(String.valueOf(getPlayerRoundByPosition(position).getDone())));
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


        Button addRoundsButton = (Button) d.findViewById(R.id.button_edit_done);
        addRoundsButton.setText("Editar");
        addRoundsButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                getPlayerRoundByPosition(position).setDone(Integer.valueOf(numberThatCanDone.get(np.getValue())));
                getPlayerRoundByPosition(position).setDoneLoaded(true);

                update();

                Toast toast = Toast.makeText(((Activity_Game)context.getActivity()).getApplicationContext(), "Carga realizada con exito", Toast.LENGTH_SHORT);
                toast.show();

                d.dismiss();
            }
        });

        Button nextBetButton = (Button) d.findViewById(R.id.button_next_done);
        if (position == (((Activity_Game)context.getActivity()).getTheGame().getPlayers().size()-1)){
            nextBetButton.setEnabled(false);
        }
        nextBetButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                getPlayerRoundByPosition(position).setDone(Integer.valueOf(numberThatCanDone.get(np.getValue())));
                getPlayerRoundByPosition(position).setDoneLoaded(true);

                update();

                Toast toast = Toast.makeText(((Activity_Game)context.getActivity()).getApplicationContext(), "Carga realizada con exito", Toast.LENGTH_SHORT);
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
