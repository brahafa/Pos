package com.pos.bringit.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class InnerProductsModel implements Parcelable, Cloneable {
    @SerializedName("id")
    private String mId;
    @SerializedName("source_product_id")
    private String mSourceProductId;
    @SerializedName("order_id")
    private String mOrderId;
    @SerializedName("changeType")
    private String mChangeType = "";
    @SerializedName("category_id")
    private String mCategoryId;
    @SerializedName("product_id")
    private String mProductId;
    @SerializedName("name")
    private String mName;
    @SerializedName("price")
    private int mPrice;
    @SerializedName("in_inventory")
    private String mInInventory;
    @SerializedName("location")
    private String mLocation;
    @SerializedName("is_deleted")
    private String mIsDeleted;
    @SerializedName("is_new")
    private String mIsNew;
    @SerializedName("is_canceled")
    private String mIsCanceled;

    private int mPriceForLayer;

    private transient boolean mIsSelected;
    private transient int mCount = 1;
    private transient boolean mIsPriceFixedOnTheCart;

    public InnerProductsModel() {
    }

    protected InnerProductsModel(Parcel in) {
        mId = in.readString();
        mCategoryId = in.readString();
        mProductId = in.readString();
        mOrderId = in.readString();
        mName = in.readString();
        mPrice = in.readInt();
        mInInventory = in.readString();
        mLocation = in.readString();
        mChangeType = in.readString();
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

    public boolean isDeleted() {
        return "1".equals(mIsDeleted);
    }

    public boolean isCanceled() {
        return "1".equals(mIsCanceled);
    }

    public boolean isNew() {
        return "1".equals(mIsNew);
    }

    public boolean isIsPriceFixedOnTheCart() {
        return mIsPriceFixedOnTheCart;
    }

    public void setIsPriceFixedOnTheCart(boolean mIsPriceFixedOnTheCart) {
        this.mIsPriceFixedOnTheCart = mIsPriceFixedOnTheCart;
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

    public int getSourceProductId() {
        return mSourceProductId == null ? -1 : Integer.parseInt(mSourceProductId);
    }

    public void setSourceProductId(String mSourceProductId) {
        this.mSourceProductId = mSourceProductId;
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

    public String getChangeType() {
        return mChangeType;
    }

    public void setChangeType(String changeType) {
        this.mChangeType = changeType;
    }

    public String getProductId() {
        return mProductId;
    }

    public void setProductId(String mProductId) {
        this.mProductId = mProductId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mId);
        dest.writeString(mCategoryId);
        dest.writeString(mProductId);
        dest.writeString(mOrderId);
        dest.writeString(mName);
        dest.writeInt(mPrice);
        dest.writeString(mInInventory);
        dest.writeString(mLocation);
        dest.writeString(mChangeType);
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

    public void setPriceForLayer(int price) {
        mPriceForLayer = price;
    }

    public int getPriceForLayer() {
        return mPriceForLayer;
    }


    public String getOrderId() {
        return mOrderId;
    }

    public void setOrderId(String mOrderId) {
        this.mOrderId = mOrderId;
    }
}
