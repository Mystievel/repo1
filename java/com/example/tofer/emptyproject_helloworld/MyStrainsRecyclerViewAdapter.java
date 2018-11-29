package com.example.tofer.emptyproject_helloworld;


import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MyStrainsRecyclerViewAdapter extends RecyclerView.Adapter<MyStrainsRecyclerViewAdapter.ViewHolder> {
	// Local variables
	private MyStrainsItemData[] itemsData;

	//**********************************************************************************************
	// Create Adapter
	//**********************************************************************************************
	public MyStrainsRecyclerViewAdapter(MyStrainsItemData[] itemsData) {
		this.itemsData = itemsData;
	} //********************************************************************************************


	//**********************************************************************************************
	// Create new views (invoked by the layout manager)
	//**********************************************************************************************
	@Override
	public MyStrainsRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_strains_item_layout, null);
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
		viewHolder.strainNameLbl.setText(itemsData[position].getStrainName());
		viewHolder.strainTypeLbl.setText(itemsData[position].getStrainType());
	} //********************************************************************************************


	//**********************************************************************************************
	// View Holder: Inner class to hold a reference to each item of RecyclerView
	//**********************************************************************************************
	public class ViewHolder extends RecyclerView.ViewHolder {
		public TextView strainNameLbl;
		public TextView strainTypeLbl;

		public ViewHolder(View itemLayoutView) {
			super(itemLayoutView);
			strainNameLbl = itemLayoutView.findViewById(R.id.strainNameLbl);
			strainTypeLbl = itemLayoutView.findViewById(R.id.strainTypeLbl);
		}
	} //********************************************************************************************


	// Return the size of your itemsData (invoked by the layout manager)
	@Override
	public int getItemCount() {
		return itemsData.length;
	}
}
