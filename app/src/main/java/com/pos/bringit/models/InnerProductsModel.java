package com.pos.bringit.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

import androidx.annotation.NonNull;

public class InnerProductsModel implements Parcelable, Cloneable {
    @SerializedName("id")
    private String mId;
    @SerializedName("category_id")
    private String mCategoryId;
    @SerializedName("name")
    private String mName;
    @SerializedName("price")
    private int mPrice;
    @SerializedName("in_inventory")
    private String mInInventory;
    @SerializedName("location")
    private String mLocation;

    private transient boolean mIsSelected;
    private transient int mCount = 1;

    public InnerProductsModel() {
    }

    protected InnerProductsModel(Parcel in) {
        mId = in.readString();
        mCategoryId = in.readString();
        mName = in.readString();
        mPrice = in.readInt();
        mInInventory = in.readString();
        mLocation = in.readString();
    }

    public static final Creator<InnerProductsModel> CREATOR = new Creator<InnerProductsModel>() {
        @Override
        public InnerProductsModel createFromParcel(Parcel in) {
            return new InnerProductsModel(in);
        }

        @Override
        public InnerProductsModel[] newArray(int size) {
            return new InnerProductsModel[size];
        }
    };

    @NonNull
    public InnerProductsModel clone() {
        try {
            return (InnerProductsModel) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getId() {
        return Integer.parseInt(mId);
    }

    public String getStringId() {
        return mId;
    }

    public void setId(int id) {
        mId = String.valueOf(id);
    }

    public String getCategoryId() {
        return mCategoryId;
    }

    public void setCategoryId(String categoryId) {
        mCategoryId = categoryId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public int getPrice() {
        return mPrice;
    }

    public void setPrice(int price) {
        mPrice = price;
    }

    public String getInInventory() {
        return mInInventory;
    }

    public void setInInventory(String inInventory) {
        mInInventory = inInventory;
    }

    public boolean isSelected() {
        return mIsSelected;
    }

    public void setSelected(boolean mIsSelected) {
        this.mIsSelected = mIsSelected;
    }

    public String getLocation() {
        return mLocation;
    }

    public void setLocation(String mLocation) {
        this.mLocation = mLocation;
    }

    public int getCount() {
        return mCount;
    }

    public void setCount(int mCount) {
        this.mCount = mCount;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mId);
        dest.writeString(mCategoryId);
        dest.writeString(mName);
        dest.writeInt(mPrice);
        dest.writeString(mInInventory);
        dest.writeString(mLocation);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InnerProductsModel)) return false;
        InnerProductsModel that = (InnerProductsModel) o;
        return mPrice == that.mPrice &&
                Objects.equals(mId, that.mId) &&
                Objects.equals(mCategoryId, that.mCategoryId) &&
                Objects.equals(mName, that.mName) &&
                Objects.equals(mInInventory, that.mInInventory) &&
                Objects.equals(mLocation, that.mLocation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mId, mCategoryId, mName, mPrice, mInInventory, mLocation);
    }
}
