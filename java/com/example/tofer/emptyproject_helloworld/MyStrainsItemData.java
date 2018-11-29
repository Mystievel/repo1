package com.example.tofer.emptyproject_helloworld;


public class MyStrainsItemData extends MainActivity {
	private String strainName;
	private String strainType;

	public MyStrainsItemData(String strainName, String strainType){
		this.strainName = strainName;
		this.strainType = strainType;
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
}
