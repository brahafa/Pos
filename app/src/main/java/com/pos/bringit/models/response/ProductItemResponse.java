package com.pos.bringit.models.response;

import com.google.gson.annotations.SerializedName;
import com.pos.bringit.models.ProductItemModel;

public class ProductItemResponse {

    @SerializedName("status")
    private boolean mStatus;
    @SerializedName("product")
    private ProductItemModel mProduct;

    public boolean isStatus() {
        return mStatus;
    }

    public void setStatus(boolean status) {
        mStatus = status;
    }

    public ProductItemModel getProduct() {
        return mProduct;
    }

    public void setProduct(ProductItemModel mProduct) {
        this.mProduct = mProduct;
    }
}
