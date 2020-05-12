package com.pos.bringit.models;

import android.os.Parcel;
import android.os.Parcelable;

public class CartFillingModel implements Parcelable {
    private String name;
    private String price;

    public CartFillingModel(String name, String price) {
        this.name = name;
        this.price = price;
    }

    protected CartFillingModel(Parcel in) {
        name = in.readString();
        price = in.readString();
    }

    public final Creator<CartFillingModel> CREATOR = new Creator<CartFillingModel>() {
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


    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
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
