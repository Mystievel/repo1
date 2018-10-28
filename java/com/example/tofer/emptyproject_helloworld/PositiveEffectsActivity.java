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

public class PositiveEffectsActivity extends FindStrainsActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_positiveeffects);

        final CannabisStrainDatabase_Helper db = new CannabisStrainDatabase_Helper(this);
        //db.addStrainRow(new CannabisStrainDatabase_Definition("Android Application Development Cookbook", "Tofer Test"));

        // Setup Button variables and listeners
        Button cancelButton = (Button)findViewById(R.id.btnCancel); // TODO Can this be moved to global?
        Button setFilterButton = (Button)findViewById(R.id.btnSetFilter);
        final RadioButton relaxedIgnoreRadioBtn = (RadioButton)findViewById(R.id.relaxed_ignore);
        final RadioButton relaxedMaxRadioBtn = (RadioButton)findViewById(R.id.relaxed_max);

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
                        Toast.makeText(PositiveEffectsActivity.this,String.valueOf(checkedButtonID), Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case R.id.relaxed_min: { //ID: 2131165313
                        Toast.makeText(PositiveEffectsActivity.this,String.valueOf(checkedButtonID), Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case R.id.relaxed_max: { //ID: 2131165312
                        Toast.makeText(PositiveEffectsActivity.this,String.valueOf(checkedButtonID), Toast.LENGTH_SHORT).show();
                        break;
                    }
                }
                //Toast.makeText(PositiveEffectsActivity.this,String.valueOf(radioButton.isChecked()), Toast.LENGTH_SHORT).show();

                // TODO uncomment the code below when the code above is fully functioning
                //startActivity(new Intent(PositiveEffectsActivity.this, FindStrainsActivity.class));
            }
        });


        relaxedIgnoreRadioBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.addStrainRow(new CannabisStrainDatabase_Definition("Android Application Development Cookbook", "Tofer Test"));
                Toast.makeText(PositiveEffectsActivity.this,String.valueOf(relaxedIgnoreRadioBtn.getId()), Toast.LENGTH_SHORT).show();
            }
        });

        relaxedMaxRadioBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(PositiveEffectsActivity.this,String.valueOf(db.getStrainData(0)), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
