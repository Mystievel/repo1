package com.example.tofer.emptyproject_helloworld;

import android.content.Context;
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

public class ResultsActivity extends FindStrainsActivity {
	ResultsItemData itemsData[] = new ResultsItemData[finalArraySize];	// Populate Array size of reduced number of results.

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        // Clear the strains buffer on create
		buffer_addToMyStrains = "";

		// 1. get a reference to recyclerView
		RecyclerView recyclerView = findViewById(R.id.resultsList);
		for (int i = 0; i < finalArraySize; i++) {							// Check resulting array for all items in database.
        	String strainName = db.getStrainData(finalArray[i] + 0).getStrainName();
        	String strainType = db.getStrainData(finalArray[i] + 0).getStrainType();
        	int strainID = db.getStrainData(finalArray[i] + 0).getStrainId();
            itemsData[i] = new ResultsItemData("" + strainName, "" + strainType, 0 + strainID);
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(this));               // 2. set layoutManger
        ResultsRecyclerViewAdapter mAdapter = new ResultsRecyclerViewAdapter(itemsData);    // 3. create an adapter
        recyclerView.setAdapter(mAdapter);                                                  // 4. set adapter
        recyclerView.setItemAnimator(new DefaultItemAnimator());                            // 5. set item animator to DefaultAnimator

        // Buttons
        Button btnMainPage = findViewById(R.id.btnMainPage);
        Button btnReviseSearch = findViewById(R.id.btnReviseSearch);
		//Button btnAddToMyStrains = findViewById(R.id.btnAddToMyStrains);

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

/*		// THIS WILL NOT WORK DO NOT WASTE MORE TIME ON THIS.
		btnAddToMyStrains.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Log.d("clickheres", ".");
			}
		});*/

	}



	// TODO: Could loop through itemsData[position].setFilter(IGNORE); and determine which items ARE present
	// then use notifyDataSetChanged() and determine which items are NOT present??? these are the added/removed items?

	public class ResultsRecyclerViewAdapter extends RecyclerView.Adapter<ResultsRecyclerViewAdapter.ViewHolder> {
		// Local variables
		public ResultsItemData[] itemsData;


		//**********************************************************************************************
		// Create Adapter
		//**********************************************************************************************
		public ResultsRecyclerViewAdapter(ResultsItemData[] itemsData) {
			this.itemsData = itemsData;
		} //********************************************************************************************


		//**********************************************************************************************
		// Create new views (invoked by the layout manager)
		//**********************************************************************************************
		@Override
		public ResultsRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.results_item_layout, null);
			ViewHolder viewHolder = new ViewHolder(itemLayoutView);
			return viewHolder;
		} //********************************************************************************************


		//**********************************************************************************************
		// Replace the contents of a view (invoked by the layout manager)
		//**********************************************************************************************
		@Override
		public void onBindViewHolder(ViewHolder viewHolder, int position) {
			// - get data from your itemsData at this position
			// - replace the contents of the view with that itemsData
			viewHolder.txtViewTitle.setText(itemsData[position].getTitle());
			viewHolder.txtViewDescription.setText(itemsData[position].getDescription());
		} //********************************************************************************************


		//**********************************************************************************************
		// View Holder: Inner class to hold a reference to each item of RecyclerView
		//**********************************************************************************************
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
				// Todo: CONTINUE HERE - https://stackoverflow.com/questions/17525886/listview-with-add-and-delete-buttons-in-each-row-in-android
				Log.d("viewHolderPositionClick", "" + position);
			}
		} //********************************************************************************************


		// Return the size of your itemsData (Required - invoked by the layout manager)
		@Override
		public int getItemCount() {
			return itemsData.length;
		}
	}

}

