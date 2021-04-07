package com.example.bazas.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.example.bazas.R;
import com.example.bazas.gameSettings.Activity_GameSettings;
import com.example.bazas.gameSettings.Fragment_Players;
import com.example.bazas.gameSettings.Fragment_Select_deck;
import com.example.bazas.model.Player;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.stream.Collectors;


public class Adapter_SelectDeck extends BaseAdapter implements ListAdapter {
    private ArrayList<Integer> list = new ArrayList<Integer>();
    private Fragment_Select_deck context;


    public Adapter_SelectDeck(Fragment_Select_deck context) {
        this.list.add(40);
        this.list.add(48);
        this.list.add(52);
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
            view = inflater.inflate(R.layout.list_item_select_deck, null);
        }


        ImageView imageView = view.findViewById(R.id.deck);
        TextView textDeck = view.findViewById(R.id.text_deck);
        TextView textQuantities = view.findViewById(R.id.quantity_of_cards);
        RelativeLayout background = view.findViewById(R.id.selection);




        Button selectButton = view.findViewById(R.id.select_button);
        selectButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                context.getTheGame().setDeck(list.get(position));
                context.checkContinueButton();
                notifyDataSetChanged();
            }
        });

        if (context.getTheGame().getDeck() == list.get(position)){
            background.setBackgroundColor(context.getActivity().getResources().getColor(R.color.cyan_400));
            selectButton.setEnabled(false);
            selectButton.setText("Seleccionado");
        }


        switch (list.get(position)){
            case 40:
                imageView.setImageResource(R.drawable.truco_deck);
                textDeck.setText("Mazo de truco");
                textQuantities.setText("(40 cartas)");
                break;
            case 48:
                imageView.setImageResource(R.drawable.spanish_deck);
                textDeck.setText("Mazo español");
                textQuantities.setText("(48 cartas)");
                break;
            case 52:
                imageView.setImageResource(R.drawable.poker_deck);
                textDeck.setText("Mazo de poker");
                textQuantities.setText("(52 cartas)");
                break;
        }


        return view;

    }

    public void update(){
        notifyDataSetChanged();
    }
}
