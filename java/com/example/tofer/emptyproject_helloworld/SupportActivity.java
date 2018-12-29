package com.example.tofer.emptyproject_helloworld;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SupportActivity extends MainActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.support_activity);


        // TODO summarize the code block below into a routine **********************************************************************
        //******************************************************************************************
        // Find Strains Page Clicked
        //******************************************************************************************
        Button btnFindStrainsPage = findViewById(R.id.btnFindStrainsPage);
        btnFindStrainsPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SupportActivity.this, FindStrainsActivity.class));
            }
        }); //**************************************************************************************

        //******************************************************************************************
        // Find Strains Page Clicked
        //******************************************************************************************
        Button btnConstructorPage = findViewById(R.id.btnConstructorPage);
        btnConstructorPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SupportActivity.this, ConstructorActivity.class));
            }
        }); //**************************************************************************************

        //******************************************************************************************
        // Find Strains Page Clicked
        //******************************************************************************************
        Button btnSupportPage = findViewById(R.id.btnSupportPage);
		btnSupportPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SupportActivity.this, SupportActivity.class));
            }
        }); //**************************************************************************************

        //******************************************************************************************
        // Find Strains Page Clicked
        //******************************************************************************************
        Button btnMyStrainsPage = findViewById(R.id.btnMyStrainsPage);
        btnMyStrainsPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SupportActivity.this, MyStrainsActivity.class));
            }
        }); //**************************************************************************************
        // TODO summarize the code block above into a routine **********************************************************************

    }
}
