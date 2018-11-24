package com.example.tofer.emptyproject_helloworld;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class FindStrainsActivity extends MainActivity {
    // Defines
    int IGNORE = 0;
    int MIN = 1;
    int MAX = 2;
    String BLANK_ENTRY = "";

    // Local variables
    final static int RELAXED = 1;
    final static int HAPPY = 2;
    final static int HUNGRY = 3;
    final static int SLEEPY = 4;
    final static int CREATIVE = 5;
	final static int ENERGETIC = 6;
	final static int EUPHORIC = 7;
    final static int[] effectsArray = new int[]{RELAXED, HAPPY, HUNGRY, SLEEPY, CREATIVE, ENERGETIC, EUPHORIC};

    final CannabisStrainDatabase_Helper db = new CannabisStrainDatabase_Helper(this);
    private TextView searchIntensityValue;
    private SeekBar searchIntensitySeekBar;
    static int finalArraySize;
    static String[] finalArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_findstrains);

        // Initiate Views
        final int startingValue = 95;
        searchIntensitySeekBar = findViewById(R.id.seekBarSearchIntensity);
        searchIntensitySeekBar.setProgress(startingValue);
        searchIntensityValue = findViewById(R.id.lblSearchIntensity);
        searchIntensityValue.setText("Search Intensity: " + searchIntensitySeekBar.getProgress());

        // Setup Button variables
        Button searchButton = findViewById(R.id.btnStartSearch);
        Button mainPageButton = findViewById(R.id.btnMainPage);


		// 1. get a reference to recyclerView
		RecyclerView recyclerView = findViewById(R.id.searchList);
		final FindStrainsItemData itemsData[] = new FindStrainsItemData[3];
		for (int i = 0; i < 3; i++) {
            itemsData[i] = new FindStrainsItemData("" + db.getStrainDatabaseColumnTitles(i+2));
		}
		recyclerView.setLayoutManager(new LinearLayoutManager(this));               // 2. set layoutManger
		FindStrainsRecyclerViewAdapter mAdapter = new FindStrainsRecyclerViewAdapter(itemsData);    // 3. create an adapter
		recyclerView.setAdapter(mAdapter);                                                  // 4. set adapter
		recyclerView.setItemAnimator(new DefaultItemAnimator());                            // 5. set item animator to DefaultAnimator


        //******************************************************************************************
        // Start Search Clicked
        //******************************************************************************************
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
				// Start out with a list of all strains.
                String[] filteredArray = getAllStrainNames();

                // Loop through the buttons array and filter based on each buttons' selection.
                for (int i = 0; i < itemsData.length; i++) {
                    filteredArray = filterArrayByColumn(itemsData[i].getFilter(), db, effectsArray[i], filteredArray);
                }

                // Reduce the array to non-null values only.
                // Todo fix odd bug, if you scroll down in the final list you'll see a lot of null values. not sure exactly how many.
                finalArraySize = getFinalArraySize(filteredArray, db);
                finalArray = new String[finalArraySize];
                finalArray = reduceFilteredArray(filteredArray, db);

                startActivity(new Intent(FindStrainsActivity.this, ResultsActivity.class));
            }
        }); //**************************************************************************************


        //******************************************************************************************
        // Main Page Clicked
        //******************************************************************************************
        mainPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FindStrainsActivity.this, MainActivity.class));
            }
        }); //**************************************************************************************


		//******************************************************************************************
		// Seek Bar: Perform seek bar change listener event used for getting the progress value
		//******************************************************************************************
        searchIntensitySeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            // Localized variables
            int progressChangedValue = startingValue;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChangedValue = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                searchIntensityValue.setText("Search Intensity: " + progressChangedValue);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                searchIntensityValue.setText("Search Intensity: " + progressChangedValue);
            }
        }); //**************************************************************************************
	} //********************************************************************************************


	//**********************************************************************************************
	//                    Gets any single Column from the Strains Database
	//**********************************************************************************************
	public String[] getAllStrainNames() {
		String[] allStrainNames = new String[(int) db.getStrainDatabaseRows()];
		for (int i = 0; i < db.getStrainDatabaseRows(); i++) {
			allStrainNames[i] = db.getStrainData(i+1).getStrainName();
		}
		return allStrainNames;
	}


	//**********************************************************************************************
	//                             Filter Array by db Column
	//
	// Search a single column within a database, make invalid entries == "" (null), and return the final array.
	// How to call: filteredArray = reduceDBArrayByColumnSearch(relaxedBtnSelected);
	// Later we take out all "" (null) entries, so we know the array length = database entries.
	//
	// Todo: Update "0.5" to something else (a percentile based off search intensity bar).
	// todo find the 'null' list item bug. is it in this method?
	//**********************************************************************************************
	public String[] filterArrayByColumn(int btnResult, CannabisStrainDatabase_Helper db, int effect, String[] originalArray) {
		// Declare local variables
		int databaseRows = (int) db.getStrainDatabaseRows();
		int index;
		String newArray[] = new String[databaseRows];

		if (btnResult == MIN) {
			for (index = 0; index < databaseRows; index++) {
				if (db.getStrainData(index + 1).getEffect(effect) < 0.5) {
					if (originalArray[index] == BLANK_ENTRY) {
						newArray[index] = BLANK_ENTRY;
					} else {
						newArray[index] = db.getStrainData(index + 1).getStrainName();
					}
				} else {
					newArray[index] = BLANK_ENTRY;
				}
			}
		} else if (btnResult == MAX) {
			for (index = 0; index < databaseRows; index++) {
				if (db.getStrainData(index + 1).getEffect(effect) >= 0.5) {
					if (originalArray[index] == BLANK_ENTRY) {
						newArray[index] = BLANK_ENTRY;
					} else {
						newArray[index] = db.getStrainData(index + 1).getStrainName();
					}
				} else {
					newArray[index] = BLANK_ENTRY;
				}
			}
		} else {
			for (index = 0; index < databaseRows; index++) {
				newArray[index] = db.getStrainData(index + 1).getStrainName();
			}
		}

		// Return the reduced array
		return newArray;
	} //********************************************************************************************


	//**********************************************************************************************
	//                             Get Filtered Array Size
	//**********************************************************************************************
	public int getFinalArraySize(String[] filteredArray, CannabisStrainDatabase_Helper db) {
		// Declare local variables
		int databaseRows = (int) db.getStrainDatabaseRows();
		int index;
		int count = 0;

		// get the size that the new array will be
		for (index = 0; index < databaseRows; index++) {
			if (filteredArray[index] != BLANK_ENTRY) {
				count++;
			}
		}
		return count;
	} //********************************************************************************************


	//**********************************************************************************************
	//                             Reduce Filtered Array
	//**********************************************************************************************
	public String[] reduceFilteredArray(String[] filteredArray, CannabisStrainDatabase_Helper db) {
		// Declare local variables
		int databaseRows = (int) db.getStrainDatabaseRows();
		int index;
		int count = getFinalArraySize(filteredArray, db);
		int subtractor = 0;
		String reducedArray[] = new String[databaseRows];

		// Now populate the reducedArray without the blank items from the original array.
		for (index = 0; index < count; index++) {
			if (filteredArray[index] != BLANK_ENTRY) {
				reducedArray[index - subtractor] = filteredArray[index];
			} else {
				subtractor++;
			}
		}
		return reducedArray;
	} //********************************************************************************************
}