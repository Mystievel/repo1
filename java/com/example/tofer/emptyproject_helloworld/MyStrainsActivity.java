package com.example.tofer.emptyproject_helloworld;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

// todo Make a way to favorite My Strains into different groups or custom category/description (like relaxed, movie high, couch lock, best shit ever, etc)
// todo: if no strains exist, make a note "You don't have any saved strains, click on "Find Strains" to begin or see the Cannabis Constructor (advanced)."


public class MyStrainsActivity extends MainActivity {
	MyStrainsItemData myItemsData[];
	//List<MyStrainsItemData> myItemsData;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mystrains_activity);

		int numberOfMyStrains = getNumberOfMyStrains();	// For some reason, if this variable is NOT declared within onCreate we receive a "Null Pointer Exception".
		int[] myStrainsIndexArray = collectAndFilterMyStrains();
		myItemsData = new MyStrainsItemData[numberOfMyStrains]; // Cannot be moved from here.

		// 1. get a reference to recyclerView
        RecyclerView recyclerView = findViewById(R.id.myStrainsList);
        for (int i = 0; i < numberOfMyStrains; i++) {
        	String strainName = db.getStrainData(myStrainsIndexArray[i] + 0).getStrainName();
        	String strainType = db.getStrainData(myStrainsIndexArray[i] + 0).getStrainType();
        	int strainID = db.getStrainData(myStrainsIndexArray[i] + 0).getStrainId();
			myItemsData[i] = new MyStrainsItemData("" + strainName, "" + strainType, 0 + strainID);
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
	}


	//**********************************************************************************************
	//						Get Number of items in "My Strains" list
	// todo: Expand the algorithm for any column or series of columns by changing getMyStrains() to an input argument.
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


	//--------------------------------------------------------------------------------------------------------------------------
	//--------------------------------------------------------------------------------------------------------------------------
	//--------------------------------------------------------------------------------------------------------------------------
	//							MyStrains: RecyclerView Adapter
	//--------------------------------------------------------------------------------------------------------------------------
	//--------------------------------------------------------------------------------------------------------------------------
	//--------------------------------------------------------------------------------------------------------------------------
	public class MyStrainsRecyclerViewAdapter extends RecyclerView.Adapter<MyStrainsRecyclerViewAdapter.ViewHolder> {
		// Local variables
		//private MyStrainsItemData[] itemsData;
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
			// - get data from your itemsData at this position
			// - replace the contents of the view with that itemsData
			viewHolder.strainNameLbl.setText(itemsData.get(position).getStrainName());
			viewHolder.strainTypeLbl.setText(itemsData.get(position).getStrainType());
		} //****************************************************************************************


		//******************************************************************************************
		// View Holder: Inner class to hold a reference to each item of RecyclerView
		//******************************************************************************************
		public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
			public TextView strainNameLbl;
			public TextView strainTypeLbl;
			public Button btnRemoveMyStrain;

			public ViewHolder(View itemLayoutView) {
				super(itemLayoutView);
				strainNameLbl = itemLayoutView.findViewById(R.id.strainNameLbl);
				strainTypeLbl = itemLayoutView.findViewById(R.id.strainTypeLbl);
				btnRemoveMyStrain = itemLayoutView.findViewById(R.id.btnRemoveMyStrain);
				btnRemoveMyStrain.setOnClickListener(this);  // Use this in conjunction with "implements View.OnClickListener" in the class header and the onClick method below to determine which item in the recyclerView was clicked
			}

			// Use this method to determine which item in the recyclerView was clicked
			@Override
			public void onClick(View view) {
				int position = getAdapterPosition();

				// Remove item from Database
				db.updateMyStrain(db.getStrainData(myItemsData[position].getStrainID()), 0);

				// Remove item from view
				// todo, need to move to onbindlistener and append use with btn click?
				itemsData.remove(position);
				notifyItemRemoved(position);
				notifyItemRangeChanged(position, getItemCount());
				//notifyDataSetChanged();
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
