package com.example.tofer.emptyproject_helloworld;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

// Todo, start populating this activity and see what shared items need to be moved.

public class NegativeEffectsActivity extends FindStrainsActivity {
    static int dryEyesBtnSelected;
    static int dryMouthBtnSelected;
    static int dizzyBtnSelected;
    static int[] effectsBtnSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_negativeeffects);

        // Setup Button variables and listeners
        Button cancelButton = findViewById(R.id.btnCancel);
        Button setFilterButton = findViewById(R.id.btnSetNegativeEffects);

        // Todo: Define all of this junk in a loop for all of the buttons and groups IDs...possible?
        // Dry Eyes
        final RadioButton relaxedIgnoreRadioBtn = findViewById(R.id.dryEyes_ignore);
        final RadioButton relaxedMaxRadioBtn = findViewById(R.id.dryEyes_max);
        final RadioButton relaxedMinRadioBtn = findViewById(R.id.dryEyes_min);
        // Dry Mouth
        final RadioButton happyIgnoreRadioBtn = findViewById(R.id.dryMouth_ignore);
        final RadioButton happyMaxRadioBtn = findViewById(R.id.dryMouth_max);
        final RadioButton happyMinRadioBtn = findViewById(R.id.dryMouth_min);
        // Dizzy
        final RadioButton hungryIgnoreRadioBtn = findViewById(R.id.dizzy_ignore);
        final RadioButton hungryMaxRadioBtn = findViewById(R.id.dizzy_max);
        final RadioButton hungryMinRadioBtn = findViewById(R.id.dizzy_min);

        // Keep radio buttons selected same as the previous selection.
        // Todo: Define all of this junk in a loop for all of the buttons and groups IDs...possible?
        setDefaultRadioButtonSelection(dryEyesBtnSelected, relaxedMinRadioBtn, relaxedMaxRadioBtn, relaxedIgnoreRadioBtn);
        setDefaultRadioButtonSelection(dryMouthBtnSelected, happyMinRadioBtn, happyMaxRadioBtn, happyIgnoreRadioBtn);
        setDefaultRadioButtonSelection(dizzyBtnSelected, hungryMinRadioBtn, hungryMaxRadioBtn, hungryIgnoreRadioBtn);


        //******************************************************************************************
        // Event: Click Button - "Cancel"
        //******************************************************************************************
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NegativeEffectsActivity.this, FindStrainsActivity.class));
            }
        }); //**************************************************************************************


        //******************************************************************************************
        // Event: Click Button - "Set Filter"
        //******************************************************************************************
        setFilterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create an array for all radio buttons.
                effectsBtnSelected = new int[]{dryEyesBtnSelected, dryMouthBtnSelected, dizzyBtnSelected};

                // Loop through the buttons array and filter based on each buttons' selection.
                for (int i = 0; i < effectsBtnSelected.length; i++) {
                    filteredArray = filterArrayByColumn(effectsBtnSelected[i], db, effectsArray[i], filteredArray);
                }

                // Reduce the array to non-null values only.
                // Todo fix odd bug, if you scroll down in the final list you'll see a lot of null values. not sure exactly how many.
                // todo: most likely how the array is being initialized!!!
                finalArraySize = getFinalArraySize(filteredArray, db);
                //Log.d("qwrekjlhf", finalArraySize + "");
                finalArray = new String[finalArraySize];
                finalArray = reduceFilteredArray(filteredArray, db);

                // 3) Change activity back a page to the FindStrainsActivity.
                startActivity(new Intent(NegativeEffectsActivity.this, FindStrainsActivity.class));
            }
        }); //**************************************************************************************

        // 'Dry Eyes' Buttons
        relaxedIgnoreRadioBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { dryEyesBtnSelected = IGNORE;
            }
        });
        relaxedMinRadioBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { dryEyesBtnSelected = MIN;
            }
        });
        relaxedMaxRadioBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { dryEyesBtnSelected = MAX;
            }
        });

        // 'Dry Mouth' Buttons
        happyIgnoreRadioBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { dryMouthBtnSelected = IGNORE;
            }
        });
        happyMinRadioBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { dryMouthBtnSelected = MIN;
            }
        });
        happyMaxRadioBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { dryMouthBtnSelected = MAX;
            }
        });

        // 'Dizzy' Buttons
        hungryIgnoreRadioBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { dizzyBtnSelected = IGNORE;
            }
        });
        hungryMinRadioBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { dizzyBtnSelected = MIN;
            }
        });
        hungryMaxRadioBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { dizzyBtnSelected = MAX;
            }
        });
    }
}
