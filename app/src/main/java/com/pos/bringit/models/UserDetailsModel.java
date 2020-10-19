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
    private AddressBean mAddress;

    public UserDetailsModel() {
        this.mName = "";
        this.mLastName = "";
        this.mPhone = "";
        this.mNotes = new NotesBean();
        this.mAddress = new AddressBean();
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

    public AddressBean getAddress() {
        return mAddress;
    }

    public void setAddress(AddressBean address) {
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
            return mOrder;
        }

        public void setOrder(String order) {
            mOrder = order;
        }

        public String getDelivery() {
            return mDelivery;
        }

        public void setDelivery(String mDelivery) {
            this.mDelivery = mDelivery;
        }
    }

    public static class AddressBean {
        @SerializedName("city_name")
        private String mCityName;
        @SerializedName("city_id")
        private String mCityId;
        @SerializedName("street")
        private String mStreet;
        @SerializedName("house_num")
        private String mHouseNum;
        @SerializedName("entrance")
        private String mEntrance;
        @SerializedName("floor")
        private String mFloor;
        @SerializedName("apt_num")
        private String mAptNum;

        public AddressBean() {
            this.mCityName = "";
            this.mCityId = "";
            this.mStreet = "";
            this.mHouseNum = "";
            this.mEntrance = "";
            this.mFloor = "";
            this.mAptNum = "";
        }

        public String getCityName() {
            return mCityName;
        }

        public void setCityName(String cityName) {
            mCityName = cityName;
        }

        public String getStreet() {
            return mStreet;
        }

        public void setStreet(String street) {
            mStreet = street;
        }

        public String getHouseNum() {
            return mHouseNum;
        }

        public void setHouseNum(String houseNum) {
            mHouseNum = houseNum;
        }

        public String getEntrance() {
            return mEntrance;
        }

        public void setEntrance(String entrance) {
            mEntrance = entrance;
        }

        public String getFloor() {
            return mFloor;
        }

        public void setFloor(String floor) {
            mFloor = floor;
        }

        public String getAptNum() {
            return mAptNum;
        }

        public void setAptNum(String aptNum) {
            mAptNum = aptNum;
        }

        public String getCityId() {
            return mCityId;
        }

        public void setCityId(String mCityId) {
            this.mCityId = mCityId;
        }
    }
}
