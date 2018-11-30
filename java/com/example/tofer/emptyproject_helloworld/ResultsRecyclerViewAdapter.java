package com.example.tofer.emptyproject_helloworld;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import static com.example.tofer.emptyproject_helloworld.FindStrainsActivity.buffer_addToMyStrains;
import static com.example.tofer.emptyproject_helloworld.ResultsActivity.addToMyStrainsBuffer;


public class ResultsRecyclerViewAdapter extends RecyclerView.Adapter<ResultsRecyclerViewAdapter.ViewHolder> {
    // Local variables
    private ResultsItemData[] itemsData;

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
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
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
            int position = getLayoutPosition();
            // Todo: CONTINUE HERE - finish making the association between clicking the btn and adding to mystrain database, see setFilter
			//db.getStrainData(position).addToMyStrains(position, "" + finalArray[position].getStrainName());
			addToMyStrainsBuffer(position);
			//object.setMyStrain(position);
            Log.d("ResultsRecViewClick", "Buffer: " + buffer_addToMyStrains);
        }
    } //********************************************************************************************


    // Return the size of your itemsData (Required - invoked by the layout manager)
    @Override
    public int getItemCount() {
        return itemsData.length;
    }
}
