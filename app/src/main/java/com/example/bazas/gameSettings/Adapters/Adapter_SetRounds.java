package com.example.bazas.gameSettings.Adapters;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.example.bazas.R;
import com.example.bazas.gameSettings.Activity_GameSettings;
import com.example.bazas.gameSettings.Fragment_Select_deck;
import com.example.bazas.gameSettings.Fragment_Set_Rounds;
import com.example.bazas.model.Round;

import java.util.ArrayList;
import java.util.Random;

public class Adapter_SetRounds extends BaseAdapter implements ListAdapter {
    private ArrayList<Round> list = new ArrayList<Round>();
    private Fragment_Set_Rounds context;

    //constructor
    public Adapter_SetRounds(Fragment_Set_Rounds context, ArrayList<Round> roundList) {
        this.list = roundList;
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
            view = inflater.inflate(R.layout.list_item_set_rounds, null);
        }

        //set the round number
        TextView roundNumber = view.findViewById(R.id.round_number);
        roundNumber.setText(String.valueOf(position+1));

        //set the quantity of cards of the round
        TextView quantityOfCards = view.findViewById(R.id.quantity_of_cards);
        quantityOfCards.setText(String.valueOf(list.get(position).getQuantity_of_cards()));

        //imageView that will be set as visible or invisible depending of the option selected
        ImageButton visibility = view.findViewById(R.id.visibility);

        //imageView that will be set as visible or invisible depending of the option selected
        ImageButton visibilityOff = view.findViewById(R.id.visibility_off);

        //set the image button as visible depending of the "visibility" attribute of the round
        if (list.get(position).isVisibility()){
            visibility.setVisibility(View.VISIBLE);
        }else{
            visibilityOff.setVisibility(View.VISIBLE);
        }

        //visibility image button behaviour
        visibility.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                //set visibility of the round as false
                list.get(position).setVisibility(false);

                //toast message
                Toast toast = Toast.makeText(context.getActivity().getApplicationContext(), "Ronda sin muestra", Toast.LENGTH_SHORT);
                toast.show();

                //update the view
                notifyDataSetChanged();
            }
        });

        //visibilityOff image button behaviour
        visibilityOff.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                //set visibility of the round as true
                list.get(position).setVisibility(true);

                //toast message
                Toast toast = Toast.makeText(context.getActivity().getApplicationContext(), "Ronda con muestra", Toast.LENGTH_SHORT);
                toast.show();

                //update the view
                notifyDataSetChanged();
            }
        });

        //edit button behaviour
        ImageButton editButton = view.findViewById(R.id.button_edit);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //create the dialog
                Dialog d = new Dialog((Activity_GameSettings)context.getActivity());

                //set the layout of the dialog
                d.setContentView(R.layout.number_picker_dialog_generate_rounds);

                //set title of the dialog
                TextView title = d.findViewById(R.id.text_title);
                title.setText("Ingrese la cantidad de cartas para la ronda");

                //create the number picker
                final NumberPicker np = (NumberPicker) d.findViewById(R.id.numberPicker);

                //set the max value depending the deck selected and the quantities of players in the game
                np.setMaxValue(((Activity_GameSettings)context.getActivity()).getTheGame().getMaxQuantityOfCardPerRound());
                np.setMinValue(1);

                //set the value
                np.setValue(list.get(position).getQuantity_of_cards());
                np.setWrapSelectorWheel(false);

                //set the behaviour of the cancel button of the dialog
                Button cancelButton = (Button) d.findViewById(R.id.button_cancel);
                cancelButton.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v) {

                        //close the dialog
                        d.dismiss();
                    }
                });

                //set the behaviour of the edit button of the dialog
                Button addRoundsButton = (Button) d.findViewById(R.id.button_add_rounds);
                addRoundsButton.setText("Editar");
                addRoundsButton.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v) {

                        //set the quantity of cards of the round with the quantity selected
                        list.get(position).setQuantity_of_cards(np.getValue());

                        update();

                        //toast text
                        Toast toast = Toast.makeText(((Activity_GameSettings)context.getActivity()).getApplicationContext(), "Ronda editada con exito", Toast.LENGTH_SHORT);
                        toast.show();

                        //close the dialog
                        d.dismiss();
                    }
                });


                Window window = d.getWindow();
                window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                //show the dialog
                d.show();
            }
        });

        //behavior of the delete button (open confirmation dialog)
        ImageButton deleteButton = view.findViewById(R.id.delete);
        deleteButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                //create the dialog builder
                AlertDialog.Builder builder = new AlertDialog.Builder(context.getActivity(), R.style.alertDialogStyle);

                //set the title of the dialog
                builder.setTitle("Confirmacion");

                //set the message of the dialog
                builder.setMessage("Seguro que quiere borrar la ronda?");

                //behaviour of the delete button (positive button)
                builder.setPositiveButton("Borrar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        //delete the round
                        context.deleteRound(list.get(position));

                        //check if the continue button must be enable or not
                        context.checkContinueButton();

                        //toast text
                        Toast toast = Toast.makeText(context.getActivity().getApplicationContext(), "Ronda eliminada con exito", Toast.LENGTH_SHORT);
                        toast.show();

                        //update the view
                        notifyDataSetChanged();
                    }
                });

                //behaviour of the cancel button (negative button)
                builder.setNegativeButton("Cancelar", null);

                //generate the dialog
                AlertDialog dialog = builder.create();

                //show the dialog
                dialog.show();


            }
        });


        return view;

    }

    //update the view and check if the continue button must be enable or disable
    public void update(){
        notifyDataSetChanged();
        context.checkContinueButton();
    }
}
