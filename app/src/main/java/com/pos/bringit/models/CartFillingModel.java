package com.pos.bringit.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class CartFillingModel implements Parcelable, Cloneable {
    private String name;
    private String price;
    private transient boolean selected;

    public CartFillingModel() {
    }

    public CartFillingModel(String name, String price) {
        this.name = name;
        this.price = price;
    }

    protected CartFillingModel(Parcel in) {
        name = in.readString();
        price = in.readString();
        selected = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(price);
        dest.writeByte((byte) (selected ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CartFillingModel> CREATOR = new Creator<CartFillingModel>() {
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

    @NonNull
    public CartFillingModel clone() throws CloneNotSupportedException {
        return (CartFillingModel) super.clone();
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
