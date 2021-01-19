package com.ynov.vernet.plusoumoins;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class MainActivity extends AppCompatActivity {

    EditText editTextNumber;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Log.d(TAG, "onCreate: Mystery number : " + mysteryNumber());


        editTextNumber = findViewById(R.id.editTextNumber);
    }

    // Generate mystery number
    public int mysteryNumber() {
        int min = 0;
        int max = 1000;
        Random r = new Random();
        return r.nextInt(max - min + 1) + min;
    }


}