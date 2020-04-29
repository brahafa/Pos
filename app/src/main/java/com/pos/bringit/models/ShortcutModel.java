package com.pos.bringit.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ShortcutModel {
    @SerializedName("shortcut_id")
    private String mShortcutId;
    @SerializedName("type")
    private String mType;
    @SerializedName("id")
    private String mId;
    @SerializedName("father_id")
    private String mFatherId;
    @SerializedName("position")
    private String mPosition;
    @SerializedName("folder_name")
    private String mFolderName;
    @SerializedName("color")
    private String mColor;
    @SerializedName("delivery_price")
    private int mDeliveryPrice;
    @SerializedName("pickup_price")
    private int mPickupPrice;
    @SerializedName("object_type")
    private String mObjectType;
    @SerializedName("object_id")
    private String mObjectId;
    @SerializedName("in_inventory")
    private String mInInventory;
    @SerializedName("name")
    private String mName;
    @SerializedName("description")
    private String mDescription;
    @SerializedName("uniq_for_business_id")
    private String mUniqForBusinessId;
    @SerializedName("isShortcut")
    private boolean mIsShortcut;
    @SerializedName("one_piece")
    private boolean mOnePiece;
    @SerializedName("shape")
    private String mShape;
    @SerializedName("filling")
    private List<FillingModel> mFilling;

    public String getShortcutId() {
        return mShortcutId;
    }

    public void setShortcutId(String shortcutId) {
        mShortcutId = shortcutId;
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getFatherId() {
        return mFatherId;
    }

    public void setFatherId(String fatherId) {
        mFatherId = fatherId;
    }

    public String getPosition() {
        return mPosition;
    }

    public void setPosition(String position) {
        mPosition = position;
    }

    public String getFolderName() {
        return mFolderName;
    }

    public void setFolderName(String folderName) {
        mFolderName = folderName;
    }

    public String getColor() {
        return mColor;
    }

    public void setColor(String color) {
        mColor = color;
    }

    public int getDeliveryPrice() {
        return mDeliveryPrice;
    }

    public void setDeliveryPrice(int deliveryPrice) {
        mDeliveryPrice = deliveryPrice;
    }

    public int getPickupPrice() {
        return mPickupPrice;
    }

    public void setPickupPrice(int pickupPrice) {
        mPickupPrice = pickupPrice;
    }

    public String getObjectType() {
        return mObjectType;
    }

    public void setObjectType(String objectType) {
        mObjectType = objectType;
    }

    public String getObjectId() {
        return mObjectId;
    }

    public void setObjectId(String objectId) {
        mObjectId = objectId;
    }

    public String getInInventory() {
        return mInInventory;
    }

    public void setInInventory(String inInventory) {
        mInInventory = inInventory;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getUniqForBusinessId() {
        return mUniqForBusinessId;
    }

    public void setUniqForBusinessId(String uniqForBusinessId) {
        mUniqForBusinessId = uniqForBusinessId;
    }

    public boolean isIsShortcut() {
        return mIsShortcut;
    }

    public void setIsShortcut(boolean isShortcut) {
        mIsShortcut = isShortcut;
    }

    public boolean isOnePiece() {
        return mOnePiece;
    }

    public void setOnePiece(boolean onePiece) {
        mOnePiece = onePiece;
    }

    public String getShape() {
        return mShape;
    }

    public void setShape(String shape) {
        mShape = shape;
    }

    public List<FillingModel> getFilling() {
        return mFilling;
    }

    public void setFilling(List<FillingModel> filling) {
        mFilling = filling;
    }


}
