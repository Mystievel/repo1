package com.example.tofer.emptyproject_helloworld;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class CannabisStrainDatabase_Definition {
    private int id;
    private String strainName;
    private double effectsRelaxed;

    public CannabisStrainDatabase_Definition(){}

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