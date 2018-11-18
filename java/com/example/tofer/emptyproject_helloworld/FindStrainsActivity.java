package com.example.tofer.emptyproject_helloworld;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class FindStrainsActivity extends MainActivity {
    // Defines
    final static int IGNORE = 0;
    final static int MIN = 1;
    final static int MAX = 2;
    final static String BLANK_ENTRY = "";
    final static int RELAXED = 1;
    final static int HAPPY = 2;
    final static int HUNGRY = 3;

    // Shared variables
    final static int[] effectsArray = new int[]{RELAXED, HAPPY, HUNGRY};
    final CannabisStrainDatabase_Helper db = new CannabisStrainDatabase_Helper(this);
    private TextView searchIntensityValue;
    private SeekBar searchIntensitySeekBar;

    // Todo: got to figure out how to declare the initial filtered array that is filled with the Strain Names
    // must be shared between both Positive and Negative Effects activities.
    static long arraySize = db.getStrainDatabaseRows();
    static String[] filteredArray = new String[(int) arraySize];
    //getAllStrainNames();


    static int finalArraySize;
    static String[] finalArray;

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
    } //********************************************************************************************


    //**********************************************************************************************
    //                              Set Default Radio Button Selection
    //**********************************************************************************************
    public void setDefaultRadioButtonSelection(int btnSelection, RadioButton minBtn, RadioButton maxBtn, RadioButton ignoreBtn) {
        if (btnSelection == MIN) {
            minBtn.setChecked(true);
        } else if (btnSelection == MAX) {
            maxBtn.setChecked(true);
        } else {
            ignoreBtn.setChecked(true);
        }
    } //********************************************************************************************


    //**********************************************************************************************
    //                    Gets any single Column from the Strains Database
    //**********************************************************************************************
    public String[] getAllStrainNames() {
        String[] allStrainNames = new String[(int) db.getStrainDatabaseRows()];
        for (int i = 0; i < db.getStrainDatabaseRows(); i++) {
            allStrainNames[i] = db.getStrainData(i+1).getStrainName();
        }
        return allStrainNames;
    } //********************************************************************************************


    //**********************************************************************************************
    //                             Filter Array by db Column
    //
    // Search a single column within a database, make invalid entries == "" (null), and return the final array.
    // How to call: filteredArray = reduceDBArrayByColumnSearch(relaxedBtnSelected);
    // Later we take out all "" (null) entries, so we know the array length = database entries.
    //
    // Todo: Update "0.5" to something else (a percentile based off search intensity bar).
    // todo find the 'null' list item bug. is it in this method?
    //**********************************************************************************************
    public String[] filterArrayByColumn(int btnResult, CannabisStrainDatabase_Helper db, int effect, String[] originalArray) {
        // Declare local variables
        int databaseRows = (int) db.getStrainDatabaseRows();
        int index;
        String newArray[] = new String[databaseRows];

        if (btnResult == MIN) {
            for (index = 0; index < databaseRows; index++) {
                if (db.getStrainData(index + 1).getEffect(effect) < 0.5) {
                    if (originalArray[index] == BLANK_ENTRY) {
                        newArray[index] = BLANK_ENTRY;
                    } else {
                        newArray[index] = db.getStrainData(index + 1).getStrainName();
                    }
                } else {
                    newArray[index] = BLANK_ENTRY;
                }
            }
        } else if (btnResult == MAX) {
            for (index = 0; index < databaseRows; index++) {
                if (db.getStrainData(index + 1).getEffect(effect) >= 0.5) {
                    if (originalArray[index] == BLANK_ENTRY) {
                        newArray[index] = BLANK_ENTRY;
                    } else {
                        newArray[index] = db.getStrainData(index + 1).getStrainName();
                    }
                } else {
                    newArray[index] = BLANK_ENTRY;
                }
            }
        } else {
            for (index = 0; index < databaseRows; index++) {
                newArray[index] = db.getStrainData(index + 1).getStrainName();
            }
        }

        // Return the reduced array
        return newArray;
    } //********************************************************************************************


    //**********************************************************************************************
    //                             Get Filtered Array Size
    //**********************************************************************************************
    public int getFinalArraySize(String[] filteredArray, CannabisStrainDatabase_Helper db) {
        // Declare local variables
        int databaseRows = (int) db.getStrainDatabaseRows();
        int index;
        int count = 0;

        // get the size that the new array will be
        for (index = 0; index < databaseRows; index++) {
            if (filteredArray[index] != BLANK_ENTRY) {
                count++;
            }
        }
        return count;
    } //********************************************************************************************


    //**********************************************************************************************
    //                             Reduce Filtered Array
    //**********************************************************************************************
    public String[] reduceFilteredArray(String[] filteredArray, CannabisStrainDatabase_Helper db) {
        // Declare local variables
        int databaseRows = (int) db.getStrainDatabaseRows();
        int index;
        int count = getFinalArraySize(filteredArray, db);
        int subtractor = 0;
        String reducedArray[] = new String[databaseRows];

        // Now populate the reducedArray without the blank items from the original array.
        for (index = 0; index < count; index++) {
            if (filteredArray[index] != BLANK_ENTRY) {
                reducedArray[index - subtractor] = filteredArray[index];
            } else {
                subtractor++;
            }
        }
        return reducedArray;
    } //********************************************************************************************
}
