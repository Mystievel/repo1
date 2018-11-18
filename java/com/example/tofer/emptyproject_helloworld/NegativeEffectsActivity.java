package com.example.tofer.emptyproject_helloworld;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

// Todo, start populating this activity and see what shared items need to be moved.

public class NegativeEffectsActivity extends FindStrainsActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_negativeeffects);

        // Setup Button variables and listeners
        Button cancelButton = (Button)findViewById(R.id.btnCancel);
        Button negativeEffectsButton = (Button)findViewById(R.id.btnSetNegativeEffects);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NegativeEffectsActivity.this, FindStrainsActivity.class));
            }
        });

        negativeEffectsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NegativeEffectsActivity.this, FindStrainsActivity.class));
            }
        });

    }
}
