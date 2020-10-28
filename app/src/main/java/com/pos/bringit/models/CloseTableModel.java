package com.pos.bringit.models;

import com.google.gson.annotations.SerializedName;

public class CloseTableModel {
    @SerializedName("table_id")
    private String mTableId;

    public String getTableId() {
        return mTableId;
    }

    public void setTableId(String mTableId) {
        this.mTableId = mTableId;
    }
}
