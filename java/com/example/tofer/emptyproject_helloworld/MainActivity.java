package com.example.tofer.emptyproject_helloworld;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

// todo add "?" to provide more info, last feature before moving to apple, speed should be tested with an actual device

// todo Change overall font to same fontFamily
// todo walk the user through how to use the app "Start screen" --> My Strains --> Find Strains, etc.
// just use general app behavior and text labels to achieve this.
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
    final String STR_SATIVA = "Sativa";
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


		// TODO summarize the code block below into a routine **********************************************************************
		// Todo - this is done by creating a fragment
		//******************************************************************************************
		// Find Strains Page Clicked
		//******************************************************************************************
		Button btnFindStrainsPage = findViewById(R.id.btnFindStrainsPage);
		btnFindStrainsPage.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				startActivity(new Intent(MainActivity.this, FindStrainsActivity.class));
			}
		}); //**************************************************************************************

		//******************************************************************************************
		// Find Strains Page Clicked
		//******************************************************************************************
		Button btnConstructorPage = findViewById(R.id.btnConstructorPage);
		btnConstructorPage.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				startActivity(new Intent(MainActivity.this, ConstructorActivity.class));
			}
		}); //**************************************************************************************

		//******************************************************************************************
		// Find Strains Page Clicked
		//******************************************************************************************
		Button btnSupportPage = findViewById(R.id.btnSupportPage);
		btnSupportPage.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				startActivity(new Intent(MainActivity.this, SupportActivity.class));
			}
		}); //**************************************************************************************

		//******************************************************************************************
		// Find Strains Page Clicked
		//******************************************************************************************
		Button btnMyStrainsPage = findViewById(R.id.btnMyStrainsPage);
		btnMyStrainsPage.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				startActivity(new Intent(MainActivity.this, MyStrainsActivity.class));
			}
		}); //**************************************************************************************
		// TODO summarize the code block above into a routine **********************************************************************
   }
}
