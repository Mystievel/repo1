package com.example.tofer.emptyproject_helloworld;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class CannabisStrainDatabase_Definition {
    private int id;
    private String strainName;
    private double effectsRelaxed;

    public CannabisStrainDatabase_Definition(){}

    // TODO:  see https://stackoverflow.com/questions/15415623/android-database-to-array
    // search for "createDataBase"
    // Issue is we aren't actually creating a database, also we don't know, does "put" place text in LogCat
    // or is our LogCat routine actually working? make a dummy printout to test just before subject lines of code.
    public CannabisStrainDatabase_Definition(String strainName, double effectsRelaxed) {
        super();
        //this.id = id;
		this.strainName = strainName;
        this.effectsRelaxed = effectsRelaxed;
    }

    //getters & setters
    @Override
    public String toString() {
        return "CannabisStrainDatabase_Definition [dbRowID=" + id + ", Strain Name=" + strainName + ", Effects - Relaxed=" + effectsRelaxed + "]";
    }

    public int getStrainId() {
        return id;
    }

    public void setStrainId(int id) {
        this.id = id;
    }

    public String getStrainName() {
        return strainName;
    }

    public void setStrainName(String strainName) {
        this.strainName = strainName;
    }

    public double getEffectsRelaxed() {
        return effectsRelaxed;
    }

    public void setEffectsRelaxed(double effectsRelaxed) {
        this.effectsRelaxed = effectsRelaxed;
    }
}