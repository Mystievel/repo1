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
	private FindStrainsItemData[] itemsData;

	public FindStrainsRecyclerViewAdapter(FindStrainsItemData[] itemsData) {
		this.itemsData = itemsData;
	}

	// Create new views (invoked by the layout manager)
	@Override
	// todo: view this https://www.codexpedia.com/android/defining-item-click-listener-for-recyclerview-in-android/
	// todo: and this https://stackoverflow.com/questions/28296708/get-clicked-item-and-its-position-in-recyclerview
	public FindStrainsRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.find_strains_item_layout, null);
		ViewHolder viewHolder = new ViewHolder(itemLayoutView);
		return viewHolder;
	}

	//**********************************************************************************************
	// Bind View Holder: Replace the contents of a view (invoked by the layout manager)
	//**********************************************************************************************
	@Override
	public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
		// - get data from your itemsData at this position
		// - replace the contents of the view with that itemsData
		viewHolder.effectLbl.setText(itemsData[position].getEffect());

		// This working piece of code shows that we can click the radiogroup and perform an action based off the click.
		// TODO: After getting this working (individual radio btn click listeners) then test with 10+ items that info isnt lost or distorted while scrolling through the list.
		viewHolder.effectsBtnGroup.check(position);
		viewHolder.effectsBtnGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				switch (checkedId) {
					case R.id.effect_ignore:
						Log.d("RecyclerViewItemClicked", "ignore clicked at " + position);
						break;
					case R.id.effect_min:
						Log.d("RecyclerViewItemClicked", "min clicked at " + position);
						break;
					case R.id.effect_max:
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
			Log.d("RecyclerViewItemClicked", "Item # Clicked: " + String.valueOf(position) + ".");
		}
	} //********************************************************************************************




	// Return the size of your itemsData (invoked by the layout manager)
	@Override
	public int getItemCount() {
		return itemsData.length;
	}
}
