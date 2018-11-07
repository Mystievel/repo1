package com.example.tofer.emptyproject_helloworld;

public class ItemData {
    private String title;
    private String description;

    public ItemData(String title,String description){
        this.title = title;
        this.description = description;
    }

    // getters & setters
    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
}
