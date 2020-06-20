package com.mangat.tictactoe;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;


import java.io.FileOutputStream;
import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * Activity class for 1 player game
 *
 */

public class single_player_game extends AppCompatActivity {

    //Initialize the parameters
    private static final String FILE_NAME = "gameData.txt";

    GridLayout grid;
    Button playBoard[][] = new Button[3][3];
    Button tempBoard[][] = new Button[3][3];
    int boardMatrix[][] = new int[3][3];
    double probMatrix[][] = new double[3][3];
    TextView player1Disp,player2Disp;
    String player1Name;
    String player2Name;
    int player1Win = 0, player2Win = 0;


    /**
     *
     * on create function of the activity
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_player_game);

        //Get the name of the player


        player1Disp = (TextView) findViewById(R.id.player1);
        player2Disp = (TextView) findViewById(R.id.player2);

        Intent intent = getIntent();
        player1Name = intent.getExtras().getString("PlayerName");

        if(player1Name.length() == 0)
        {
            player1Name = "Player1";
        }

        player2Name = "Android";


        player1Disp.setText(player1Name + "'s is (X)");

        player2Disp.setText(player2Name + "'s is (O)");



        grid = (GridLayout) findViewById(R.id.grid);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                playBoard[i][j] = (Button) grid.getChildAt(3 * i + j);
                boardMatrix[i][j]=0;
            }
        }

      //Start the asynch thread for android
        new backAsync().execute();


    }

    /**
     *
     * Function to handle the move of the player
     *
     *
     * @param view
     */
    public void playmove(View view)
    {
        Button btn = (Button) view;
        btn.setText("X");

        //check if a win condition occurred

        if(checkWin())
        {
            player1Win = 1;
            updateWin(player1Name);
        }
        else
        {
            computerMove();

        }
    }

    /**
     *
     * Function to show the toast message on win
     *
     *
     * @param name
     */

    public void updateWin(String name)
    {
        String text = name + " win!";
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
        writeToFile();
    }

    /**
     *
     * Function to carry out the android move
     *
     *
     */


    public void computerMove(){


        int found = 0;

        //find a position to place the O

        for(int i=0;i<3;i++)
        {
            for(int j=0;j<3;j++)
            {
                System.out.println(playBoard[i][j].getText().toString());
                if(playBoard[i][j].getText().toString().equals(" "))
                {

                    playBoard[i][j].setText("O");
                    found = 1;
                    break;
                }
            }
            if(found==1)
            {
                break;
            }
        }

        //check if a win condition occurred

        if(checkWin())
        {
            player2Win = 1;
            updateWin(player2Name);

        }



    }

    /**
     *
     * Function to start a new game
     *
     *
     * @param view
     */
    public void newGame(View view) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                playBoard[i][j].setText(" ");
            }
        }

    }

    /**
     *
     *
     * Function to end the game and return to the main activity
     *
     * @param view
     */

    public void endGame(View view)
    {

        Intent intent = new Intent(single_player_game.this, MainActivity.class);
        startActivity(intent);
    }

    /**
     *
     *
     * Function to check if a win condition occurred
     *
     * @return
     */

    public boolean checkWin() {

        /**
         *
         * checking for columns upper left to bottom right
         *
         *
         */

        if (playBoard[0][0].getText().toString().equals(playBoard[1][1].getText().toString())
                && playBoard[0][0].getText().toString().equals(playBoard[2][2].getText().toString())
                && !playBoard[0][0].getText().toString().equals(" ")) {
            return true;
        }

        /**
         *
         * checking for columns upper right to bottom left
         *
         *
         */

        if (playBoard[0][2].getText().toString().equals(playBoard[1][1].getText().toString())
                && playBoard[0][2].getText().toString().equals(playBoard[2][0].getText().toString())
                && !playBoard[0][2].getText().toString().equals(" ")) {
            return true;
        }


        /**
         * checking for row
         *
         */
        for (int i = 0; i < 3; i++) {
            if (playBoard[i][0].getText().toString().equals(playBoard[i][1].getText().toString())
                    && playBoard[i][0].getText().toString().equals(playBoard[i][2].getText().toString())
                    && !playBoard[i][0].getText().toString().equals(" ")) {
                return true;
            }
        }
        /**
         *
         *
         * checking for column
         *
         */
        for (int i = 0; i < 3; i++) {
            if (playBoard[0][i].getText().toString().equals(playBoard[1][i].getText().toString())
                    && playBoard[0][i].getText().toString().equals(playBoard[2][i].getText().toString())
                    && !playBoard[0][i].getText().toString().equals(" ")) {
                return true;
            }
        }


        return false;

    }



    /**
     *
     * Function to write game results to local file
     *
     */

    private void writeToFile() {

        FileOutputStream ostream = null;

        StringBuilder data = new StringBuilder();

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        player1Name = player1Name.replaceAll("\\s", ",");

        data.append(player1Name + " " +player1Win + " "+ dtf.format(now).toString() +"\n");
        data.append(player2Name + " " +player2Win + " "+ dtf.format(now).toString() +"\n");


        try {
            ostream = openFileOutput(FILE_NAME,MODE_APPEND);
            ostream.write(data.toString().getBytes());

        }

        catch (Exception e)
        {
            Log.e("Multi Player Activity","Exception : File write failed: " + e.toString());
        }
    }

    /**
     *
     * Async task class for android/computer move
     *
     *
     */

    public class backAsync extends AsyncTask<Void, Void, Boolean> {

        public void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            if(checkWin())
            {
                return  true;
            }

            return true;
        }
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
                computerMove();


        }
    }

}