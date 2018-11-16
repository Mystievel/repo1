package com.example.tofer.emptyproject_helloworld;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


// TODO: keep radio buttons highlighted when exit/return to screen.


public class PositiveEffectsActivity extends FindStrainsActivity {
    static int relaxedBtnSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_positiveeffects);

        // Setup Button variables and listeners
        Button cancelButton = (Button)findViewById(R.id.btnCancel);
        Button setFilterButton = (Button)findViewById(R.id.btnSetFilter);
        final RadioButton relaxedIgnoreRadioBtn = (RadioButton)findViewById(R.id.relaxed_ignore);
        final RadioButton relaxedMaxRadioBtn = (RadioButton)findViewById(R.id.relaxed_max);
        final RadioButton relaxedMinRadioBtn = (RadioButton)findViewById(R.id.relaxed_min);

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
                switch(checkedButtonID){
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

                filteredArray = new String[(int) db.getStrainDatabaseRows()];
                filteredArray = filterArrayByColumn(relaxedBtnSelected, db);
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
    }

    //**********************************************************************************************
    //                             Filter Array by db Column
    //
    // Search a single column within a database, make invalid entries == "0", and return the final array.
    // How to call: filteredArray = reduceDBArrayByColumnSearch(relaxedBtnSelected);
    // Later we take out all "0" entries, so we know the array length = database entries.
    //**********************************************************************************************
    public static String[] filterArrayByColumn(int btnResult, CannabisStrainDatabase_Helper db) {
        // Declare local variables
        Log.d("_filterArrayByColumn", "1");
        Log.d("_filterArrayByColumn", "2");
        int tempNumberOfResults = 0;
        int index = 0;
        String relaxed_results[] = new String[(int) db.getStrainDatabaseRows()];
        Log.d("_filterArrayByColumn", "3");
        if (btnResult == 1) {
            for (index = 0; index < tempNumberOfResults; index++) {
                if (db.getStrainData(index+1).getEffectsRelaxed() < 0.5) {
                    relaxed_results[index] = db.getStrainData(index+1).getStrainName();
                }
            }
        } else if (btnResult == 2) {
            for (index = 0; index < tempNumberOfResults; index++) {
                if (db.getStrainData(index+1).getEffectsRelaxed() >= 0.5) {
                    relaxed_results[index] = db.getStrainData(index+1).getStrainName();
                }
            }
        }
        Log.d("_filterArrayByColumn", "4");
        // Store the resulting array size
        finalResultsArraySize = tempNumberOfResults;

        // Return the reduced array
        return relaxed_results;
    }
}
