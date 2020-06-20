package com.mangat.tictactoe;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.logging.Logger;

/**
 *
 * Activity class for 2 Players game
 *
 *
 *
 */

public class multi_player_game extends AppCompatActivity {

    //File name to store the results
    private static final String FILE_NAME = "gameData.txt";

    int player1move = 1;

    //Initializing parameters

    GridLayout grid;
    Button playBoard[][] = new Button[3][3];
    TextView player1Disp,player2Disp;
    String player1Name;
    String player2Name;

    int player1Win = 0, player2Win = 0;


    /**
     *
     * onCreate function for the activity
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_multiple_player_game);

        //Get the names of the players
        player1Disp = (TextView) findViewById(R.id.player1);
        player2Disp = (TextView) findViewById(R.id.player2);


        Intent intent = getIntent();
        player1Name = intent.getExtras().getString("PlayerName1");
        if(player1Name.length() == 0)
        {
            player1Name = "Player1";
        }
        player2Name = intent.getExtras().getString("PlayerName2");

        if(player2Name.length() == 0)
        {
            player2Name = "Player2";
        }


        grid = (GridLayout) findViewById(R.id.grid);
        //Show Players names with X or O options

        player1Disp.setText(player1Name + "'s turn (X)");
        player2Disp.setText(player2Name + "'s turn (O)");

        //Get the button data for all the buttons in a matrix

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                playBoard[i][j] = (Button) grid.getChildAt(3 * i + j);

            }
        }


    }

    /**
     *
     * Handle the each option button click by user
     *
     *
     * @param view
     */
    public void playmove(View view) {
        Button btn = (Button) view;

        //update the sign based on the user Player 1 or 2


        if(player1move == 1)
        {
            btn.setText("X");
        }
        else
        {
            btn.setText("O");
        }

        // check if a win condition occured or not


        if(checkWin())
        {
            //check if player 1 won
            if(player1move == 1)
            {
                player1Win ++;
                updateWin(player1Name);
            }

            //check if player 2 won
            else if(player1move == 0)
            {
                player2Win ++;
                updateWin(player2Name);
            }

        }
        //update the player turns
        else if(player1move == 1)
        {
            player1move = 0;

        }
        else if(player1move == 0 )
        {
            player1move = 1;
        }


    }

    /**
     *
     * Function to show the Win Toast message
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
     * Function to check if a win occurred
     *
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
     * Function to start a new game
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
     * Function to write game results to local file
     *
     */
    private void writeToFile() {

        FileOutputStream ostream = null;

        StringBuilder data = new StringBuilder();

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        player1Name = player1Name.replaceAll("\\s", ",");
        player2Name = player2Name.replaceAll("\\s", ",");

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
     * Function to end the game
     *
     *
     * @param view
     */

    public void endGame(View view)
    {
        Intent intent = new Intent(multi_player_game.this, MainActivity.class);
        startActivity(intent);
    }





}



