package com.example.tofer.emptyproject_helloworld;


public class MyStrainsItemData extends MainActivity {
	private String strainName;
	private String strainType;
	private int strainID;

	public MyStrainsItemData(String strainName, String strainType, int strainID){
		this.strainName = strainName;
		this.strainType = strainType;
		this.strainID = strainID;
	}

	// getters & setters
	public String getStrainName() {
		return strainName;
	}
	public void setStrainName(String strainName) {
		this.strainName = strainName;
	}

	public String getStrainType() {
		return strainType;
	}
	public void setStrainType(String strainType) {
		this.strainType = strainType;
	}

	public int getStrainID() {
		return strainID;
	}
	public void setStrainID(int strainID) {
		this.strainID = strainID;
	}
}
