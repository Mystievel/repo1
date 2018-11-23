package com.example.tofer.emptyproject_helloworld;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class CannabisStrainDatabase_Definition extends FindStrainsActivity{
	private int id;
	private String strainName;
	private double effectsRelaxed;
	private double effectsHappy;
	private double effectsHungry;
	private double effectsDryEyes;
	private double effectsDryMouth;
	private double effectsDizzy;

	public CannabisStrainDatabase_Definition(){}

	// Getters and Setters
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
			case DRY_EYES:
				getEffect = getEffectsDryEyes();
				break;
			case DRY_MOUTH:
				getEffect = getEffectsDryMouth();
				break;
			case DIZZY:
				getEffect = getEffectsDizzy();
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

	public double getEffectsHappy() {
		return effectsHappy;
	}

	public void setEffectsHappy(double data) {
		this.effectsHappy = data;
	}

	public double getEffectsHungry() {
		return effectsHungry;
	}

	public void setEffectsHungry(double data) {
		this.effectsHungry= data;
	}

	public double getEffectsDryEyes() {
		return effectsDryEyes;
	}

	public void setEffectsDryEyes(double data) {
		this.effectsDryEyes = data;
	}

	public double getEffectsDryMouth() {
		return effectsDryMouth;
	}

	public void setEffectsDryMouth(double data) {
		this.effectsDryMouth = data;
	}

	public double getEffectsDizzy() {
		return effectsDizzy;
	}

	public void setEffectsDizzy(double data) {
		this.effectsDizzy = data;
	}
}