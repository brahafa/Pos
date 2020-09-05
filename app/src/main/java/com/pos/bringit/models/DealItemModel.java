package com.pos.bringit.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

public class DealItemModel implements Parcelable, Cloneable {

    @SerializedName("id")
    private String mId;
    @SerializedName("deal_id")
    private String mDealId;
    @SerializedName("type_id")
    private String mTypeId;
    @SerializedName("type_name")
    private String mTypeName;
    @SerializedName("products")
    private List<ProductItemModel> mProducts = new ArrayList<>();

    private transient List<ProductItemModel> mSourceProducts = new ArrayList<>();
    private transient boolean mIsSelected;

    public DealItemModel() {
    }

    protected DealItemModel(Parcel in) {
        mId = in.readString();
        mDealId = in.readString();
        mTypeId = in.readString();
        mTypeName = in.readString();
        mProducts = in.createTypedArrayList(ProductItemModel.CREATOR);
        mSourceProducts = in.createTypedArrayList(ProductItemModel.CREATOR);
    }

    public static final Creator<DealItemModel> CREATOR = new Creator<DealItemModel>() {
        @Override
        public DealItemModel createFromParcel(Parcel in) {
            return new DealItemModel(in);
        }

        @Override
        public DealItemModel[] newArray(int size) {
            return new DealItemModel[size];
        }
    };

    @NonNull
    public DealItemModel clone() {

        DealItemModel newModel = null;
        try {
            newModel = (DealItemModel) super.clone();

            newModel.mProducts = new ArrayList<>();
            for (ProductItemModel temp : this.mProducts) {
                newModel.mProducts.add(temp.clone());
            }
            newModel.mSourceProducts = new ArrayList<>();
            for (ProductItemModel temp : this.mSourceProducts) {
                newModel.mSourceProducts.add(temp.clone());
            }
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

        return newModel;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getDealId() {
        return mDealId;
    }

    public void setDealId(String dealId) {
        mDealId = dealId;
    }

    public String getTypeId() {
        return mTypeId;
    }

    public void setTypeId(String typeId) {
        mTypeId = typeId;
    }

    public String getTypeName() {
        return mTypeName;
    }

    public void setTypeName(String typeName) {
        mTypeName = typeName;
    }

    public List<ProductItemModel> getProducts() {
        return mProducts;
    }

    public void setProducts(List<ProductItemModel> products) {
        mProducts = products;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mId);
        dest.writeString(mDealId);
        dest.writeString(mTypeId);
        dest.writeString(mTypeName);
        dest.writeTypedList(mProducts);
        dest.writeTypedList(mSourceProducts);
    }

    public List<ProductItemModel> getSourceProducts() {
        return mSourceProducts;
    }

    public void setSourceProducts(List<ProductItemModel> mSourceProducts) {
        this.mSourceProducts = mSourceProducts;
    }

    public boolean isSelected() {
        return mIsSelected;
    }

    public void setSelected(boolean mIsSelected) {
        this.mIsSelected = mIsSelected;
    }
}
