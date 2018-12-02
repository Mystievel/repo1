package com.example.tofer.emptyproject_helloworld;

public class ResultsItemData {
    private String title;
    private String description;
    private int ID;

    public ResultsItemData(String title, String description, int ID){
        this.title = title;
        this.description = description;
        this.ID = ID;
    }

    // getters & setters
    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
    public int getID() {
        return ID;
    }
}
