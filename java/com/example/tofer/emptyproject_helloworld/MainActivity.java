package com.example.tofer.emptyproject_helloworld;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

// todo Make background color scheme more earthy or psychadelic/mystical
// todo Change overall font to same fontFamily
// todo add buttons across all pages? Make main activity just a start screen then go to my strains
// todo walk the user through how to use the app "Start screen" --> My Strains --> Find Strains, etc.


public class MainActivity extends AppCompatActivity {
    // Global Defines
    int IGNORE = 0;
    int MIN = 1;
    int MAX = 2;
    int BLANK_ENTRY = -1;
    final static int RELAXED = 1;
    final static int HAPPY = 2;
    final static int HUNGRY = 3;
    final static int SLEEPY = 4;
    final static int CREATIVE = 5;
    final static int ENERGETIC = 6;
    final static int EUPHORIC = 7;

    // Globals
    static int finalArraySize;
    static int[] finalArray;

    CannabisStrainDatabase_Helper db = new CannabisStrainDatabase_Helper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        setContentView(R.layout.activity_main);
    }

    public void changePageFindStrains(View view) {
        //setContentView(R.layout.activity_findstrains);
    }

    public void changePageResults(View view) {
        setContentView(R.layout.activity_results);
    }

    public void changePageMyStrains(View view) {
        setContentView(R.layout.activity_mystrains);
    }

    public void changePageConstructor(View view) {
        setContentView(R.layout.activity_constructor);
    }

    public void changePageSupport(View view) {
        setContentView(R.layout.activity_support);
    }


    public void logStrainInfo(String tag, CannabisStrainDatabase_Helper strainInfo, int id) {
        Log.d("" + tag, "ID: " + strainInfo.getStrainData(id).getStrainId()
                + ". Strain Name: " + strainInfo.getStrainData(id).getStrainName()
                + ". MyStrain?: " + strainInfo.getStrainData(id).getMyStrains());
    }
}
