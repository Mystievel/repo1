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
    static int relaxedBtnSelected;
    static int happyBtnSelected;
    static int hungryBtnSelected;
    static int dryEyesBtnSelected;
    static int dryMouthBtnSelected;
    static int dizzyBtnSelected;
    static int[] effectsBtnSelected;
    final static int RELAXED = 1;
    final static int HAPPY = 2;
    final static int HUNGRY = 3;
    final static int DRY_EYES = 4;
    final static int DRY_MOUTH = 5;
    final static int DIZZY = 6;
    final static int[] effectsArray = new int[]{RELAXED, HAPPY, HUNGRY};

    final String[] effectList = new String[]{"Relaxed", "Happy", "Hungry"};

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
		FindStrainsItemData itemsData[] = new FindStrainsItemData[effectList.length];
		for (int i = 0; i < effectList.length; i++) {
            itemsData[i] = new FindStrainsItemData("" + effectList[i]);
		}
		recyclerView.setLayoutManager(new LinearLayoutManager(this));               // 2. set layoutManger
		FindStrainsRecyclerViewAdapter mAdapter = new FindStrainsRecyclerViewAdapter(itemsData);    // 3. create an adapter
		recyclerView.setAdapter(mAdapter);                                                  // 4. set adapter
		recyclerView.setItemAnimator(new DefaultItemAnimator());                            // 5. set item animator to DefaultAnimator



/*		// That below does NOT work
		RadioButton ignoreBtn = findViewById(R.id.effect_ignore);
		RadioButton minBtn = findViewById(R.id.effect_min);
		RadioButton maxBtn = findViewById(R.id.effect_max);

		ignoreBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Log.d("RecyclerViewItemClicked", "Ignore Clicked");
			}
		});

		minBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Log.d("RecyclerViewItemClicked", "Min Clicked");
			}
		});

		maxBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Log.d("RecyclerViewItemClicked", "Max Clicked");
			}
		});*/





        // Todo: Define all of this junk in a loop for all of the buttons and groups IDs...possible?
/*        // Relaxed
        final RadioButton relaxedIgnoreRadioBtn = findViewById(R.id.relaxed_ignore);
        final RadioButton relaxedMaxRadioBtn = findViewById(R.id.relaxed_max);
        final RadioButton relaxedMinRadioBtn = findViewById(R.id.relaxed_min);
        // Happy
        final RadioButton happyIgnoreRadioBtn = findViewById(R.id.happy_ignore);
        final RadioButton happyMaxRadioBtn = findViewById(R.id.happy_max);
        final RadioButton happyMinRadioBtn = findViewById(R.id.happy_min);
        // Hungry
        final RadioButton hungryIgnoreRadioBtn = findViewById(R.id.hungry_ignore);
        final RadioButton hungryMaxRadioBtn = findViewById(R.id.hungry_max);
        final RadioButton hungryMinRadioBtn = findViewById(R.id.hungry_min);
        // Dry Eyes
        final RadioButton dryEyesIgnoreRadioBtn = findViewById(R.id.dryEyes_ignore);
        final RadioButton dryEyesMaxRadioBtn = findViewById(R.id.dryEyes_max);
        final RadioButton dryEyesMinRadioBtn = findViewById(R.id.dryEyes_min);
        // Dry Mouth
        final RadioButton dryMouthIgnoreRadioBtn = findViewById(R.id.dryMouth_ignore);
        final RadioButton dryMouthMaxRadioBtn = findViewById(R.id.dryMouth_max);
        final RadioButton dryMouthMinRadioBtn = findViewById(R.id.dryMouth_min);
        // Dizzy
        final RadioButton dizzyIgnoreRadioBtn = findViewById(R.id.dizzy_ignore);
        final RadioButton dizzyMaxRadioBtn = findViewById(R.id.dizzy_max);
        final RadioButton dizzyMinRadioBtn = findViewById(R.id.dizzy_min);


        // Keep radio buttons selected same as the previous selection.
        // Todo: Define all of this junk in a loop for all of the buttons and groups IDs...possible?
        setDefaultRadioButtonSelection(relaxedBtnSelected, relaxedMinRadioBtn, relaxedMaxRadioBtn, relaxedIgnoreRadioBtn);
        setDefaultRadioButtonSelection(happyBtnSelected, happyMinRadioBtn, happyMaxRadioBtn, happyIgnoreRadioBtn);
        setDefaultRadioButtonSelection(hungryBtnSelected, hungryMinRadioBtn, hungryMaxRadioBtn, hungryIgnoreRadioBtn);
        setDefaultRadioButtonSelection(dryEyesBtnSelected, dryEyesMinRadioBtn, dryEyesMaxRadioBtn, dryEyesIgnoreRadioBtn);
        setDefaultRadioButtonSelection(dryMouthBtnSelected, dryMouthMinRadioBtn, dryMouthMaxRadioBtn, dryMouthIgnoreRadioBtn);
        setDefaultRadioButtonSelection(dizzyBtnSelected, dizzyMinRadioBtn, dizzyMaxRadioBtn, dizzyIgnoreRadioBtn);*/


        //******************************************************************************************
        // Event: Click Button - "Start Search"
        //******************************************************************************************
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
/*                // Start out with a list of all strains.
                String[] filteredArray = getAllStrainNames();

                // Create an array for all radio buttons.
                effectsBtnSelected = new int[]{relaxedBtnSelected, happyBtnSelected, hungryBtnSelected, dryEyesBtnSelected, dryMouthBtnSelected, dizzyBtnSelected};

                // Loop through the buttons array and filter based on each buttons' selection.
                for (int i = 0; i < effectsBtnSelected.length; i++) {
                    filteredArray = filterArrayByColumn(effectsBtnSelected[i], db, effectsArray[i], filteredArray);
                }

                // Reduce the array to non-null values only.
                // Todo fix odd bug, if you scroll down in the final list you'll see a lot of null values. not sure exactly how many.
                finalArraySize = getFinalArraySize(filteredArray, db);
                //Log.d("asdkljsdglkj", finalArraySize + "");
                finalArray = new String[finalArraySize];
                finalArray = reduceFilteredArray(filteredArray, db);
                */
                startActivity(new Intent(FindStrainsActivity.this, ResultsActivity.class));
            }
        }); //**************************************************************************************


        //******************************************************************************************
        // Event: Click Button - "Main Page"
        //******************************************************************************************
        mainPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FindStrainsActivity.this, MainActivity.class));
            }
        }); //**************************************************************************************


        // perform seek bar change listener event used for getting the progress value
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
        });


