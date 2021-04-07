package com.example.bazas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.bazas.gameSettings.Activity_GameSettings;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void iniciarPartida(View view) {
        Intent intent = new Intent(this, Activity_GameSettings.class);
        startActivity(intent);
    }
}