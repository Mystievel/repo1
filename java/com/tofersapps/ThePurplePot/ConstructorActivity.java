package com.tofersapps.ThePurplePot;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

// todo: High Priority - must find something to do with this page or modify/remove it from all layouts

public class ConstructorActivity extends MainActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.constructor_activity);


        //******************************************************************************************
        // Menu Bar Object - Button Clicked
        // todo: Medium Priority - summarize the code block below into a routine **********************************************************************
        //******************************************************************************************
        //******************************************************************************************
        // Find Strains Page Clicked
        //******************************************************************************************
        Button btnFindStrainsPage = findViewById(R.id.btnFindStrainsPage);
        btnFindStrainsPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ConstructorActivity.this, FindStrainsActivity.class));
            }
        }); //**************************************************************************************
        //******************************************************************************************
        // FindStore Page Clicked
        //******************************************************************************************
        Button btnFindStore = findViewById(R.id.btnFindStorePage);
        btnFindStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ConstructorActivity.this, ConstructorActivity.class));
            }
        }); //**************************************************************************************
        //******************************************************************************************
        // Support Page Clicked
        //******************************************************************************************
        Button btnSupportPage = findViewById(R.id.btnSupportPage);
        btnSupportPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ConstructorActivity.this, SupportActivity.class));
            }
        }); //**************************************************************************************
        //******************************************************************************************
        // My Strains Page Clicked
        //******************************************************************************************
        Button btnMyStrainsPage = findViewById(R.id.btnMyStrainsPage);
        btnMyStrainsPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ConstructorActivity.this, MyStrainsActivity.class));
            }
        }); //**************************************************************************************
    }
}
