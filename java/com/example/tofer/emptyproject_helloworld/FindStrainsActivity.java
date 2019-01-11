package com.example.tofer.emptyproject_helloworld;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


// todo: High Priority - active search - show "Search will produce: x results." while moving the slider and selecting radio buttons


public class FindStrainsActivity extends MainActivity {
    // Defines
    private int EFFECTS_COL_START_INDEX = 5;	// #DEFINE

    // Local variables
	private static int startingValue = 75;
	private static int progressChangedValue = startingValue;
    private final static int[] effectsArray = new int[]{HAPPINESS, EUPHORIA, FOCUS, ENERGY, RELAXATION, SLEEPINESS, SICKNESS_RELIEF, PAIN_RELIEF, HUNGER, DEHYDRATION, ANXIETY};
    private int itemDataSize = effectsArray.length;
    private TextView searchIntensityValue;
    private SeekBar searchIntensitySeekBar;
    ArrayList<FindStrainsListItemData> itemsData = new ArrayList<>();
    String[] infoList = new String[itemDataSize];
	Button btnInfo;
    TextView lblInfoBox;
    Button btnCancel;
    RecyclerView recyclerView;


	//----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	//----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	//----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	//							FindStrainsActivity: onCreate
	//----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	//----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	//----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	@Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.findstrains_activity);

        // Initiate Views
        searchIntensitySeekBar = findViewById(R.id.seekBarSearchIntensity);
        searchIntensitySeekBar.setProgress(progressChangedValue);
        searchIntensityValue = findViewById(R.id.lblSearchIntensity);
        searchIntensityValue.setText("Search Intensity: " + searchIntensitySeekBar.getProgress() + "%");

		// 1. get a reference to recyclerView
		recyclerView = findViewById(R.id.searchList);
		for (int i = 0; i < itemDataSize; i++) {
			//Log.d("populateArrayList", "i=" + i + ". arraySize: " + itemDataSize + ". string: " + db.getStrainDatabaseColumnTitles(i + EFFECTS_COL_START_INDEX));
            itemsData.add(new FindStrainsListItemData("" + db.getStrainDatabaseColumnTitles(i + EFFECTS_COL_START_INDEX)));

			// Populate information tip text.  For now, all results are the same string when the user clicks on an item for more info
			try {
				infoList[i] = getString(R.string.findStrainsTip1);
			} catch (Exception e){
				infoList[i] = String.format("Failed to load string data for item #%d.", i);
			}
		}
		recyclerView.setLayoutManager(new LinearLayoutManager(this));               // 2. set layoutManger
		FindStrainsRecyclerViewAdapter mAdapter = new FindStrainsRecyclerViewAdapter(itemsData);    // 3. create an adapter
		recyclerView.setAdapter(mAdapter);                                                  // 4. set adapter
		recyclerView.setItemAnimator(new DefaultItemAnimator());                            // 5. set item animator to DefaultAnimator

