package com.example.tofer.emptyproject_helloworld;

// Todo: Is this taking too much memory and therefore processing time? If so, can we have a line in the local database that is just for filteredArray calculations? or will this take time too...
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
