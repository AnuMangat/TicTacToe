package com.mangat.tictactoe;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

/**
 *
 * Activity class for multi player name
 *
 */
public class multi_player_name extends AppCompatActivity {

    //Initialize the parameters

    EditText name1 = null;
    EditText name2 = null;
    SharedPreferences sp;
    SharedPreferences.Editor editor;

    /**
     *
     * on create function for the activity
     *
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiple_player_name);

        // Get the shared preference object
        name1 = (EditText) findViewById(R.id.player1);
        name2 = (EditText) findViewById(R.id.player2);
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sp.edit();
    }

    /**
     *
     * Function to save the names
     *
     *
     * @param view
     */

    public void saveName(View view)
    {
        /**
         *
         * Saving the player names to the shared preferences
         *
         *
         */
        String player1 = name1.getText().toString();
        String player2 = name2.getText().toString();

        editor.putInt("countOfPlayers",2);
        editor.putString("PlayerName1",player1);
        editor.putString("PlayerName2",player2);
        editor.commit();

        Intent intent = new Intent(multi_player_name.this, MainActivity.class);
        startActivity(intent);

    }

}
