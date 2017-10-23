package com.example.easts.listview;

/**
 * Created by easts on 2017/10/19.
 */

public class Fruit {

    private String name;
    private int resourceId;

    public Fruit(String name, int resourceId) {
        this.name = name;
        this.resourceId = resourceId;
    }

    public String getName() {
        return name;
    }

    public int getResourceId() {
        return resourceId;
    }

}
