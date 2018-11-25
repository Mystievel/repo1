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

// Todo: make each radiobutton a label "ignore" "min" "max" not just circle, show when selected

public class FindStrainsActivity extends MainActivity {
    // Defines
    int IGNORE = 0;
    int MIN = 1;
    int MAX = 2;
    String BLANK_ENTRY = "";

    // Local variables
	static int startingValue = 75;
	static int progressChangedValue = startingValue;

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
    static FilteredArray[] finalArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_findstrains);

        // Initiate Views
        searchIntensitySeekBar = findViewById(R.id.seekBarSearchIntensity);
        searchIntensitySeekBar.setProgress(progressChangedValue);
        searchIntensityValue = findViewById(R.id.lblSearchIntensity);
        searchIntensityValue.setText("Search Intensity: " + searchIntensitySeekBar.getProgress() + "%");

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
				FilteredArray[] filteredArray = new FilteredArray[(int) db.getStrainDatabaseRows()];
				filteredArray = getAllStrainNamesAndIDs(filteredArray);
				Log.d("FObjName", "" + filteredArray[77].getStrainName());
				Log.d("FObjID", "" + filteredArray[77].getId());

                //String[] filteredArray = getAllStrainNames();

                //Log.d("ItemLength", "" + itemsData.length);
				//Log.d("FilteredArrayIndex 02", "" + filteredArray[2]);

                // Loop through the buttons array and filter based on each buttons' selection.
                for (int i = 0; i < itemsData.length; i++) {
                    filteredArray = filterArrayByColumn(itemsData[i].getFilter(), db, effectsArray[i], filteredArray);

					Log.d("FilteredArraySize", "" + getFinalArraySize(filteredArray, db));
					//Log.d("FilteredArrayIndex 2", "" + filteredArray[2]); // not working
					//Log.d("FilteredArrayIndex 25", "" + filteredArray[25]); // not working
					//Log.d("FilteredArrayIndex 60", "" + filteredArray[60]); // not working
                }

                // Reduce the array to non-null values only.
                finalArraySize = getFinalArraySize(filteredArray, db);
                finalArray = new FilteredArray[finalArraySize];
                finalArray = reduceFilteredArray(filteredArray, db);
                Log.d("FinalArraySize", "" + finalArraySize);
                Log.d("FinalArrayIndex 2", "" + finalArray[2].getStrainName());
				Log.d("FinalArrayIndex 25", "" + finalArray[25].getStrainName());
				Log.d("FinalArrayIndex 60", "" + finalArray[60].getId());
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
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChangedValue = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                searchIntensityValue.setText("Search Intensity: " + progressChangedValue + "%");
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                searchIntensityValue.setText("Search Intensity: " + progressChangedValue + "%");
            }
        }); //**************************************************************************************
	} //********************************************************************************************


	//**********************************************************************************************
	//                    Gets any single Column from the Strains Database
	// todo: see todo below or move items into database rather than memory.
	//https://stackoverflow.com/questions/24121589/how-to-iterate-through-very-large-string-arrays-in-android
	//
	// todo: instead of getting all strains at once, get just the n amount that are shown on the screen
	// due to the recycler view loading, this will speed up the searching too (must also be applied to
	// the searching routines too! This is not an easy task but is recommended for speed improvments.
	// https://developer.android.com/training/articles/perf-tips
	//**********************************************************************************************
	public FilteredArray[] getAllStrainNamesAndIDs(FilteredArray[] filteredArray) {
		for (int i = 0; i < db.getStrainDatabaseRows(); i++) {
			filteredArray[i] = new FilteredArray(db.getStrainData(i + 0).getStrainId() + 0, "" + db.getStrainData(i + 0).getStrainName());
		}
		//Log.d("getAllStrainNames()", "Got all strains.");
		return filteredArray;
	}


	//**********************************************************************************************
	//                             Filter Array by db Column
	//
	// Search a single column within a database, make invalid entries == "" (null), and return the final array.
	// How to call: filteredArray = reduceDBArrayByColumnSearch(relaxedBtnSelected);
	// Later we take out all "" (null) entries, so we know the array length = database entries.
	//**********************************************************************************************
	public FilteredArray[] filterArrayByColumn(int btnResult, CannabisStrainDatabase_Helper db, int effect, FilteredArray[] originalArray) {
		// Declare local variables
		int databaseRows = (int) db.getStrainDatabaseRows();
		int index;
		double minLimit = (1.0 - (progressChangedValue/100.0));
		double maxLimit = (progressChangedValue/100.0);
		double effectResult = 0;
		FilteredArray newArray[] = new FilteredArray[databaseRows];

		if (btnResult == MIN) {
			for (index = 0; index < databaseRows; index++) {
				effectResult = db.getStrainData(index + 0).getEffect(effect);
				Log.d("minSelected", "Min is selected for effect #: " + effect + ". original name: " + originalArray[index].getStrainName() + ". original id: " + originalArray[index].getId() + ". data: " + effectResult + ". limit: " + minLimit);
				if (effectResult < minLimit) {
					if (originalArray[index].getStrainName() == BLANK_ENTRY) {
						newArray[index] = new FilteredArray(db.getStrainData(index + 0).getStrainId() + 0, "" + BLANK_ENTRY);
						Log.d("KeepMinFieldBlank", "original: " + originalArray[index].getStrainName() + ". new: " + newArray[index].getStrainName() + ". data: " + effectResult + ". limit: " + minLimit);
					} else {
						newArray[index] = new FilteredArray(db.getStrainData(index + 0).getStrainId() + 0, "" + db.getStrainData(index + 0).getStrainName());
						Log.d("KeepMinField", "original: " + originalArray[index].getStrainName() + ". new: " + newArray[index].getStrainName() + ". data: " + effectResult + ". limit: " + minLimit);
					}
				} else {
					newArray[index] = new FilteredArray(db.getStrainData(index + 0).getStrainId() + 0, "" + BLANK_ENTRY);
					Log.d("ScrapMinField", "original: " + originalArray[index].getStrainName() + ". new: " + newArray[index].getStrainName() + ". data: " + effectResult + ". limit: " + minLimit);
				}
				Log.d("filterArrayByColumnMin", "original: " + originalArray[index].getStrainName() + ". new: " + newArray[index].getStrainName() + ". data: " + db.getStrainData(index + 0).getEffect(effect));
			}
		} else if (btnResult == MAX) {
			for (index = 0; index < databaseRows; index++) {
				effectResult = db.getStrainData(index + 0).getEffect(effect);
				Log.d("maxSelected", "Max is selected for effect #: " + effect + ". original name: " + originalArray[index].getStrainName() + ". original id: " + originalArray[index].getId() + ". data: " + effectResult + ". limit: " + maxLimit);
				if (effectResult >= maxLimit) {
					if (originalArray[index].getStrainName() == BLANK_ENTRY) {
						newArray[index] = new FilteredArray(db.getStrainData(index + 0).getStrainId() + 0, "" + BLANK_ENTRY);
						Log.d("KeepMaxFieldBlank", "original: " + originalArray[index].getStrainName() + ". new: " + newArray[index].getStrainName() + ". data: " + effectResult + ". limit: " + maxLimit);
					} else {
						newArray[index] = new FilteredArray(db.getStrainData(index + 0).getStrainId() + 0, "" + db.getStrainData(index + 0).getStrainName());
						Log.d("KeepMaxField", "original: " + originalArray[index].getStrainName() + ". new: " + newArray[index].getStrainName() + ". data: " + effectResult + ". limit: " + maxLimit);
					}
				} else {
					newArray[index] = new FilteredArray(db.getStrainData(index + 0).getStrainId() + 0, "" + BLANK_ENTRY);
					Log.d("ScrapMaxField", "original: " + originalArray[index].getStrainName() + ". new: " + newArray[index].getStrainName() + ". data: " + effectResult + ". limit: " + maxLimit);
				}
				Log.d("filterArrayByColumnMax", "original: " + originalArray[index].getStrainName() + ". new: " + newArray[index].getStrainName() + ". data: " + db.getStrainData(index + 0).getEffect(effect));
			}
		} else {
			Log.d("ignoreSelected", "Ignore is selected for effect #: " + effect + ".");
			for (index = 0; index < databaseRows; index++) {
				newArray[index] = new FilteredArray(db.getStrainData(index + 0).getStrainId() + 0, "" + db.getStrainData(index + 0).getStrainName());
			}
		}

		// Return the reduced array
		//Log.d("filterArrayByColumn()", "Filter " + effect + " complete.");
		//Log.d("arraysize", "" + getFinalArraySize(newArray, db));
		return newArray;
	} //********************************************************************************************


	//**********************************************************************************************
	//                             Get Filtered Array Size
	//**********************************************************************************************
	public int getFinalArraySize(FilteredArray[] filteredArray, CannabisStrainDatabase_Helper db) {
		// Declare local variables
		int databaseRows = (int) db.getStrainDatabaseRows();
		int index;
		int count = 0;

		// get the size that the new array will be
		for (index = 0; index < databaseRows; index++) {
			if (filteredArray[index].getStrainName() != BLANK_ENTRY) {
				count++;
			}
		}
		return count;
	} //********************************************************************************************


	//**********************************************************************************************
	//                             Reduce Filtered Array
	//**********************************************************************************************
	public FilteredArray[] reduceFilteredArray(FilteredArray[] filteredArray, CannabisStrainDatabase_Helper db) {
		// Declare local variables
		int databaseRows = (int) db.getStrainDatabaseRows();
		int index;
		int count = getFinalArraySize(filteredArray, db);
		Log.d("reduceFilteredArrayCt", "Final array size: " + count);
		int subtractor = 0;
		FilteredArray reducedArray[] = new FilteredArray[databaseRows];

		// Now populate the reducedArray without the blank items from the original array.
		for (index = 0; index < databaseRows; index++) {
			if (filteredArray[index].getStrainName() == BLANK_ENTRY) {
				subtractor++;
				Log.d("reduceFilteredArrayIf", "Found blank at index: " + index + ". filteredArray = " + filteredArray[index].getStrainName() + ".");
			} else {
				reducedArray[index - subtractor] = new FilteredArray(db.getStrainData(index + 0).getStrainId() + 0, "" + db.getStrainData(index + 0).getStrainName());
				Log.d("reduceFilteredArrayEl", "index: " + (index - subtractor) + ". reducedArray " + reducedArray[index - subtractor].getStrainName() + ". filteredArray " + filteredArray[index].getStrainName());
			}
			Log.d("subtractorMath", "index: " + index + ". subtractor: " + subtractor + ". difference: " + (index - subtractor));
		}

		// Return the result
		Log.d("reduceFilteredArrayEnd", "Blanks found: " + subtractor + " out of " + databaseRows + " items.");
		return reducedArray;
	} //********************************************************************************************
}