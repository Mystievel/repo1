package com.example.tofer.emptyproject_helloworld;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

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
    static String buffer_addToMyStrains;

    public CannabisStrainDatabase_Helper db = new CannabisStrainDatabase_Helper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button findstrainsbutton = (Button)findViewById(R.id.btnFindStrains);
        Button mystrainsbutton = (Button)findViewById(R.id.btnMyStrains);
        Button constructorbutton = (Button)findViewById(R.id.btnConstructor);
        Button supportbutton = (Button)findViewById(R.id.btnSupport);


        findstrainsbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, FindStrainsActivity.class));
            }
        });

        mystrainsbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, MyStrainsActivity.class));
            }
        });

        constructorbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ConstructorActivity.class));
            }
        });

        supportbutton.setOnClickListener(new View.OnClickListener() {
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
}
