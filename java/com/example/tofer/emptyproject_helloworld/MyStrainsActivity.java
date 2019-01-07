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
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

// todo: 12/29/18 It's time to do a time study on this one and make it faster!!!

// todo: Make a way to favorite My Strains into different groups or custom category/description (like relaxed, movie high, couch lock, best shit ever, etc)
// todo: click and hold feature on item titles or "?" button for more info, or popup window for info when item is clicked

public class MyStrainsActivity extends MainActivity {
	// Globals
	int numberOfMyStrains;
	TextView lblInfoBox;
	Button btnCancel;
	RecyclerView recyclerView;

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

        // todo: Routine takes 4 seconds: 1-2 = 200ms, 2-3 = 1200ms, 3-4 = 2400ms, 4-5 = 0ms.
        Log.d("timer1", "1");
		numberOfMyStrains = db.getNumberOfMyStrains();	// For some reason, if this variable is populated anywhere but within this onCreate method, we receive a "Null Pointer Exception".
		Log.d("timer1", "2");
		int[] myStrainsIndexArray = db.collectAndFilterMyStrains(numberOfMyStrains);
		Log.d("timer1", "3");

		// 1. get a reference to recyclerView
		ArrayList<MyStrainsItemData> itemsDataArrayList = new ArrayList<>();

		recyclerView = findViewById(R.id.myStrainsList);
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
		Log.d("timer1", "4");

		// Let the user know if no strains exist.
		setNoStrainsLabel(numberOfMyStrains);
		Log.d("timer1", "5");


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
				//btnRemoveMyStrain.setOnClickListener(this);  // Use this in conjunction with "implements View.OnClickListener" in the class header and the onClick method below to determine which item in the recyclerView was clicked
				itemLayoutView.setOnClickListener(this);
			}

			// Use this method to determine which item in the recyclerView was clicked
			@Override
			public void onClick(View view) {
				int position = getAdapterPosition();
				//Log.d("itemClicked", "" + position);
				// Determine which item was clicked and act accordingly.
		/*		if (view == btnRemoveMyStrain) {
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

				// default action (item clicked)
				} else {*/
					// Set text based on the item clicked
					lblInfoBox.setText(getMyStrainsInfoPacket(position, db));
					// Show the 'x' btn and details.
					lblInfoBox.setVisibility(View.VISIBLE);
					btnCancel.setVisibility(View.VISIBLE);
					// Hide the list to prevent accidental button clicks.
					recyclerView.setVisibility(View.INVISIBLE);
				//}
			}
		} //****************************************************************************************


		public String getMyStrainsInfoPacket(int position, CannabisStrainDatabase_Helper db) {
			// todo: Continue populating how the text info packet is formatted based on the MyStrains data. Pull & display values from database for each effect.
			// try and use this same routine in the resultsactivity...
			//db.getStrainData(itemsData.get(position).getStrainID());
			return "test";
		}


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
