package com.pos.bringit.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class PaymentModel implements Parcelable {
    @SerializedName("payment_id")
    private String id;
    private String price;
    private String type;

    public PaymentModel(String price, String type) {
        this.price = price;
        this.type = type;
    }

    protected PaymentModel(Parcel in) {
        id = in.readString();
        price = in.readString();
        type = in.readString();
    }

    public static final Creator<PaymentModel> CREATOR = new Creator<PaymentModel>() {
        @Override
        public PaymentModel createFromParcel(Parcel in) {
            return new PaymentModel(in);
        }

        @Override
        public PaymentModel[] newArray(int size) {
            return new PaymentModel[size];
        }
    };

    public String getPrice() {
        if (price == null) return "";
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getType() {
        if (type == null) return "";
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(price);
        dest.writeString(type);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
