package com.pos.bringit.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FolderItemModel extends ProductItemModel {

    @SerializedName("position")
    private String mPosition;
    @SerializedName("father_id")
    private String mFatherId;
    @SerializedName("type")
    private String mType;
    @SerializedName("itemsCount")
    private int mItemsCount;

    //      old
    @SerializedName("color")
    private String mColor;
    @SerializedName("pickup_price")
    private int mPickupPrice;
    @SerializedName("topping_price_on_slice")
    private int mToppingPriceOnSlice;
    @SerializedName("object_id")
    private String mObjectId;
    @SerializedName("filling")
    private List<FillingModel> mFilling;
    @SerializedName("isShortcut")
    private boolean mIsShortcut;
    @SerializedName("one_piece")
    private boolean mOnePiece;
    @SerializedName("uniq_for_business_id")
    private String mUniqForBusinessId;

    public String getColor() {
        return mColor;
    }

    public void setColor(String color) {
        mColor = color;
    }

    public String getPosition() {
        return mPosition;
    }

    public void setPosition(String position) {
        mPosition = position;
    }

    public String getFatherId() {
        return mFatherId;
    }

    public void setFatherId(String fatherId) {
        mFatherId = fatherId;
    }

    public int getPickupPrice() {
        return mPickupPrice;
    }

    public void setPickupPrice(int pickupPrice) {
        mPickupPrice = pickupPrice;
    }

    public int getToppingPriceOnSlice() {
        return mToppingPriceOnSlice;
    }

    public void setToppingPriceOnSlice(int toppingPriceOnSlice) {
        mToppingPriceOnSlice = toppingPriceOnSlice;
    }

    public String getObjectId() {
        return mObjectId;
    }

    public void setObjectId(String objectId) {
        mObjectId = objectId;
    }

    public List<FillingModel> getFilling() {
        return mFilling;
    }

    public void setFilling(List<FillingModel> filling) {
        mFilling = filling;
    }

    public boolean isIsShortcut() {
        return mIsShortcut;
    }

    public void setIsShortcut(boolean isShortcut) {
        mIsShortcut = isShortcut;
    }

    public String getUniqForBusinessId() {
        return mUniqForBusinessId;
    }

    public void setUniqForBusinessId(String uniqForBusinessId) {
        mUniqForBusinessId = uniqForBusinessId;
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
    }


    public boolean isOnePiece() {
        return mOnePiece;
    }

    public void setOnePiece(boolean mOnePiece) {
        this.mOnePiece = mOnePiece;
    }

    public int getItemsCount() {
        return mItemsCount;
    }

    public void setItemsCount(int mItemsCount) {
        this.mItemsCount = mItemsCount;
    }

}
