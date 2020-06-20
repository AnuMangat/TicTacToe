package com.mangat.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * Activity class for showing the Game data
 *
 *
 */
public class showstandings extends AppCompatActivity {

    //Initialize the parameters

    HashMap<String,Integer> data = new HashMap<String,Integer>();
    private static final String FILE_NAME = "gameData.txt";
    ArrayAdapter arrayAdapter;

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
        setContentView(R.layout.activity_showstandings);

        //call the showHistoricalData function
        showHistoricalData();
    }

    /**
     *
     * Function to show the historical data
     *
     */
    protected void showHistoricalData()
    {
        ListView lv = (ListView)findViewById(R.id.historyListView);

        ArrayList<String> list = new ArrayList<String>();

        //if reading from local file successfull, add it to the adaptor
        if(readData())
        {
            for(String key:data.keySet())
            {
                String value = data.get(key).toString();
                list.add(key+":"+value + " wins");
            }

            arrayAdapter = new ArrayAdapter(showstandings.this, android.R.layout.simple_list_item_1, list);

        }
        // no data in the file
        else
        {
            list.add("Historical Data not available!!!");
            arrayAdapter = new ArrayAdapter(showstandings.this, android.R.layout.simple_list_item_1, list);

        }
        // sort the data alphabetically
        Collections.sort(list);
        lv.setAdapter(arrayAdapter);


    }

    /**
     *
     * Function to read the data from the local file
     *
     *
     * @return
     */
    protected boolean readData()
    {

        FileInputStream rstream = null;

        // cumulating the data into the HashMap
        try
        {
            rstream = openFileInput(FILE_NAME);
            InputStreamReader is = new InputStreamReader(rstream);
            BufferedReader br = new BufferedReader(is);

            String line;
            while((line = br.readLine())!= null)
            {
                String[] texts = line.split(" ");

                if(texts.length>1)
                {
                    String key = texts[0];
                    key = key.replaceAll("\\,", " ");

                    int value = Integer.valueOf(texts[1]);
                    if(data.containsKey(key))
                    {
                        value += data.get(key);
                    }
                    data.put(key,value);

                }
            }

            return  true;

        }
        catch(Exception e)
        {
            Log.e("Multi Player Activity","Exception : File read failed: " + e.toString());
        }


        return false;

    }


    /**
     *
     * Function to return the main activity
     *
     *
     * @param view
     */
    public void goBackToMain(View view)
    {
        Intent intent = new Intent(showstandings.this, MainActivity.class);
        startActivity(intent);

    }
}
