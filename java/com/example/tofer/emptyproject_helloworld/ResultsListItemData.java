package com.example.tofer.emptyproject_helloworld;

public class ResultsListItemData {
    private int id;
    private String title;
    private String description;

    public ResultsListItemData(int id, String title, String description){
        this.id = id;
        this.title = title;
        this.description = description;
    }

    // getters & setters
    public int getStrainID() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
