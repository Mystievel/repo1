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

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

// todo: click and hold feature on item titles or "?" button for more info, or popup window for info when item is clicked
// todo: create filter option: sort results list by strain type, name, highest/lowest of some value.

public class ResultsActivity extends FindStrainsActivity {
	// Globals


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

		// 1. get a reference to recyclerView
		ArrayList<ResultsListItemData> itemsDataArrayList = new ArrayList<>();

		RecyclerView recyclerView = findViewById(R.id.resultsList);
		for (int i = 0; i < finalArraySize; i++) {							// Check resulting array for all items in database.
			int strainID = db.getStrainData(finalArray[i] + 0).getStrainId();
        	String strainName = db.getStrainData(finalArray[i] + 0).getStrainName();
        	String strainType = db.getStrainData(finalArray[i] + 0).getStrainType();
            itemsDataArrayList.add(new ResultsListItemData(0 + strainID, "" + strainName, "" + strainType));
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this));               // 2. set layoutManger
        ResultsRecyclerViewAdapter mAdapter = new ResultsRecyclerViewAdapter(itemsDataArrayList);    // 3. create an adapter
        recyclerView.setAdapter(mAdapter);                                                  // 4. set adapter
        recyclerView.setItemAnimator(new DefaultItemAnimator());                            // 5. set item animator to DefaultAnimator

		// Let the user know if there were no results.
		TextView noResults = findViewById(R.id.lblNoResults);
		if (finalArraySize == 0) {
			noResults.setVisibility(View.VISIBLE);
		} else {
			noResults.setVisibility(View.INVISIBLE);
		}

        // Buttons
        Button btnMainPage = findViewById(R.id.btnMainPage);
        Button btnReviseSearch = findViewById(R.id.btnReviseSearch);

		btnMainPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ResultsActivity.this, MainActivity.class));
            }
        });

        btnReviseSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ResultsActivity.this, FindStrainsActivity.class));
            }
        });
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
		//******************************************************************************************
		@Override
		public void onBindViewHolder(ViewHolder viewHolder, int position) {
			// - get data from your itemsData at this position
			// - replace the contents of the view with that itemsData
			viewHolder.txtViewTitle.setText(itemsData.get(position).getTitle());
			viewHolder.txtViewDescription.setText(itemsData.get(position).getDescription());
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
			}

			// Use this method to determine which item in the recyclerView was clicked
			@Override
			public void onClick(View view) {
				int position = getAdapterPosition();
				//Log.d("itemClicked", "" + position);

				// Remove item from the Database - must be done before removing from view.
				db.updateMyStrain(db.getStrainData(itemsData.get(position).getStrainID() + 0), 1);
				//Log.d("viewHolderUpdate", "" + itemsData.get(position).getTitle());

				// Remove item from the view
				itemsData.remove(position);
				//notifyItemRemoved(position);
				//notifyItemRangeChanged(position, getItemCount());
				notifyDataSetChanged();
				//Log.d("viewHolderUpdate", "" + itemsData.get(position).getTitle());
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
