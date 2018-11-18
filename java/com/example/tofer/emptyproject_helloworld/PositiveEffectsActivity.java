package com.example.tofer.emptyproject_helloworld;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

public class PositiveEffectsActivity extends FindStrainsActivity {
    // Local variables
    static int relaxedBtnSelected;
    static int happyBtnSelected;
    static int hungryBtnSelected;
    static int[] effectsBtnSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_positiveeffects);

        // Setup Button variables and listeners
        final Button cancelButton = findViewById(R.id.btnCancel);
        final Button setFilterButton = findViewById(R.id.btnSetFilter);

        // Todo: Define all of this junk in a loop for all of the buttons and groups IDs...possible?
        // Relaxed
        final RadioButton relaxedIgnoreRadioBtn = findViewById(R.id.relaxed_ignore);
        final RadioButton relaxedMaxRadioBtn = findViewById(R.id.relaxed_max);
        final RadioButton relaxedMinRadioBtn = findViewById(R.id.relaxed_min);
        // Happy
        final RadioButton happyIgnoreRadioBtn = findViewById(R.id.happy_ignore);
        final RadioButton happyMaxRadioBtn = findViewById(R.id.happy_max);
        final RadioButton happyMinRadioBtn = findViewById(R.id.happy_min);
        // Hungry
        final RadioButton hungryIgnoreRadioBtn = findViewById(R.id.hungry_ignore);
        final RadioButton hungryMaxRadioBtn = findViewById(R.id.hungry_max);
        final RadioButton hungryMinRadioBtn = findViewById(R.id.hungry_min);

        // Keep radio buttons selected same as the previous selection.
        // Todo: Define all of this junk in a loop for all of the buttons and groups IDs...possible?
        setDefaultRadioButtonSelection(relaxedBtnSelected, relaxedMinRadioBtn, relaxedMaxRadioBtn, relaxedIgnoreRadioBtn);
        setDefaultRadioButtonSelection(happyBtnSelected, happyMinRadioBtn, happyMaxRadioBtn, happyIgnoreRadioBtn);
        setDefaultRadioButtonSelection(hungryBtnSelected, hungryMinRadioBtn, hungryMaxRadioBtn, hungryIgnoreRadioBtn);

        //******************************************************************************************
        // Event: Click Button - "Cancel"
        //******************************************************************************************
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PositiveEffectsActivity.this, FindStrainsActivity.class));
            }
        }); //**************************************************************************************


        //******************************************************************************************
        // Event: Click Button - "Set Filter"
        //******************************************************************************************
        setFilterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create an array for all radio buttons.
				effectsBtnSelected = new int[]{relaxedBtnSelected, happyBtnSelected, hungryBtnSelected};

				// Loop through the buttons array and filter based on each buttons' selection.
				for (int i = 0; i < effectsBtnSelected.length; i++) {
					filteredArray = filterArrayByColumn(effectsBtnSelected[i], db, effectsArray[i], filteredArray);
				}

                // Reduce the array to non-null values only.
                // Todo fix odd bug, if you scroll down in the final list you'll see a lot of null values. not sure exactly how many.
                // todo: most likely how the array is being initialized!!!
                finalArraySize = getFinalArraySize(filteredArray, db);
				//Log.d("asdkljsdglkj", finalArraySize + "");
                finalArray = new String[finalArraySize];
                finalArray = reduceFilteredArray(filteredArray, db);

                // 3) Change activity back a page to the FindStrainsActivity.
                startActivity(new Intent(PositiveEffectsActivity.this, FindStrainsActivity.class));
            }
        }); //**************************************************************************************

        // 'Relaxed' Buttons
        relaxedIgnoreRadioBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { relaxedBtnSelected = IGNORE;
            }
        });
        relaxedMinRadioBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { relaxedBtnSelected = MIN;
            }
        });
        relaxedMaxRadioBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { relaxedBtnSelected = MAX;
            }
        });

        // 'Happy' Buttons
        happyIgnoreRadioBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { happyBtnSelected = IGNORE;
            }
        });
        happyMinRadioBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                happyBtnSelected = MIN;
            }
        });
        happyMaxRadioBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                happyBtnSelected = MAX;
            }
        });

        // 'Hungry' Buttons
        hungryIgnoreRadioBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { hungryBtnSelected = IGNORE;
            }
        });
        hungryMinRadioBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { hungryBtnSelected = MIN;
            }
        });
        hungryMaxRadioBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { hungryBtnSelected = MAX;
            }
        });
    } //********************************************************************************************
}