/*		// 'Relaxed' Buttons
		relaxedIgnoreRadioBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				relaxedBtnSelected = IGNORE;
			}
		});

		relaxedMinRadioBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				relaxedBtnSelected = MIN;
			}
		});

		relaxedMaxRadioBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				relaxedBtnSelected = MAX;
			}
		});

		// 'Happy' Buttons
		happyIgnoreRadioBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				happyBtnSelected = IGNORE;
			}
		});

		happyMinRadioBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				happyBtnSelected = MIN;
			}
		});

		happyMaxRadioBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				happyBtnSelected = MAX;
			}
		});


		// 'Hungry' Buttons
		hungryIgnoreRadioBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				hungryBtnSelected = IGNORE;
			}
		});

		hungryMinRadioBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				hungryBtnSelected = MIN;
			}
		});

		hungryMaxRadioBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				hungryBtnSelected = MAX;
			}
		});


		// 'Dry Eyes' Buttons
		dryEyesIgnoreRadioBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				dryEyesBtnSelected = IGNORE;
			}
		});

		dryEyesMinRadioBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				dryEyesBtnSelected = MIN;
			}
		});

		dryEyesMaxRadioBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				dryEyesBtnSelected = MAX;
			}
		});


		// 'Dry Mouth' Buttons
		dryMouthIgnoreRadioBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				dryMouthBtnSelected = IGNORE;
			}
		});

		dryMouthMinRadioBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				dryMouthBtnSelected = MIN;
			}
		});

		dryMouthMaxRadioBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				dryMouthBtnSelected = MAX;
			}
		});


		// 'Dizzy' Buttons
		dizzyIgnoreRadioBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				dizzyBtnSelected = IGNORE;
			}
		});

		dizzyMinRadioBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				dizzyBtnSelected = MIN;
			}
		});

		dizzyMaxRadioBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				dizzyBtnSelected = MAX;
			}
		});*/
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
	//                              Set Default Radio Button Selection
	//**********************************************************************************************
	public void setDefaultRadioButtonSelection(int btnSelection, RadioButton minBtn, RadioButton maxBtn, RadioButton ignoreBtn) {
		if (btnSelection == MIN) {
			minBtn.setChecked(true);
		} else if (btnSelection == MAX) {
			maxBtn.setChecked(true);
		} else {
			ignoreBtn.setChecked(true);
		}
	} //********************************************************************************************


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