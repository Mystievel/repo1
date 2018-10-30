package com.example.tofer.emptyproject_helloworld;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class CannabisStrainDatabase_Definition {
    private int id;
    private String strainName;
    private String effectsRelaxed;

    public CannabisStrainDatabase_Definition(){}

    public CannabisStrainDatabase_Definition(String strainName, String effectsRelaxed) {
        super();
        this.strainName = strainName;
        this.effectsRelaxed = effectsRelaxed;
    }

    //getters & setters
    @Override
    public String toString() {
        return "CannabisStrainDatabase_Definition [id=" + id + ", strainName=" + strainName + ", effectsRelaxed=" + effectsRelaxed + "]";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStrainName() {
        return strainName;
    }

    public void setStrainName(String strainName) {
        this.strainName = strainName;
    }

    public String getEffectsRelaxed() {
        return effectsRelaxed;
    }

    public void setEffectsRelaxed(String effectsRelaxed) {
        this.effectsRelaxed = effectsRelaxed;
    }
}