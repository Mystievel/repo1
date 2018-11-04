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
                long id = db.addStrain(new CannabisStrainDatabase_Definition("Cannabis Strain 2", 55.5));
                Toast.makeText(PositiveEffectsActivity.this,String.valueOf(id), Toast.LENGTH_SHORT).show();
                //Log.d("addStrain returned", ""+id);
            }
        });

        relaxedMinRadioBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double effectsRelaxedValue = db.getStrainData(3).getEffectsRelaxed() + 1;
                //db.getStrainData(4).setEffectsRelaxed(effectsRelaxedValue + db.getStrainData(4).getEffectsRelaxed());
                db.updateStrain(db.getStrainData(4));
                Toast.makeText(PositiveEffectsActivity.this,String.valueOf(" value: " + effectsRelaxedValue + " data: " + db.getStrainData(3).getEffectsRelaxed()), Toast.LENGTH_SHORT).show();
            }
        });

        relaxedMaxRadioBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.deleteStrain(db.getStrainData(4));
                //Toast.makeText(PositiveEffectsActivity.this,String.valueOf(db.getStrainData(4).getEffectsRelaxed()), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
