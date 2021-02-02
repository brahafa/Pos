package com.pos.bringit.models;

import com.google.gson.annotations.SerializedName;

public class UserDetailsModel {


    @SerializedName("f_name")
    private String mName;
    @SerializedName("l_name")
    private String mLastName;
    @SerializedName("phone")
    private String mPhone;
    @SerializedName("notes")
    private NotesBean mNotes;
    @SerializedName("address")
    private AddressModel mAddress;

    public UserDetailsModel() {
        this.mName = "";
        this.mLastName = "";
        this.mPhone = "";
        this.mNotes = new NotesBean();
        this.mAddress = new AddressModel();
    }

    public String getName() {
        return mName;
    }

    public void setName(String fName) {
        mName = fName;
    }

    public String getLastName() {
        return mLastName;
    }

    public void setLastName(String lName) {
        mLastName = lName;
    }

    public String getPhone() {
        return mPhone;
    }

    public void setPhone(String phone) {
        mPhone = phone;
    }

    public NotesBean getNotes() {
        return mNotes;
    }

    public void setNotes(NotesBean notes) {
        mNotes = notes;
    }

    public AddressModel getAddress() {
        return mAddress;
    }

    public void setAddress(AddressModel address) {
        mAddress = address;
    }

    public static class NotesBean {
        @SerializedName("order")
        private String mOrder;
        @SerializedName("delivery")
        private String mDelivery;

        public NotesBean() {
            this.mOrder = "";
            this.mDelivery = "";
        }

        public String getOrder() {
            if(mOrder == null) return "";
            return mOrder;
        }

        public void setOrder(String order) {
            mOrder = order;
        }

        public String getDelivery() {
            if(mDelivery == null) return "";
            return mDelivery;
        }

        public void setDelivery(String mDelivery) {
            this.mDelivery = mDelivery;
        }
    }


}
