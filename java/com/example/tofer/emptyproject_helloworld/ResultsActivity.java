package com.example.tofer.emptyproject_helloworld;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

// todo: click and hold feature on item titles or "?" button for more info, or popup window for info when item is clicked
// todo: create filter option: sort results list by strain type, name, highest/lowest of some value.

public class ResultsActivity extends FindStrainsActivity {
	// Globals
	String[] arrayOfStrainNames;
	String[] arrayOfStrainTypes;
	RecyclerView recyclerView;

	//----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	//----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	//----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	//							ResultsActivity: onCreate
	//----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	//----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	//----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	@Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.results_activity);

        Log.d("timerR", "1");
		// 1. get a reference to recyclerView
		ArrayList<ResultsListItemData> itemsDataArrayList = new ArrayList<>();
		Log.d("timerR", "2");

		recyclerView = findViewById(R.id.resultsList);
		Log.d("timerR", "3"); // longest time is from 3-4.

		//Log.d("FinalArraySize", "" + finalArraySize);
		//Log.d("FinalArraySize", "" + finalArray.length);
		int[] arrayOfStrainIDs = db.getDatabaseItemValueByID("id", finalArray, finalArraySize);
		arrayOfStrainNames = db.getDatabaseItemValueByID("StrainName", finalArraySize, finalArray);
		arrayOfStrainTypes = db.getDatabaseItemValueByID("StrainType", finalArraySize, finalArray);

		Log.d("timerR", "3.5"); // longest time is from 3-4.

		// Compare resulting array for all items in database.
		for (int i = 0; i < finalArraySize; i++) {
			itemsDataArrayList.add(new ResultsListItemData(0 + arrayOfStrainIDs[i], "" + arrayOfStrainNames[i], "" + arrayOfStrainTypes[i]));
        }
		Log.d("timerR", "4");
		recyclerView.setLayoutManager(new LinearLayoutManager(this));               // 2. set layoutManger
        ResultsRecyclerViewAdapter mAdapter = new ResultsRecyclerViewAdapter(itemsDataArrayList);    // 3. create an adapter
        recyclerView.setAdapter(mAdapter);                                                  // 4. set adapter
        recyclerView.setItemAnimator(new DefaultItemAnimator());                            // 5. set item animator to DefaultAnimator

		Log.d("timerR", "5");

		// Let the user know if there were no results.
		TextView noResults = findViewById(R.id.lblNoResults);
		if (finalArraySize == 0) {
			noResults.setVisibility(View.VISIBLE);
		} else {
			noResults.setVisibility(View.INVISIBLE);
		}

		//Log.d("timerR", "6");


		//******************************************************************************************
		// Info Object - Button Clicked
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


		// TODO summarize the code block below into a routine **********************************************************************
		// Todo - this is done by creating a fragment
		//******************************************************************************************
		// Find Strains Page Clicked
		//******************************************************************************************
		Button btnFindStrainsPage = findViewById(R.id.btnFindStrainsPage);
		btnFindStrainsPage.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				startActivity(new Intent(ResultsActivity.this, FindStrainsActivity.class));
			}
		}); //**************************************************************************************

		//******************************************************************************************
		// Find Strains Page Clicked
		//******************************************************************************************
		Button btnConstructorPage = findViewById(R.id.btnConstructorPage);
		btnConstructorPage.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				startActivity(new Intent(ResultsActivity.this, ConstructorActivity.class));
			}
		}); //**************************************************************************************

		//******************************************************************************************
		// Find Strains Page Clicked
		//******************************************************************************************
		Button btnSupportPage = findViewById(R.id.btnSupportPage);
		btnSupportPage.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				startActivity(new Intent(ResultsActivity.this, SupportActivity.class));
			}
		}); //**************************************************************************************

		//******************************************************************************************
		// Find Strains Page Clicked
		//******************************************************************************************
		Button btnMyStrainsPage = findViewById(R.id.btnMyStrainsPage);
		btnMyStrainsPage.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				startActivity(new Intent(ResultsActivity.this, MyStrainsActivity.class));
			}
		}); //**************************************************************************************
		// TODO summarize the code block above into a routine **********************************************************************
	}


	//----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	//----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	//----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	//							Results: RecyclerView Adapter
	//----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	//----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	//----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	public class ResultsRecyclerViewAdapter extends RecyclerView.Adapter<ResultsRecyclerViewAdapter.ViewHolder> {
		// Local variables
		public List<ResultsListItemData> itemsData;

		//******************************************************************************************
		// Create Adapter
		//******************************************************************************************
		public ResultsRecyclerViewAdapter(List<ResultsListItemData> itemsData) {
			this.itemsData = itemsData;
		} //****************************************************************************************


		//******************************************************************************************
		// Create new views (invoked by the layout manager)
		//******************************************************************************************
		@Override
		public ResultsRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.results_itemlayout, null);
			ViewHolder viewHolder = new ViewHolder(itemLayoutView);
			return viewHolder;
		} //****************************************************************************************


		//******************************************************************************************
		// Replace the contents of a view (invoked by the layout manager)
		// - get data from your itemsData at this position
		// - replace the contents of the view with that itemsData
		//******************************************************************************************
		@Override
		public void onBindViewHolder(ViewHolder viewHolder, int position) {
			String strainName = itemsData.get(position).getTitle();
			String strainType = itemsData.get(position).getDescription();

			viewHolder.txtViewTitle.setText(strainName);
			viewHolder.txtViewDescription.setText(strainType);

			// Change the list item background color based on the strainType (Hybrid, Sativa, Indica)
			setItemBackgroundByStrainType(strainType, viewHolder);
		} //****************************************************************************************


		//******************************************************************************************
		// View Holder: Inner class to hold a reference to each item of RecyclerView
		//******************************************************************************************
		public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
			public TextView txtViewTitle;
			public TextView txtViewDescription;
			public Button btnAddToMyStrains;

			public ViewHolder(View itemLayoutView) {
				super(itemLayoutView);
				txtViewTitle = itemLayoutView.findViewById(R.id.item_title);
				txtViewDescription = itemLayoutView.findViewById(R.id.item_description);
				btnAddToMyStrains = itemLayoutView.findViewById(R.id.btnAddToMyStrains);
				btnAddToMyStrains.setOnClickListener(this);  // Use this in conjunction with "implements View.OnClickListener" in the class header and the onClick method below to determine which item in the recyclerView was clicked
				itemLayoutView.setOnClickListener(this);
			}

			// Use this method to determine which item in the recyclerView was clicked
			@Override
			public void onClick(View view) {
				int position = getAdapterPosition();
				//Log.d("itemClicked", "" + position);

				// Determine which item was clicked and act accordingly.
				if (view == btnAddToMyStrains) {
					// Remove item from the Database - must be done before removing from view.
					db.updateMyStrain(db.getStrainData(itemsData.get(position).getStrainID() + 0), 1);
					//Log.d("viewHolderUpdate", "" + itemsData.get(position).getTitle());

					// Remove item from the view
					itemsData.remove(position);
					//notifyItemRemoved(position);
					//notifyItemRangeChanged(position, getItemCount());
					notifyDataSetChanged();

					// todo: setNoResultsLabel(numberOfMyStrains);
					//Log.d("viewHolderUpdate", "" + itemsData.get(position).getTitle());
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
		// Todo: try to combine this routine with that in ResultsActivity
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
		// Return the size of your itemsData (Required - invoked by the layout manager)
		//******************************************************************************************
		@Override
		public int getItemCount() {
			return itemsData.size();
		} //****************************************************************************************
	} //********************************************************************************************
} //************************************************************************************************
