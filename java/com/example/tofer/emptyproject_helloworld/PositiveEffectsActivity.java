package com.example.tofer.emptyproject_helloworld;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import static java.lang.StrictMath.toIntExact;


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

                // Get the strain data for the items that meet the "relaxed" search criteria
                tempNumberOfResults = 0;
                numberOfResults = db.getStrainDatabaseRows();
                // Get the array size of the "reduced" array due to search criterion.
                if (relaxedBtnSelected == 1) {
                    for (int i=1; i<numberOfResults; i++) {
                        if (db.getStrainData(i).getEffectsRelaxed() < 0.5) {
                            tempNumberOfResults++;
                        }
                    }
                } else if (relaxedBtnSelected == 2){
                    for (int i=1; i<numberOfResults; i++) {
                        if (db.getStrainData(i).getEffectsRelaxed() >= 0.5) {
                            tempNumberOfResults++;
                        }
                    }
                }

                // Store the final results into a results array.
                relaxed_results = new String[(int) tempNumberOfResults];
                if (relaxedBtnSelected == 1) {
                    for (int i=0; i<tempNumberOfResults; i++) {
                        if (db.getStrainData(i+1).getEffectsRelaxed() < 0.5) {
                            relaxed_results[i] = db.getStrainData(i+1).getStrainName();
                        } else {
                            relaxed_results[i] = "";
                        }
                    }
                } else if (relaxedBtnSelected == 2){
                    for (int i=0; i<tempNumberOfResults; i++) {
                        if (db.getStrainData(i+1).getEffectsRelaxed() >= 0.5) {
                            relaxed_results[i] = db.getStrainData(i+1).getStrainName();
                        } else {
                            relaxed_results[i] = "";
                        }
                    }
                }

                Toast.makeText(PositiveEffectsActivity.this,String.valueOf(tempNumberOfResults), Toast.LENGTH_SHORT).show();
                //Toast.makeText(PositiveEffectsActivity.this,String.valueOf(relaxed_results[3]), Toast.LENGTH_SHORT).show();
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
                //int dbRows = db.getStrainDatabaseRows();
                //Toast.makeText(PositiveEffectsActivity.this, String.valueOf(dbRows), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
