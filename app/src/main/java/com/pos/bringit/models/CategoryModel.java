package com.pos.bringit.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

public class CategoryModel implements Parcelable, Cloneable {

    @SerializedName("id")
    private String mId;
    @SerializedName("product_id")
    private String mProductId;
    @SerializedName("name")
    private String mName;
    @SerializedName("list_order")
    private int mListOrder;
    @SerializedName("products_limit")
    private int mProductsLimit;
    @SerializedName("products_fixed_price")
    private int mProductsFixedPrice;
    @SerializedName("category_has_fixed_price")
    private boolean mCategoryHasFixedPrice;
    @SerializedName("fixed_price")
    private double mFixedPrice;
    @SerializedName("is_topping_divided")
    private boolean mIsToppingDivided;
    @SerializedName("is_mandatory")
    private boolean mIsMandatory;
    @SerializedName("products")
    private List<InnerProductsModel> mProducts;

    protected CategoryModel(Parcel in) {
        mId = in.readString();
        mProductId = in.readString();
        mName = in.readString();
        mListOrder = in.readInt();
        mProductsLimit = in.readInt();
        mProductsFixedPrice = in.readInt();
        mCategoryHasFixedPrice = in.readByte() != 0;
        mFixedPrice = in.readDouble();
        mIsToppingDivided = in.readByte() != 0;
        mIsMandatory = in.readByte() != 0;
        mProducts = in.createTypedArrayList(InnerProductsModel.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mId);
        dest.writeString(mProductId);
        dest.writeString(mName);
        dest.writeInt(mListOrder);
        dest.writeInt(mProductsLimit);
        dest.writeInt(mProductsFixedPrice);
        dest.writeByte((byte) (mCategoryHasFixedPrice ? 1 : 0));
        dest.writeDouble(mFixedPrice);
        dest.writeByte((byte) (mIsToppingDivided ? 1 : 0));
        dest.writeByte((byte) (mIsMandatory ? 1 : 0));
        dest.writeTypedList(mProducts);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CategoryModel> CREATOR = new Creator<CategoryModel>() {
        @Override
        public CategoryModel createFromParcel(Parcel in) {
            return new CategoryModel(in);
        }

        @Override
        public CategoryModel[] newArray(int size) {
            return new CategoryModel[size];
        }
    };

    @NonNull
    public CategoryModel clone() {

        CategoryModel newModel = null;
        try {
            newModel = (CategoryModel) super.clone();

            newModel.mProducts = new ArrayList<>();
            for (InnerProductsModel temp : this.mProducts) {
                newModel.mProducts.add(temp.clone());
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

    public String getProductId() {
        return mProductId;
    }

    public void setProductId(String productId) {
        mProductId = productId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public int getListOrder() {
        return mListOrder;
    }

    public void setListOrder(int listOrder) {
        mListOrder = listOrder;
    }

    public int getProductsLimit() {
        return mProductsLimit;
    }

    public void setProductsLimit(int productsLimit) {
        mProductsLimit = productsLimit;
    }

    public int getProductsFixedPrice() {
        return mProductsFixedPrice;
    }

    public void setProductsFixedPrice(int productsFixedPrice) {
        mProductsFixedPrice = productsFixedPrice;
    }

    public boolean getCategoryHasFixedPrice() {
        return mCategoryHasFixedPrice;
    }

    public void setCategoryHasFixedPrice(boolean categoryHasFixedPrice) {
        mCategoryHasFixedPrice = categoryHasFixedPrice;
    }

    public double getFixedPrice() {
        return mFixedPrice;
    }

    public void setFixedPrice(double fixedPrice) {
        mFixedPrice = fixedPrice;
    }

    public boolean isToppingDivided() {
        return mIsToppingDivided;
    }

    public void setIsToppingDivided(boolean isToppingDivided) {
        mIsToppingDivided = isToppingDivided;
    }

    public boolean isMandatory() {
        return mIsMandatory;
    }

    public void setIsMandatory(boolean isMandatory) {
        mIsMandatory = isMandatory;
    }

    public List<InnerProductsModel> getProducts() {
        return mProducts;
    }

    public void setProducts(List<InnerProductsModel> products) {
        mProducts = products;
    }

}
