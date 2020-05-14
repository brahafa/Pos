package com.pos.bringit.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderDetailsModel {


    @SerializedName("order_id")
    private String mOrderId;
    @SerializedName("client_id")
    private String mClientId;
    @SerializedName("address")
    private String mAddress;
    @SerializedName("added_by")
    private String mAddedBy;
    @SerializedName("action_time")
    private String mActionTime;
    @SerializedName("order_time")
    private String mOrderTime;
    @SerializedName("is_delivery")
    private String mIsDelivery;
    @SerializedName("order_total")
    private String mOrderTotal;
    @SerializedName("total_paid")
    private String mTotalPaid;
    @SerializedName("delivery_price")
    private String mDeliveryPrice;
    @SerializedName("status")
    private String mStatus;
    @SerializedName("order_notes")
    private String mOrderNotes;
    @SerializedName("f_name")
    private String mFName;
    @SerializedName("l_name")
    private String mLName;
    @SerializedName("phone")
    private String mPhone;
    @SerializedName("payment_name")
    private String mPaymentName;
    @SerializedName("payment_display")
    private String mPaymentDisplay;
    @SerializedName("added_by_system")
    private String mAddedBySystem;
    @SerializedName("pizza_count")
    private String mPizzaCount;
    @SerializedName("order_items")
    private List<OrderItemsBean> mOrderItems;

    public String getOrderId() {
        return mOrderId;
    }

    public void setOrderId(String orderId) {
        mOrderId = orderId;
    }

    public String getClientId() {
        return mClientId;
    }

    public void setClientId(String clientId) {
        mClientId = clientId;
    }

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String address) {
        mAddress = address;
    }

    public String getAddedBy() {
        return mAddedBy;
    }

    public void setAddedBy(String addedBy) {
        mAddedBy = addedBy;
    }

    public String getActionTime() {
        return mActionTime;
    }

    public void setActionTime(String actionTime) {
        mActionTime = actionTime;
    }

    public String getOrderTime() {
        return mOrderTime;
    }

    public void setOrderTime(String orderTime) {
        mOrderTime = orderTime;
    }

    public String getIsDelivery() {
        return mIsDelivery;
    }

    public void setIsDelivery(String isDelivery) {
        mIsDelivery = isDelivery;
    }

    public String getOrderTotal() {
        return mOrderTotal;
    }

    public void setOrderTotal(String orderTotal) {
        mOrderTotal = orderTotal;
    }

    public String getTotalPaid() {
        return mTotalPaid;
    }

    public void setTotalPaid(String totalPaid) {
        mTotalPaid = totalPaid;
    }

    public String getDeliveryPrice() {
        return mDeliveryPrice;
    }

    public void setDeliveryPrice(String deliveryPrice) {
        mDeliveryPrice = deliveryPrice;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

    public String getOrderNotes() {
        return mOrderNotes;
    }

    public void setOrderNotes(String orderNotes) {
        mOrderNotes = orderNotes;
    }

    public String getFName() {
        return mFName;
    }

    public void setFName(String fName) {
        mFName = fName;
    }

    public String getLName() {
        return mLName;
    }

    public void setLName(String lName) {
        mLName = lName;
    }

    public String getPhone() {
        return mPhone;
    }

    public void setPhone(String phone) {
        mPhone = phone;
    }

    public String getPaymentName() {
        return mPaymentName;
    }

    public void setPaymentName(String paymentName) {
        mPaymentName = paymentName;
    }

    public String getPaymentDisplay() {
        return mPaymentDisplay;
    }

    public void setPaymentDisplay(String paymentDisplay) {
        mPaymentDisplay = paymentDisplay;
    }

    public String getAddedBySystem() {
        return mAddedBySystem;
    }

    public void setAddedBySystem(String addedBySystem) {
        mAddedBySystem = addedBySystem;
    }

    public String getPizzaCount() {
        return mPizzaCount;
    }

    public void setPizzaCount(String pizzaCount) {
        mPizzaCount = pizzaCount;
    }

    public List<OrderItemsBean> getOrderItems() {
        return mOrderItems;
    }

    public void setOrderItems(List<OrderItemsBean> orderItems) {
        mOrderItems = orderItems;
    }

    public static class OrderItemsBean {
        @SerializedName("item_id")
        private String mItemId;
        @SerializedName("item_type")
        private String mItemType;
        @SerializedName("father_id")
        private String mFatherId;
        @SerializedName("location")
        private String mLocation;
        @SerializedName("cart_id")
        private String mCartId;
        @SerializedName("price")
        private int mPrice;
        @SerializedName("is_compensation")
        private String mIsCompensation;
        @SerializedName("item_filling")
        private List<CartFillingModel> mItemFilling;
        @SerializedName("change_type")
        private String mChangeType;
        @SerializedName("item_name")
        private String mItemName;
        @SerializedName("name")
        private String mName;
        @SerializedName("item_picture")
        private String mItemPicture;
        @SerializedName("description")
        private String mDescription;
        @SerializedName("shape")
        private String mShape;

        public String getItemId() {
            return mItemId;
        }

        public void setItemId(String itemId) {
            mItemId = itemId;
        }

        public String getItemType() {
            return mItemType;
        }

        public void setItemType(String itemType) {
            mItemType = itemType;
        }

        public String getFatherId() {
            return mFatherId;
        }

        public void setFatherId(String fatherId) {
            mFatherId = fatherId;
        }

        public String getLocation() {
            return mLocation;
        }

        public void setLocation(String location) {
            mLocation = location;
        }

        public String getCartId() {
            return mCartId;
        }

        public void setCartId(String cartId) {
            mCartId = cartId;
        }

        public int getPrice() {
            return mPrice;
        }

        public void setPrice(int price) {
            mPrice = price;
        }

        public String getIsCompensation() {
            return mIsCompensation;
        }

        public void setIsCompensation(String isCompensation) {
            mIsCompensation = isCompensation;
        }

        public List<CartFillingModel> getItemFilling() {
            return mItemFilling;
        }

        public void setItemFilling(List<CartFillingModel> itemFilling) {
            mItemFilling = itemFilling;
        }

        public String getChangeType() {
            return mChangeType;
        }

        public void setChangeType(String changeType) {
            mChangeType = changeType;
        }

        public String getItemName() {
            return mItemName;
        }

        public void setItemName(String itemName) {
            mItemName = itemName;
        }

        public String getName() {
            return mName;
        }

        public void setName(String name) {
            mName = name;
        }

        public String getItemPicture() {
            return mItemPicture;
        }

        public void setItemPicture(String itemPicture) {
            mItemPicture = itemPicture;
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

        public void setShape(String shape) {
            mShape = shape;
        }
    }
}
