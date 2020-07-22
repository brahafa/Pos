package com.pos.bringit.models;

import androidx.annotation.Nullable;

public class ClocksSendModel {
    @Nullable
    private String timeId;
    private String workerId;
    private String startTime;
    private String endTime;

    public ClocksSendModel(String timeId, String workerId, String startTime, String endTime) {
        this.timeId = timeId;
        this.workerId = workerId;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Nullable
    public String getTimeId() {
        return timeId;
    }
}
