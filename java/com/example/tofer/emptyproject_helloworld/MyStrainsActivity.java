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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static com.example.tofer.emptyproject_helloworld.FindStrainsActivity.effectsArray;

// todo: High Priority - Add popup window "are you sure you want to remove <strain name> from your list?"
// todo: High Priority - create filter option: highest/lowest of some value, strain type, name, effect (assign priority to tasks), etc.
// todo: Medium Priority - Make a way to favorite My Strains into different groups or custom category/description (like relaxed, movie high, couch lock, best shit ever, etc)
// todo: Medium Priority - Make a thumbs down feature that shows across the board which strains you've thumbs down and a folder or view that shows disliked strains so you can add or remove them from the list.

public class MyStrainsActivity extends MainActivity {
	// Globals
	int numberOfMyStrains;
	TextView lblInfoBox;
	Button btnCancel;
	RecyclerView recyclerView;

	// Filtering
	int sortByValue;
	int filterByValue;
	Button btnFilter;
	TextView backgroundFilters;
	TextView lblSortBy;
	Spinner spinnerSortBy;
	TextView lblFilterBy;
	Spinner spinnerFilterBy;
	Button btnApplyFilters;
	Button btnCancelFilters;

	//----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	//----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	//----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	//							MyStrainsActivity: onCreate
	//----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	//----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	//----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	@Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mystrains_activity);

		//Log.d("timer1", "1");
		numberOfMyStrains = db.getNumberOfMyStrains();	// This variable must be populated here under onCreate, otherwise "Null Pointer Exception".
		// todo: Medium Priority - routine takes 600 ms from 2-3.
		//Log.d("timer1", "2");
		int[] myStrainsIndexArray = db.collectAndFilterMyStrains(numberOfMyStrains);
		//Log.d("timer1", "3");

		// 1. get a reference to recyclerView
		ArrayList<MyStrainsItemData> itemsDataArrayList = new ArrayList<>();

