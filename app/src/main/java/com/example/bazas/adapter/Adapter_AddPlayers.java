package com.example.bazas.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.example.bazas.R;
import com.example.bazas.gameSettings.Fragment_Players;
import com.example.bazas.model.Player;

import java.util.ArrayList;
import java.util.stream.Collectors;


public class Adapter_AddPlayers extends BaseAdapter implements ListAdapter {
    private ArrayList<Player> list = new ArrayList<Player>();
    private Fragment_Players context;


    public Adapter_AddPlayers(ArrayList<Player> list, Fragment_Players context) {
        this.list = list;
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
            view = inflater.inflate(R.layout.list_item_add_players, null);
        }

        //Handle TextView and display string from your list
        TextView name= (TextView)view.findViewById(R.id.player_name);
        name.setText(list.get(position).getName());

        //Handle buttons and add onClickListeners
        ImageButton deletebtn= (ImageButton) view.findViewById(R.id.delete);


        deletebtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context.getActivity(), R.style.alertDialogStyle);
                builder.setTitle("Confirmacion");
                builder.setMessage("Seguro que quiere borrar al jugador?");
                builder.setPositiveButton("Borrar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        context.deletePlayer(list.get(position));
                        context.checkContinueButton();
                        Toast toast = Toast.makeText(context.getActivity().getApplicationContext(), "Jugador eliminado con exito", Toast.LENGTH_SHORT);
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
    }
}
