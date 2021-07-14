package com.pos.bringit.models;

import com.google.gson.annotations.SerializedName;

public class FinanceItem {
    @SerializedName("session_id")
    private String sessionId;
    @SerializedName("finance_id")
    private String financeId;
    @SerializedName("opened_by")
    private String openedBy;
    @SerializedName("opened_at")
    private String openedAt;
    @SerializedName("closed_at")
    private Object closedAt;
    @SerializedName("closed_by")
    private Object closedBy;

    public String getFinanceId() {
        return financeId;
    }

    public void setFinanceId(String financeId) {
        this.financeId = financeId;
    }

    public String getOpenedBy() {
        return openedBy;
    }

    public void setOpenedBy(String openedBy) {
        this.openedBy = openedBy;
    }

    public String getOpenedAt() {
        return openedAt;
    }

    public void setOpenedAt(String openedAt) {
        this.openedAt = openedAt;
    }

    public Object getClosedAt() {
        return closedAt;
    }

    public void setClosedAt(Object closedAt) {
        this.closedAt = closedAt;
    }

    public Object getClosedBy() {
        return closedBy;
    }

    public void setClosedBy(Object closedBy) {
        this.closedBy = closedBy;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId() {
        this.sessionId = financeId;
    }
}
