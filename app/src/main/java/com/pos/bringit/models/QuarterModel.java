package com.pos.bringit.models;

public class QuarterModel {
    private int index;
    private int price;

    public QuarterModel(int index, int price) {
        this.index = index;
        this.price = price;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
