package com.example.tofer.emptyproject_helloworld;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

// todo: High Priority - add new email address for feedback support@appname.com or @gmail.com eventually create a form and youtube link for instructional "about/how to use" video

public class SupportActivity extends MainActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.support_activity);


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
                startActivity(new Intent(SupportActivity.this, FindStrainsActivity.class));
            }
        }); //**************************************************************************************
        //******************************************************************************************
        // Support Page Clicked
        //******************************************************************************************
        Button btnSupportPage = findViewById(R.id.btnSupportPage);
        btnSupportPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SupportActivity.this, SupportActivity.class));
            }
        }); //**************************************************************************************
        //******************************************************************************************
        // My Strains Page Clicked
        //******************************************************************************************
        Button btnMyStrainsPage = findViewById(R.id.btnMyStrainsPage);
        btnMyStrainsPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SupportActivity.this, MyStrainsActivity.class));
            }
        }); //**************************************************************************************
    }
}
