package com.ynov.vernet.plusoumoins;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    TextView textViewInfo, textViewCount;
    EditText editTextNumber;
    Button btnValidate;

    int mysteryNumber;
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewInfo = findViewById(R.id.textViewInfo);
        textViewCount = findViewById(R.id.textViewCount);
        editTextNumber = (EditText) findViewById(R.id.editTextNumber);
        btnValidate = findViewById(R.id.btnValidate);

        // Save mystery number
        mysteryNumber = mysteryNumber();

        // Display count
        textViewCount.setText(getString(R.string.counts, count));

        // Press button
        btnValidate.setOnClickListener(v -> game());

        // Enter keyboard
        editTextNumber.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                game();
            }
            return true;
        });
    }

    // Generate mystery number
    public int mysteryNumber() {
        int min = 0;
        int max = 1000;
        Random r = new Random();
        return r.nextInt(max - min + 1) + min;
    }

    public void game() {

        // Get user number from editText
        String value = editTextNumber.getText().toString();

        // Empty edit text
        if (value.isEmpty()) {
            editTextNumber.setError(getString(R.string.textbox_empty));
            new Handler().postDelayed((Runnable) () -> editTextNumber.setError(null), 2000);
            return;
        }

        // Convert value to int
        int userNumber = Integer.parseInt(value);

        // Compare userNumber and mysteryNumber
        if (userNumber < mysteryNumber)
            textViewInfo.setText(R.string.number_bigger);
        else if (userNumber > mysteryNumber)
            textViewInfo.setText(R.string.number_lower);

        if (userNumber != mysteryNumber) {
            // Update color & vibrate
            textViewInfo.setTextColor(getResources().getColor(R.color.red));
            Vibrator vibe = (Vibrator) this.getSystemService(Context.VIBRATOR_SERVICE);
            if (vibe != null) {
                vibe.vibrate(400);
            }

            // Increment count
            count++;

            // Clear edit text
            editTextNumber.setText(null);

            // Open keyboard
            editTextNumber.requestFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.showSoftInput(editTextNumber, InputMethodManager.SHOW_IMPLICIT);
            }
        }

        /*Win*/
        if (userNumber == mysteryNumber) {

            // Update text and color
            textViewInfo.setText(getString(R.string.win, count));
            textViewInfo.setTextColor(getResources().getColor(R.color.blue));

            // Hide edit text
            editTextNumber.setVisibility(View.INVISIBLE);

            // Button replay
            btnValidate.setText(R.string.replay);
            btnValidate.setOnClickListener(view -> replay());
        }

        // Update count
        textViewCount.setText(getString(R.string.counts, count));

        // Hide keyboard
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);

    }

    public void replay() {

        // Reset score
        count = 0;
        textViewCount.setText(getString(R.string.counts, count));

        // Reset text and color
        textViewInfo.setText(getString(R.string.enter_a_number_between_0_and_1000));
        textViewInfo.setTextColor(getResources().getColor(R.color.grey));

        // Display edit text
        editTextNumber.setVisibility(View.VISIBLE);
        editTextNumber.setText(null);

        // Button replay
        btnValidate.setText(R.string.validate);

        // Choose a new mystery number
        mysteryNumber = mysteryNumber();

        // Start new game
        btnValidate.setOnClickListener(view -> game());
    }

    @Override
    public void onBackPressed() {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle(R.string.leave)
                .setMessage(R.string.want_leave)
                .setPositiveButton(R.string.yes, (dialogInterface, i) -> {
                    super.onBackPressed();
                    finish();
                })
                .setNegativeButton(R.string.no, null)
                .show();
        alertDialog.setCanceledOnTouchOutside(false);
    }
}