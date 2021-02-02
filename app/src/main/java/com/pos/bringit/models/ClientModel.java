package com.pos.bringit.models;

import com.google.gson.annotations.SerializedName;

public class ClientModel {
        @SerializedName("f_name")
        private String mFName;
        @SerializedName("l_name")
        private String mLName;
        @SerializedName("phone")
        private String mPhone;
        @SerializedName("address")
        private AddressModel mAddress;

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

        public AddressModel getAddress() {
            return mAddress;
        }

        public void setAddress(AddressModel address) {
            mAddress = address;
        }

    }
