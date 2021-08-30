package com.pos.bringit.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderDetailsModel implements Parcelable {

    @SerializedName("id")
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
    @SerializedName("is_paid")
    private int mIsPaid;
    @SerializedName("pay_to_delivery_man")
    private int mPayToDeliveryMan;
    @SerializedName("is_canceled")
    private boolean mIsCanceled;
    @SerializedName("is_changed")
    private boolean mIsChanged;
    @SerializedName("color")
    private String mColor;
    @SerializedName("change_type")
    private String mChangeType;
    @SerializedName("table_id")
    private String mTableId;
    @SerializedName("table_is_active")
    private String mTableIsActive;
    @SerializedName("is_scheduled")
    private String mIsScheduled;
    @SerializedName("scheduled_time")
    private String mScheduledTime;

    public OrderDetailsModel() {
    }

    public OrderDetailsModel(OrderModel orderModel) {
        this.mOrderId = orderModel.getId();
        this.mActionTime = String.valueOf(orderModel.getActionTime());
        this.mOrderTime = orderModel.getOrderTime();
        this.mStatus = orderModel.getStatus();
        this.mIsPaid = orderModel.getIsPaid();
        this.mPayToDeliveryMan = orderModel.getPayToDeliveryMan();
        this.mChangeType = orderModel.getChangeType();
        this.mTableId = orderModel.getTableId();
        this.mDeliveryOption = orderModel.getDeliveryOption();
        this.mColor = orderModel.getColor();
        this.mIsCanceled = orderModel.isCanceled();
        this.mIsChanged = orderModel.isChanged();
        this.mTableIsActive = orderModel.getTableIsActive();
        this.mIsScheduled = orderModel.getIsScheduled();
        this.mScheduledTime = orderModel.getScheduledTime();

        UserDetailsModel client = new UserDetailsModel();
        client.setName(orderModel.getClient().getFName());
        client.setLastName(orderModel.getClient().getLName());
        client.setPhone(orderModel.getClient().getPhone());
        client.setAddress(orderModel.getClient().getAddress());
        this.mClient = client;

        this.mAddedBySystem = orderModel.getAddedBySystem();
    }

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

    public void setClient(UserDetailsModel mClient) {
        this.mClient = mClient;
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

    public void setOrderNotes(String orderNotes) {
        mOrderNotes = orderNotes;
    }

    public String getDeliveryNotes() {
        return mDeliveryNotes;
    }

    public void setDeliveryNotes(String deliveryNotes) {
        mDeliveryNotes = deliveryNotes;
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

    public boolean isCanceled() {
        return mIsCanceled;
    }

    public void setIsCanceled(boolean mIsCanceled) {
        this.mIsCanceled = mIsCanceled;
    }

    public boolean isChanged() {
        return mIsChanged;
    }

    public void setIsChanged(boolean isChanged) {
        mIsChanged = isChanged;
    }

    public String getColor() {
        return mColor;
    }

    public void setColor(String mColor) {
        this.mColor = mColor;
    }

    public int getIsPaid() {
        return mIsPaid;
    }

    public void setIsPaid(int isPaid) {
        mIsPaid = isPaid;
    }

    public String getChangeType() {
        return mChangeType;
    }

    public void setChangeType(String changeType) {
        mChangeType = changeType;
    }

    public String getTableId() {
        return mTableId;
    }

    public void setTableId(String mTableId) {
        this.mTableId = mTableId;
    }

    public boolean isTableIsActive() {
        return mTableIsActive.equals("1");
    }

    public void setTableIsActive(String mTableIsActive) {
        this.mTableIsActive = mTableIsActive;
    }

    public boolean isScheduled() {
        return mIsScheduled != null && mIsScheduled.equals("1");
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
