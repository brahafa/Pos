package com.pos.bringit.models.response;

import com.google.gson.annotations.SerializedName;
import com.pos.bringit.models.OrderDetailsModel;

public class OrderDetailsResponse {

    @SerializedName("order")
    private OrderDetailsModel mOrder;

    public OrderDetailsModel getOrder() {
        return mOrder;
    }

    public void setOrders(OrderDetailsModel mOrder) {
        this.mOrder = mOrder;
    }
}
