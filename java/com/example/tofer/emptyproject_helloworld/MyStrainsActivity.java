package com.example.tofer.emptyproject_helloworld;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MyStrainsActivity extends MainActivity {
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mystrains);

		int numberOfMyStrains = getNumberOfMyStrains();	// For some reason, if this variable is NOT declared within onCreate we receive a "Null Pointer Exception".
		int[] myStrainsIndexArray = collectAndFilterMyStrains();
		MyStrainsItemData myItemsData[] = new MyStrainsItemData[numberOfMyStrains];

        // 1. get a reference to recyclerView
        RecyclerView recyclerView = findViewById(R.id.myStrainsList);
        for (int i = 0; i < numberOfMyStrains; i++) {
        	String strainName = db.getStrainData(myStrainsIndexArray[i] + 0).getStrainName();
        	String strainType = db.getStrainData(myStrainsIndexArray[i] + 0).getStrainType();
			myItemsData[i] = new MyStrainsItemData("" + strainName, "" + strainType);
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this));               // 2. set layoutManger
        MyStrainsRecyclerViewAdapter mAdapter = new MyStrainsRecyclerViewAdapter(myItemsData);    // 3. create an adapter
        recyclerView.setAdapter(mAdapter);                                                  // 4. set adapter
        recyclerView.setItemAnimator(new DefaultItemAnimator());                            // 5. set item animator to DefaultAnimator


		//******************************************************************************************
		// Main Page Clicked
		//******************************************************************************************
		Button mainpagebutton = findViewById(R.id.btnMainPage);
        mainpagebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MyStrainsActivity.this, MainActivity.class));
            }
        }); //**************************************************************************************

		// todo add buttons across all pages? Make main activity just a start screen then go to my strains
		// todo walk the user through how to use the app "Start screen" --> My Strains --> Find Strains, etc.
		// todo: if no strains exist, make a note "You don't have any saved strains, click on "Find Strains" to begin or see the Cannabis Constructor (advanced)."
    }


    // Todo CONTINUE HERE: Similar method to findstrainsactivity where we get a simplified array of indexes for the mystrains column.
	// todo: Maybe we can expand the algorithm for any column or series of columns.
	//**********************************************************************************************
	//						Get Number of items in "My Strains" list
	//**********************************************************************************************
	public int getNumberOfMyStrains() {
    	int count = 0;
		int numberOfRows = (int) db.getStrainDatabaseRows();
		for (int i = 0; i < numberOfRows; i++) {
			if (db.getStrainData(i).getMyStrains() == 1) {
				count++;
			}
		}
		return count;
	} //********************************************************************************************


	//**********************************************************************************************
	//                        Collect and Filter My Strains List
	//**********************************************************************************************
	public int[] collectAndFilterMyStrains() {
		// Start out with a list of all strains.
		int subtractor = 0;
		int numberOfRows = (int) db.getStrainDatabaseRows();
		int[] filteredArray = new int[numberOfRows];
		int[] finalArray = new int[getNumberOfMyStrains()];

		// Remove all entries not == 1.
		for (int i = 0; i < numberOfRows; i++) {
			if (db.getStrainData(i).getMyStrains() == 1) {
				filteredArray[i] = db.getStrainData(i).getStrainId();
			} else {
				filteredArray[i] = BLANK_ENTRY;
			}
		}

		// Now populate the reducedArray without the blank items from the original array.
		for (int i = 0; i < numberOfRows; i++) {
			if (filteredArray[i] == BLANK_ENTRY) {
				subtractor++;
			} else {
				finalArray[i - subtractor] = db.getStrainData(i + 0).getStrainId();
			}
		}

		// Return
		return finalArray;
	} //********************************************************************************************
}
