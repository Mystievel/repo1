package com.example.tofer.emptyproject_helloworld;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;


// TODO: keep radio buttons highlighted when exit/return to screen.


public class PositiveEffectsActivity extends FindStrainsActivity {
    static int relaxedBtnSelected;
    String blankEntry = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_positiveeffects);

        // Setup Button variables and listeners
        Button cancelButton = (Button) findViewById(R.id.btnCancel);
        Button setFilterButton = (Button) findViewById(R.id.btnSetFilter);
        final RadioButton relaxedIgnoreRadioBtn = (RadioButton) findViewById(R.id.relaxed_ignore);
        final RadioButton relaxedMaxRadioBtn = (RadioButton) findViewById(R.id.relaxed_max);
        final RadioButton relaxedMinRadioBtn = (RadioButton) findViewById(R.id.relaxed_min);

        final TextView relaxedLabel = findViewById(R.id.lblRelaxed);
        final RadioGroup relaxedRadioGroup = findViewById(R.id.relaxedRadio);
        final RadioButton btnRelaxedIgnore = findViewById(R.id.relaxed_ignore);
        final RadioButton btnRelaxedMax = findViewById(R.id.relaxed_max);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PositiveEffectsActivity.this, FindStrainsActivity.class));
            }
        });

        setFilterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int checkedButtonID = relaxedRadioGroup.getCheckedRadioButtonId();
                int relaxedIgnoreID = 0;
                int ignoreRelaxedBtnID = btnRelaxedIgnore.getId();

                RadioButton radioButton = findViewById(checkedButtonID);
                switch (checkedButtonID) {
                    case R.id.relaxed_ignore: { //ID: 2131165311
                        //Toast.makeText(PositiveEffectsActivity.this,String.valueOf(checkedButtonID), Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case R.id.relaxed_min: { //ID: 2131165313
                        //Toast.makeText(PositiveEffectsActivity.this,String.valueOf(checkedButtonID), Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case R.id.relaxed_max: { //ID: 2131165312
                        //Toast.makeText(PositiveEffectsActivity.this,String.valueOf(checkedButtonID), Toast.LENGTH_SHORT).show();
                        break;
                    }
                }
                //Toast.makeText(PositiveEffectsActivity.this,String.valueOf(radioButton.isChecked()), Toast.LENGTH_SHORT).show();

                // 1) Filter the database results by type 1
                String[] filteredArray = new String[(int) db.getStrainDatabaseRows()];
                filteredArray = filterArrayByColumn(relaxedBtnSelected, db);

                // 2) Reduce the array to non-null values only.
                finalArraySize = getFinalArraySize(filteredArray, db);
                Log.d("_main", "Final array size: " + String.valueOf(finalArraySize));

                finalArray = new String[finalArraySize];
                finalArray = reduceFilteredArray(filteredArray, db);

                // 3) Store for FindStrainsActivity

                startActivity(new Intent(PositiveEffectsActivity.this, FindStrainsActivity.class));
            }
        });

        relaxedIgnoreRadioBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                relaxedBtnSelected = 0;
                //listItem1 = db.getStrainData(3).getStrainName();
                //long id = db.addStrain(new CannabisStrainDatabase_Definition("Cannabis Strain 2", 55.5));
                //Toast.makeText(PositiveEffectsActivity.this,String.valueOf(id), Toast.LENGTH_SHORT).show();
            }
        });

        relaxedMinRadioBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                relaxedBtnSelected = 1;
                //Toast.makeText(PositiveEffectsActivity.this,String.valueOf(db.getStrainData(3).getStrainName()), Toast.LENGTH_SHORT).show();
            }
        });

        relaxedMaxRadioBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                relaxedBtnSelected = 2;
                //long dbRows = db.getStrainDatabaseRows();
                //Toast.makeText(PositiveEffectsActivity.this, String.valueOf(dbRows), Toast.LENGTH_SHORT).show();
            }
        });
    } //********************************************************************************************


    //**********************************************************************************************
    //                             Filter Array by db Column
    //
    // Search a single column within a database, make invalid entries == "" (null), and return the final array.
    // How to call: filteredArray = reduceDBArrayByColumnSearch(relaxedBtnSelected);
    // Later we take out all "" (null) entries, so we know the array length = database entries.
    //
    // ToDo: remove dependency to getEffectsRelaxed, exchange with any column
    //**********************************************************************************************
    public String[] filterArrayByColumn(int btnResult, CannabisStrainDatabase_Helper db) {
        // Declare local variables
        int databaseRows = (int) db.getStrainDatabaseRows();
        int index = 0;
        String filteredArray[] = new String[databaseRows];

        if (btnResult == 1) {
            for (index = 0; index < databaseRows; index++) {
                if (db.getStrainData(index + 1).getEffectsRelaxed() < 0.5) {
                    filteredArray[index] = db.getStrainData(index + 1).getStrainName();
                } else {
                    filteredArray[index] = blankEntry;
                }
            }
        } else if (btnResult == 2) {
            for (index = 0; index < databaseRows; index++) {
                if (db.getStrainData(index + 1).getEffectsRelaxed() >= 0.5) {
                    filteredArray[index] = db.getStrainData(index + 1).getStrainName();
                } else {
                    filteredArray[index] = blankEntry;
                }
            }
        } else {
            for (index = 0; index < databaseRows; index++) {
                filteredArray[index] = db.getStrainData(index + 1).getStrainName();
            }
        }

        // Return the reduced array
        return filteredArray;
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
            if (filteredArray[index] != blankEntry) {
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
            if (filteredArray[index] != blankEntry) {
                reducedArray[index - subtractor] = filteredArray[index];
            } else {
                subtractor++;
            }
        }
        return reducedArray;
    } //********************************************************************************************
}
