package com.pos.bringit.models;

import com.google.gson.annotations.SerializedName;

public class FillingModel {
    @SerializedName("name")
    private String mName;
    @SerializedName("delivery_price")
    private String mDeliveryPrice;
    @SerializedName("pickup_price")
    private String mPickupPrice;
    @SerializedName("in_inventory")
    private String mInInventory;

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getDeliveryPrice() {
        return mDeliveryPrice;
    }

    public void setDeliveryPrice(String deliveryPrice) {
        mDeliveryPrice = deliveryPrice;
    }

    public String getPickupPrice() {
        return mPickupPrice;
    }

    public void setPickupPrice(String pickupPrice) {
        mPickupPrice = pickupPrice;
    }

    public String getInInventory() {
        return mInInventory;
    }

    public void setInInventory(String inInventory) {
        mInInventory = inInventory;
    }
}