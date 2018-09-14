package com.example.tofer.emptyproject_helloworld;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class PositiveEffectsActivity extends FindStrainsActivity {

    //TextView relaxedLabel = (TextView)findViewById(R.id.lblRelaxed);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_positiveeffects);

        // Setup Button variables and listeners
        Button cancelButton = (Button)findViewById(R.id.btnCancel); // TODO Can this be moved to global?
        Button setFilterButton = (Button)findViewById(R.id.btnSetFilter);

        //final RadioGroup relaxedRadioGroup = (RadioGroup)findViewById(R.id.relaxedRadio);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PositiveEffectsActivity.this, FindStrainsActivity.class));
            }
        });

        setFilterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //int checkedButton = relaxedRadioGroup.getCheckedRadioButtonId();
                //relaxedLabel.setText(Integer.toString(checkedButton));

                // TODO uncomment the code below when the code above is fully functioning
                startActivity(new Intent(PositiveEffectsActivity.this, FindStrainsActivity.class));
            }
        });
    }
}
