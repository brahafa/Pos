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
    private double mTotalPaid;
    @SerializedName("is_paid")
    private int mIsPaid;
    @SerializedName("position")
    private int mPosition;
    @SerializedName("table_id")
    private String mTableId;
    @SerializedName("delivery_option")
    private String mDeliveryOption;
    @SerializedName("cooking_time")
    private int mCookingTime;
    @SerializedName("pay_to_delivery_man")
    private int mPayToDeliveryMan;
    @SerializedName("is_canceled")
    private boolean mIsCanceled;
    @SerializedName("is_changed")
    private boolean mIsChanged;
    @SerializedName("is_scheduled")
    private String mIsScheduled;
    @SerializedName("scheduled_time")
    private String mScheduledTime;
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
    @SerializedName("table_is_active")
    private String mTableIsActive;

    @SerializedName("products")
    private List<ProductItemModel> mProductItemModel = new ArrayList<>();


    public OrderModel() {
    }

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
                mIsChanged == that.mIsChanged &&
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
                mPosition, mIsChanged, mTableId, mDeliveryOption, mCookingTime, mStartTimeStr, mColor, mClient, mIsCanceled);
    }

    public List<ProductItemModel> getProductItemModel() {
        return mProductItemModel;
    }

    public void setProductItemModel(List<ProductItemModel> mProductItemModel) {
        this.mProductItemModel = mProductItemModel;
    }

    public boolean isCanceled() {
        return mIsCanceled;
    }

    public void setIsCanceled(boolean mIsCanceled) {
        this.mIsCanceled = mIsCanceled;
    }

    public String getChangeType() {
        return mChangeType;
    }

    public void setChangeType(String changeType) {
        mChangeType = changeType;
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

    public double getTotalPaid() {
        return mTotalPaid;
    }

    public void setTotalPaid(double totalPaid) {
        mTotalPaid = totalPaid;
    }

    public int getIsPaid() {
        return mIsPaid;
    }

    public void setIsPaid(int isPaid) {
        mIsPaid = isPaid;
    }

    public int getPosition() {
        return mPosition;
    }

    public void setPosition(int position) {
        mPosition = position;
    }

    public boolean isChanged() {
        return mIsChanged;
    }

    public void setIsChanged(boolean isChanged) {
        mIsChanged = isChanged;
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

    public boolean isTableIsActive() {
        return mTableIsActive.equals("1");
    }

    public String getTableIsActive() {
        return mTableIsActive;
    }

    public void setTableIsActive(String mTableIsActive) {
        this.mTableIsActive = mTableIsActive;
    }

    public boolean isScheduled() {
        return mIsScheduled.equals("1");
    }

    public String getIsScheduled() {
        return mIsScheduled;
    }

    public void setIsScheduled(String mIsScheduled) {
        this.mIsScheduled = mIsScheduled;
    }

    public String getScheduledTime() {
        return mScheduledTime;
    }

    public void setScheduledTime(String mScheduledTime) {
        this.mScheduledTime = mScheduledTime;
    }

    public int getPayToDeliveryMan() {
        return mPayToDeliveryMan;
    }

    public void setPayToDeliveryMan(int mPayToDeliveryMan) {
        this.mPayToDeliveryMan = mPayToDeliveryMan;
    }
}
