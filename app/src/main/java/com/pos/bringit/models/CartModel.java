package com.pos.bringit.models;

public class CartModel {
    private String type;
    private String objectId;
    private String fatherId;
    private String toppingLocation;

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
}
