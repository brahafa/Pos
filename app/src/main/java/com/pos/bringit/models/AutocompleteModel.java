package com.pos.bringit.models;

import com.google.gson.annotations.SerializedName;

public class AutocompleteModel {
    @SerializedName("id")
    private String mId;
    @SerializedName("city_id")
    private String mCityId;
    @SerializedName("name")
    private String mName;


    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getCityId() {
        return mCityId;
    }

    public void setCityId(String mCityId) {
        this.mCityId = mCityId;
    }
}
