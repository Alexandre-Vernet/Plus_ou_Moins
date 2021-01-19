package com.ynov.vernet.plusoumoins;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    TextView textViewInfo, textViewCount;
    EditText editTextNumber;
    Button btnValidate;

    int count = 0;

    // Débug
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewInfo = findViewById(R.id.textViewInfo);
        textViewCount = findViewById(R.id.textViewCount);
        editTextNumber = findViewById(R.id.editTextNumber);
        btnValidate = findViewById(R.id.btnValidate);

        // Save mystery number
        int mysteryNumber = mysteryNumber();
        Log.d(TAG, "onCreate: " + mysteryNumber);

        // Display count
        textViewCount.setText(getString(R.string.counts, count));

        // Enter keyboard
        editTextNumber.setOnKeyListener((v, keyCode, event) -> {
            if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                // Get user number from editText
                String value = editTextNumber.getText().toString();

                // Empty edit text
                if (value.isEmpty()) {
                    editTextNumber.setError(getString(R.string.textbox_empty));
                    new Handler().postDelayed((Runnable) () -> editTextNumber.setError(null), 1000);
                    return false;
                }

                // Convert value to int
                int userNumber = Integer.parseInt(value);

                // Compare userNumber and mysteryNumber
                if (userNumber < mysteryNumber)
                    textViewInfo.setText(R.string.number_bigger);
                else if (userNumber > mysteryNumber)
                    textViewInfo.setText(R.string.number_lower);


                textViewInfo.setTextColor(getResources().getColor(R.color.red));
                Vibrator vibe = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
                if (vibe != null) {
                    vibe.vibrate(200);
                }
                count++;

                /*Win*/
                if (userNumber == mysteryNumber) {
                    textViewInfo.setText(getString(R.string.win, count));
                    textViewInfo.setTextColor(getResources().getColor(R.color.blue));
                    editTextNumber.setVisibility(View.INVISIBLE);

                    // Replay
                    btnValidate.setText(R.string.replay);
                    btnValidate.setOnClickListener(view -> {

                    });
                }

                // Update count
                textViewCount.setText(getString(R.string.counts, count));

                // Open keyboard
                editTextNumber.requestFocus();

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