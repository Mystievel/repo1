package com.example.tofer.emptyproject_helloworld;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

// todo Make background color scheme more earthy or psychadelic/mystical
// todo Change overall font to same fontFamily
// todo add buttons across all pages? Make main activity just a start screen then go to my strains
// todo walk the user through how to use the app "Start screen" --> My Strains --> Find Strains, etc.
// todo add "blog" smoking tips, cautions, etc. very short easy to read information, no large pages.
// todo admob advertisement banner?

// todo: copy database to memory on init?


public class MainActivity extends AppCompatActivity {
    // Global Defines
    int IGNORE = 0;						    // #DEFINE
    int MIN = 1;						    // #DEFINE
    int MAX = 2;						    // #DEFINE
    int BLANK_ENTRY = -1;				    // #DEFINE
	final static int HAPPINESS = 1;		    // #DEFINE
	final static int EUPHORIA = 2;		    // #DEFINE
	final static int FOCUS = 3;		        // #DEFINE
	final static int ENERGY = 4;		    // #DEFINE
	final static int RELAXATION = 5;	    // #DEFINE
	final static int SLEEPINESS = 6;	 	// #DEFINE
	final static int SICKNESS_RELIEF = 7;	// #DEFINE
	final static int PAIN_RELIEF = 8;	    // #DEFINE
	final static int HUNGER = 9;		    // #DEFINE
	final static int DEHYDRATION = 10;       // #DEFINE
	final static int ANXIETY = 11;      	 // #DEFINE
    final String STR_SATIVA= "Sativa";
    final String STR_INDICA = "Indica";
    final String STR_HYBRID = "Hybrid";

    // Globals
    static int finalArraySize;
    static int[] finalArray;

    CannabisStrainDatabase_Helper db = new CannabisStrainDatabase_Helper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);


        // THIS IS ALL OLD CODE
        Button findStrainsButton = findViewById(R.id.btnFindStrains);
        Button myStrainsButton = findViewById(R.id.btnMyStrains);
        Button constructorButton = findViewById(R.id.btnConstructor);
        Button supportButton = findViewById(R.id.btnSupport);


        findStrainsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, FindStrainsActivity.class));
            }
        });

        myStrainsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, MyStrainsActivity.class));
            }
        });

        constructorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ConstructorActivity.class));
            }
        });

        supportButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SupportActivity.class));
            }
        });
   }
}
