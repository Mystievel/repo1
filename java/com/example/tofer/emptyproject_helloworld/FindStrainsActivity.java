package com.example.tofer.emptyproject_helloworld;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

//todo is there a bug in the sorting now? Investigate and fix if needed.

public class FindStrainsActivity extends MainActivity {
    // Defines
    private int EFFECTS_COL_START_INDEX = 5;  // #DEFINE

    // Local variables
	private static int startingValue = 75;
	private static int progressChangedValue = startingValue;
    private final static int[] effectsArray = new int[]{RELAXED, HAPPY, HUNGRY};
    private TextView searchIntensityValue;
    private SeekBar searchIntensitySeekBar;

	private FindStrainsItemData itemsData[] = new FindStrainsItemData[3];

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
		for (int i = 0; i < 3; i++) {
            itemsData[i] = new FindStrainsItemData("" + db.getStrainDatabaseColumnTitles(i + EFFECTS_COL_START_INDEX));
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
				finalArray = collectAndFilterAllStrainData();
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
	//                        Collect and Filter All Strain Data
	//**********************************************************************************************
	public int[] collectAndFilterAllStrainData() {
		// Start out with a list of all strains.
		int[] filteredArray = new int[(int) db.getStrainDatabaseRows()];
		filteredArray = getAllStrainIDs(filteredArray);
		//Log.d("FObjName", "" + db.getStrainData(filteredArray[77]).getStrainName());  // Banana OG
		//Log.d("FObjID", "" + db.getStrainData(filteredArray[77]).getStrainId());  // 77
		//Log.d("ItemLength", "" + itemsData.length);  // 3

		// Loop through the buttons array and filter based on each buttons' selection.
		// TODO: This piece of code still appears to take the longest.
		for (int i = 0; i < itemsData.length; i++) {
			filteredArray = filterArrayByColumn(itemsData[i].getFilter(), db, effectsArray[i], filteredArray);
		}

		// Reduce the array to non-null values only.
		finalArraySize = getFinalArraySize(filteredArray, db);
		finalArray = new int[finalArraySize];
		finalArray = reduceFilteredArray(filteredArray, db);
		Log.d("FinalArraySize", "" + finalArraySize);
		Log.d("FinalArrayIndex 2", "" + db.getStrainData(finalArray[2]).getStrainName());
		Log.d("FinalArrayIndex 25", "" + db.getStrainData(finalArray[25]).getStrainName());
		Log.d("FinalArrayIndex 60", "" + db.getStrainData(finalArray[60]).getStrainName());

		// Return
		return finalArray;
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
	//
	// todo: Besides those listed above, maybe pull all strain data from the database once and store locally
	//		into an array, will this speed things up rather than pulling from the database objects as we need
	//		values?
	//
	// NOTE: This part does take a bit of time, but it's the next routine(s) that take 10x longer...maybe it's not worth investigating the above
	//		necessarilly for this routine, but for the others.
	//**********************************************************************************************
	public int[] getAllStrainIDs(int[] filteredArray) {
		for (int i = 0; i < db.getStrainDatabaseRows(); i++) {
			filteredArray[i] = db.getStrainData(i + 0).getStrainId();
			//Log.d("getAllStrainIDs", "filteredArray[i] = " + filteredArray[i]);
		}
		//Log.d("getAllStrainIDs", "Got all strains.");
		return filteredArray;
	} //********************************************************************************************


	//**********************************************************************************************
	//                             Filter Array by db Column
	//
	// Search a single column within a database, make invalid entries == "" (null), and return the final array.
	// How to call: filteredArray = reduceDBArrayByColumnSearch(relaxedBtnSelected);
	// Later we take out all "" (null) entries, so we know the array length = database entries.
	//**********************************************************************************************
	public int[] filterArrayByColumn(int btnResult, CannabisStrainDatabase_Helper db, int effect, int[] originalArray) {
		// Declare local variables
		int databaseRows = (int) db.getStrainDatabaseRows();
		int i;
		double minLimit = (1.0 - (progressChangedValue/100.0));
		double maxLimit = (progressChangedValue/100.0);
		double effectValue = 0;
		int newArray[] = new int[databaseRows];

		if (btnResult == MIN) {
			for (i = 0; i < databaseRows; i++) {
				effectValue = db.getStrainData(i + 0).getEffect(effect);
				//Log.d("minSelected", "Min is selected for effect #: " + effect + ". original id: " + originalArray[i] + ". value: " + effectValue + ". limit: " + minLimit);
				if (effectValue < minLimit) {
					if (originalArray[i] == BLANK_ENTRY) {
						newArray[i] = BLANK_ENTRY;
						//Log.d("KeepMinFieldBlank", "new id: " + newArray[i] + ". original id: " + originalArray[i] + ". new = blank. value: " + effectValue + ". limit: " + minLimit);
					} else {
						newArray[i] = db.getStrainData(i + 0).getStrainId();
						//Log.d("KeepMinField", ". new id: " + newArray[i] + ". original id: " + originalArray[i] + ". value: " + effectValue + ". limit: " + minLimit);
					}
				} else {
					newArray[i] = BLANK_ENTRY;
					//Log.d("ScrapMinField",". new id = blank. value: " + effectValue + ". limit: " + minLimit);
				}
				//Log.d("filterArrayByColumnMin", "original id: " + originalArray[i] + ". new ID: " + newArray[i] + ". value: " + effectValue + ". limit: " + minLimit);
			}
		} else if (btnResult == MAX) {
			for (i = 0; i < databaseRows; i++) {
				effectValue = db.getStrainData(i + 0).getEffect(effect);
				//Log.d("maxSelected", "Max is selected for effect #: " + effect + ". original id: " + originalArray[i] + ". value: " + effectValue + ". limit: " + minLimit);
				if (effectValue >= maxLimit) {
					if (originalArray[i] == BLANK_ENTRY) {
						newArray[i] = BLANK_ENTRY;
						//Log.d("KeepMaxFieldBlank", "new id: " + newArray[i] + ". original id: " + originalArray[i] + ". new = blank. value: " + effectValue + ". limit: " + minLimit);
					} else {
						newArray[i] = db.getStrainData(i + 0).getStrainId();
						//Log.d("KeepMaxField", ". new id: " + newArray[i] + ". original id: " + originalArray[i] + ". value: " + effectValue + ". limit: " + minLimit);
					}
				} else {
					newArray[i] = BLANK_ENTRY;
					//Log.d("ScrapMaxField",". new id = blank. value: " + effectValue + ". limit: " + minLimit);
				}
				//Log.d("filterArrayByColumnMax", "new ID: " + newArray[i] + ". original id: " + originalArray[i] + ". value: " + effectValue + ". limit: " + minLimit);
			}
		} else {
			//Log.d("ignoreSelected", "Ignore is selected for effect #: " + effect + ".");
			for (i = 0; i < databaseRows; i++) {
				newArray[i] = db.getStrainData(i + 0).getStrainId();
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
	public int getFinalArraySize(int[] filteredArray, CannabisStrainDatabase_Helper db) {
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
	public int[] reduceFilteredArray(int[] filteredArray, CannabisStrainDatabase_Helper db) {
		// Declare local variables
		int databaseRows = (int) db.getStrainDatabaseRows();
		int i;
		int count = getFinalArraySize(filteredArray, db);
		//Log.d("reduceFilteredArrayCt", "Final array size: " + count);
		int subtractor = 0;
		int reducedArray[] = new int[databaseRows];

		// Now populate the reducedArray without the blank items from the original array.
		for (i = 0; i < databaseRows; i++) {
			if (filteredArray[i] == BLANK_ENTRY) {
				subtractor++;
				//Log.d("reduceFilteredArrayIf", "Found blank at index: " + i + ". filteredArray = " + db.getStrainData(filteredArray[i] + 0).getStrainName() + ".");
			} else {
				reducedArray[i - subtractor] = db.getStrainData(filteredArray[i]).getStrainId();
				//Log.d("reduceFilteredArrayEl", "index: " + (i - subtractor) + ". reducedArray " + db.getStrainData(reducedArray[i - subtractor] + 0).getStrainName() + ". filteredArray " + db.getStrainData(filteredArray[i] + 0).getStrainName());
			}
			//Log.d("subtractorMath", "index: " + i + ". subtractor: " + subtractor + ". difference: " + (i - subtractor));
		}

		// Return the result
		//Log.d("reduceFilteredArrayEnd", "Blanks found: " + subtractor + " out of " + databaseRows + " items.");
		return reducedArray;
	} //********************************************************************************************


	//**********************************************************************************************
	//**********************************************************************************************
	//**********************************************************************************************
	//							Find Strains: RecyclerView Adapter
	//**********************************************************************************************
	//**********************************************************************************************
	//**********************************************************************************************
	public class FindStrainsRecyclerViewAdapter extends RecyclerView.Adapter<FindStrainsRecyclerViewAdapter.ViewHolder> {
		// Local variables
		private FindStrainsItemData[] itemsData;

		//******************************************************************************************
		// Create Adapter
		//******************************************************************************************
		public FindStrainsRecyclerViewAdapter(FindStrainsItemData[] itemsData) {
			this.itemsData = itemsData;
		} //****************************************************************************************


		//******************************************************************************************
		// Create new views (invoked by the layout manager)
		//******************************************************************************************
		@Override
		public FindStrainsRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.find_strains_item_layout, null);
			ViewHolder viewHolder = new ViewHolder(itemLayoutView);
			return viewHolder;
		} //****************************************************************************************


		//******************************************************************************************
		// Bind View Holder: Replace the contents of a view (invoked by the layout manager)
		//******************************************************************************************
		@Override
		public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
			// - get data from your itemsData at this position
			// - replace the contents of the view with that itemsData
			viewHolder.effectLbl.setText(itemsData[position].getEffect());

			// This working piece of code shows that we can click the radiogroup and perform an action based off the click.
			viewHolder.effectsBtnGroup.check(position);
			viewHolder.effectsBtnGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(RadioGroup group, int checkedId) {
					int IGNORE = 0;
					int MIN = 1;
					int MAX = 2;

					switch (checkedId) {
						case R.id.effect_ignore:
							itemsData[position].setFilter(IGNORE);
							//Log.d("RecyclerViewItemClicked", "ignore clicked at " + position);
							break;
						case R.id.effect_min:
							itemsData[position].setFilter(MIN);
							//Log.d("RecyclerViewItemClicked", "min clicked at " + position);
							break;
						case R.id.effect_max:
							itemsData[position].setFilter(MAX);
							//Log.d("RecyclerViewItemClicked", "max clicked at " + position);
							break;
						default:
							break;
					}
				}
			});
		} //****************************************************************************************


		//******************************************************************************************
		// View Holder: Inner class to hold a reference to each item of RecyclerView
		//******************************************************************************************
		public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
			public TextView effectLbl;
			public RadioGroup effectsBtnGroup;

			public ViewHolder(View itemLayoutView) {
				super(itemLayoutView);
				effectLbl = itemLayoutView.findViewById(R.id.item_title);
				effectsBtnGroup = itemLayoutView.findViewById(R.id.effect_group);
				itemLayoutView.setOnClickListener(this);  // Use this in conjunction with "implements View.OnClickListener" in the class header and the onClick method below to determine which item in the recyclerView was clicked
			}


			// Use this method to determine which item in the recyclerView was clicked
			@Override
			public void onClick(View view) {
				int position = getLayoutPosition();
				// get ID of the item clicked on
				// Add to TABLE "MY_STRAINS_TABLE"
				Log.d("SearchRecViewClick", "Item # Clicked: " + String.valueOf(position) + ".");
			}
		} //****************************************************************************************


		//******************************************************************************************
		// Return the size of your itemsData (invoked by the layout manager)
		//******************************************************************************************
		@Override
		public int getItemCount() {
			return itemsData.length;
		}
	}
}

