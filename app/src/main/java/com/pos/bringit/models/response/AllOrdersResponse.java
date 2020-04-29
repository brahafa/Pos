package com.pos.bringit.models.response;

import com.google.gson.annotations.SerializedName;
import com.pos.bringit.models.OrderModel;

import java.util.ArrayList;
import java.util.List;

public class AllOrdersResponse {

    @SerializedName("orders")
    private List<OrderModel> mOrders = new ArrayList<>();

    public List<OrderModel> getOrders() {
        return mOrders;
    }

    public void setOrders(List<OrderModel> orders) {
        mOrders = orders;
    }
}
