package com.mangat.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;

public class single_player_name extends AppCompatActivity {

    EditText name = null;

    SharedPreferences sp;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_player_name);
        name = (EditText) findViewById(R.id.txtName);

        sp = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sp.edit();


    }

    public void saveName(View view)
    {
        String player = name.getText().toString();

        editor.putInt("countOfPlayers",1);
        editor.putString("PlayerName",player);
        editor.commit();


        Intent intent = new Intent(single_player_name.this, MainActivity.class);
        startActivity(intent);


    }
}
