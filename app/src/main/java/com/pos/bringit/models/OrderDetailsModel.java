package com.pos.bringit.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderDetailsModel {


    @SerializedName("order_id")
    private String mOrderId;
    @SerializedName("client_id")
    private String mClientId;
    @SerializedName("address")
    private String mAddress;
    @SerializedName("added_by")
    private String mAddedBy;
    @SerializedName("action_time")
    private String mActionTime;
    @SerializedName("order_time")
    private String mOrderTime;
    @SerializedName("is_delivery")
    private String mIsDelivery;
    @SerializedName("order_total")
    private String mOrderTotal;
    @SerializedName("total_paid")
    private String mTotalPaid;
    @SerializedName("delivery_price")
    private String mDeliveryPrice;
    @SerializedName("status")
    private String mStatus;
    @SerializedName("order_notes")
    private String mOrderNotes;
    @SerializedName("f_name")
    private String mFName;
    @SerializedName("l_name")
    private String mLName;
    @SerializedName("phone")
    private String mPhone;
    @SerializedName("payment_name")
    private String mPaymentName;
    @SerializedName("payment_display")
    private String mPaymentDisplay;
    @SerializedName("added_by_system")
    private String mAddedBySystem;
    @SerializedName("pizza_count")
    private String mPizzaCount;
    @SerializedName("order_items")
    private List<OrderItemsModel> mOrderItems;

    public String getOrderId() {
        return mOrderId;
    }

    public void setOrderId(String orderId) {
        mOrderId = orderId;
    }

    public String getClientId() {
        return mClientId;
    }

    public void setClientId(String clientId) {
        mClientId = clientId;
    }

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String address) {
        mAddress = address;
    }

    public String getAddedBy() {
        return mAddedBy;
    }

    public void setAddedBy(String addedBy) {
        mAddedBy = addedBy;
    }

    public String getActionTime() {
        return mActionTime;
    }

    public void setActionTime(String actionTime) {
        mActionTime = actionTime;
    }

    public String getOrderTime() {
        return mOrderTime;
    }

    public void setOrderTime(String orderTime) {
        mOrderTime = orderTime;
    }

    public String getIsDelivery() {
        return mIsDelivery;
    }

    public void setIsDelivery(String isDelivery) {
        mIsDelivery = isDelivery;
    }

    public String getOrderTotal() {
        return mOrderTotal;
    }

    public void setOrderTotal(String orderTotal) {
        mOrderTotal = orderTotal;
    }

    public String getTotalPaid() {
        return mTotalPaid;
    }

    public void setTotalPaid(String totalPaid) {
        mTotalPaid = totalPaid;
    }

    public String getDeliveryPrice() {
        return mDeliveryPrice;
    }

    public void setDeliveryPrice(String deliveryPrice) {
        mDeliveryPrice = deliveryPrice;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

    public String getOrderNotes() {
        return mOrderNotes;
    }

    public void setOrderNotes(String orderNotes) {
        mOrderNotes = orderNotes;
    }

    public String getFName() {
        return mFName;
    }

    public void setFName(String fName) {
        mFName = fName;
    }

    public String getLName() {
        return mLName;
    }

    public void setLName(String lName) {
        mLName = lName;
    }

    public String getPhone() {
        return mPhone;
    }

    public void setPhone(String phone) {
        mPhone = phone;
    }

    public String getPaymentName() {
        return mPaymentName;
    }

    public void setPaymentName(String paymentName) {
        mPaymentName = paymentName;
    }

    public String getPaymentDisplay() {
        return mPaymentDisplay;
    }

    public void setPaymentDisplay(String paymentDisplay) {
        mPaymentDisplay = paymentDisplay;
    }

    public String getAddedBySystem() {
        return mAddedBySystem;
    }

    public void setAddedBySystem(String addedBySystem) {
        mAddedBySystem = addedBySystem;
    }

    public String getPizzaCount() {
        return mPizzaCount;
    }

    public void setPizzaCount(String pizzaCount) {
        mPizzaCount = pizzaCount;
    }

    public List<OrderItemsModel> getOrderItems() {
        return mOrderItems;
    }

    public void setOrderItems(List<OrderItemsModel> orderItems) {
        mOrderItems = orderItems;
    }

}
