package com.example.tofer.emptyproject_helloworld;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtViewTitle;
        public TextView txtViewDescription;

        public ViewHolder(View itemLayoutView) {
            super(itemLayoutView);
            txtViewTitle = (TextView) itemLayoutView.findViewById(R.id.item_title);
            txtViewDescription = (TextView) itemLayoutView.findViewById(R.id.item_description);
        }
    } //********************************************************************************************


    // Return the size of your itemsData (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return itemsData.length;
    }
}
