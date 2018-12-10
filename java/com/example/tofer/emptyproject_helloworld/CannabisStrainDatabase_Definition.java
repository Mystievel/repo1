package com.example.tofer.emptyproject_helloworld;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class CannabisStrainDatabase_Definition extends MainActivity{
	private int id;
	private String StrainName;
	private String StrainType;
	private int MyStrains;
	private int FavoriteStrains;
	private double Dehydration;
	private double Energy;
	private double Euphoria;
	private double Focus;
	private double Happiness;
	private double Hunger;
	private double Pain_Relief;
	private double Relaxation;
	private double Sickness_Relief;
	private double Sleepiness;

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
			case DEHYDRATION:
				getEffect = getEffectsDehydration();
				break;
			case ENERGY:
				getEffect = getEffectsEnergy();
				break;
			case EUPHORIA:
				getEffect = getEffectsEuphoria();
				break;
			case FOCUS:
				getEffect = getEffectsFocus();
				break;
			case HAPPINESS:
				getEffect = getEffectsHappiness();
				break;
			case HUNGER:
				getEffect = getEffectsHunger();
				break;
			case PAIN_RELIEF:
				getEffect = getEffectsPain_Relief();
				break;
			case RELAXATION:
				getEffect = getEffectsRelaxation();
				break;
			case SICKNESS_RELIEF:
				getEffect = getEffectsSickness_Relief();
				break;
			case SLEEPINESS:
				getEffect = getEffectsSleepiness();
				break;
			default:
				getEffect = -1;
				break;
		}
		return getEffect;
	}

	public void setEffect(int effect, double data) {
		switch(effect) {
			case DEHYDRATION:
				setEffectsDehydration(data);
				break;
			case ENERGY:
				setEffectsEnergy(data);
				break;
			case EUPHORIA:
				setEffectsEuphoria(data);
				break;
			case FOCUS:
				setEffectsFocus(data);
				break;
			case HAPPINESS:
				setEffectsHappiness(data);
				break;
			case HUNGER:
				setEffectsHunger(data);
				break;
			case PAIN_RELIEF:
				setEffectsPain_Relief(data);
				break;
			case RELAXATION:
				setEffectsRelaxation(data);
				break;
			case SICKNESS_RELIEF:
				setEffectsSickness_Relief(data);
				break;
			case SLEEPINESS:
				setEffectsSleepiness(data);
				break;
			default:
				break;
		}
	}


	// Dehydration
	public double getEffectsDehydration() {
		return Dehydration;
	}

	public void setEffectsDehydration(double data) {
		this.Dehydration= data;
	}


	// Energy
	public double getEffectsEnergy() {
		return Energy;
	}

	public void setEffectsEnergy(double data) {
		this.Energy= data;
	}


	// Euphoria
	public double getEffectsEuphoria() {
		return Euphoria;
	}

	public void setEffectsEuphoria(double data) {
		this.Euphoria= data;
	}


	// Focus
	public double getEffectsFocus() {
		return Focus;
	}

	public void setEffectsFocus(double data) {
		this.Focus= data;
	}


	// Happiness
	public double getEffectsHappiness() {
		return Happiness;
	}

	public void setEffectsHappiness(double data) {
		this.Happiness = data;
	}


	// Hunger
	public double getEffectsHunger() {
		return Hunger;
	}

	public void setEffectsHunger(double data) {
		this.Hunger = data;
	}


	// Pain_Relief
	public double getEffectsPain_Relief() {
		return Pain_Relief;
	}

	public void setEffectsPain_Relief(double data) {
		this.Pain_Relief = data;
	}


	// Relaxation
	public double getEffectsRelaxation() {
		return Relaxation;
	}

	public void setEffectsRelaxation(double data) {
		this.Relaxation = data;
	}


	// Sleepiness
	public double getEffectsSleepiness() {
		return Sleepiness;
	}

	public void setEffectsSleepiness(double data) {
		this.Sleepiness= data;
	}


	// Sickness_Relief
	public double getEffectsSickness_Relief() {
		return Sickness_Relief;
	}

	public void setEffectsSickness_Relief(double data) {
		this.Sickness_Relief= data;
	}
}