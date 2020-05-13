package com.pos.bringit.models;

import android.os.Parcel;
import android.os.Parcelable;

public class CartFillingModel implements Parcelable {
    private String name;
    private String price;

    public CartFillingModel() {
    }

    public CartFillingModel(String name, String price) {
        this.name = name;
        this.price = String.valueOf(price);
    }

    protected CartFillingModel(Parcel in) {
        name = in.readString();
        price = in.readString();
    }

    public transient final Creator<CartFillingModel> CREATOR = new Creator<CartFillingModel>() {
        @Override
        public CartFillingModel createFromParcel(Parcel in) {
            return new CartFillingModel(in);
        }

        @Override
        public CartFillingModel[] newArray(int size) {
            return new CartFillingModel[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public int getPrice() {
        return Integer.parseInt(price);
    }

    public void setPrice(int price) {
        this.price = String.valueOf(price);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(price);
    }
}
