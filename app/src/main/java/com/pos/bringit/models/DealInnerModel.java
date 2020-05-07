package com.pos.bringit.models;

public class DealInnerModel {
    private String objectType;
    private String name;
    private boolean isComplete;
    private boolean isSelected;

    public DealInnerModel(String objectType, String name) {
        this.objectType = objectType;
        this.name = name;
    }

    public String getObjectType() {
        return objectType;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }


    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
