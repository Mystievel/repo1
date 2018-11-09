package com.example.tofer.emptyproject_helloworld;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class FindStrainsActivity extends MainActivity {

    private TextView searchIntensityValue;
    private SeekBar searchIntensitySeekBar;
    static String listItem1 = "";   // Declare as 'static' to not lose memory when changing intents...or activities, not sure which one.
    static int numberOfResults = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_findstrains);

        // Initiate Views
        final int startingValue = 95;
        searchIntensitySeekBar = findViewById(R.id.seekBarSearchIntensity);
        searchIntensitySeekBar.setProgress(startingValue);
        searchIntensityValue = findViewById(R.id.lblSearchIntensity);
        searchIntensityValue.setText("Search Intensity: " + searchIntensitySeekBar.getProgress());

        // Setup Button variables and listeners
        Button negativeEffectsButton = (Button)findViewById(R.id.btnNegativeEffects);
        Button positiveEffectsButton = (Button)findViewById(R.id.btnPositiveEffects);
        Button searchButton = (Button)findViewById(R.id.btnStartSearch);
        Button mainPageButton = (Button)findViewById(R.id.btnMainPage);

        negativeEffectsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FindStrainsActivity.this, NegativeEffectsActivity.class));
            }
        });

        positiveEffectsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FindStrainsActivity.this, PositiveEffectsActivity.class));
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FindStrainsActivity.this, ResultsActivity.class));
            }
        });

        mainPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FindStrainsActivity.this, MainActivity.class));
            }
        });

        // perform seek bar change listener event used for getting the progress value
        searchIntensitySeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            // Localized variables
            int progressChangedValue = startingValue;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChangedValue = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                searchIntensityValue.setText("Search Intensity: " + progressChangedValue);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                searchIntensityValue.setText("Search Intensity: " + progressChangedValue);
            }
        });
    }
}
