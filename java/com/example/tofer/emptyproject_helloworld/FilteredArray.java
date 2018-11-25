package com.example.tofer.emptyproject_helloworld;

public class FilteredArray extends FindStrainsActivity{
	private int id;
	private String strainName;

	public FilteredArray(int id, String strainName){
		this.id = id;
		this.strainName = strainName;
	}

	// getters & setters
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

}
