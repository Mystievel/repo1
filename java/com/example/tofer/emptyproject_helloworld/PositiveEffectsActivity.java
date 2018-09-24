package com.example.tofer.emptyproject_helloworld;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
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

        // Setup Button variables and listeners
        Button cancelButton = (Button)findViewById(R.id.btnCancel); // TODO Can this be moved to global?
        Button setFilterButton = (Button)findViewById(R.id.btnSetFilter);

        final TextView relaxedLabel = findViewById(R.id.lblRelaxed);
        final RadioGroup relaxedRadioGroup = findViewById(R.id.relaxedRadio);
        final RadioButton btnrelaxedIgnore = findViewById(R.id.relaxed_ignore);

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

                int ignoreRelaxedBtnID = btnrelaxedIgnore.getId();

                RadioButton radioButton = findViewById(checkedButtonID);
                switch(checkedButtonID){
                    case R.id.relaxed_ignore: {
                        Toast.makeText(PositiveEffectsActivity.this,String.valueOf(checkedButtonID), Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case R.id.relaxed_max: {
                        Toast.makeText(PositiveEffectsActivity.this,String.valueOf(checkedButtonID), Toast.LENGTH_SHORT).show();
                        break;
                    }

                    case R.id.relaxed_min: {
                        Toast.makeText(PositiveEffectsActivity.this,String.valueOf(checkedButtonID), Toast.LENGTH_SHORT).show();
                        break;
                    }
                }
                //Toast.makeText(PositiveEffectsActivity.this,String.valueOf(radioButton.isChecked()), Toast.LENGTH_SHORT).show();

                // TODO uncomment the code below when the code above is fully functioning
                //startActivity(new Intent(PositiveEffectsActivity.this, FindStrainsActivity.class));
            }
        });
    }
}
