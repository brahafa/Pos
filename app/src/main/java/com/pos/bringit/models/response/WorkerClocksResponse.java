package com.pos.bringit.models.response;

import com.google.gson.annotations.SerializedName;
import com.pos.bringit.models.ClocksModel;
import com.pos.bringit.models.WorkerModel;

import java.util.List;

public class WorkerClocksResponse {

    @SerializedName("worker")
    private WorkerModel mWorker;
    @SerializedName("status")
    private boolean mStatus;
    @SerializedName("clocks")
    private List<ClocksModel> mClocks;

    public WorkerModel getWorker() {
        return mWorker;
    }

    public void setWorker(WorkerModel worker) {
        mWorker = worker;
    }

    public boolean isStatus() {
        return mStatus;
    }

    public void setStatus(boolean status) {
        mStatus = status;
    }

    public List<ClocksModel> getClocks() {
        return mClocks;
    }

    public void setClocks(List<ClocksModel> clocks) {
        mClocks = clocks;
    }

}
