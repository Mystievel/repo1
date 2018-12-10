package com.example.tofer.emptyproject_helloworld;

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


public class MainActivity extends AppCompatActivity {
    // Global Defines
    int IGNORE = 0;						    // #DEFINE
    int MIN = 1;						    // #DEFINE
    int MAX = 2;						    // #DEFINE
    int BLANK_ENTRY = -1;				    // #DEFINE
    final static int DEHYDRATION = 1;       // #DEFINE
    final static int ENERGY = 2;		    // #DEFINE
    final static int EUPHORIA = 3;		    // #DEFINE
    final static int FOCUS = 4;		        // #DEFINE
    final static int HAPPINESS = 5;		    // #DEFINE
    final static int HUNGER = 6;		    // #DEFINE
    final static int PAIN_RELIEF = 7;	    // #DEFINE
    final static int RELAXATION = 8;	    // #DEFINE
    final static int SICKNESS_RELIEF = 9;	// #DEFINE
    final static int SLEEPINESS = 10;	 	// #DEFINE


    // Globals
    static int finalArraySize;
    static int[] finalArray;

    CannabisStrainDatabase_Helper db = new CannabisStrainDatabase_Helper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

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


    // Page Change Events
    public void changePageMainActivity(View view) {
        setContentView(R.layout.main_activity);
    }

    public void changePageFindStrains(View view) {
        //setContentView(R.layout.findstrains_activity);
    }

    public void changePageResults(View view) {
        setContentView(R.layout.results_activity);
    }

    public void changePageMyStrains(View view) {
        setContentView(R.layout.mystrains_activity);
    }

    public void changePageConstructor(View view) {
        setContentView(R.layout.constructor_activity);
    }

    public void changePageSupport(View view) {
        setContentView(R.layout.support_activity);
    }


    public void logStrainInfo(String tag, CannabisStrainDatabase_Helper strainInfo, int id) {
        Log.d("" + tag, "ID: " + strainInfo.getStrainData(id).getStrainId()
                + ". Strain Name: " + strainInfo.getStrainData(id).getStrainName()
                + ". MyStrain?: " + strainInfo.getStrainData(id).getMyStrains());
    }
}