		recyclerView = findViewById(R.id.myStrainsList);
        for (int i = 0; i < numberOfMyStrains; i++) {
			CannabisStrainDatabase_Definition Strain = db.getStrainData(myStrainsIndexArray[i]);
        	String strainName = Strain.getStrainName();
			String strainType = Strain.getStrainType();
			int strainID = Strain.getStrainId();
			itemsDataArrayList.add(new MyStrainsItemData("" + strainName, "" + strainType, 0 + strainID));
			//Log.d("itemAdded id: " + i, " id: " + itemsDataArrayList.get(i).getStrainID() + ". name: " + itemsDataArrayList.get(i).getStrainName() + ".  type: " + itemsDataArrayList.get(i).getStrainType());
        	//Log.d("itemAdded", " id: " + strainID + ". name: " + strainName + ".  type: " + strainType);
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this));               // 2. set layoutManger
        MyStrainsRecyclerViewAdapter mAdapter = new MyStrainsRecyclerViewAdapter(itemsDataArrayList);    // 3. create an adapter
        recyclerView.setAdapter(mAdapter);                                                  // 4. set adapter
        recyclerView.setItemAnimator(new DefaultItemAnimator());                            // 5. set item animator to DefaultAnimator
		//Log.d("timer1", "4");

		// Let the user know if no strains exist.
		setNoStrainsLabel(numberOfMyStrains);
		//Log.d("timer1", "5");


		//******************************************************************************************
		// Filter Object
		// todo: Medium Priority - summarize the code block below using a fragment
		//******************************************************************************************
		btnFilter = findViewById(R.id.btnFilter);
		backgroundFilters = findViewById(R.id.lblFilterBoxBkgd);
		lblSortBy = findViewById(R.id.lblSortBy);
		spinnerSortBy = findViewById(R.id.spinner_sortby);
		lblFilterBy = findViewById(R.id.lblFilterBy);
		spinnerFilterBy = findViewById(R.id.spinner_filterby);
		btnApplyFilters = findViewById(R.id.okBtn_filters);
		btnCancelFilters = findViewById(R.id.cancelBtn_filters);
		btnFilter = findViewById(R.id.btnFilter);

		// Populate "Sort Spinner"
		String[] sortTitles = new String[]{"low to high (a to z)", "high to low (z to a)"};
		ArrayAdapter<String> adapterSortSpinner = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, sortTitles);
		spinnerSortBy.setAdapter(adapterSortSpinner);


		// Populate "Filter Spinner"
		String[] effectsNames = new String[effectsArray.length];
		for (int i = 0; i < effectsArray.length; i++) {
			effectsNames[i] = getEffectString(effectsArray[i]);
		}
		ArrayAdapter<String> adapterFilterSpinner = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, effectsNames);
		spinnerFilterBy.setAdapter(adapterFilterSpinner);


		//******************************************************************************************
		// Info Object - Filter
		// todo: Medium Priority - summarize the code block below using a fragment
		//******************************************************************************************
		//******************************************************************************************
		btnCancelFilters.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				// Set filter box items Invisible.
				recyclerView.setVisibility(View.VISIBLE); // Set recycler view visible first
				backgroundFilters.setVisibility(View.INVISIBLE);
				lblSortBy.setVisibility(View.INVISIBLE);
				spinnerSortBy.setVisibility(View.INVISIBLE);
				lblFilterBy.setVisibility(View.INVISIBLE);
				spinnerFilterBy.setVisibility(View.INVISIBLE);
				btnApplyFilters.setVisibility(View.INVISIBLE);
				btnCancelFilters.setVisibility(View.INVISIBLE);
			}
		}); //**************************************************************************************
		//******************************************************************************************
		btnFilter.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				// Set filter box items Visible.
				backgroundFilters.setVisibility(View.VISIBLE);
				lblSortBy.setVisibility(View.VISIBLE);
				spinnerSortBy.setVisibility(View.VISIBLE);
				lblFilterBy.setVisibility(View.VISIBLE);
				spinnerFilterBy.setVisibility(View.VISIBLE);
				btnApplyFilters.setVisibility(View.VISIBLE);
				btnCancelFilters.setVisibility(View.VISIBLE);
				recyclerView.setVisibility(View.INVISIBLE); // Set recycler view invisible last
			}
		}); //**************************************************************************************


		//******************************************************************************************
		// Info Object - Button Clicked
		// todo: Medium Priority - summarize the code block below using a fragment
		//******************************************************************************************
		btnCancel = findViewById(R.id.cancelBtn);
		lblInfoBox = findViewById(R.id.lblInfoBox);
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


		// todo: Medium Priority - summarize the code block below using a fragment
		//******************************************************************************************
		// Find Strains Page Clicked
		//******************************************************************************************
		Button btnFindStrainsPage = findViewById(R.id.btnFindStrainsPage);
		btnFindStrainsPage.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				startActivity(new Intent(MyStrainsActivity.this, FindStrainsActivity.class));
			}
		}); //**************************************************************************************

		//******************************************************************************************
		// Find Strains Page Clicked
		//******************************************************************************************
		Button btnConstructorPage = findViewById(R.id.btnConstructorPage);
		btnConstructorPage.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				startActivity(new Intent(MyStrainsActivity.this, ConstructorActivity.class));
			}
		}); //**************************************************************************************

		//******************************************************************************************
		// Find Strains Page Clicked
		//******************************************************************************************
		Button btnSupportPage = findViewById(R.id.btnSupportPage);
		btnSupportPage.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				startActivity(new Intent(MyStrainsActivity.this, SupportActivity.class));
			}
		}); //**************************************************************************************

		//******************************************************************************************
		// Find Strains Page Clicked
		//******************************************************************************************
		Button btnMyStrainsPage = findViewById(R.id.btnMyStrainsPage);
		btnMyStrainsPage.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				startActivity(new Intent(MyStrainsActivity.this, MyStrainsActivity.class));
			}
		}); //**************************************************************************************
	} //********************************************************************************************


	public void setNoStrainsLabel(int numberOfMyStrains) {
		TextView noStrains = findViewById(R.id.lblNoStrains);
		if (numberOfMyStrains == 0) {
			noStrains.setVisibility(View.VISIBLE);
		} else {
			noStrains.setVisibility(View.INVISIBLE);
		}
	}


	//**********************************************************************************************
	//								Sort Results List based on Filter
	// sortByValue:		0 = low to high (a to z)
	//					1 = high to low (z to a)
	//
	// filterByValue	0 = effectsArray(0) "Happiness" etc
	//**********************************************************************************************
	public ArrayList<MyStrainsItemData> sortResultsItems(int sortByValue, int filterByValue, List<MyStrainsItemData> itemsDataArrayList) {
		int listSize = itemsDataArrayList.size();
		int[] listOfIDs = getItemsDataIDs(itemsDataArrayList);
		int[] tempListOfIDs = new int[listSize];
		int[] arrayOfStrainIDs;
		String[] arrayOfStrainNames;
		String[] arrayOfStrainTypes;
		ArrayList<MyStrainsItemData> tempItemsDataArrayList = new ArrayList<>();
		int tempID;
		int tempValue;
		Log.d("listSize", String.format("Effect: %s", getEffectString(filterByValue)));
		double[] listValues = db.getDatabaseItemValueByID_double(getEffectString(filterByValue), listOfIDs, listSize);

		// Sort the list.
		if (sortByValue == 0) {
			tempListOfIDs = sortLowToHigh(listOfIDs, listValues);
		} else {
			tempListOfIDs = sortHighToLow(listOfIDs, listValues);
		}

		arrayOfStrainIDs = db.getDatabaseItemValueByID_int("id", tempListOfIDs, listSize);
		arrayOfStrainNames = db.getDatabaseItemValueByID_string("StrainName", tempListOfIDs, listSize);
		arrayOfStrainTypes = db.getDatabaseItemValueByID_string("StrainType", tempListOfIDs, listSize);

		for (int i = 0; i < listSize; i++) {
			tempItemsDataArrayList.add(new MyStrainsItemData("" + arrayOfStrainNames[i], "" + arrayOfStrainTypes[i], 0 + arrayOfStrainIDs[i]));
		}

		return tempItemsDataArrayList;
	}


	public int[] getItemsDataIDs(List<MyStrainsItemData> itemsDataArrayList) {
		int listSize = itemsDataArrayList.size();
		int[] listOfIDs = new int[listSize];
		for (int i = 0; i < listSize; i++) {
			listOfIDs[i] = itemsDataArrayList.get(i).getStrainID();
		}
		return listOfIDs;
	}


	//----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	//----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	//----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	//							MyStrains: RecyclerView Adapter
	//----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	//----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	//----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	public class MyStrainsRecyclerViewAdapter extends RecyclerView.Adapter<MyStrainsRecyclerViewAdapter.ViewHolder> {
		// Local variables
		private List<MyStrainsItemData> itemsData;


		//******************************************************************************************
		// Create Adapter
		//******************************************************************************************
		public MyStrainsRecyclerViewAdapter(List<MyStrainsItemData> itemsData) {
			this.itemsData = itemsData;
		} //****************************************************************************************


		//******************************************************************************************
		// Create new views (invoked by the layout manager)
		//******************************************************************************************
		@Override
		public MyStrainsRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.mystrains_itemlayout, null);
			ViewHolder viewHolder = new ViewHolder(itemLayoutView);
			return viewHolder;
		} //****************************************************************************************


		//******************************************************************************************
		// Bind View Holder: Replace the contents of a view (invoked by the layout manager)
		//******************************************************************************************
		@Override
		public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
			String strainName = itemsData.get(position).getStrainName();
			String strainType = itemsData.get(position).getStrainType();

			viewHolder.strainNameLbl.setText(strainName);
			viewHolder.strainTypeLbl.setText(strainType);

			// Change the list item background color based on the strainType (Hybrid, Sativa, Indica)
			setItemBackgroundByStrainType(strainType, viewHolder);
		} //****************************************************************************************


		//******************************************************************************************
		// View Holder: Inner class to hold a reference to each item of RecyclerView
		//******************************************************************************************
		public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
			public TextView strainNameLbl;
			public TextView strainTypeLbl;
			public Button btnRemoveMyStrain;
			public Button btnOkFilters;

			public ViewHolder(View itemLayoutView) {
				super(itemLayoutView);
				strainNameLbl = itemLayoutView.findViewById(R.id.strainNameLbl);
				strainTypeLbl = itemLayoutView.findViewById(R.id.strainTypeLbl);
				btnRemoveMyStrain = itemLayoutView.findViewById(R.id.btnRemoveMyStrain);
				btnOkFilters = findViewById(R.id.okBtn_filters);
				btnRemoveMyStrain.setOnClickListener(this);  // Use this in conjunction with "implements View.OnClickListener" in the class header and the onClick method below to determine which item in the recyclerView was clicked
				btnOkFilters.setOnClickListener(this);
				itemLayoutView.setOnClickListener(this);
			}

			// Use this method to determine which item in the recyclerView was clicked
			@Override
			public void onClick(View view) {
				int position = getAdapterPosition();
				//Log.d("itemClicked", "" + position);

				// Determine which item was clicked and act accordingly.
				if (view == btnRemoveMyStrain) {
					// Remove item from the Database - must be done before removing from view.
					//Log.d("itemRemoved id: " + position, "" + itemsData.get(position).getStrainID());
					db.updateMyStrain(db.getStrainData(itemsData.get(position).getStrainID() + 0), 0);

					// Remove item from the view
					itemsData.remove(position);
					//notifyItemRemoved(position);
					//notifyItemRangeChanged(position, getItemCount());
					notifyDataSetChanged();
					numberOfMyStrains--;
					setNoStrainsLabel(numberOfMyStrains);
				} else if (view == btnOkFilters) {
					// Store filter selection and re-sort the list
					// todo: 	need to make a decision...get database read into array on startup...or need to learn how to now convert this into the view order based on
					sortByValue = spinnerSortBy.getSelectedItemPosition();
					filterByValue = spinnerFilterBy.getSelectedItemPosition();
					itemsData = sortResultsItems(sortByValue, filterByValue, itemsData);
					notifyDataSetChanged();

					// Set filter box items Invisible.
					recyclerView.setVisibility(View.VISIBLE);	// Set recycler view visible first
					backgroundFilters.setVisibility(View.INVISIBLE);
					lblSortBy.setVisibility(View.INVISIBLE);
					spinnerSortBy.setVisibility(View.INVISIBLE);
					lblFilterBy.setVisibility(View.INVISIBLE);
					spinnerFilterBy.setVisibility(View.INVISIBLE);
					btnApplyFilters.setVisibility(View.INVISIBLE);
					btnCancelFilters.setVisibility(View.INVISIBLE);
				// default action (item clicked)
				} else {
					// Set text based on the item clicked
					lblInfoBox.setText(getStrainsInfoPacket(position, db));
					// Show the 'x' btn and details.
					lblInfoBox.setVisibility(View.VISIBLE);
					btnCancel.setVisibility(View.VISIBLE);
					// Hide the list to prevent accidental button clicks.
					recyclerView.setVisibility(View.INVISIBLE);
				}
			}
		} //****************************************************************************************


		//******************************************************************************************
		//							Get And Display Strains Info Packet
		//
		// todo: Medium Priority - try to combine this routine with that in ResultsActivity
		// This routine displays relevant information for the list item clicked.
		//******************************************************************************************
		public String getStrainsInfoPacket(int position, CannabisStrainDatabase_Helper db) {
			CannabisStrainDatabase_Definition Strain = db.getStrainData(itemsData.get(position).getStrainID());;

			String dataPacket;
			String strainName = Strain.getStrainName();
			String strainType = Strain.getStrainType();
			Double happiness = 100.0 * Strain.getEffectsHappiness();
			Double euphoria = 100.0 * Strain.getEffectsEuphoria();
			Double focus = 100.0 * Strain.getEffectsFocus();
			Double energy = 100.0 * Strain.getEffectsEnergy();
			Double relaxation = 100.0 * Strain.getEffectsRelaxation();
			Double sleepiness = 100.0 * Strain.getEffectsSleepiness();
			Double sick = 100.0 * Strain.getEffectsSickness_Relief();
			Double pain = 100.0 * Strain.getEffectsPain_Relief();
			Double hunger = 100.0 * Strain.getEffectsHunger();
			Double dehydration = 100.0 * Strain.getEffectsDehydration();
			Double anxiety = 100.0 * Strain.getEffectsAnxiety();

			dataPacket = String.format(	"Name: %s\n" +
										"Type: %s\n\n" +
										"Happiness: %.2f%%\n" +
										"Euphoria: %.2f%%\n" +
										"Focus: %.2f%%\n" +
										"Energy: %.2f%%\n" +
										"Relaxation: %.2f%%\n" +
										"Sleepiness: %.2f%%\n" +
										"Sickness Relief: %.2f%%\n" +
										"Pain Relief: %.2f%%\n" +
										"Hunger: %.2f%%\n" +
										"Dehydration: %.2f%%\n" +
										"Anxiety: %.2f%%", strainName, strainType, happiness, euphoria, focus, energy, relaxation, sleepiness, sick, pain, hunger, dehydration, anxiety);

			return dataPacket;
		} //****************************************************************************************


		//******************************************************************************************
		//							Set Item Background by Strain Type
		//
		// Changes the list item background color based on the strainType (Hybrid, Sativa, Indica)
		// To compare two strings, use string.equals. use trim() to remove white space and ignore case to ignore capitalizations.
		// Note: The above adds some overhead, remove if time becomes sensitive here to just strainType.equals("String");
		//******************************************************************************************
		public void setItemBackgroundByStrainType(String strainType, ViewHolder viewHolder) {
			if (strainType.trim().equalsIgnoreCase("" + STR_INDICA)) {
				//Log.d("SetItemColor", "Position: " + position + ". Color: Purple. Strain Name: " + strainName + ". Strain Type: " + strainType + ".");
				viewHolder.itemView.setBackgroundResource(R.drawable.gradient_radial_purple1);
			} else if (strainType.trim().equalsIgnoreCase("" + STR_SATIVA)) {
				//Log.d("SetItemColor", "Position: " + position + ". Color: Orange. Strain Name: " + strainName + ". Strain Type: " + strainType + ".");
				viewHolder.itemView.setBackgroundResource(R.drawable.gradient_radial_orange1);
			} else { // strainType == "Hybrid"
				//Log.d("SetItemColor", "Position: " + position + ". Color: Green. Strain Name: " + strainName + ". Strain Type: " + strainType + ".");
				viewHolder.itemView.setBackgroundResource(R.drawable.gradient_radial_green1);
			}
		} //****************************************************************************************


		//******************************************************************************************
		// Return the size of your itemsData (invoked by the layout manager)
		//******************************************************************************************
		@Override
		public int getItemCount() {
			return itemsData.size();
		} //****************************************************************************************
	} //********************************************************************************************
} //************************************************************************************************
