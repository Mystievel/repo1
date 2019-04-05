package com.tofersapps.ThePurplePot;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;
import java.util.List;

// todo: High Priority - admob final setup, see email/account.
// todo: High Priority - v2 use the "Reward Video" adMob type and implement after every 2-3 searches or so unless they upgrade to premium to show their search results.
// todo: Medium Priority - click on list item shows directly on list the "info box" text rather than creating separate pop-up window.

public class FindStrainsActivity extends MainActivity {
    // Defines
    private int EFFECTS_COL_START_INDEX = 5;	// #DEFINE

    // Local variables
	private static int startingValue = 75;
	private static int progressChangedValue = startingValue;
    public final static int[] effectsArray = new int[]{HAPPINESS, EUPHORIA, FOCUS, ENERGY, RELAXATION, SLEEPINESS, SICKNESS_RELIEF, PAIN_RELIEF, HUNGER, DEHYDRATION, ANXIETY};
	private int itemDataSize = effectsArray.length;
    private TextView searchIntensityValue;
    private SeekBar searchIntensitySeekBar;
    ArrayList<FindStrainsListItemData> itemsData = new ArrayList<>();
    String[] infoList = new String[itemDataSize];
    TextView lblInfoBox;
    Button btnCancel;
    RecyclerView recyclerView;
	Button infoBtn;


	// ads
	private InterstitialAd mInterstitialAd;


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

		// Initialize ads.
		MobileAds.initialize(this, "ca-app-pub-7421513423472505~9290286998");
		mInterstitialAd = new InterstitialAd(this);
		mInterstitialAd.setAdUnitId("ca-app-pub-7421513423472505~9290286998");
		mInterstitialAd.loadAd(new AdRequest.Builder().build());

		// Initiate Views
        searchIntensitySeekBar = findViewById(R.id.seekBarSearchIntensity);
        searchIntensitySeekBar.setProgress(progressChangedValue);
        searchIntensityValue = findViewById(R.id.lblSearchIntensity);
		createSearchIntensityString(progressChangedValue, numberOfRows);

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

            	// Crunch data.
				finalArray = collectAndFilterAllStrainData(numberOfRows);

				// Show an advertisement. https://developers.google.com/admob/android/quick-start, https://developers.google.com/admob/android/interstitial
				if (mInterstitialAd.isLoaded()) {
					Log.d("mInterstitialAdTag", "The interstitial ad will now be shown.");
					mInterstitialAd.show();
				} else {
					Log.d("mInterstitialAdTag", "The interstitial ad was not loaded.");
				}

