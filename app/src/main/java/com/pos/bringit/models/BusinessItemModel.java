package com.pos.bringit.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BusinessItemModel {
    @SerializedName("topping_id")
    private String mToppingId;
    @SerializedName("drink_id")
    private String mDrinkId;
    @SerializedName("description")
    private String mDescription;
    @SerializedName("picture")
    private String mPicture;
    @SerializedName("default_price")
    private String mDefaultPrice;
    @SerializedName("uniq_for_business_id")
    private String mUniqForBusinessId;
    @SerializedName("id")
    private String mId;
    @SerializedName("delivery_price")
    private String mDeliveryPrice;
    @SerializedName("pickup_price")
    private String mPickupPrice;
    @SerializedName("name")
    private String mName;
    @SerializedName("object_status")
    private boolean mObjectStatus;
    @SerializedName("object_id")
    private String mObjectId;
    @SerializedName("object_type")
    private String mObjectType;
    @SerializedName("filling")
    private List<FillingModel> mFilling;
    @SerializedName("category")
    private String mCategory;
    @SerializedName("inInventory")
    private boolean mInInventory;
    @SerializedName("in_inventory")
    private boolean mIn_Inventory;

    private boolean mIsSelected;

    public String getToppingId() {
        return mToppingId;
    }

    public void setToppingId(String toppingId) {
        mToppingId = toppingId;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getPicture() {
        return mPicture;
    }

    public void setPicture(String picture) {
        mPicture = picture;
    }

    public double getDefaultPrice() {
        return Double.parseDouble(mDefaultPrice);
    }

    public void setDefaultPrice(double defaultPrice) {
        mDefaultPrice = String.valueOf(defaultPrice);
    }

    public String getUniqForBusinessId() {
        return mUniqForBusinessId;
    }

    public void setUniqForBusinessId(String uniqForBusinessId) {
        mUniqForBusinessId = uniqForBusinessId;
    }

    public int getId() {
        return Integer.parseInt(mId);
    }

    public String getStringId() {
        return mId;
    }

    public void setId(int id) {
        mId = String.valueOf(id);
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

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public boolean isObjectStatus() {
        return mObjectStatus;
    }

    public void setObjectStatus(boolean objectStatus) {
        mObjectStatus = objectStatus;
    }

    public int getObjectId() {
        return Integer.parseInt(mObjectId);
    }


    public String getStringObjectId() {
        return mObjectId;
    }

    public void setObjectId(int objectId) {
        mObjectId = String.valueOf(objectId);
    }

    public String getObjectType() {
        return mObjectType.substring(0, 1).toUpperCase() + mObjectType.substring(1).toLowerCase();
    }

    public void setObjectType(String objectType) {
        mObjectType = objectType;
    }

    public String getCategory() {
        return mCategory;
    }

    public void setCategory(String category) {
        mCategory = category;
    }

    public boolean isInInventory() {
        return mInInventory;
    }

    public void setInInventory(boolean inInventory) {
        mInInventory = inInventory;
    }

    public boolean isIn_Inventory() {
        return mIn_Inventory;
    }

    public void setIn_Inventory(boolean inInventory) {
        mIn_Inventory = inInventory;
    }

    public boolean isSelected() {
        return mIsSelected;
    }

    public void setSelected(boolean mIsSelected) {
        this.mIsSelected = mIsSelected;
    }

    public String getmDrinkId() {
        return mDrinkId;
    }

    public void setmDrinkId(String mDrinkId) {
        this.mDrinkId = mDrinkId;
    }

    public List<FillingModel> getmFilling() {
        return mFilling;
    }

    public void setmFilling(List<FillingModel> mFilling) {
        this.mFilling = mFilling;
    }

}
