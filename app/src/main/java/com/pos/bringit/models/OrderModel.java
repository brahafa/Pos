package com.pos.bringit.models;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class OrderModel {

    @SerializedName("order_id")
    private String mOrderId;
    @SerializedName("business_id")
    private String mBusinessId;
    @SerializedName("client_id")
    private String mClientId;
    @SerializedName("address_id")
    private String mAddressId;
    @SerializedName("action_time")
    private String mActionTime;
    @SerializedName("order_time")
    private String mOrderTime;
    @SerializedName("order_sound_time")
    private String mOrderSoundTime;
    @SerializedName("order_is_delivery")
    private String mOrderIsDelivery;
    @SerializedName("total_paid")
    private String mTotalPaid;
    @SerializedName("changes_cost")
    private String mChangesCost;
    @SerializedName("order_delivery_price")
    private String mOrderDeliveryPrice;
    @SerializedName("order_site_fee")
    private String mOrderSiteFee;
    @SerializedName("order_added_by")
    private String mOrderAddedBy;
    @SerializedName("payment_method_id")
    private String mPaymentMethodId;
    @SerializedName("order_token")
    private String mOrderToken;
    @SerializedName("status")
    private String mStatus;
    @SerializedName("order_is_active")
    private String mOrderIsActive;
    @SerializedName("order_confirmation_id")
    private String mOrderConfirmationId;
    @SerializedName("order_notes")
    private String mOrderNotes;
    @SerializedName("delivery_notes")
    private String mDeliveryNotes;
    @SerializedName("order_bringit_coupon")
    private String mOrderBringitCoupon;
    @SerializedName("order_business_coupon")
    private String mOrderBusinessCoupon;
    @SerializedName("order_is_opened")
    private String mOrderIsOpened;
    @SerializedName("order_user_ip")
    private String mOrderUserIp;
    @SerializedName("order_rate_code")
    private String mOrderRateCode;
    @SerializedName("order_rate_sms_sent")
    private String mOrderRateSmsSent;
    @SerializedName("order_deliver_code")
    private String mOrderDeliverCode;
    @SerializedName("order_deliver_code_time")
    private String mOrderDeliverCodeTime;
    @SerializedName("order_can_follow")
    private String mOrderCanFollow;
    @SerializedName("order_is_paid")
    private String mOrderIsPaid;
    @SerializedName("order_position")
    private int mOrderPosition;
    @SerializedName("order_has_changes")
    private String mOrderHasChanges;
    @SerializedName("is_delivery")
    private String mIsDelivery;
    @SerializedName("city_name")
    private String mCityName;
    @SerializedName("street")
    private String mStreet;
    @SerializedName("house_num")
    private String mHouseNum;
    @SerializedName("order_cooking_time")
    private String mOrderCookingTime;
    @SerializedName("name")
    private String mName;
    @SerializedName("startTimeStr")
    private String mStartTimeStr;
    @SerializedName("phone")
    private String mPhone;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderModel that = (OrderModel) o;
        return mOrderPosition == that.mOrderPosition &&
                Objects.equals(mOrderId, that.mOrderId) &&
                Objects.equals(mBusinessId, that.mBusinessId) &&
                Objects.equals(mClientId, that.mClientId) &&
                Objects.equals(mAddressId, that.mAddressId) &&
                Objects.equals(mActionTime, that.mActionTime) &&
                Objects.equals(mOrderTime, that.mOrderTime) &&
                Objects.equals(mOrderSoundTime, that.mOrderSoundTime) &&
                Objects.equals(mOrderIsDelivery, that.mOrderIsDelivery) &&
                Objects.equals(mTotalPaid, that.mTotalPaid) &&
                Objects.equals(mChangesCost, that.mChangesCost) &&
                Objects.equals(mOrderDeliveryPrice, that.mOrderDeliveryPrice) &&
                Objects.equals(mOrderSiteFee, that.mOrderSiteFee) &&
                Objects.equals(mOrderAddedBy, that.mOrderAddedBy) &&
                Objects.equals(mPaymentMethodId, that.mPaymentMethodId) &&
                Objects.equals(mOrderToken, that.mOrderToken) &&
                Objects.equals(mStatus, that.mStatus) &&
                Objects.equals(mOrderIsActive, that.mOrderIsActive) &&
                Objects.equals(mOrderConfirmationId, that.mOrderConfirmationId) &&
                Objects.equals(mOrderNotes, that.mOrderNotes) &&
                Objects.equals(mDeliveryNotes, that.mDeliveryNotes) &&
                Objects.equals(mOrderBringitCoupon, that.mOrderBringitCoupon) &&
                Objects.equals(mOrderBusinessCoupon, that.mOrderBusinessCoupon) &&
                Objects.equals(mOrderIsOpened, that.mOrderIsOpened) &&
                Objects.equals(mOrderUserIp, that.mOrderUserIp) &&
                Objects.equals(mOrderRateCode, that.mOrderRateCode) &&
                Objects.equals(mOrderRateSmsSent, that.mOrderRateSmsSent) &&
                Objects.equals(mOrderDeliverCode, that.mOrderDeliverCode) &&
                Objects.equals(mOrderDeliverCodeTime, that.mOrderDeliverCodeTime) &&
                Objects.equals(mOrderCanFollow, that.mOrderCanFollow) &&
                Objects.equals(mOrderIsPaid, that.mOrderIsPaid) &&
                Objects.equals(mOrderHasChanges, that.mOrderHasChanges) &&
                Objects.equals(mIsDelivery, that.mIsDelivery) &&
                Objects.equals(mCityName, that.mCityName) &&
                Objects.equals(mStreet, that.mStreet) &&
                Objects.equals(mHouseNum, that.mHouseNum) &&
                Objects.equals(mOrderCookingTime, that.mOrderCookingTime) &&
                Objects.equals(mName, that.mName) &&
                Objects.equals(mStartTimeStr, that.mStartTimeStr) &&
                Objects.equals(mPhone, that.mPhone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mOrderId, mBusinessId, mClientId, mAddressId, mActionTime, mOrderTime, mOrderSoundTime, mOrderIsDelivery, mTotalPaid, mChangesCost, mOrderDeliveryPrice, mOrderSiteFee, mOrderAddedBy, mPaymentMethodId, mOrderToken, mStatus, mOrderIsActive, mOrderConfirmationId, mOrderNotes, mDeliveryNotes, mOrderBringitCoupon, mOrderBusinessCoupon, mOrderIsOpened, mOrderUserIp, mOrderRateCode, mOrderRateSmsSent, mOrderDeliverCode, mOrderDeliverCodeTime, mOrderCanFollow, mOrderIsPaid, mOrderPosition, mOrderHasChanges, mIsDelivery, mCityName, mStreet, mHouseNum, mOrderCookingTime, mName, mStartTimeStr, mPhone);
    }

    public int getOrderId() {
        return Integer.parseInt(mOrderId);
    }

    public void setOrderId(int orderId) {
        mOrderId = String.valueOf(orderId);
    }

    public String getBusinessId() {
        return mBusinessId;
    }

    public void setBusinessId(String businessId) {
        mBusinessId = businessId;
    }

    public String getClientId() {
        return mClientId;
    }

    public void setClientId(String clientId) {
        mClientId = clientId;
    }

    public String getAddressId() {
        return mAddressId;
    }

    public void setAddressId(String addressId) {
        mAddressId = addressId;
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

    public String getOrderSoundTime() {
        return mOrderSoundTime;
    }

    public void setOrderSoundTime(String orderSoundTime) {
        mOrderSoundTime = orderSoundTime;
    }

    public String getOrderIsDelivery() {
        return mOrderIsDelivery;
    }

    public void setOrderIsDelivery(String orderIsDelivery) {
        mOrderIsDelivery = orderIsDelivery;
    }

    public String getTotalPaid() {
        return mTotalPaid;
    }

    public void setTotalPaid(String totalPaid) {
        mTotalPaid = totalPaid;
    }

    public String getChangesCost() {
        return mChangesCost;
    }

    public void setChangesCost(String changesCost) {
        mChangesCost = changesCost;
    }

    public String getOrderDeliveryPrice() {
        return mOrderDeliveryPrice;
    }

    public void setOrderDeliveryPrice(String orderDeliveryPrice) {
        mOrderDeliveryPrice = orderDeliveryPrice;
    }

    public String getOrderSiteFee() {
        return mOrderSiteFee;
    }

    public void setOrderSiteFee(String orderSiteFee) {
        mOrderSiteFee = orderSiteFee;
    }

    public String getOrderAddedBy() {
        return mOrderAddedBy;
    }

    public void setOrderAddedBy(String orderAddedBy) {
        mOrderAddedBy = orderAddedBy;
    }

    public String getPaymentMethodId() {
        return mPaymentMethodId;
    }

    public void setPaymentMethodId(String paymentMethodId) {
        mPaymentMethodId = paymentMethodId;
    }

    public String getOrderToken() {
        return mOrderToken;
    }

    public void setOrderToken(String orderToken) {
        mOrderToken = orderToken;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

    public String getOrderIsActive() {
        return mOrderIsActive;
    }

    public void setOrderIsActive(String orderIsActive) {
        mOrderIsActive = orderIsActive;
    }

    public String getOrderConfirmationId() {
        return mOrderConfirmationId;
    }

    public void setOrderConfirmationId(String orderConfirmationId) {
        mOrderConfirmationId = orderConfirmationId;
    }

    public String getOrderNotes() {
        return mOrderNotes;
    }

    public void setOrderNotes(String orderNotes) {
        mOrderNotes = orderNotes;
    }

    public String getDeliveryNotes() {
        return mDeliveryNotes;
    }

    public void setDeliveryNotes(String deliveryNotes) {
        mDeliveryNotes = deliveryNotes;
    }

    public String getOrderBringitCoupon() {
        return mOrderBringitCoupon;
    }

    public void setOrderBringitCoupon(String orderBringitCoupon) {
        mOrderBringitCoupon = orderBringitCoupon;
    }

    public String getOrderBusinessCoupon() {
        return mOrderBusinessCoupon;
    }

    public void setOrderBusinessCoupon(String orderBusinessCoupon) {
        mOrderBusinessCoupon = orderBusinessCoupon;
    }

    public String getOrderIsOpened() {
        return mOrderIsOpened;
    }

    public void setOrderIsOpened(String orderIsOpened) {
        mOrderIsOpened = orderIsOpened;
    }

    public String getOrderUserIp() {
        return mOrderUserIp;
    }

    public void setOrderUserIp(String orderUserIp) {
        mOrderUserIp = orderUserIp;
    }

    public String getOrderRateCode() {
        return mOrderRateCode;
    }

    public void setOrderRateCode(String orderRateCode) {
        mOrderRateCode = orderRateCode;
    }

    public String getOrderRateSmsSent() {
        return mOrderRateSmsSent;
    }

    public void setOrderRateSmsSent(String orderRateSmsSent) {
        mOrderRateSmsSent = orderRateSmsSent;
    }

    public String getOrderDeliverCode() {
        return mOrderDeliverCode;
    }

    public void setOrderDeliverCode(String orderDeliverCode) {
        mOrderDeliverCode = orderDeliverCode;
    }

    public String getOrderDeliverCodeTime() {
        return mOrderDeliverCodeTime;
    }

    public void setOrderDeliverCodeTime(String orderDeliverCodeTime) {
        mOrderDeliverCodeTime = orderDeliverCodeTime;
    }

    public String getOrderCanFollow() {
        return mOrderCanFollow;
    }

    public void setOrderCanFollow(String orderCanFollow) {
        mOrderCanFollow = orderCanFollow;
    }

    public String getOrderIsPaid() {
        return mOrderIsPaid;
    }

    public void setOrderIsPaid(String orderIsPaid) {
        mOrderIsPaid = orderIsPaid;
    }

    public int getOrderPosition() {
        return mOrderPosition;
    }

    public void setOrderPosition(int orderPosition) {
        mOrderPosition = orderPosition;
    }

    public String getOrderHasChanges() {
        return mOrderHasChanges;
    }

    public void setOrderHasChanges(String orderHasChanges) {
        mOrderHasChanges = orderHasChanges;
    }

    public String getIsDelivery() {
        return mIsDelivery;
    }

    public void setIsDelivery(String isDelivery) {
        mIsDelivery = isDelivery;
    }

    public String getCityName() {
        return mCityName;
    }

    public void setCityName(String cityName) {
        mCityName = cityName;
    }

    public String getStreet() {
        return mStreet;
    }

    public void setStreet(String street) {
        mStreet = street;
    }

    public String getHouseNum() {
        return mHouseNum;
    }

    public void setHouseNum(String houseNum) {
        mHouseNum = houseNum;
    }

    public String getOrderCookingTime() {
        return mOrderCookingTime;
    }

    public void setOrderCookingTime(String orderCookingTime) {
        mOrderCookingTime = orderCookingTime;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getStartTimeStr() {
        return mStartTimeStr;
    }

    public void setStartTimeStr(String startTimeStr) {
        mStartTimeStr = startTimeStr;
    }

    public String getPhone() {
        return mPhone;
    }

    public void setPhone(String mPhone) {
        this.mPhone = mPhone;
    }

}
