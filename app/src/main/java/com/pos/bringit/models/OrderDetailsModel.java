package com.pos.bringit.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderDetailsModel implements Parcelable {


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
    @SerializedName("total_with_delivery")
    private double mTotalWithDelivery;
    @SerializedName("total")
    private double mTotal;
    @SerializedName("delivery_price")
    private double mDeliveryPrice;
    @SerializedName("delivery_option")
    private String mDeliveryOption;
    @SerializedName("status")
    private String mStatus;
    @SerializedName("order_notes")
    private String mOrderNotes;
    @SerializedName("delivery_notes")
    private String mDeliveryNotes;
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
    @SerializedName("products")
    private List<ProductItemModel> mOrderItems;
    @SerializedName("payments")
    private List<PaymentModel> mPayments;
    @SerializedName("client")
    private UserDetailsModel mClient;

    protected OrderDetailsModel(Parcel in) {
        mOrderId = in.readString();
        mClientId = in.readString();
        mAddress = in.readString();
        mAddedBy = in.readString();
        mActionTime = in.readString();
        mOrderTime = in.readString();
        mIsDelivery = in.readString();
        mOrderTotal = in.readString();
        mTotalWithDelivery = in.readDouble();
        mTotal = in.readDouble();
        mDeliveryPrice = in.readDouble();
        mDeliveryOption = in.readString();
        mStatus = in.readString();
        mOrderNotes = in.readString();
        mDeliveryNotes = in.readString();
        mFName = in.readString();
        mLName = in.readString();
        mPhone = in.readString();
        mPaymentName = in.readString();
        mPaymentDisplay = in.readString();
        mAddedBySystem = in.readString();
        mPizzaCount = in.readString();
        mOrderItems = in.createTypedArrayList(ProductItemModel.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mOrderId);
        dest.writeString(mClientId);
        dest.writeString(mAddress);
        dest.writeString(mAddedBy);
        dest.writeString(mActionTime);
        dest.writeString(mOrderTime);
        dest.writeString(mIsDelivery);
        dest.writeString(mOrderTotal);
        dest.writeDouble(mTotalWithDelivery);
        dest.writeDouble(mTotal);
        dest.writeDouble(mDeliveryPrice);
        dest.writeString(mDeliveryOption);
        dest.writeString(mStatus);
        dest.writeString(mOrderNotes);
        dest.writeString(mDeliveryNotes);
        dest.writeString(mFName);
        dest.writeString(mLName);
        dest.writeString(mPhone);
        dest.writeString(mPaymentName);
        dest.writeString(mPaymentDisplay);
        dest.writeString(mAddedBySystem);
        dest.writeString(mPizzaCount);
        dest.writeTypedList(mOrderItems);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<OrderDetailsModel> CREATOR = new Creator<OrderDetailsModel>() {
        @Override
        public OrderDetailsModel createFromParcel(Parcel in) {
            return new OrderDetailsModel(in);
        }

        @Override
        public OrderDetailsModel[] newArray(int size) {
            return new OrderDetailsModel[size];
        }
    };

    public UserDetailsModel getClient() {
        return mClient;
    }

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

    public double getTotal() {
        return mTotal;
    }

    public void setTotal(double totalPaid) {
        mTotal = totalPaid;
    }

    public double getDeliveryPrice() {
        return mDeliveryPrice;
    }

    public void setDeliveryPrice(double deliveryPrice) {
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

    public String getDeliveryNotes() {
        return mDeliveryNotes;
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

    public List<ProductItemModel> getOrderItems() {
        return mOrderItems;
    }

    public void setOrderItems(List<ProductItemModel> orderItems) {
        mOrderItems = orderItems;
    }

    public String getDeliveryOption() {
        return mDeliveryOption;
    }

    public void setDeliveryOption(String mDeliveryOption) {
        this.mDeliveryOption = mDeliveryOption;
    }

    public List<PaymentModel> getPayments() {
        return mPayments;
    }

    public void setPayments(List<PaymentModel> mPayments) {
        this.mPayments = mPayments;
    }

    public double getTotalWithDelivery() {
        return mTotalWithDelivery;
    }

    public void setTotalWithDelivery(double mTotalWithDelivery) {
        this.mTotalWithDelivery = mTotalWithDelivery;
    }
}
