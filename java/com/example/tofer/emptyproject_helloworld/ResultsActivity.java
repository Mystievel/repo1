package com.example.tofer.emptyproject_helloworld;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class ResultsActivity extends FindStrainsActivity {
    private List resultsList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ResultsRecyclerViewAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        // 1. get a reference to recyclerView
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.resultsList);
        // this is data for the recycler view
/*        ItemData itemsData[] = {new ItemData("title 1", "description 1"),
                new ItemData("title 2", "" + listItem1),
                new ItemData("title 3", "description 3")};*/

        // This is how to display all of the results found from the database based on the search criteria
        ItemData itemsData[] = new ItemData[(int) tempNumberOfResults]; // Populate Array size of reduced number of results.
        for (int i=0; i<tempNumberOfResults; i++) {                         // Check resulting array for all items in database.
            //if (relaxed_results[i] != "") {
                //itemsData[i] = new ItemData(String.format("title %d", i), String.format("description %d", i));
                itemsData[i] = new ItemData(String.valueOf(db.getStrainData(i+1).getStrainName()), String.valueOf(db.getStrainData(i+1).getEffectsRelaxed()));
            //}
        }
        //itemsData[0] = new ItemData("title 1", "description 1");
        //itemsData[1] = new ItemData("title 2", "description 2");

        recyclerView.setLayoutManager(new LinearLayoutManager(this));               // 2. set layoutManger
        ResultsRecyclerViewAdapter mAdapter = new ResultsRecyclerViewAdapter(itemsData);    // 3. create an adapter
        recyclerView.setAdapter(mAdapter);                                                  // 4. set adapter
        recyclerView.setItemAnimator(new DefaultItemAnimator());                            // 5. set item animator to DefaultAnimator

        // Buttons
        Button btnMainPage = (Button)findViewById(R.id.btnMainPage);
        Button btnReviseSearch = (Button)findViewById(R.id.btnReviseSearch);

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
}

