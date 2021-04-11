package com.example.bazas.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
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
import com.example.bazas.gameSettings.Fragment_Select_deck;
import com.example.bazas.gameSettings.Fragment_Set_Rounds;
import com.example.bazas.model.Round;

import java.util.ArrayList;

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
                Toast toast = Toast.makeText(context.getActivity().getApplicationContext(), "Ronda sin muestra", Toast.LENGTH_LONG);
                toast.show();
                notifyDataSetChanged();
            }
        });

        visibilityOff.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                list.get(position).setVisibility(true);
                Toast toast = Toast.makeText(context.getActivity().getApplicationContext(), "Ronda con muestra", Toast.LENGTH_LONG);
                toast.show();
                notifyDataSetChanged();
            }
        });


        return view;

    }

    public void update(){
        notifyDataSetChanged();
    }
}
