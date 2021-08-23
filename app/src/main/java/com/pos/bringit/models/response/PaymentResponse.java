package com.pos.bringit.models.response;

import com.google.gson.annotations.SerializedName;

public class PaymentResponse {

    @SerializedName("status")
    private boolean mStatus;
    @SerializedName("message")
    private String mMessage;
    @SerializedName("payment_hash")
    private String mPaymentHash;

    public boolean isStatus() {
        return mStatus;
    }

    public void setStatus(boolean status) {
        mStatus = status;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String mMessage) {
        this.mMessage = mMessage;
    }

    public String getPaymentHash() {
        return mPaymentHash;
    }

    public void setPaymentHash(String mPaymentHash) {
        this.mPaymentHash = mPaymentHash;
    }
}
