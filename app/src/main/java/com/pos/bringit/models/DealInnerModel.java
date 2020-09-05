package com.pos.bringit.models;

public class DealInnerModel {
    private DealItemModel dealItem;
    private boolean isComplete;
    private boolean isSelected;

    public DealInnerModel(DealItemModel dealItem) {
        this.dealItem = dealItem;
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

    public DealItemModel getDealItem() {
        return dealItem;
    }

    public void setDealItem(DealItemModel dealItem) {
        this.dealItem = dealItem;
    }
}
