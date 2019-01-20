package com.example.tofer.emptyproject_helloworld;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

// todo: High Priority - portrait mode only.
// todo: Medium Priority - maybe add a teaser "where to buy?" when viewing myStrains.
// todo: High Prority - before launch, ensure color and font size/scheme is same across app, maybe create "labels" and "buttons" for default use with variable fields.
// todo: High Priority - v2 - Find strain in store.
// todo: Low Priority - Change overall font to same fontFamily using a variable
// todo: Medium Priority - use general app behavior along with good text labels (Error messages) to walk the user through how to use the app My Strains --> "you dont have any strains, click on 'Find Strains' to begin searching.", , etc.
// todo Low Priority - add "blog" smoking tips, cautions, etc. very short easy to read information, no large pages.
// todo: Medium Priority - phone app seems fast...in case not, improve activity to activity load time by moving to "async"?: https://stackoverflow.com/questions/26382943/how-to-increase-the-speed-to-load-an-activity

public class MainActivity extends AppCompatActivity {
    // Global Defines
    int IGNORE = 0;						    // #DEFINE
    int MIN = 1;						    // #DEFINE
    int MAX = 2;						    // #DEFINE
    int BLANK_ENTRY = -1;				    // #DEFINE
	final static int HAPPINESS = 0;		    // #DEFINE
	final static int EUPHORIA = 1;		    // #DEFINE
	final static int FOCUS = 2;		        // #DEFINE
	final static int ENERGY = 3;		    // #DEFINE
	final static int RELAXATION = 4;	    // #DEFINE
	final static int SLEEPINESS = 5;	 	// #DEFINE
	final static int SICKNESS_RELIEF = 6;	// #DEFINE
	final static int PAIN_RELIEF = 7;	    // #DEFINE
	final static int HUNGER = 8;		    // #DEFINE
	final static int DEHYDRATION = 9;       // #DEFINE
	final static int ANXIETY = 10;      	 // #DEFINE
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
   }

   public String getEffectString(int effect) {
    	String effectName;
    	switch(effect) {
			case HAPPINESS:
				effectName = "Happiness";
				break;
			case EUPHORIA:
				effectName = "Euphoria";
				break;
			case FOCUS:
				effectName = "Focus";
				break;
			case ENERGY:
				effectName = "Energy";
				break;
			case RELAXATION:
				effectName = "Relaxation";
				break;
			case SLEEPINESS:
				effectName = "Sleepiness";
				break;
			case SICKNESS_RELIEF:
				effectName = "Sickness_Relief";
				break;
			case PAIN_RELIEF:
				effectName = "Pain_Relief";
				break;
			case HUNGER:
				effectName = "Hunger";
				break;
			case DEHYDRATION:
				effectName = "Dehydration";
				break;
			case ANXIETY:
				effectName = "Anxiety";
				break;
			default:
				effectName = "";
				break;
		}
		return effectName;
   }


	// Bubble sort
	public int[] sortHighToLow(int[] arrayOfStrainIDs, double[] listValues) {
		int arraySize = arrayOfStrainIDs.length - 1; // Bubble sort uses -1 on the total length since we compare j+1.
		int tempID;
		double tempValue;

		for (int i = 0; i < arraySize; i++) {
			for (int j = 0; j < arraySize; j++) {
				if (listValues[j] < listValues[j+1]) {
					// Swap the two items (ID)
					tempID = arrayOfStrainIDs[j];
					arrayOfStrainIDs[j] = arrayOfStrainIDs[j+1];
					arrayOfStrainIDs[j+1] = tempID;

					// Swap the two items (value)
					tempValue = listValues[j];
					listValues[j] = listValues[j+1];
					listValues[j+1] = tempValue;
				}
			}
		}
		return arrayOfStrainIDs;
	}


	// Bubble sort
	public int[] sortLowToHigh(int[] arrayOfStrainIDs, double[] listValues) {
		int arraySize = arrayOfStrainIDs.length - 1; // Bubble sort uses -1 on the total length since we compare j+1.
		int tempID;
		double tempValue;

		for (int i = 0; i < arraySize; i++) {
			for (int j = 0; j < arraySize; j++) {
				if (listValues[j] > listValues[j+1]) {
					// Swap the two items (ID)
					tempID = arrayOfStrainIDs[j];
					arrayOfStrainIDs[j] = arrayOfStrainIDs[j+1];
					arrayOfStrainIDs[j+1] = tempID;

					// Swap the two items (value)
					tempValue = listValues[j];
					listValues[j] = listValues[j+1];
					listValues[j+1] = tempValue;
				}
			}
		}
		return arrayOfStrainIDs;
	}
}
