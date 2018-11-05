package com.example.tofer.emptyproject_helloworld;

import android.content.Intent;
import android.os.Bundle;
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

