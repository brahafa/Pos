package com.pos.bringit.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class PaymentDetailsModel implements Parcelable {

    private String total;
    private List<PaymentModel> payments;

    public PaymentDetailsModel(String total, List<PaymentModel> payments) {
        this.total = total;
        this.payments = payments;
    }

    protected PaymentDetailsModel(Parcel in) {
        total = in.readString();
        payments = in.createTypedArrayList(PaymentModel.CREATOR);
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(total);
        dest.writeTypedList(payments);
    }

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
}
