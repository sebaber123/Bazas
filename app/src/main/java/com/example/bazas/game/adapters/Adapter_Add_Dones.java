package com.example.bazas.game.adapters;

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

    //construct the view
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View view = null;

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_item_add_dones, null);
        }

        //set player name text
        TextView playerName = view.findViewById(R.id.player_name);
        playerName.setText(getPlayerByPosition(position).getName());

        //get player points done text
        TextView playerPointsDone= view.findViewById(R.id.points_done_text);
        TextView playerDones= view.findViewById(R.id.dones_text);

        //set the amount of points if the done is loaded unless set "-"
        if (getPlayerRoundByPosition(position).isDoneLoaded()){
            playerDones.setText(String.valueOf(getPlayerRoundByPosition(position).getDone()));
            playerPointsDone.setText(String.valueOf(((Activity_Game)context.getActivity()).getTheGame().calculatePoints(getPlayerRoundByPosition(position).getBet(), getPlayerRoundByPosition(position).getDone())));
        }else{
            playerDones.setText("-");
            playerPointsDone.setText("-");
        }

        //behavior of the "edit" button
        ImageButton editButton = view.findViewById(R.id.button_edit);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //open the number picker dialog
                openNumberPickerDialog(position);
            }
        });

        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void openNumberPickerDialog(int position) {

        //get the quantities that the player could have done
        ArrayList<String> numberThatCanDone = ((Activity_Game)context.getActivity()).getTheGame().getQuantitiesThatCanDoneThePlayer(position);

        //if the number of dones are equal to the quantity of cards of the round the player must have done 0
        /*if (numberThatCanDone.size()==1){
            Toast toast = Toast.makeText(((Activity_Game)context.getActivity()).getApplicationContext(), "Solo puede cargar 0. No restan bazas!", Toast.LENGTH_SHORT);
            toast.show();
        }*/

        //transform the arrayList to an array
        String[] numberThatCanBetToDisplay = numberThatCanDone.toArray(new String[0]);

        //create the Dialog window
        Dialog d = new Dialog((Activity_Game)context.getActivity());

        //set the layout of the dialog
        d.setContentView(R.layout.number_picker_dialog_add_dones);



        //set the title of the dialog
        TextView title = d.findViewById(R.id.text_title);
        title.setText("Bazas hechas por " + getPlayerByPosition(position).getName() + "\n (Pidió: " + String.valueOf(getPlayerRoundByPosition(position).getBet()) + ")");

        //create the number picker
        final NumberPicker np = (NumberPicker) d.findViewById(R.id.numberPicker);

        //set the numbers to display
        np.setDisplayedValues(numberThatCanBetToDisplay);

        np.setMaxValue(numberThatCanBetToDisplay.length - 1);
        np.setMinValue(0);

        //if the player is editing the quantity of dones set the selected value in the number picker
        if (getPlayerRoundByPosition(position).isDoneLoaded()){
            np.setValue(numberThatCanDone.indexOf(String.valueOf(getPlayerRoundByPosition(position).getDone())));

        //else set the value of 0
        } else{
            np.setValue(0);
        }

        //behaviour of the "cancel" button
        np.setWrapSelectorWheel(false);
        Button cancelButton = (Button) d.findViewById(R.id.button_cancel);
        cancelButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                d.dismiss();
            }
        });

        //behaviour of the "edit" button
        Button editButton = (Button) d.findViewById(R.id.button_edit_done);
        editButton.setText("Editar");
        editButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                //behavior of the onClick
                editOrNextDialog(d, position, numberThatCanDone, np  );

            }
        });

        //behaviour of the "next" button
        Button nextDoneButton = (Button) d.findViewById(R.id.button_next_done);
        if (position == (((Activity_Game)context.getActivity()).getTheGame().getPlayers().size()-1)){
            nextDoneButton.setEnabled(false);
        }
        nextDoneButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                //behavior of the onClick
                editOrNextDialog(d, position, numberThatCanDone, np  );
                openNumberPickerDialog(position+1);
            }
        });



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
    public void editOrNextDialog(Dialog d, int position, ArrayList<String> numberThatCanDone, NumberPicker np  ){

        int quantity = Integer.valueOf(numberThatCanDone.get(np.getValue()));

        //set the values selected to the PlayerRound
        getPlayerRoundByPosition(position).setDone(quantity);
        getPlayerRoundByPosition(position).setDoneLoaded(true);

        context.updateMissingText();

        //update the view
        update();

        //create a toast message
        Toast toast = Toast.makeText(((Activity_Game)context.getActivity()).getApplicationContext(), "Carga realizada con exito", Toast.LENGTH_SHORT);
        toast.show();

        //close the dialog
        d.dismiss();
    }
}
