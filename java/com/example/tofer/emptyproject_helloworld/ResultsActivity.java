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
        RecyclerView recyclerView = findViewById(R.id.resultsList);

        // Display filtered results in the RecyclerView.
        ResultsItemData itemsData[] = new ResultsItemData[finalArraySize];	// Populate Array size of reduced number of results.
        for (int i = 0; i < finalArraySize; i++) {							// Check resulting array for all items in database.
            itemsData[i] = new ResultsItemData("" + finalArray[i], "Sativa/Indica/Hybrid");
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(this));               // 2. set layoutManger
        ResultsRecyclerViewAdapter mAdapter = new ResultsRecyclerViewAdapter(itemsData);    // 3. create an adapter
        recyclerView.setAdapter(mAdapter);                                                  // 4. set adapter
        recyclerView.setItemAnimator(new DefaultItemAnimator());                            // 5. set item animator to DefaultAnimator

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
}

