package com.pos.bringit.models;

import com.google.gson.annotations.SerializedName;

public class BreadcrumbModel {
    @SerializedName("id")
    private String mId;
    @SerializedName("name")
    private String mName;
    @SerializedName("father_id")
    private String mFatherId;

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

    public String getFatherId() {
        return mFatherId;
    }

    public void setFatherId(String fatherId) {
        mFatherId = fatherId;
    }
}