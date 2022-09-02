package com.example.bazas.gameSettings.Adapters;

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

    //add the option of the different decks
    public Adapter_SelectDeck(Fragment_Select_deck context) {

        //"truco" deck
        this.list.add(40);

        //"spanish" deck
        this.list.add(48);

        //"poker" deck
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

    //construct the view
    @SuppressLint("ResourceAsColor")
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = null;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_item_select_deck, null);
        }

        //imageView that will be fill with the image of the deck
        ImageView imageView = view.findViewById(R.id.deck);

        //TextView that will be fill with the text of the deck
        TextView textDeck = view.findViewById(R.id.text_deck);

        //TextView that will be fill with the quantity of cards of the deck
        TextView textQuantities = view.findViewById(R.id.quantity_of_cards);

        //used to change the background if the deck has been selected
        RelativeLayout background = view.findViewById(R.id.selection);

        //behavior of the select button
        Button selectButton = view.findViewById(R.id.select_button);
        selectButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {

                //set the deck to the game
                context.getTheGame().setDeck(list.get(position));

                //delete all the game rounds (if there are generated before)
                context.getTheGame().deleteAllRounds();

                //check if the continue button must be enable or not
                context.checkContinueButton();

                //update the view
                notifyDataSetChanged();
            }
        });


        //if the deck is selected
        if (context.getTheGame().getDeck() == list.get(position)){

            //change the background color to the selected one
            background.setBackgroundColor(context.getActivity().getResources().getColor(R.color.cyan_400));

            //set the button enable in false
            selectButton.setEnabled(false);

            //change the text of the button to "selected"
            selectButton.setText("Seleccionado");
        }

        //add the text, images and quantities of cards to each option
        switch (list.get(position)){

            //"truco" deck
            case 40:
                imageView.setImageResource(R.drawable.truco_deck);
                textDeck.setText("Mazo de truco");
                textQuantities.setText("(40 cartas)");
                break;

            //"spanish" deck
            case 48:
                imageView.setImageResource(R.drawable.spanish_deck);
                textDeck.setText("Mazo español");
                textQuantities.setText("(48 cartas)");
                break;

            //"poker" deck
            case 52:
                imageView.setImageResource(R.drawable.poker_deck);
                textDeck.setText("Mazo de poker");
                textQuantities.setText("(52 cartas)");
                break;
        }


        return view;

    }

    //update the view
    public void update(){
        notifyDataSetChanged();
    }
}
