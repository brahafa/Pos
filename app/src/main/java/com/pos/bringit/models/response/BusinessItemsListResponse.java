package com.pos.bringit.models.response;

import com.google.gson.annotations.SerializedName;
import com.pos.bringit.models.BusinessItemModel;

import java.util.List;

public class BusinessItemsListResponse {

    @SerializedName("status")
    private boolean mStatus;
    @SerializedName("message")
    private List<BusinessItemModel> mToppingList;

    public boolean isStatus() {
        return mStatus;
    }

    public void setStatus(boolean status) {
        mStatus = status;
    }

    public List<BusinessItemModel> getMessage() {
        return mToppingList;
    }

    public void setMessage(List<BusinessItemModel> message) {
        mToppingList = message;
    }

}
