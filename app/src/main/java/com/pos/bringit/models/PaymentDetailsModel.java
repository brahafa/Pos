package com.pos.bringit.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class PaymentDetailsModel implements Parcelable {

    private String total;
    private List<PaymentModel> payments;
    private String orderId;
    private String phone;

    public PaymentDetailsModel(String total, List<PaymentModel> payments, String orderId, String phone) {
        this.total = total;
        this.payments = payments;
        this.orderId = orderId;
        this.phone = phone;
    }

    protected PaymentDetailsModel(Parcel in) {
        total = in.readString();
        payments = in.createTypedArrayList(PaymentModel.CREATOR);
        orderId = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(total);
        dest.writeTypedList(payments);
        dest.writeString(orderId);
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
}
