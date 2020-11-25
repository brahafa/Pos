package com.pos.bringit.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OrderModel {

    @SerializedName("id")
    private String mId;
    @SerializedName("business_id")
    private String mBusinessId;
    @SerializedName("action_time")
    private int mActionTime;
    @SerializedName("order_time")
    private String mOrderTime;
    @SerializedName("is_delivery")
    private boolean mIsDelivery;
    @SerializedName("status")
    private String mStatus;
    @SerializedName("total_paid")
    private int mTotalPaid;
    @SerializedName("is_paid")
    private boolean mIsPaid;
    @SerializedName("position")
    private int mPosition;
    @SerializedName("has_changes")
    private boolean mHasChanges;
    @SerializedName("table_id")
    private String mTableId;
    @SerializedName("delivery_option")
    private String mDeliveryOption;
    @SerializedName("cooking_time")
    private int mCookingTime;
    @SerializedName("added_by_system")
    private String mAddedBySystem;
    @SerializedName("startTimeStr")
    private String mStartTimeStr;
    @SerializedName("color")
    private String mColor;
    @SerializedName("change_type")
    private String mChangeType;
    @SerializedName("client")
    private ClientModel mClient;
    @SerializedName("is_canceled")
    private boolean mIsCanceled;

    @SerializedName("products")
    private List<ProductItemModel> mProductItemModel = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderModel)) return false;
        OrderModel that = (OrderModel) o;
        return mActionTime == that.mActionTime &&
                mIsDelivery == that.mIsDelivery &&
                mTotalPaid == that.mTotalPaid &&
                mIsPaid == that.mIsPaid &&
                mPosition == that.mPosition &&
                mHasChanges == that.mHasChanges &&
                mCookingTime == that.mCookingTime &&
                mIsCanceled == that.mIsCanceled &&
                Objects.equals(mId, that.mId) &&
                Objects.equals(mBusinessId, that.mBusinessId) &&
                Objects.equals(mOrderTime, that.mOrderTime) &&
                Objects.equals(mStatus, that.mStatus) &&
                Objects.equals(mTableId, that.mTableId) &&
                Objects.equals(mDeliveryOption, that.mDeliveryOption) &&
                Objects.equals(mStartTimeStr, that.mStartTimeStr) &&
                Objects.equals(mColor, that.mColor) &&
                Objects.equals(mClient, that.mClient);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mId, mBusinessId, mActionTime, mOrderTime, mIsDelivery, mStatus, mTotalPaid, mIsPaid,
                mPosition, mHasChanges, mTableId, mDeliveryOption, mCookingTime, mStartTimeStr, mColor, mClient, mIsCanceled);
    }

    public List<ProductItemModel> getProductItemModel() {
        return mProductItemModel;
    }

    public void setProductItemModel(List<ProductItemModel> mProductItemModel) {
        this.mProductItemModel = mProductItemModel;
    }

    public boolean getIsCanceled() {
        return mIsCanceled;
    }

    public String getChangeType() {
        return mChangeType;
    }

    public void setIsCanceled(boolean mIsCanceled) {
        this.mIsCanceled = mIsCanceled;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public int getActionTime() {
        return mActionTime;
    }

    public void setActionTime(int actionTime) {
        mActionTime = actionTime;
    }

    public boolean isDelivery() {
        return mIsDelivery;
    }

    public void setIsDelivery(boolean isDelivery) {
        mIsDelivery = isDelivery;
    }

    public int getTotalPaid() {
        return mTotalPaid;
    }

    public void setTotalPaid(int totalPaid) {
        mTotalPaid = totalPaid;
    }

    public boolean isPaid() {
        return mIsPaid;
    }

    public void setIsPaid(boolean isPaid) {
        mIsPaid = isPaid;
    }

    public int getPosition() {
        return mPosition;
    }

    public void setPosition(int position) {
        mPosition = position;
    }

    public boolean isHasChanges() {
        return mHasChanges;
    }

    public void setHasChanges(boolean hasChanges) {
        mHasChanges = hasChanges;
    }

    public int getCookingTime() {
        return mCookingTime;
    }

    public void setCookingTime(int cookingTime) {
        mCookingTime = cookingTime;
    }

    public ClientModel getClient() {
        return mClient;
    }

    public void setClient(ClientModel client) {
        mClient = client;
    }

    public String getOrderTime() {
        return mOrderTime;
    }

    public void setOrderTime(String mOrderTime) {
        this.mOrderTime = mOrderTime;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String mStatus) {
        this.mStatus = mStatus;
    }

    public String getStartTimeStr() {
        return mStartTimeStr;
    }

    public void setStartTimeStr(String mStartTimeStr) {
        this.mStartTimeStr = mStartTimeStr;
    }

    public String getTableId() {
        return mTableId;
    }

    public void setTableId(String mTableId) {
        this.mTableId = mTableId;
    }

    public String getBusinessId() {
        return mBusinessId;
    }

    public void setBusinessId(String mBusinessId) {
        this.mBusinessId = mBusinessId;
    }

    public String getDeliveryOption() {
        return mDeliveryOption;
    }

    public void setDeliveryOption(String mDeliveryOption) {
        this.mDeliveryOption = mDeliveryOption;
    }

    public String getColor() {
        return mColor;
    }

    public void setColor(String mColor) {
        this.mColor = mColor;
    }

    public String getAddedBySystem() {
        return mAddedBySystem;
    }

    public void setAddedBySystem(String mAddedBySystem) {
        this.mAddedBySystem = mAddedBySystem;
    }
}
