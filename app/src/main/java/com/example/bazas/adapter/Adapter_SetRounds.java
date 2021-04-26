package com.example.bazas.adapter;

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

    @SuppressLint("ResourceAsColor")
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = null;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_item_set_rounds, null);
        }


        TextView roundNumber = view.findViewById(R.id.round_number);
        roundNumber.setText(String.valueOf(position+1));

        TextView quantityOfCards = view.findViewById(R.id.quantity_of_cards);
        quantityOfCards.setText(String.valueOf(list.get(position).getQuantity_of_cards()));

        ImageButton visibility = view.findViewById(R.id.visibility);
        ImageButton visibilityOff = view.findViewById(R.id.visibility_off);

        if (list.get(position).isVisibility()){
            visibility.setVisibility(View.VISIBLE);
        }else{
            visibilityOff.setVisibility(View.VISIBLE);
        }

        visibility.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                list.get(position).setVisibility(false);
                Toast toast = Toast.makeText(context.getActivity().getApplicationContext(), "Ronda sin muestra", Toast.LENGTH_SHORT);
                toast.show();
                notifyDataSetChanged();
            }
        });

        visibilityOff.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                list.get(position).setVisibility(true);
                Toast toast = Toast.makeText(context.getActivity().getApplicationContext(), "Ronda con muestra", Toast.LENGTH_SHORT);
                toast.show();
                notifyDataSetChanged();
            }
        });

        ImageButton editButton = view.findViewById(R.id.button_edit);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog d = new Dialog((Activity_GameSettings)context.getActivity());
                d.setContentView(R.layout.number_picker_dialog_generate_rounds);
                final NumberPicker np = (NumberPicker) d.findViewById(R.id.numberPicker);
                TextView title = d.findViewById(R.id.text_title);
                title.setText("Ingrese la cantidad de cartas para la ronda");
                np.setMaxValue(((Activity_GameSettings)context.getActivity()).getTheGame().getMaxQuantityOfCardPerRound());
                np.setMinValue(1);
                np.setValue(list.get(position).getQuantity_of_cards());
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
                addRoundsButton.setText("Editar");
                addRoundsButton.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v) {
                        list.get(position).setQuantity_of_cards(np.getValue());

                        update();

                        Toast toast = Toast.makeText(((Activity_GameSettings)context.getActivity()).getApplicationContext(), "Ronda editada con exito", Toast.LENGTH_SHORT);
                        toast.show();

                        d.dismiss();
                    }
                });


                Window window = d.getWindow();
                window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                d.show();
            }
        });


        ImageButton deleteButton = view.findViewById(R.id.delete);
        deleteButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context.getActivity(), R.style.alertDialogStyle);
                builder.setTitle("Confirmacion");
                builder.setMessage("Seguro que quiere borrar la ronda?");
                builder.setPositiveButton("Borrar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        context.deleteRound(list.get(position));
                        context.checkContinueButton();
                        Toast toast = Toast.makeText(context.getActivity().getApplicationContext(), "Ronda eliminada con exito", Toast.LENGTH_SHORT);
                        toast.show();
                        notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("Cancelar", null);

                AlertDialog dialog = builder.create();
                dialog.show();


            }
        });


        return view;

    }

    public void update(){
        notifyDataSetChanged();
        context.checkContinueButton();
    }
}
