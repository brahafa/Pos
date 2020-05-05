package com.pos.bringit.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.Collections;
import java.util.List;

public class FolderItemModel {
    @SerializedName("color")
    private String mColor;
    @SerializedName("id")
    private String mId;
    @SerializedName("position")
    private String mPosition;
    @SerializedName("father_id")
    private String mFatherId;
    @SerializedName("in_inventory")
    private String mInInventory;
    @SerializedName("delivery_price")
    private int mDeliveryPrice;
    @SerializedName("pickup_price")
    private int mPickupPrice;
    @SerializedName("topping_price_on_slice")
    private Object mToppingPriceOnSlice;
    @SerializedName("object_type")
    private String mObjectType;
    @SerializedName("name")
    private String mName;
    @SerializedName("object_id")
    private String mObjectId;
    @SerializedName("filling")
    private Object mFilling;
    @SerializedName("isShortcut")
    private boolean mIsShortcut;
    @SerializedName("valueJson")
    private DealValuesModel mValueJson;
    @SerializedName("uniq_for_business_id")
    private String mUniqForBusinessId;
    @SerializedName("type")
    private String mType;
    @SerializedName("description")
    private String mDescription;

    public String getColor() {
        return mColor;
    }

    public void setColor(String color) {
        mColor = color;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getPosition() {
        return mPosition;
    }

    public void setPosition(String position) {
        mPosition = position;
    }

    public String getFatherId() {
        return mFatherId;
    }

    public void setFatherId(String fatherId) {
        mFatherId = fatherId;
    }

    public String getInInventory() {
        return mInInventory;
    }

    public void setInInventory(String inInventory) {
        mInInventory = inInventory;
    }

    public int getDeliveryPrice() {
        return mDeliveryPrice;
    }

    public void setDeliveryPrice(int deliveryPrice) {
        mDeliveryPrice = deliveryPrice;
    }

    public int getPickupPrice() {
        return mPickupPrice;
    }

    public void setPickupPrice(int pickupPrice) {
        mPickupPrice = pickupPrice;
    }

    public Object getToppingPriceOnSlice() {
        return mToppingPriceOnSlice;
    }

    public void setToppingPriceOnSlice(Object toppingPriceOnSlice) {
        mToppingPriceOnSlice = toppingPriceOnSlice;
    }

    public String getObjectType() {
        return mObjectType;
    }

    public void setObjectType(String objectType) {
        mObjectType = objectType;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getObjectId() {
        return mObjectId;
    }

    public void setObjectId(String objectId) {
        mObjectId = objectId;
    }

    public Object getFilling() {
        return mFilling;
    }

    public void setFilling(Object filling) {
        mFilling = filling;
    }

    public boolean isIsShortcut() {
        return mIsShortcut;
    }

    public void setIsShortcut(boolean isShortcut) {
        mIsShortcut = isShortcut;
    }

    public DealValuesModel getValueJson() {
        return mValueJson;
    }

    public void setValueJson(DealValuesModel valueJson) {
        mValueJson = valueJson;
    }

    public String getUniqForBusinessId() {
        return mUniqForBusinessId;
    }

    public void setUniqForBusinessId(String uniqForBusinessId) {
        mUniqForBusinessId = uniqForBusinessId;
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public class DealValuesModel implements Parcelable {
        @SerializedName("food")
        private List<FoodBean> mFood;
        @SerializedName("topping")
        private List<ToppingBean> mTopping;
        @SerializedName("drink")
        private List<DrinkBean> mDrink;
        @SerializedName("additionalOffer")
        private List<AdditionalOfferBean> mAdditionalOffer;

        protected DealValuesModel(Parcel in) {
            mFood = in.readParcelable(FoodBean.class.getClassLoader());
            mTopping = in.readParcelable(ToppingBean.class.getClassLoader());
            mDrink = in.readParcelable(DrinkBean.class.getClassLoader());
            mAdditionalOffer = in.readParcelable(AdditionalOfferBean.class.getClassLoader());
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeTypedList(mFood);
            dest.writeTypedList(mTopping);
            dest.writeTypedList(mDrink);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public final Creator<DealValuesModel> CREATOR = new Creator<DealValuesModel>() {
            @Override
            public DealValuesModel createFromParcel(Parcel in) {
                return new DealValuesModel(in);
            }

            @Override
            public DealValuesModel[] newArray(int size) {
                return new DealValuesModel[size];
            }
        };

        public List<FoodBean> getFood() {
            return mFood != null ? mFood : Collections.emptyList();
        }

        public void setFood(List<FoodBean> food) {
            mFood = food;
        }

        public List<ToppingBean> getTopping() {
            return mTopping != null ? mTopping : Collections.emptyList();
        }

        public void setTopping(List<ToppingBean> topping) {
            mTopping = topping;
        }

        public List<DrinkBean> getDrink() {
            return mDrink != null ? mDrink : Collections.emptyList();
        }

        public void setDrink(List<DrinkBean> drink) {
            mDrink = drink;
        }

        public List<AdditionalOfferBean> getAdditionalOffer() {
            return mAdditionalOffer != null ? mAdditionalOffer : Collections.emptyList();
        }

        public void setAdditionalOffer(List<AdditionalOfferBean> additionalOffer) {
            mAdditionalOffer = additionalOffer;
        }

        public class FoodBean implements Parcelable {
            @SerializedName("type")
            private String mType;
            @SerializedName("quantity")
            private String mQuantity;
            @SerializedName("shape")
            private String mShape;

            protected FoodBean(Parcel in) {
                mType = in.readString();
                mQuantity = in.readString();
                mShape = in.readString();
            }

            public final Creator<FoodBean> CREATOR = new Creator<FoodBean>() {
                @Override
                public FoodBean createFromParcel(Parcel in) {
                    return new FoodBean(in);
                }

                @Override
                public FoodBean[] newArray(int size) {
                    return new FoodBean[size];
                }
            };

            public String getType() {
                return mType;
            }

            public void setType(String type) {
                mType = type;
            }

            public int getQuantity() {
                return Integer.parseInt(mQuantity);
            }

            public void setQuantity(String quantity) {
                mQuantity = quantity;
            }

            public String getShape() {
                return mShape;
            }

            public void setShape(String shape) {
                mShape = shape;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(mType);
                dest.writeString(mQuantity);
                dest.writeString(mShape);
            }
        }

        public class ToppingBean implements Parcelable {
            @SerializedName("category")
            private String mCategory;
            @SerializedName("quantity")
            private String mQuantity;

            protected ToppingBean(Parcel in) {
                mCategory = in.readString();
                mQuantity = in.readString();
            }

            public final Creator<ToppingBean> CREATOR = new Creator<ToppingBean>() {
                @Override
                public ToppingBean createFromParcel(Parcel in) {
                    return new ToppingBean(in);
                }

                @Override
                public ToppingBean[] newArray(int size) {
                    return new ToppingBean[size];
                }
            };

            public String getCategory() {
                return mCategory;
            }

            public void setCategory(String category) {
                mCategory = category;
            }

            public String getQuantity() {
                return mQuantity;
            }

            public void setQuantity(String quantity) {
                mQuantity = quantity;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(mCategory);
                dest.writeString(mQuantity);
            }
        }

        public class DrinkBean implements Parcelable {
            @SerializedName("type")
            private String mType;
            @SerializedName("quantity")
            private String mQuantity;

            protected DrinkBean(Parcel in) {
                mType = in.readString();
                mQuantity = in.readString();
            }

            public final Creator<DrinkBean> CREATOR = new Creator<DrinkBean>() {
                @Override
                public DrinkBean createFromParcel(Parcel in) {
                    return new DrinkBean(in);
                }

                @Override
                public DrinkBean[] newArray(int size) {
                    return new DrinkBean[size];
                }
            };

            public String getType() {
                return mType;
            }

            public void setType(String type) {
                mType = type;
            }

            public int getQuantity() {
                return Integer.parseInt(mQuantity);
            }

            public void setQuantity(String quantity) {
                mQuantity = quantity;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(mType);
                dest.writeString(mQuantity);
            }
        }

        public class AdditionalOfferBean implements Parcelable {
            @SerializedName("type")
            private String mType;
            @SerializedName("name")
            private String mName;
            @SerializedName("quantity")
            private String mQuantity;

            protected AdditionalOfferBean(Parcel in) {
                mType = in.readString();
                mName = in.readString();
                mQuantity = in.readString();
            }

            public final Creator<AdditionalOfferBean> CREATOR = new Creator<AdditionalOfferBean>() {
                @Override
                public AdditionalOfferBean createFromParcel(Parcel in) {
                    return new AdditionalOfferBean(in);
                }

                @Override
                public AdditionalOfferBean[] newArray(int size) {
                    return new AdditionalOfferBean[size];
                }
            };

            public String getType() {
                return mType;
            }

            public void setType(String type) {
                mType = type;
            }

            public String getName() {
                return mName;
            }

            public void setName(String name) {
                mName = name;
            }

            public int getQuantity() {
                return Integer.parseInt(mQuantity);
            }

            public void setQuantity(String quantity) {
                mQuantity = quantity;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(mType);
                dest.writeString(mName);
                dest.writeString(mQuantity);
            }
        }
    }
}
