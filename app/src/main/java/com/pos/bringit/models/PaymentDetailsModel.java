package com.pos.bringit.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class PaymentDetailsModel implements Parcelable {

    private String total;
    private List<PaymentModel> payments;
    private String orderType;
    private String orderId;
    private String phone;
    private boolean isEdited;

    public PaymentDetailsModel(String total, List<PaymentModel> payments, String orderType, String orderId, String phone, boolean isEdited) {
        this.total = total;
        this.payments = payments;
        this.orderType = orderType;
        this.orderId = orderId;
        this.phone = phone;
        this.isEdited = isEdited;
    }

    protected PaymentDetailsModel(Parcel in) {
        total = in.readString();
        payments = in.createTypedArrayList(PaymentModel.CREATOR);
        orderType = in.readString();
        orderId = in.readString();
        phone = in.readString();
        isEdited = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(total);
        dest.writeTypedList(payments);
        dest.writeString(orderType);
        dest.writeString(orderId);
        dest.writeString(phone);
        dest.writeByte((byte) (isEdited ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PaymentDetailsModel> CREATOR = new Creator<PaymentDetailsModel>() {
        @Override
        public PaymentDetailsModel createFromParcel(Parcel in) {
            return new PaymentDetailsModel(in);
        }

        @Override
        public PaymentDetailsModel[] newArray(int size) {
            return new PaymentDetailsModel[size];
        }
    };

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<PaymentModel> getPayments() {
        return payments;
    }

    public void setPayments(List<PaymentModel> payments) {
        this.payments = payments;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isEdited() {
        return isEdited;
    }

    public void setEdited(boolean edited) {
        isEdited = edited;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

}
