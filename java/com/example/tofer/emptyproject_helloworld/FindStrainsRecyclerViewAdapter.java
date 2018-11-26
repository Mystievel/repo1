package com.example.tofer.emptyproject_helloworld;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class FindStrainsRecyclerViewAdapter extends RecyclerView.Adapter<FindStrainsRecyclerViewAdapter.ViewHolder> {
	// Local variables
	private FindStrainsItemData[] itemsData;

	//**********************************************************************************************
	// Create Adapter
	//**********************************************************************************************
	public FindStrainsRecyclerViewAdapter(FindStrainsItemData[] itemsData) {
		this.itemsData = itemsData;
	} //********************************************************************************************


	//**********************************************************************************************
	// Create new views (invoked by the layout manager)
	//**********************************************************************************************
	@Override
	public FindStrainsRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.find_strains_item_layout, null);
		ViewHolder viewHolder = new ViewHolder(itemLayoutView);
		return viewHolder;
	} //********************************************************************************************


	//**********************************************************************************************
	// Bind View Holder: Replace the contents of a view (invoked by the layout manager)
	//**********************************************************************************************
	@Override
	public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
		// - get data from your itemsData at this position
		// - replace the contents of the view with that itemsData
		viewHolder.effectLbl.setText(itemsData[position].getEffect());

		// This working piece of code shows that we can click the radiogroup and perform an action based off the click.
		viewHolder.effectsBtnGroup.check(position);
		viewHolder.effectsBtnGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				int IGNORE = 0;
				int MIN = 1;
				int MAX = 2;

				switch (checkedId) {
					case R.id.effect_ignore:
						itemsData[position].setFilter(IGNORE);
						Log.d("RecyclerViewItemClicked", "ignore clicked at " + position);
						break;
					case R.id.effect_min:
						itemsData[position].setFilter(MIN);
						Log.d("RecyclerViewItemClicked", "min clicked at " + position);
						break;
					case R.id.effect_max:
						itemsData[position].setFilter(MAX);
						Log.d("RecyclerViewItemClicked", "max clicked at " + position);
						break;
					default:
						break;
				}
			}
		});
	} //********************************************************************************************


	//**********************************************************************************************
	// View Holder: Inner class to hold a reference to each item of RecyclerView
	//**********************************************************************************************
	public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
		public TextView effectLbl;
		public RadioGroup effectsBtnGroup;

		public ViewHolder(View itemLayoutView) {
			super(itemLayoutView);
			effectLbl = itemLayoutView.findViewById(R.id.item_title);
			effectsBtnGroup = itemLayoutView.findViewById(R.id.effect_group);
			itemLayoutView.setOnClickListener(this);  // Use this in conjunction with "implements View.OnClickListener" in the class header and the onClick method below to determine which item in the recyclerView was clicked
		}


		// Use this method to determine which item in the recyclerView was clicked
		@Override
		public void onClick(View view) {
			int position = getLayoutPosition();
			// get ID of the item clicked on
			// Add to TABLE "MY_STRAINS_TABLE"
			Log.d("SearchRecViewClick", "Item # Clicked: " + String.valueOf(position) + ".");
		}
	} //********************************************************************************************


	// Return the size of your itemsData (invoked by the layout manager)
	@Override
	public int getItemCount() {
		return itemsData.length;
	}
}
