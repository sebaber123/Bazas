package com.example.bazas.game.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bazas.R;

import java.util.ArrayList;

public class Adapter_Results_Spinner extends ArrayAdapter<String> {
    Context context;
    int flags[];
    ArrayList<String> rounds;
    LayoutInflater inflater;

    public Adapter_Results_Spinner(Context applicationContext, ArrayList<String> rounds) {
        super(applicationContext,0,rounds);
        this.context = applicationContext;
        this.rounds = rounds;
        inflater = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return rounds.size();
    }

    @Override
    public String getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.list_item_results_spinner, null);

        TextView roundText = (TextView) view.findViewById(R.id.round_text);
        roundText.setText(rounds.get(position));

        View dividerBottom = view.findViewById(R.id.dividerbottom);
        dividerBottom.setVisibility(View.INVISIBLE);

        return view;
    }

    @Override
    public View getDropDownView(int position, View view, ViewGroup viewGroup) {
        if(view == null){
            view = inflater.inflate(R.layout.list_item_results_spinner, viewGroup, false);
        }

        TextView roundText = (TextView) view.findViewById(R.id.round_text);
        roundText.setText(rounds.get(position));

        ImageView arrowIcon = (ImageView) view.findViewById(R.id.arrow_down);
        arrowIcon.setVisibility(View.INVISIBLE);

        if ((position + 1) == rounds.size()){
            View dividerBottom = view.findViewById(R.id.dividerbottom);
            dividerBottom.setVisibility(View.INVISIBLE);
        }

        return view;
    }

}


