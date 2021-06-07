package com.pos.bringit.models.response;

import com.google.gson.annotations.SerializedName;
import com.pos.bringit.models.OrderModel;

import java.util.ArrayList;
import java.util.List;

public class SearchByFiltersResponse {

    @SerializedName("status")
    private boolean mStatus;
    @SerializedName("results")
    private List<OrderModel> mOrdersList = new ArrayList<>();

    public boolean isStatus() {
        return mStatus;
    }

    public void setStatus(boolean status) {
        mStatus = status;
    }

    public List<OrderModel> getOrdersList() {
        return mOrdersList;
    }

    public void setOrdersList(List<OrderModel> ordersList) {
        this.mOrdersList = ordersList;
    }
}
