package com.pos.bringit.models;

import com.google.gson.annotations.SerializedName;

public class AddressModel {
        @SerializedName("city")
        private String mCity;
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

        public AddressModel() {
            this.mCity = "";
            this.mCityId = "";
            this.mStreet = "";
            this.mHouseNum = "";
            this.mEntrance = "";
            this.mFloor = "";
            this.mAptNum = "";
        }

        public String getCity() {
            return mCity;
        }

        public void setCity(String city) {
            mCity = city;
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