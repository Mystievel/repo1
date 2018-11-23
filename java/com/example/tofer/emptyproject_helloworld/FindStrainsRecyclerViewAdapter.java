package com.example.tofer.emptyproject_helloworld;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
		// create a new view
		View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.find_strains_item_layout, null);

		// create ViewHolder
		ViewHolder viewHolder = new ViewHolder(itemLayoutView);
		return viewHolder;
	}

	// Replace the contents of a view (invoked by the layout manager)
	@Override
	public void onBindViewHolder(ViewHolder viewHolder, int position) {
		// - get data from your itemsData at this position
		// - replace the contents of the view with that itemsData
		viewHolder.txtViewTitle.setText(itemsData[position].getEffect());
	}

	// inner class to hold a reference to each item of RecyclerView
	public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
		public TextView txtViewTitle;

		public ViewHolder(View itemLayoutView) {
			super(itemLayoutView);
			txtViewTitle = itemLayoutView.findViewById(R.id.item_title);
			itemLayoutView.setOnClickListener(this);
		}

		@Override
		public void onClick(View view) {
			int position = getLayoutPosition();
			Log.d("sdfgdfgppdf", "Here 2: " + String.valueOf(position) + ".");
		}
	}

	// Return the size of your itemsData (invoked by the layout manager)
	@Override
	public int getItemCount() {
		return itemsData.length;
	}
}