				// Show the results.
                startActivity(new Intent(FindStrainsActivity.this, ResultsActivity.class));
            }
        }); //**************************************************************************************


		infoBtn = findViewById(R.id.infoBtn);
		infoBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				// Set text based on the item clicked
				lblInfoBox.setText(getString(R.string.findStrainsInfo));

				// Show the 'x' btn and details.
				lblInfoBox.setVisibility(View.VISIBLE);
				btnCancel.setVisibility(View.VISIBLE);

				// Hide the list to prevent accidental button clicks.
				recyclerView.setVisibility(View.INVISIBLE);
			}
		}); //**************************************************************************************


		//******************************************************************************************
		// Info Object - Button Clicked
		// todo: Medium Priority - summarize the code block below into a routine **********************************************************************
		//******************************************************************************************
		lblInfoBox = findViewById(R.id.lblInfoBox);
		btnCancel = findViewById(R.id.cancelBtn);
		lblInfoBox.setMovementMethod(new ScrollingMovementMethod()); // allow scrolling through text within textview
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
                startActivity(new Intent(FindStrainsActivity.this, FindStrainsActivity.class));
            }
        }); //**************************************************************************************
		//******************************************************************************************
		// Support Page Clicked
		//******************************************************************************************
		Button btnSupportPage = findViewById(R.id.btnSupportPage);
		btnSupportPage.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				startActivity(new Intent(FindStrainsActivity.this, SupportActivity.class));
			}
		}); //**************************************************************************************
		//******************************************************************************************
		// My Strains Page Clicked
		//******************************************************************************************
		Button btnMyStrainsPage = findViewById(R.id.btnMyStrainsPage);
		btnMyStrainsPage.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				startActivity(new Intent(FindStrainsActivity.this, MyStrainsActivity.class));
			}
		}); //**************************************************************************************


		//******************************************************************************************
		// Seek Bar: Perform seek bar change listener event used for getting the progress value
		//******************************************************************************************
        searchIntensitySeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChangedValue = progress;
				int finalArraySize = getAndCreateFinalArraySize(numberOfRows);
				createSearchIntensityString(progressChangedValue, finalArraySize);
			}

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        }); //**************************************************************************************
	} //********************************************************************************************


	//**********************************************************************************************
	//                     Get and Create Final Array and Return Array Size
	//**********************************************************************************************
	public int getAndCreateFinalArraySize(int numberOfRows) {
		int[] filteredArray = createFilteredArray(numberOfRows);
		int finalArraySize = getFinalArraySize(filteredArray, db, numberOfRows);
		return finalArraySize;
	} //********************************************************************************************


	//**********************************************************************************************
	//                        Create Search Intensity String
	//**********************************************************************************************
	public void createSearchIntensityString(int progressChangedValue, int finalArraySize) {
		searchIntensityValue.setText(String.format("Search Intensity: %d%%\n" +
				"(%d results)", progressChangedValue, finalArraySize));
	} //********************************************************************************************


	//**********************************************************************************************
	//                        Collect and Filter All Strain Data
	//**********************************************************************************************
	public int[] collectAndFilterAllStrainData(int numberOfRows) {
		//Log.d("timerF", "2");
		int[] filteredArray = createFilteredArray(numberOfRows);
		//Log.d("timerF", "3");
		finalArraySize = getFinalArraySize(filteredArray, db, numberOfRows);	// Reduce the array to non-null values only.
		//Log.d("timerF", "4");
		finalArray = new int[finalArraySize];
		finalArray = reduceFilteredArray(filteredArray, db);
		//Log.d("timerF", "5");
		return finalArray;
	} //********************************************************************************************


	//**********************************************************************************************
	//                             Create Filtered Array
	//
	// todo: Low Priority - Speed up this bit of code. (1400 ms).
	//**********************************************************************************************
	public int[] createFilteredArray(int numberOfRows) {
		int[] filteredArray = arrayOfIDs;

		// Loop through the buttons array and filter based on each buttons' selection.
		for (int i = 0; i < itemDataSize; i++) {
			filteredArray = filterArrayByColumn(itemsData.get(i).getFilter(), db, effectsArray[i], filteredArray);
		}

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
		int i;
		double minLimit = (1.0 - (progressChangedValue/100.0));
		double maxLimit = (progressChangedValue/100.0);
		double effectValue = 0;
		int newArray[] = new int[numberOfRows];

		//Log.d("timerFA", "1");
		int[] IDArray = arrayOfIDs;
		//double[] valuesArray = db.getDatabaseValuesFromColumn_doubleArray(getEffectString(effect), numberOfRows); // too slow...?
		double[] valuesArray = getEffectValueArray(effect);

		//Log.d("timerFA", "2");

		if (btnResult == MIN) {
			//Log.d("minSelected", "Min is selected for effect #: " + effect);
			for (i = 0; i < numberOfRows; i++) {
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
			for (i = 0; i < numberOfRows; i++) {
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
			for (i = 0; i < numberOfRows; i++) {
				if (originalArray[i] == BLANK_ENTRY) {
					newArray[i] = BLANK_ENTRY;
				} else {
					newArray[i] = originalArray[i];
				}
				//Log.d("ignoreSelected", "original = " + originalArray[i] + ". newArray = " + newArray[i]);
			}
		}

		//Log.d("timerFA", "3");

		// Return the reduced array
		//Log.d("filterArrayByColumn()", "Filter " + effect + " complete.");
		//Log.d("arraysize", "" + getFinalArraySize(newArray, db));
		return newArray;
	} //********************************************************************************************


	public double[] getEffectValueArray(int effect) {
		double[] valuesArray;
		switch(effect) {
			case HAPPINESS:
				valuesArray = effectArray_happiness;
				break;
			case EUPHORIA:
				valuesArray = effectArray_focus;
				break;
			case FOCUS:
				valuesArray = effectArray_focus;
				break;
			case ENERGY:
				valuesArray = effectArray_energy;
				break;
			case RELAXATION:
				valuesArray = effectArray_relaxation;
				break;
			case SLEEPINESS:
				valuesArray = effectArray_sleepiness;
				break;
			case SICKNESS_RELIEF:
				valuesArray = effectArray_sicknessRelief;
				break;
			case PAIN_RELIEF:
				valuesArray = effectArray_painRelief;
				break;
			case HUNGER:
				valuesArray = effectArray_hunger;
				break;
			case DEHYDRATION:
				valuesArray = effectArray_dehydration;
				break;
			case ANXIETY:
				valuesArray = effectArray_anxiety;
				break;
			default:
				valuesArray = effectArray_happiness;
				break;
		}
		return valuesArray;
	}


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
		int i;
		int count = getFinalArraySize(filteredArray, db, numberOfRows);
		//Log.d("reduceFilteredArrayCt", "Final array size: " + count);
		int subtractor = 0;
		int reducedArray[] = new int[numberOfRows];
		int[] IDArray = arrayOfIDs;

		// Now populate the reducedArray without the blank items from the original array.
		for (i = 0; i < numberOfRows; i++) {
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
			int myFilter = itemsData.get(position).getFilter();
			if (myFilter == MIN) {
				viewHolder.minBtn.setChecked(true);
				//Log.d("RecyclerViewItemClicked", String.format("Set item #: %d to min. Read in %d.", position, myFilter));
			} else if (myFilter == MAX) {
				viewHolder.maxBtn.setChecked(true);
				//Log.d("RecyclerViewItemClicked", String.format("Set item #: %d to max. Read in %d.", position, myFilter));
			} else {
				viewHolder.ignoreBtn.setChecked(true);
				//Log.d("RecyclerViewItemClicked", String.format("Set item #: %d to ignore. Read in %d.", position, myFilter));
			}


			// This working piece of code shows that we can click the radiogroup and perform an action based off the click.
			viewHolder.effectsBtnGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
				// todo: High Priority - save search criteria when navigating about the app, return to page & last selections are preserved. Btn then to "clear selection"
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
						//Log.d("RecyclerViewItemClicked", String.format("min clicked at %d, set to %d with value read-in: %d.", position, MIN, myFilter));
					} else if (checkedId == maxID) {
						itemsData.get(position).setFilter(MAX);
						//Log.d("RecyclerViewItemClicked", String.format("max clicked at %d, set to %d with value read-in: %d.", position, MAX, myFilter));
					} else {
						itemsData.get(position).setFilter(IGNORE);
						//Log.d("RecyclerViewItemClicked", String.format("ignore clicked at %d, set to %d with value read-in: %d.", position, IGNORE, myFilter));
					}

					int finalArraySize = getAndCreateFinalArraySize(numberOfRows);
					createSearchIntensityString(progressChangedValue, finalArraySize);
				}
			});
		} //****************************************************************************************


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

				// todo: Low Priority - Uncomment the block below and determine which item was clicked and act accordingly: See comments directly below...
				// ...either 1) set focus on element clicked using recyclerView.smoothScrollToPosition(position), requires creating a list or changing text in list, or...
				// ...2) st convert code so that the itemsData just all change text to these strings;

				/*// Set text based on the item clicked
				lblInfoBox.setText(infoList[position]);
				// Show the 'x' btn and details.
				lblInfoBox.setVisibility(View.VISIBLE);
				btnCancel.setVisibility(View.VISIBLE);
				// Hide the list to prevent accidental button clicks.
				recyclerView.setVisibility(View.INVISIBLE);*/
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

