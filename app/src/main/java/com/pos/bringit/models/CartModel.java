package com.pos.bringit.models;

import java.util.List;

public class CartModel {
    private String id;
    private String cart_id;
    private String type;
    private String name;
    private String price;
    private String objectId;
    private String fatherId;
    private String toppingLocation;
    private List<CartFilling> item_filling;


    public CartModel(String id, int position, String type, String name, String price, String objectId) {
        this.id = id;
        this.cart_id = "item_" + position;
        this.type = type;
        this.name = name;
        this.price = price;
        this.objectId = objectId;
    }

    public CartModel(String type, String objectId, String fatherId, String toppingLocation) {
        this.type = type;
        this.objectId = objectId;
        this.fatherId = fatherId;
        this.toppingLocation = toppingLocation;
    }

    public CartModel(String type, String objectId) {
        this.type = type;
        this.objectId = objectId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getFatherId() {
        return fatherId;
    }

    public void setFatherId(String fatherId) {
        this.fatherId = fatherId;
    }

    public String getToppingLocation() {
        return toppingLocation;
    }

    public void setToppingLocation(String toppingLocation) {
        this.toppingLocation = toppingLocation;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCartId() {
        return cart_id;
    }

    public void setCartId(int position) {
        this.cart_id = "item_" + position;
    }

    public List<CartFilling> getItem_filling() {
        return item_filling;
    }

    public void setItem_filling(List<CartFilling> item_filling) {
        this.item_filling = item_filling;
    }

    class CartFilling {
        private String name;
        private String price;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }


        public String getPrise() {
            return price;
        }

        public void setPrise(String price) {
            this.price = price;
        }
    }
}
