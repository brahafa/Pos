package com.pos.bringit.models.response;

import com.google.gson.annotations.SerializedName;
import com.pos.bringit.models.FinanceItem;

public class FinanceSessionResponse {
    private String order_id;
    private String message;
    private boolean status;
    @SerializedName("data")
    private FinanceItem data;

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public FinanceItem getData() {
        return data;
    }

    public void setData(FinanceItem data) {
        this.data = data;
    }

}
