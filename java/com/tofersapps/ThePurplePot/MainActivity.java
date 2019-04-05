package com.tofersapps.ThePurplePot;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.sql.SQLException;

// todo: High Priority - v2 - Find strain in store.
// todo: Medium Priority - code cleanup - remove use of database calls and store a copy into various arrays as needed. Crunch data in Main (loading screen), and allow all activities to access variables (always make copies when using) - huge speed improvements seen using this concept.
// todo: Medium Prority - Create "labels" and "buttons" for default use of xml with variable fields across the board.
// todo: Medium Priority - add "blog" smoking tips, cautions, etc. very short easy to read information, no large pages.

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
	final static int ANXIETY = 10;      	// #DEFINE
    final String STR_SATIVA = "Sativa";
    final String STR_INDICA = "Indica";
    final String STR_HYBRID = "Hybrid";

    // Globals
    static int finalArraySize;
    static int[] finalArray;
	CannabisStrainDatabase_Helper db;

	int numberOfRows;
	int[] arrayOfIDs;
	double[] effectArray_happiness;
	double[] effectArray_euphoria;
	double[] effectArray_focus;
	double[] effectArray_energy;
	double[] effectArray_relaxation;
	double[] effectArray_sleepiness;
	double[] effectArray_sicknessRelief;
	double[] effectArray_painRelief;
	double[] effectArray_hunger;
	double[] effectArray_dehydration;
	double[] effectArray_anxiety;


	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        db = new CannabisStrainDatabase_Helper(this);
		numberOfRows = db.getStrainDatabaseRows();
		arrayOfIDs = db.getDatabaseValuesFromColumn_intArray("id", numberOfRows);
		effectArray_happiness = db.getDatabaseValuesFromColumn_doubleArray(getEffectString(HAPPINESS), numberOfRows);
		effectArray_euphoria = db.getDatabaseValuesFromColumn_doubleArray(getEffectString(EUPHORIA), numberOfRows);
		effectArray_focus = db.getDatabaseValuesFromColumn_doubleArray(getEffectString(FOCUS), numberOfRows);
		effectArray_energy = db.getDatabaseValuesFromColumn_doubleArray(getEffectString(ENERGY), numberOfRows);
		effectArray_relaxation = db.getDatabaseValuesFromColumn_doubleArray(getEffectString(RELAXATION), numberOfRows);
		effectArray_sleepiness = db.getDatabaseValuesFromColumn_doubleArray(getEffectString(SLEEPINESS), numberOfRows);
		effectArray_sicknessRelief = db.getDatabaseValuesFromColumn_doubleArray(getEffectString(SICKNESS_RELIEF), numberOfRows);
		effectArray_painRelief = db.getDatabaseValuesFromColumn_doubleArray(getEffectString(PAIN_RELIEF), numberOfRows);
		effectArray_hunger = db.getDatabaseValuesFromColumn_doubleArray(getEffectString(HUNGER), numberOfRows);
		effectArray_dehydration = db.getDatabaseValuesFromColumn_doubleArray(getEffectString(DEHYDRATION), numberOfRows);
		effectArray_anxiety = db.getDatabaseValuesFromColumn_doubleArray(getEffectString(ANXIETY), numberOfRows);


		// Background Clicked
		TextView welcomeMessage = findViewById(R.id.WelcomeMessage);
		welcomeMessage.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				startActivity(new Intent(MainActivity.this, FindStrainsActivity.class));
			}
		});

		//******************************************************************************************
		// Menu Bar Object - Button Clicked
		// todo: Medium Priority - summarize the code block below into a routine **********************************************************************
		//******************************************************************************************
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
		// Support Page Clicked
		//******************************************************************************************
		Button btnSupportPage = findViewById(R.id.btnSupportPage);
		btnSupportPage.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				startActivity(new Intent(MainActivity.this, SupportActivity.class));
			}
		}); //**************************************************************************************
		//******************************************************************************************
		// My Strains Page Clicked
		//******************************************************************************************
		Button btnMyStrainsPage = findViewById(R.id.btnMyStrainsPage);
		btnMyStrainsPage.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				startActivity(new Intent(MainActivity.this, MyStrainsActivity.class));
			}
		}); //**************************************************************************************
		/*
		//******************************************************************************************
		// FindStore Page Clicked
		//******************************************************************************************
		Button btnFindStore = findViewById(R.id.btnFindStorePage);
		btnFindStore.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				startActivity(new Intent(MainActivity.this, FindInStoreActivity.class));
			}
		}); //**************************************************************************************
*/
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
