package com.ynov.vernet.plusoumoins;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    TextView textViewBox;
    EditText editTextNumber;

    int count = 0;

    // Débug
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int mysteryNumber = mysteryNumber();

        Log.d(TAG, "onCreate: Mystery number : " + mysteryNumber);

        textViewBox = findViewById(R.id.textViewBox);
        editTextNumber = findViewById(R.id.editTextNumber);

        // Enter keyboard
        editTextNumber.setOnKeyListener((v, keyCode, event) -> {
            if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                // Get user number from editText
                String value = editTextNumber.getText().toString();
                int userNumber = Integer.parseInt(value);

                // Compare userNumber and mysterNumber
                if (userNumber < mysteryNumber) {
                    textViewBox.setText("The mystery number is bigger !");
                    count++;
                } else if (userNumber > mysteryNumber) {
                    textViewBox.setText("The mystery number is lower !");
                    count++;
                } else if (userNumber == mysteryNumber) {
                    textViewBox.setText("Good game ! ");
                }
                return true;
            }
            return false;
        });
    }

    // Generate mystery number
    public int mysteryNumber() {
        int min = 0;
        int max = 1000;
        Random r = new Random();
        return r.nextInt(max - min + 1) + min;
    }


}