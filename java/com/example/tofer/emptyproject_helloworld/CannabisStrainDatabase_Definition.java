package com.example.tofer.emptyproject_helloworld;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class CannabisStrainDatabase_Definition extends FindStrainsActivity{
	private int id;
	private String StrainName;
	private String StrainType;
	private int MyStrains;
	private int FavoriteStrains;
	private double Relaxed;
	private double Happy;
	private double Hungry;
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
		return StrainName;
	}
	public void setStrainName(String strainName) {
		this.StrainName = strainName;
	}


	// Strain Type
	public String getStrainType() {
		return StrainType;
	}
	public void setStrainType(String strainType) {
		this.StrainType = strainType;
	}


	// My Strains
	public int getMyStrains() {
		return MyStrains;
	}
	public void setMyStrains(int myStrain) {
		this.MyStrains = myStrain;
	}


	// Favorite Strains
	public int getFavoriteStrains() {
		return FavoriteStrains;
	}
	public void setFavoriteStrains(int favoriteStrain) {
		this.FavoriteStrains = favoriteStrain;
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
		return Relaxed;
	}

	public void setEffectsRelaxed(double data) {
		this.Relaxed = data;
	}

	public double getEffectsHappy() {
		return Happy;
	}

	public void setEffectsHappy(double data) {
		this.Happy = data;
	}

	public double getEffectsHungry() {
		return Hungry;
	}

	public void setEffectsHungry(double data) {
		this.Hungry= data;
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