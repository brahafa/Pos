package com.pos.bringit.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderItemsModel {
    @SerializedName("item_id")
    private String mItemId;
    @SerializedName("item_type")
    private String mItemType;
    @SerializedName("father_id")
    private String mFatherId;
    @SerializedName("folder_id")
    private String mFolderId;
    @SerializedName("location")
    private String mLocation;
    @SerializedName("cart_id")
    private String mCartId;
    @SerializedName("price")
    private int mPrice;
    @SerializedName("is_compensation")
    private String mIsCompensation;
    @SerializedName("item_filling")
    private List<CartFillingModel> mItemFilling;
    @SerializedName("change_type")
    private String mChangeType;
    @SerializedName("item_name")
    private String mItemName;
    @SerializedName("name")
    private String mName;
    @SerializedName("item_picture")
    private String mItemPicture;
    @SerializedName("description")
    private String mDescription;
    @SerializedName("shape")
    private String mShape = "";
    @SerializedName("valueJson")
    private FolderItemModel.DealValuesModel valueJson;
    @SerializedName("one_piece")
    private boolean mOnePiece;
    @SerializedName("topping_price_on_slice")
    private int mToppingPriceOnSlice;

    public String getItemId() {
        return mItemId;
    }

    public void setItemId(String itemId) {
        mItemId = itemId;
    }

    public String getItemType() {
        return mItemType;
    }

    public void setItemType(String itemType) {
        mItemType = itemType;
    }

    public String getFatherId() {
        return mFatherId;
    }

    public void setFatherId(String fatherId) {
        mFatherId = fatherId;
    }

    public String getLocation() {
        return mLocation;
    }

    public void setLocation(String location) {
        mLocation = location;
    }

    public String getCartId() {
        return mCartId;
    }

    public void setCartId(String cartId) {
        mCartId = cartId;
    }

    public int getPrice() {
        return mPrice;
    }

    public void setPrice(int price) {
        mPrice = price;
    }

    public String getIsCompensation() {
        return mIsCompensation;
    }

    public void setIsCompensation(String isCompensation) {
        mIsCompensation = isCompensation;
    }

    public List<CartFillingModel> getItemFilling() {
        return mItemFilling;
    }

    public void setItemFilling(List<CartFillingModel> itemFilling) {
        mItemFilling = itemFilling;
    }

    public String getChangeType() {
        return mChangeType;
    }

    public void setChangeType(String changeType) {
        mChangeType = changeType;
    }

    public String getItemName() {
        return mItemName;
    }

    public void setItemName(String itemName) {
        mItemName = itemName;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getItemPicture() {
        return mItemPicture;
    }

    public void setItemPicture(String itemPicture) {
        mItemPicture = itemPicture;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getShape() {
        return mShape;
    }

    public void setShape(String shape) {
        mShape = shape;
    }

    public FolderItemModel.DealValuesModel getValueJson() {
        return valueJson;
    }

    public void setValueJson(FolderItemModel.DealValuesModel valueJson) {
        this.valueJson = valueJson;
    }

    public String getFolderId() {
        return mFolderId;
    }

    public void setFolderId(String mFolderId) {
        this.mFolderId = mFolderId;
    }

    public boolean isOnePiece() {
        return mOnePiece;
    }

    public void setOnePiece(boolean mOnePiece) {
        this.mOnePiece = mOnePiece;
    }

    public int getToppingPriceOnSlice() {
        return mToppingPriceOnSlice;
    }

    public void setToppingPriceOnSlice(int mToppingPriceOnSlice) {
        this.mToppingPriceOnSlice = mToppingPriceOnSlice;
    }
}
