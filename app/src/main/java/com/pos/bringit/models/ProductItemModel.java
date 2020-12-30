package com.pos.bringit.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ProductItemModel implements Parcelable, Cloneable {

    @SerializedName("id")
    private String mId;
    @SerializedName("type_id")
    private String mTypeId;
    @SerializedName("order_id")
    private String mOrderId;
    @SerializedName("source_product_id")
    private String mSourceProductId;
    @SerializedName("name")
    private String mName;
    @SerializedName("description")
    private String mDescription;
    @SerializedName("price")
    private String mPrice;
    @SerializedName("delivery_price")
    private String mDeliveryPrice;
    @SerializedName("not_delivery_price")
    private String mNotDeliveryPrice;
    @SerializedName("picture")
    private String mPicture;
    @SerializedName("business_id")
    private transient String mBusinessId;
    @SerializedName("in_inventory")
    private String mInInventory;
    @SerializedName("shape")
    private String mShape;
    @SerializedName("type_name")
    private String mTypeName;
    @SerializedName("folder_id")
    private String mFolderId;
    @SerializedName("is_deleted")
    private String mIsDeleted;
    @SerializedName("is_new")
    private String mIsNew;
    @SerializedName("is_canceled")
    private String mIsCanceled;
    @SerializedName("categories")
    private List<CategoryModel> mCategories = new ArrayList<>();
    @SerializedName("items")
    private List<DealItemModel> mDealItems = new ArrayList<>();
    @SerializedName("products")
    private List<ProductItemModel> mProducts = new ArrayList<>();

    @SerializedName("changeType")
    private String changeType = "";

    private transient List<CategoryModel> mSourceCategories = new ArrayList<>();
    private transient List<DealItemModel> mSourceDealItems = new ArrayList<>();

    private transient boolean mIsSelected;


    public ProductItemModel() {
    }

    public ProductItemModel(FolderItemModel folderItem) {
        this.mId = folderItem.getId();
        this.mTypeId = folderItem.getTypeId();
        this.mName = folderItem.getName();
        this.mDescription = folderItem.getDescription();
        this.mPrice = String.valueOf(folderItem.getPrice());
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
        this.changeType = folderItem.getChangeType();
        for (ProductItemModel productItemModel : folderItem.getProducts())
            this.mProducts.add(productItemModel.clone());

        for (CategoryModel category : folderItem.getCategories())
            this.mSourceCategories.add(category.clone());

        for (DealItemModel dealItem : folderItem.getDealItems())
            this.mSourceDealItems.add(dealItem.clone());
    }

    protected ProductItemModel(Parcel in) {
        mId = in.readString();
        mTypeId = in.readString();
        mSourceProductId = in.readString();
        mOrderId = in.readString();
        mName = in.readString();
        mDescription = in.readString();
        mPrice = in.readString();
        mDeliveryPrice = in.readString();
        mNotDeliveryPrice = in.readString();
        mPicture = in.readString();
        mInInventory = in.readString();
        mShape = in.readString();
        mTypeName = in.readString();
        mFolderId = in.readString();
        mIsDeleted = in.readString();
        mIsNew = in.readString();
        mIsCanceled = in.readString();
        mCategories = in.createTypedArrayList(CategoryModel.CREATOR);
        mDealItems = in.createTypedArrayList(DealItemModel.CREATOR);
        mProducts = in.createTypedArrayList(ProductItemModel.CREATOR);
        changeType = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mId);
        dest.writeString(mTypeId);
        dest.writeString(mSourceProductId);
        dest.writeString(mOrderId);
        dest.writeString(mName);
        dest.writeString(mDescription);
        dest.writeString(mPrice);
        dest.writeString(mDeliveryPrice);
        dest.writeString(mNotDeliveryPrice);
        dest.writeString(mPicture);
        dest.writeString(mInInventory);
        dest.writeString(mShape);
        dest.writeString(mTypeName);
        dest.writeString(mFolderId);
        dest.writeString(mIsDeleted);
        dest.writeString(mIsNew);
        dest.writeString(mIsCanceled);
        dest.writeTypedList(mCategories);
        dest.writeTypedList(mDealItems);
        dest.writeTypedList(mProducts);
        dest.writeString(changeType);
    }

    @Override
    public int describeContents() {
        return 0;
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

            newModel.mProducts = new ArrayList<>();
            for (ProductItemModel temp : this.mProducts) {
                newModel.mProducts.add(temp.clone());
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

    public List<ProductItemModel> getProducts() {
        return mProducts;
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

    public String getChangeType() {
        return changeType;
    }

    public void setChangeType(String changeType) {
        this.changeType = changeType;
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
        if (mNotDeliveryPrice == null) {
            return Double.parseDouble(mPrice);
        }
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

    public double getPrice() {
        return Double.parseDouble(mPrice);
    }

    public void setPrice(double mPrice) {
        this.mPrice = String.valueOf(mPrice);
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

    public String getSourceProductId() {
        return mSourceProductId;
    }

    public void setSourceProductId(String mSourceProductId) {
        this.mSourceProductId = mSourceProductId;
    }

    public String getOrderId() {
        return mOrderId;
    }

    public void setOrderId(String mOrderId) {
        this.mOrderId = mOrderId;
    }
}
