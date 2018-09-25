package com.example.tofer.emptyproject_helloworld;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ResultsActivity extends FindStrainsActivity {
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


    // Database for the Results
    public class ResultsDatabase {
        public static final String TABLE_NAME = "Results";

        // Columns in the database
        public static final String COLUMN_STRAINID = "strainID";
        public static final String COLUMN_STRAINNAME = "strainName";
        public static final String COLUMN_STRAINTYPE = "strainType";

        // 'Column' variables
        private int strainID;
        private String strainName;
        private String strainType;

        // Create table SQL query
        public static final String CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME + "("
                        + COLUMN_STRAINID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                        + COLUMN_STRAINNAME + " DATETIME DEFAULT CURRENT_TIMESTAMP"
                        + COLUMN_STRAINTYPE + " TEXT,"
                        + ")";

        public ResultsDatabase() {
        }

        public ResultsDatabase(int strainID, String strainName, String strainType) {
            this.strainID = strainID;
            this.strainName = strainName;
            this.strainType = strainType;
        }

        // Getters
        public int getStrainID() {
            return strainID;
        }

        public String getStrainName() {
            return strainName;
        }

        public String getStrainType() {
            return strainType;
        }

        // Setters
        public void setStrainID(int text) {
            this.strainID = text;
        }

        public void setStrainName(String text) {
            this.strainName = text;
        }

        public void setStrainType(String text) {
            this.strainType = text;
        }
    }
}

