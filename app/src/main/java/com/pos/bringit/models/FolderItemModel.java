package com.pos.bringit.models;

import com.google.gson.annotations.SerializedName;

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

    public class DealValuesModel {
        @SerializedName("food")
        private List<FoodBean> mFood;
        @SerializedName("topping")
        private List<ToppingBean> mTopping;
        @SerializedName("drink")
        private List<DrinkBean> mDrink;
        @SerializedName("additionalOffer")
        private List<AdditionalOfferBean> mAdditionalOffer;

        public List<FoodBean> getFood() {
            return mFood;
        }

        public void setFood(List<FoodBean> food) {
            mFood = food;
        }

        public List<ToppingBean> getTopping() {
            return mTopping;
        }

        public void setTopping(List<ToppingBean> topping) {
            mTopping = topping;
        }

        public List<DrinkBean> getDrink() {
            return mDrink;
        }

        public void setDrink(List<DrinkBean> drink) {
            mDrink = drink;
        }

        public List<AdditionalOfferBean> getAdditionalOffer() {
            return mAdditionalOffer;
        }

        public void setAdditionalOffer(List<AdditionalOfferBean> additionalOffer) {
            mAdditionalOffer = additionalOffer;
        }

        public class FoodBean {
            @SerializedName("type")
            private String mType;
            @SerializedName("quantity")
            private String mQuantity;
            @SerializedName("shape")
            private String mShape;

            public String getType() {
                return mType;
            }

            public void setType(String type) {
                mType = type;
            }

            public String getQuantity() {
                return mQuantity;
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
        }

        public class ToppingBean {
            @SerializedName("category")
            private String mCategory;
            @SerializedName("quantity")
            private String mQuantity;

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
        }

        public class DrinkBean {
            @SerializedName("type")
            private String mType;
            @SerializedName("quantity")
            private String mQuantity;

            public String getType() {
                return mType;
            }

            public void setType(String type) {
                mType = type;
            }

            public String getQuantity() {
                return mQuantity;
            }

            public void setQuantity(String quantity) {
                mQuantity = quantity;
            }
        }

        public class AdditionalOfferBean {
            @SerializedName("type")
            private String mType;
            @SerializedName("name")
            private String mName;
            @SerializedName("quantity")
            private String mQuantity;

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

            public String getQuantity() {
                return mQuantity;
            }

            public void setQuantity(String quantity) {
                mQuantity = quantity;
            }
        }
    }
}
