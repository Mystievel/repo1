package com.example.tofer.emptyproject_helloworld;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

// todo: Make a way to favorite My Strains into different groups or custom category/description (like relaxed, movie high, couch lock, best shit ever, etc)
// todo: click and hold feature on item titles or "?" button for more info, or popup window for info when item is clicked

public class MyStrainsActivity extends MainActivity {
	// Globals
	int numberOfMyStrains;


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

		numberOfMyStrains = getNumberOfMyStrains();	// For some reason, if this variable is populated anywhere but within this onCreate method, we receive a "Null Pointer Exception".
		int[] myStrainsIndexArray = collectAndFilterMyStrains();

		// 1. get a reference to recyclerView
		ArrayList<MyStrainsItemData> itemsDataArrayList = new ArrayList<>();

        RecyclerView recyclerView = findViewById(R.id.myStrainsList);
        for (int i = 0; i < numberOfMyStrains; i++) {
        	String strainName = db.getStrainData(myStrainsIndexArray[i] + 0).getStrainName();
        	String strainType = db.getStrainData(myStrainsIndexArray[i] + 0).getStrainType();
        	int strainID = db.getStrainData(myStrainsIndexArray[i] + 0).getStrainId();
			itemsDataArrayList.add(new MyStrainsItemData("" + strainName, "" + strainType, 0 + strainID));
			//Log.d("itemAdded id: " + i, " id: " + itemsDataArrayList.get(i).getStrainID() + ". name: " + itemsDataArrayList.get(i).getStrainName() + ".  type: " + itemsDataArrayList.get(i).getStrainType());
        	//Log.d("itemAdded", " id: " + strainID + ". name: " + strainName + ".  type: " + strainType);
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this));               // 2. set layoutManger
        MyStrainsRecyclerViewAdapter mAdapter = new MyStrainsRecyclerViewAdapter(itemsDataArrayList);    // 3. create an adapter
        recyclerView.setAdapter(mAdapter);                                                  // 4. set adapter
        recyclerView.setItemAnimator(new DefaultItemAnimator());                            // 5. set item animator to DefaultAnimator

		// Let the user know if no strains exist.
		setNoStrainsLabel(numberOfMyStrains);



		// TODO summarize the code block below into a routine **********************************************************************
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
		// TODO summarize the code block above into a routine **********************************************************************


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
	//						Get Number of items in "My Strains" list
	// todo: Expand the algorithm for any column or series of columns by changing getMyStrains() to an input argument.
	// todo: this routine is taking a long time, find a way to make this faster, copy into array on startup?
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
				//Log.d("itemClicked", "" + position);

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
			}
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
				viewHolder.itemView.setBackgroundColor(Color.parseColor("#8924f4"));
			} else if (strainType.trim().equalsIgnoreCase("" + STR_SATIVA)) {
				//Log.d("SetItemColor", "Position: " + position + ". Color: Orange. Strain Name: " + strainName + ". Strain Type: " + strainType + ".");
				viewHolder.itemView.setBackgroundColor(Color.parseColor("#efa328"));
			} else { // strainType == "Hybrid"
				//Log.d("SetItemColor", "Position: " + position + ". Color: Green. Strain Name: " + strainName + ". Strain Type: " + strainType + ".");
				viewHolder.itemView.setBackgroundColor(Color.parseColor("#68f442"));
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
