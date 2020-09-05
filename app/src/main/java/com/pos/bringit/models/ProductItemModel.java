package com.pos.bringit.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

public class ProductItemModel implements Parcelable, Cloneable {

    @SerializedName("id")
    private String mId;
    @SerializedName("type_id")
    private String mTypeId;
    @SerializedName("name")
    private String mName;
    @SerializedName("description")
    private String mDescription;
    @SerializedName("delivery_price")
    private String mDeliveryPrice;
    @SerializedName("not_delivery_price")
    private String mNotDeliveryPrice;
    @SerializedName("picture")
    private String mPicture;
    @SerializedName("business_id")
    private String mBusinessId;
    @SerializedName("in_inventory")
    private String mInInventory;
    @SerializedName("shape")
    private String mShape;
    @SerializedName("type_name")
    private String mTypeName;
    @SerializedName("folder_id")
    private String mFolderId;
    @SerializedName("categories")
    private List<CategoryModel> mCategories = new ArrayList<>();
    @SerializedName("items")
    private List<DealItemModel> mDealItems = new ArrayList<>();

    private transient List<CategoryModel> mSourceCategories = new ArrayList<>();
    private transient List<DealItemModel> mSourceDealItems = new ArrayList<>();

    private transient boolean mIsSelected;


    protected ProductItemModel(Parcel in) {
        mId = in.readString();
        mTypeId = in.readString();
        mName = in.readString();
        mDescription = in.readString();
        mDeliveryPrice = in.readString();
        mNotDeliveryPrice = in.readString();
        mPicture = in.readString();
        mBusinessId = in.readString();
        mInInventory = in.readString();
        mShape = in.readString();
        mTypeName = in.readString();
        mFolderId = in.readString();
        mCategories = in.createTypedArrayList(CategoryModel.CREATOR);
        mDealItems = in.createTypedArrayList(DealItemModel.CREATOR);

        mSourceCategories = in.createTypedArrayList(CategoryModel.CREATOR);
        mSourceDealItems = in.createTypedArrayList(DealItemModel.CREATOR);
    }

    public static final Creator<ProductItemModel> CREATOR = new Creator<ProductItemModel>() {
        @Override
        public ProductItemModel createFromParcel(Parcel in) {
            return new ProductItemModel(in);
        }

        @Override
        public ProductItemModel[] newArray(int size) {
            return new ProductItemModel[size];
        }
    };

    public ProductItemModel() {
    }

    public ProductItemModel(FolderItemModel folderItem) {
        this.mId = folderItem.getId();
        this.mTypeId = folderItem.getTypeId();
        this.mName = folderItem.getName();
        this.mDescription = folderItem.getDescription();
        this.mDeliveryPrice = String.valueOf(folderItem.getDeliveryPrice());
        this.mNotDeliveryPrice = String.valueOf(folderItem.getNotDeliveryPrice());
        this.mPicture = folderItem.getPicture();
        this.mBusinessId = folderItem.getBusinessId();
        this.mInInventory = folderItem.getInInventory();
        this.mShape = folderItem.getShape();
        this.mTypeName = folderItem.getTypeName();
        this.mFolderId = folderItem.getFolderId();
        this.mCategories = folderItem.getCategories();
        this.mDealItems = folderItem.getDealItems();
        this.mIsSelected = folderItem.isSelected();

        for (CategoryModel category : folderItem.getCategories())
            this.mSourceCategories.add(category.clone());

        for (DealItemModel dealItem : folderItem.getDealItems())
            this.mSourceDealItems.add(dealItem.clone());
    }


    @NonNull
    public ProductItemModel clone() {

        ProductItemModel newModel = null;
        try {
            newModel = (ProductItemModel) super.clone();


            newModel.mCategories = new ArrayList<>();
            for (CategoryModel temp : this.mCategories) {
                newModel.mCategories.add(temp.clone());
            }
            newModel.mDealItems = new ArrayList<>();
            for (DealItemModel temp : this.mDealItems) {
                newModel.mDealItems.add(temp.clone());
            }

            newModel.mSourceCategories = new ArrayList<>();
            for (CategoryModel temp : this.mSourceCategories) {
                newModel.mSourceCategories.add(temp.clone());
            }
            newModel.mSourceDealItems = new ArrayList<>();
            for (DealItemModel temp : this.mSourceDealItems) {
                newModel.mSourceDealItems.add(temp.clone());
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

    public String getInInventory() {
        return mInInventory;
    }

    public void setInInventory(String inInventory) {
        mInInventory = inInventory;
    }

    public double getDeliveryPrice() {
        return Double.parseDouble(mDeliveryPrice);
    }

    public void setDeliveryPrice(double deliveryPrice) {
        mDeliveryPrice = String.valueOf(deliveryPrice);
    }

    public String getTypeName() {
        return mTypeName;
    }

    public void setTypeName(String objectType) {
        mTypeName = objectType;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getShape() {
        return mShape;
    }

    public void setShape(String mShape) {
        this.mShape = mShape;
    }

    public String getTypeId() {
        return mTypeId;
    }

    public void setTypeId(String mTypeId) {
        this.mTypeId = mTypeId;
    }

    public double getNotDeliveryPrice() {
        return Double.parseDouble(mNotDeliveryPrice);
    }

    public void setNotDeliveryPrice(double mNotDeliveryPrice) {
        this.mNotDeliveryPrice = String.valueOf(mNotDeliveryPrice);
    }

    public String getPicture() {
        return mPicture;
    }

    public void setPicture(String mPicture) {
        this.mPicture = mPicture;
    }

    public String getBusinessId() {
        return mBusinessId;
    }

    public void setBusinessId(String mBusinessId) {
        this.mBusinessId = mBusinessId;
    }

    public String getFolderId() {
        return mFolderId;
    }

    public void setFolderId(String mFolderId) {
        this.mFolderId = mFolderId;
    }

    public List<CategoryModel> getCategories() {
        return mCategories;
    }

    public void setCategories(List<CategoryModel> mCategories) {
        this.mCategories = mCategories;
    }

    public List<DealItemModel> getDealItems() {
        return mDealItems;
    }

    public void setDealItems(List<DealItemModel> mDealItems) {
        this.mDealItems = mDealItems;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mId);
        dest.writeString(mTypeId);
        dest.writeString(mName);
        dest.writeString(mDescription);
        dest.writeString(mDeliveryPrice);
        dest.writeString(mNotDeliveryPrice);
        dest.writeString(mPicture);
        dest.writeString(mBusinessId);
        dest.writeString(mInInventory);
        dest.writeString(mShape);
        dest.writeString(mTypeName);
        dest.writeString(mFolderId);
        dest.writeTypedList(mCategories);
        dest.writeTypedList(mDealItems);

        dest.writeTypedList(mSourceCategories);
        dest.writeTypedList(mSourceDealItems);
    }

    public boolean isSelected() {
        return mIsSelected;
    }

    public void setSelected(boolean mIsSelected) {
        this.mIsSelected = mIsSelected;
    }

    public List<CategoryModel> getSourceCategories() {
        return mSourceCategories;
    }

    public void setSourceCategories(List<CategoryModel> mSourceCategories) {
        this.mSourceCategories = mSourceCategories;
    }

    public List<DealItemModel> getSourceDealItems() {
        return mSourceDealItems;
    }

    public void setSourceDealItems(List<DealItemModel> mSourceDealItems) {
        this.mSourceDealItems = mSourceDealItems;
    }

}
