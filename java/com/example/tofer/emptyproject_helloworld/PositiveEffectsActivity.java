package com.example.tofer.emptyproject_helloworld;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

public class PositiveEffectsActivity extends FindStrainsActivity {
    // Defines
    int IGNORE = 0;
    int MIN = 1;
    int MAX = 2;
    String BLANK_ENTRY = "";

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

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PositiveEffectsActivity.this, FindStrainsActivity.class));
            }
        });

        //**********************************************************************************************
        // Event: Click Button - "Set Filter"
        //**********************************************************************************************
        setFilterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start out with a list of all strains.
                String[] filteredArray = getAllStrainNames();

                // Create an array for all radio buttons.
				effectsBtnSelected = new int[]{relaxedBtnSelected, happyBtnSelected, hungryBtnSelected};

				// Loop through the buttons array and filter based on each buttons' selection.
				for (int i = 0; i < effectsBtnSelected.length; i++) {
					filteredArray = filterArrayByColumn(effectsBtnSelected[i], db, effectsArray[i], filteredArray);
				}

                // Reduce the array to non-null values only.
                // Todo fix odd bug, if you scroll down in the final list you'll see a lot of null values. not sure exactly how many.
                finalArraySize = getFinalArraySize(filteredArray, db);
				//Log.d("asdkljsdglkj", finalArraySize + "");
                finalArray = new String[finalArraySize];
                finalArray = reduceFilteredArray(filteredArray, db);

                // 3) Change activity back a page to the FindStrainsActivity.
                startActivity(new Intent(PositiveEffectsActivity.this, FindStrainsActivity.class));
            }
        });


        // 'Relaxed' Buttons
        relaxedIgnoreRadioBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                relaxedBtnSelected = IGNORE;
                //listItem1 = db.getStrainData(3).getStrainName();
                //long id = db.addStrain(new CannabisStrainDatabase_Definition("Cannabis Strain 2", 55.5));
                //Toast.makeText(PositiveEffectsActivity.this,String.valueOf(id), Toast.LENGTH_SHORT).show();
                //Toast.makeText(PositiveEffectsActivity.this,String.valueOf(relaxedRadioGroup.getCheckedRadioButtonId()), Toast.LENGTH_SHORT).show();
            }
        });

        relaxedMinRadioBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                relaxedBtnSelected = MIN;
                //Toast.makeText(PositiveEffectsActivity.this,String.valueOf(db.getStrainData(3).getStrainName()), Toast.LENGTH_SHORT).show();
                //Toast.makeText(PositiveEffectsActivity.this,String.valueOf(relaxedRadioGroup.getCheckedRadioButtonId()), Toast.LENGTH_SHORT).show();
            }
        });

        relaxedMaxRadioBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                relaxedBtnSelected = MAX;
                //long dbRows = db.getStrainDatabaseRows();
                //Toast.makeText(PositiveEffectsActivity.this, String.valueOf(dbRows), Toast.LENGTH_SHORT).show();
                //Toast.makeText(PositiveEffectsActivity.this,String.valueOf(relaxedRadioGroup.getCheckedRadioButtonId()), Toast.LENGTH_SHORT).show();
            }
        });

        // 'Happy' Buttons
        happyIgnoreRadioBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                happyBtnSelected = IGNORE;
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
            public void onClick(View view) {
                hungryBtnSelected = IGNORE;
            }
        });

        hungryMinRadioBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hungryBtnSelected = MIN;
            }
        });

        hungryMaxRadioBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hungryBtnSelected = MAX;
            }
        });
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
    }


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
