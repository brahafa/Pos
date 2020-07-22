package com.pos.bringit.models;

import com.google.gson.annotations.SerializedName;

public class ClocksModel {
    @SerializedName("id")
    private String mId;
    @SerializedName("worker_id")
    private String mWorkerId;
    @SerializedName("start_time")
    private String mStartTime;
    @SerializedName("approve_start")
    private String mApproveStart;
    @SerializedName("end_time")
    private String mEndTime;
    @SerializedName("approve_end")
    private String mApproveEnd;
    @SerializedName("business_id")
    private String mBusinessId;

    public ClocksModel(String workerId) {
        mWorkerId = workerId;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getWorkerId() {
        return mWorkerId;
    }

    public void setWorkerId(String workerId) {
        mWorkerId = workerId;
    }

    public String getStartTime() {
        return mStartTime;
    }

    public void setStartTime(String startTime) {
        mStartTime = startTime;
    }

    public String getApproveStart() {
        return mApproveStart;
    }

    public void setApproveStart(String approveStart) {
        mApproveStart = approveStart;
    }

    public String getEndTime() {
        return mEndTime;
    }

    public void setEndTime(String endTime) {
        mEndTime = endTime;
    }

    public String getApproveEnd() {
        return mApproveEnd;
    }

    public void setApproveEnd(String approveEnd) {
        mApproveEnd = approveEnd;
    }

    public String getBusinessId() {
        return mBusinessId;
    }

    public void setBusinessId(String businessId) {
        mBusinessId = businessId;
    }
}
