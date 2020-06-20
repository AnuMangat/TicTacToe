package com.mangat.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 *
 * This is the main activity of the app.
 *
 *
 */
public class MainActivity extends AppCompatActivity {

    /**
     *
     *
     * Initialize the variables
     *
     *
     */
    String newPlayer1 = "";
    String newPlayer2 = "";
    TextView welcome = null;

    SharedPreferences sp;
    SharedPreferences.Editor editor;

    /**
     *
     * oncreate Function of the main activity
     *
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sp = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sp.edit();


        String[] options_multi = {"Enter Name - 1P", "Play Game - 1P","Enter Name - 2P","Play Game - 2P", "Show Standings "};

        /**
         *
         * Add the main app options into a listview
         *
         *
         */

        ListAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,options_multi);
        ListView optionView = (ListView) findViewById(R.id.list_view);

        optionView.setAdapter(adapter);

        /**
         *
         *
         * Adding the click item listener to the list view
         *
         */

        optionView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                /**
                 *
                 * Call activity to enter name for 1 Player name
                 *
                 */

                if(position == 0){
                    Intent intent = new Intent(MainActivity.this, single_player_name.class);
                    startActivity(intent);
                }

                /**
                 *
                 * Call activity to play 1 Player game
                 *
                 *
                 */

                if(position == 1){
                    Intent intent = new Intent(MainActivity.this, single_player_game.class);
                    intent.putExtra("PlayerName",newPlayer1);
                    startActivity(intent);
                }

                /**
                 *
                 * Call activity to enter 2 Players name
                 *
                 *
                 */

                if(position == 2){
                    Intent intent = new Intent(MainActivity.this, multi_player_name.class);
                    editor.clear();
                    startActivity(intent);
                }
                /**
                 *
                 * Call activity to play 2 Players game
                 *
                 *
                 */

                if(position == 3){
                    Intent intent = new Intent(MainActivity.this, multi_player_game.class);
                    intent.putExtra("PlayerName1",newPlayer1);
                    intent.putExtra("PlayerName2",newPlayer2);


                    startActivity(intent);
                }
                /**
                 *
                 * Call activity to show the game data results
                 *
                 *
                 */

                if(position == 4){
                    Intent intent = new Intent(MainActivity.this,showstandings.class);
                    editor.clear();
                    startActivity(intent);
                }

            }
        });

        /**
         *
         * Update the welcome message using the player names
         *
         *
         */

        int count  = sp.getInt("countOfPlayers",-1);

        if(count == 1)
        {
            //single player

            newPlayer1 = sp.getString("PlayerName","");
            welcome = (TextView) findViewById(R.id.txtWelcome);

            welcome.setText("Welcome " + newPlayer1);

        }

        else if(count == 2 )
        {
           //multi player
           newPlayer1 = sp.getString("PlayerName1","");
           newPlayer2 = sp.getString("PlayerName2","");

           welcome = (TextView) findViewById(R.id.txtWelcome);

           welcome.setText("Welcome " + newPlayer1 + " & " + newPlayer2);

        }


    }
}