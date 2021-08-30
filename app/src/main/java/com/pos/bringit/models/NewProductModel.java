package com.pos.bringit.models;

import com.google.gson.annotations.SerializedName;

public class NewProductModel {

    @SerializedName("f_name")
    private String mName;
    @SerializedName("price")
    private String mPrice;
    @SerializedName("notes")
    private String mNotes;

    public NewProductModel() {
        this.mName = "";
        this.mPrice = "";
        this.mNotes = "";
    }

    public String getName() {
        return mName;
    }

    public void setName(String fName) {
        mName = fName;
    }

    public String getPrice() {
        return mPrice;
    }

    public void setPrice(String price) {
        mPrice = price;
    }

    public String getNotes() {
        return mNotes;
    }

    public void setNotes(String notes) {
        mNotes = notes;
    }

}
