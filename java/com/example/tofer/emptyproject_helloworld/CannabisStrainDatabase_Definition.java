package com.example.tofer.emptyproject_helloworld;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class CannabisStrainDatabase_Definition extends FindStrainsActivity{
	private int id;
	private String strainName;
	private double effectsRelaxed;
	private double effectsHappy;
	private double effectsHungry;

	public CannabisStrainDatabase_Definition(){}

	// Getters and Setters
	@Override
	public String toString() {
		return "CannabisStrainDatabase_Definition [dbRowID=" + id + ", Strain Name=" + strainName + ", Effects - Relaxed=" + effectsRelaxed + "]";
	}

	// Strain ID
	public int getStrainId() {
		return id;
	}
	public void setStrainId(int id) {
		this.id = id;
	}

	// Strain Name
	public String getStrainName() {
		return strainName;
	}
	public void setStrainName(String strainName) {
		this.strainName = strainName;
	}


	// Strain Effects
	public double getEffect(int effect) {
		double getEffect = -1;
		switch(effect) {
			case RELAXED:
				getEffect = getEffectsRelaxed();
				break;
			case HAPPY:
				getEffect = getEffectsHappy();
				break;
			case HUNGRY:
				getEffect = getEffectsHungry();
				break;
			default:
				getEffect = -1;
				break;
		}
		return getEffect;
	}

	public void setEffect(int effect, double data) {
		switch(effect) {
			case RELAXED:
				setEffectsRelaxed(data);
				break;
			case HAPPY:
				setEffectsHappy(data);
				break;
			case HUNGRY:
				setEffectsHungry(data);
				break;
			default:
				break;
		}
	}

	public double getEffectsRelaxed() {
		return effectsRelaxed;
	}

	public void setEffectsRelaxed(double data) {
		this.effectsRelaxed = data;
	}

	public void setEffectsHappy(double data) {
		this.effectsHappy = data;
	}

	public double getEffectsHappy() {
		return effectsHappy;
	}

	public void setEffectsHungry(double data) {
		this.effectsHungry= data;
	}

	public double getEffectsHungry() {
		return effectsHungry;
	}
}