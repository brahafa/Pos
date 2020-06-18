package com.pos.bringit.models.response;

import com.google.gson.annotations.SerializedName;
import com.pos.bringit.models.TableItem;

import java.util.List;

public class WorkingAreaResponse {

    @SerializedName("workingArea")
    private WorkingAreaItem mWorkingArea;
    @SerializedName("status")
    private boolean mStatus;

    public WorkingAreaItem getWorkingArea() {
        return mWorkingArea;
    }

    public void setWorkingArea(WorkingAreaItem workingArea) {
        mWorkingArea = workingArea;
    }

    public boolean isStatus() {
        return mStatus;
    }

    public void setStatus(boolean status) {
        mStatus = status;
    }

    public class WorkingAreaItem {
        @SerializedName("id")
        private String mId;
        @SerializedName("width")
        private String mWidth;
        @SerializedName("height")
        private String mHeight;
        @SerializedName("proper_width")
        private int mProperWidth;
        @SerializedName("proper_height")
        private int mProperHeight;
        @SerializedName("tables")
        private List<TableItem> mTables;

        public String getId() {
            return mId;
        }

        public void setId(String id) {
            mId = id;
        }

        public String getWidth() {
            return mWidth;
        }

        public void setWidth(String width) {
            mWidth = width;
        }

        public String getHeight() {
            return mHeight;
        }

        public void setHeight(String height) {
            mHeight = height;
        }

        public int getProperWidth() {
            return mProperWidth;
        }

        public void setProperWidth(int properWidth) {
            mProperWidth = properWidth;
        }

        public int getProperHeight() {
            return mProperHeight;
        }

        public void setProperHeight(int properHeight) {
            mProperHeight = properHeight;
        }

        public List<TableItem> getTables() {
            return mTables;
        }

        public void setTables(List<TableItem> tables) {
            mTables = tables;
        }

    }
}
