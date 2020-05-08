package com.pos.bringit.models;

import com.google.gson.annotations.SerializedName;

import static com.pos.bringit.utils.Constants.ITEM_TYPE_FOLDER_END;

public class BreadcrumbModel {
    @SerializedName("id")
    private String mId;
    @SerializedName("name")
    private String mName;
    @SerializedName("father_id")
    private String mFatherId;

    private int type = ITEM_TYPE_FOLDER_END;

    public BreadcrumbModel() {
    }

    public BreadcrumbModel(String mId, String mName, int type) {
        this.mId = mId;
        this.mName = mName;
        this.type = type;
    }


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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}