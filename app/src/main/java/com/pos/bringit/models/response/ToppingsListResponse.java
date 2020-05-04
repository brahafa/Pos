package com.pos.bringit.models.response;

import com.google.gson.annotations.SerializedName;
import com.pos.bringit.models.ToppingModel;

import java.util.List;

public class ToppingsListResponse {

    @SerializedName("status")
    private boolean mStatus;
    @SerializedName("message")
    private List<ToppingModel> mToppingList;

    public boolean isStatus() {
        return mStatus;
    }

    public void setStatus(boolean status) {
        mStatus = status;
    }

    public List<ToppingModel> getMessage() {
        return mToppingList;
    }

    public void setMessage(List<ToppingModel> message) {
        mToppingList = message;
    }

}