        //******************************************************************************************
		// Start Search Clicked
		//******************************************************************************************
		final Button searchButton = findViewById(R.id.btnStartSearch);
		searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            	// todo: Medium Priority - only sort of working... search term: "android onclick set button text"
				// https://stackoverflow.com/questions/6297159/change-button-text-and-action-android-development
            	searchButton.setText("Processing...");
				finalArray = collectAndFilterAllStrainData();
                startActivity(new Intent(FindStrainsActivity.this, ResultsActivity.class));
            }
        }); //**************************************************************************************


		//******************************************************************************************
		// Info Object - Button Clicked
		// todo: Medium Priority - summarize the code block below into a routine **********************************************************************
		//******************************************************************************************
		btnInfo = findViewById(R.id.infoBtn);
		lblInfoBox = findViewById(R.id.lblInfoBox);
		btnCancel = findViewById(R.id.cancelBtn);
		lblInfoBox.setMovementMethod(new ScrollingMovementMethod()); // allow scrolling through text within textview
		btnInfo.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				// Show the 'x' btn and details.
				lblInfoBox.setVisibility(View.VISIBLE);
				btnCancel.setVisibility(View.VISIBLE);
				// Hide the list to prevent accidental button clicks.
				recyclerView.setVisibility(View.INVISIBLE);
			}
		});
		btnCancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				// Show the list to prevent accidental button clicks.
				recyclerView.setVisibility(View.VISIBLE);
				// Hide the'x' btn and details.
				lblInfoBox.setVisibility(View.INVISIBLE);
				btnCancel.setVisibility(View.INVISIBLE);
			}
		}); //**************************************************************************************


		// todo: Medium Priority - summarize the code block below into a routine **********************************************************************
		// todo: Medium Priority - this is done by creating a fragment
		//******************************************************************************************
        // Find Strains Page Clicked
        //******************************************************************************************
		Button btnFindStrainsPage = findViewById(R.id.btnFindStrainsPage);
		btnFindStrainsPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FindStrainsActivity.this, FindStrainsActivity.class));
            }
        }); //**************************************************************************************

		//******************************************************************************************
		// Find Strains Page Clicked
		//******************************************************************************************
		Button btnConstructorPage = findViewById(R.id.btnConstructorPage);
		btnConstructorPage.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				startActivity(new Intent(FindStrainsActivity.this, ConstructorActivity.class));
			}
		}); //**************************************************************************************

		//******************************************************************************************
		// Find Strains Page Clicked
		//******************************************************************************************
		Button btnSupportPage = findViewById(R.id.btnSupportPage);
		btnSupportPage.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				startActivity(new Intent(FindStrainsActivity.this, SupportActivity.class));
			}
		}); //**************************************************************************************

		//******************************************************************************************
		// Find Strains Page Clicked
		//******************************************************************************************
		Button btnMyStrainsPage = findViewById(R.id.btnMyStrainsPage);
		btnMyStrainsPage.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				startActivity(new Intent(FindStrainsActivity.this, MyStrainsActivity.class));
			}
		}); //**************************************************************************************
		// todo: Medium Priority - summarize the code block above into a routine **********************************************************************




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
		Log.d("timerF", "1");
		int numberOfRows = db.getStrainDatabaseRows();
		Log.d("timerF", "2");
		int[] filteredArray = db.getDatabaseValuesFromColumn_intArray("id", numberOfRows);
		Log.d("timerF", "3");
		//Log.d("FObjName", "" + db.getStrainData(filteredArray[77]).getStrainName());  // Banana OG
		//Log.d("FObjID", "" + db.getStrainData(filteredArray[77]).getStrainId());  // 77
		//Log.d("ItemLength", "" + itemsData.size());  // 10

		// Loop through the buttons array and filter based on each buttons' selection.
		// todo: Low Priority - This piece of code still takes quite a bit of time. (1400 ms).
		for (int i = 0; i < itemDataSize; i++) {
			filteredArray = filterArrayByColumn(itemsData.get(i).getFilter(), db, effectsArray[i], filteredArray);
		}
		Log.d("timerF", "4");

		// Reduce the array to non-null values only.
		finalArraySize = getFinalArraySize(filteredArray, db, numberOfRows);
		Log.d("timerF", "4.1");
		Log.d("FinalArraySize", "" + finalArraySize);
		finalArray = new int[finalArraySize];
		Log.d("FinalArraySize", "" + finalArray.length);
		Log.d("timerF", "4.2");
		finalArray = reduceFilteredArray(filteredArray, db);
		Log.d("timerF", "5");
		//Log.d("FinalArraySize", "" + finalArraySize);
		//Log.d("FinalArrayIndex 2", "" + db.getStrainData(finalArray[2]).getStrainName());
		//Log.d("FinalArrayIndex 25", "" + db.getStrainData(finalArray[25]).getStrainName());
		//Log.d("FinalArrayIndex 60", "" + db.getStrainData(finalArray[60]).getStrainName());

		// Return
		return finalArray;
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
		int databaseRows = db.getStrainDatabaseRows();
		int i;
		double minLimit = (1.0 - (progressChangedValue/100.0));
		double maxLimit = (progressChangedValue/100.0);
		double effectValue = 0;
		int newArray[] = new int[databaseRows];

		Log.d("timerFA", "1");
		int[] IDArray = db.getDatabaseValuesFromColumn_intArray("id", databaseRows);
		double[] valuesArray = db.getDatabaseValuesFromColumn_doubleArray(getEffectString(effect), databaseRows);

		Log.d("timerFA", "2");

		if (btnResult == MIN) {
			//Log.d("minSelected", "Min is selected for effect #: " + effect);
			for (i = 0; i < databaseRows; i++) {
				effectValue = valuesArray[i];
				//Log.d("minSelected", "Min is selected for effect #: " + effect + ". original id: " + originalArray[i] + ". value: " + effectValue + ". limit: " + minLimit);
				if (effectValue < minLimit) {
					if (originalArray[i] == BLANK_ENTRY) {
						newArray[i] = BLANK_ENTRY;
						//Log.d("KeepMinFieldBlank", "new id: " + newArray[i] + ". original id: " + originalArray[i] + ". new = blank. value: " + effectValue + ". limit: " + minLimit);
					} else {
						newArray[i] = IDArray[i];
						//Log.d("KeepMinField", ". new id: " + newArray[i] + ". original id: " + originalArray[i] + ". value: " + effectValue + ". limit: " + minLimit);
					}
				} else {
					newArray[i] = BLANK_ENTRY;
					//Log.d("ScrapMinField",". new id = blank. value: " + effectValue + ". limit: " + minLimit);
				}
				//Log.d("filterArrayByColumnMin", "original id: " + originalArray[i] + ". new ID: " + newArray[i] + ". value: " + effectValue + ". limit: " + minLimit);
			}
		} else if (btnResult == MAX) {
			//Log.d("maxSelected", "Max is selected for effect #: " + effect);
			for (i = 0; i < databaseRows; i++) {
				effectValue = valuesArray[i];
				//Log.d("maxSelected", "Max is selected for effect #: " + effect + ". original id: " + originalArray[i] + ". value: " + effectValue + ". limit: " + minLimit);
				if (effectValue >= maxLimit) {
					if (originalArray[i] == BLANK_ENTRY) {
						newArray[i] = BLANK_ENTRY;
						//Log.d("KeepMaxFieldBlank", "new id: " + newArray[i] + ". original id: " + originalArray[i] + ". new = blank. value: " + effectValue + ". limit: " + minLimit);
					} else {
						newArray[i] = IDArray[i];
						//Log.d("KeepMaxField", ". new id: " + newArray[i] + ". original id: " + originalArray[i] + ". value: " + effectValue + ". limit: " + minLimit);
					}
				} else {
					newArray[i] = BLANK_ENTRY;
					//Log.d("ScrapMaxField",". new id = blank. value: " + effectValue + ". limit: " + minLimit);
				}
				//Log.d("filterArrayByColumnMax", "new ID: " + newArray[i] + ". original id: " + originalArray[i] + ". value: " + effectValue + ". limit: " + minLimit);
			}
		} else {
			//Log.d("ignoreSelected", "Ignore is selected for effect #: " + effect);
			for (i = 0; i < databaseRows; i++) {
				if (originalArray[i] == BLANK_ENTRY) {
					newArray[i] = BLANK_ENTRY;
				} else {
					newArray[i] = originalArray[i];
				}
				//Log.d("ignoreSelected", "original = " + originalArray[i] + ". newArray = " + newArray[i]);
			}
		}

		Log.d("timerFA", "3");

		// Return the reduced array
		//Log.d("filterArrayByColumn()", "Filter " + effect + " complete.");
		//Log.d("arraysize", "" + getFinalArraySize(newArray, db));
		return newArray;
	} //********************************************************************************************


	//**********************************************************************************************
	//                             Get Filtered Array Size
	//**********************************************************************************************
	public int getFinalArraySize(int[] filteredArray, CannabisStrainDatabase_Helper db, int databaseRows) {
		// Declare local variables
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
		int databaseRows = db.getStrainDatabaseRows();
		int i;
		int count = getFinalArraySize(filteredArray, db, databaseRows);
		//Log.d("reduceFilteredArrayCt", "Final array size: " + count);
		int subtractor = 0;
		int reducedArray[] = new int[databaseRows];
		int[] IDArray = db.getDatabaseValuesFromColumn_intArray("id", databaseRows);

		// Now populate the reducedArray without the blank items from the original array.
		for (i = 0; i < databaseRows; i++) {
			if (filteredArray[i] == BLANK_ENTRY) {
				subtractor++;
				//Log.d("reduceFilteredArrayIf", "Found blank at index: " + i + ". filteredArray = " + db.getStrainData(filteredArray[i] + 0).getStrainName() + ".");
			} else {
				reducedArray[i - subtractor] = IDArray[filteredArray[i]];
				//Log.d("reduceFilteredArrayEl", "index: " + (i - subtractor) + ". reducedArray " + db.getStrainData(reducedArray[i - subtractor] + 0).getStrainName() + ". filteredArray " + db.getStrainData(filteredArray[i] + 0).getStrainName());
			}
			//Log.d("subtractorMath", "index: " + i + ". subtractor: " + subtractor + ". difference: " + (i - subtractor));
		}

		// Return the result
		//Log.d("reduceFilteredArrayEnd", "Blanks found: " + subtractor + " out of " + databaseRows + " items.");
		return reducedArray;
	} //********************************************************************************************


	//----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	//----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	//----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	//							FindStrains: RecyclerView Adapter
	//----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	//----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	//----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	public class FindStrainsRecyclerViewAdapter extends RecyclerView.Adapter<FindStrainsRecyclerViewAdapter.ViewHolder> {
		// Local variables
		private List<FindStrainsListItemData> itemsData;

		//******************************************************************************************
		// Create Adapter
		//******************************************************************************************
		public FindStrainsRecyclerViewAdapter(List<FindStrainsListItemData> itemsData) {
			this.itemsData = itemsData;
		} //****************************************************************************************


		//******************************************************************************************
		// Create new views (invoked by the layout manager)
		//******************************************************************************************
		@Override
		public FindStrainsRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.findstrains_itemlayout, null);
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
			viewHolder.effectLbl.setText(itemsData.get(position).getEffect());

			// Set the default selection to "omit", save previous selection otherwise.
			// todo: High Priority - save fields when return to this page in a single opened session
			// todo: High Priority - appears that we are not properly recycling the view when code is placed in onBind...???
			int myFilter = itemsData.get(position).getFilter();
			if (myFilter == MIN) {
				viewHolder.minBtn.setChecked(true);
				Log.d("RecyclerViewItemClicked", String.format("Set item #: %d to min. Read in %d.", position, myFilter));
			} else if (myFilter == MAX) {
				viewHolder.maxBtn.setChecked(true);
				Log.d("RecyclerViewItemClicked", String.format("Set item #: %d to max. Read in %d.", position, myFilter));
			} else {
				viewHolder.ignoreBtn.setChecked(true);
				Log.d("RecyclerViewItemClicked", String.format("Set item #: %d to ignore. Read in %d.", position, myFilter));
			}


			// This working piece of code shows that we can click the radiogroup and perform an action based off the click.
			viewHolder.effectsBtnGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
				// todo: High Priority - issue is when scrolling it activates a click event when the next item appears in view...
				@Override
				public void onCheckedChanged(RadioGroup group, int checkedId) {
					RadioButton checkedBtn = findViewById(checkedId);
					int ignoreID = R.id.effect_ignore;
					int minID = R.id.effect_min;
					int maxID = R.id.effect_max;
					int myFilter = itemsData.get(position).getFilter();
					//Log.d("RecyclerViewItemClicked", "all btn ids: ignore: " + ignoreID + ". min: " + minID + ". max: " + maxID);

					// Sets the value of the filter clicked at the given position in the list to the itemsData object.
					//Log.d("RecyclerViewItemClicked", "btn id: " + checkedId);
					if (checkedId == minID) {
						itemsData.get(position).setFilter(MIN);
						Log.d("RecyclerViewItemClicked", String.format("min clicked at %d, set to %d with value read-in: %d.", position, MIN, myFilter));
					} else if (checkedId == maxID) {
						itemsData.get(position).setFilter(MAX);
						Log.d("RecyclerViewItemClicked", String.format("max clicked at %d, set to %d with value read-in: %d.", position, MAX, myFilter));
					} else {
						itemsData.get(position).setFilter(IGNORE);
						Log.d("RecyclerViewItemClicked", String.format("ignore clicked at %d, set to %d with value read-in: %d.", position, IGNORE, myFilter));
					}

					// Print all itemsData clicked results in order.
					//logAllItemsClicked(getItemCount(), itemsData);
				}
			});
		} //****************************************************************************************


		public void logAllItemsClicked(int itemsSize, List<FindStrainsListItemData> itemsData) {
			// Stylistic printing (box)
			Log.d("RecyclerViewItemClicked", "-----------------------------------------");

			// Log which radio button is clicked for all items in the list.
			for(int i = 0; i < itemsSize; i++) {
				Log.d("RecyclerViewItemClicked", itemsData.get(i).getFilter() + " at " + i);
			}

			// Stylistic printing (box)
			Log.d("RecyclerViewItemClicked", "-----------------------------------------");
		}


		//******************************************************************************************
		// View Holder: Inner class to hold a reference to each item of RecyclerView
		//******************************************************************************************
		public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
			public TextView effectLbl;
			public RadioGroup effectsBtnGroup;

			// These must be defined?
			public RadioButton ignoreBtn;
			public RadioButton minBtn;
			public RadioButton maxBtn;

			// Define all items in here that we want to listen for on click?
			public ViewHolder(View itemLayoutView) {
				super(itemLayoutView);
				effectLbl = itemLayoutView.findViewById(R.id.item_title);
				effectsBtnGroup = itemLayoutView.findViewById(R.id.effect_group);
				// These must be defined?
				ignoreBtn = itemLayoutView.findViewById(R.id.effect_ignore);
				minBtn = itemLayoutView.findViewById(R.id.effect_min);
				maxBtn = itemLayoutView.findViewById(R.id.effect_max);

				// onClick listener for item clicked
				itemLayoutView.setOnClickListener(this);
			}

			@Override
			public void onClick(View view) {
				int position = getAdapterPosition();

				// Determine which item was clicked and act accordingly.
				// todo: Low Priority - either 1) set focus on element clicked using recyclerView.smoothScrollToPosition(position), requires creating a list or changing text in list, or...
				// ...2) st convert code so that the itemsData just all change text to these strings;

				// Set text based on the item clicked
				lblInfoBox.setText(infoList[position]);
				// Show the 'x' btn and details.
				lblInfoBox.setVisibility(View.VISIBLE);
				btnCancel.setVisibility(View.VISIBLE);
				// Hide the list to prevent accidental button clicks.
				recyclerView.setVisibility(View.INVISIBLE);
			}
		} //****************************************************************************************


		//******************************************************************************************
		// Return the size of your itemsData (invoked by the layout manager)
		//******************************************************************************************
		@Override
		public int getItemCount() {
			return itemsData.size();
		}
	}
}

