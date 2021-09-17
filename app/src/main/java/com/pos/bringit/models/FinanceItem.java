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
    private String closedAt;
    @SerializedName("closed_by")
    private String closedBy;
    @SerializedName("sum")
    private SumItem sum;


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

    public String getClosedAt() {
        return closedAt;
    }

    public void setClosedAt(String closedAt) {
        this.closedAt = closedAt;
    }

    public String getClosedBy() {
        return closedBy;
    }

    public void setClosedBy(String closedBy) {
        this.closedBy = closedBy;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public SumItem getSum() {
        return sum;
    }

    public void setSum(SumItem sum) {
        this.sum = sum;
    }

    public static class SumItem {
        @SerializedName("cash")
        private int cash;
        @SerializedName("credit")
        private int credit;
        @SerializedName("balance")
        private int balance;
        @SerializedName("fund")
        private String fund;

        public int getCash() {
            return cash;
        }

        public void setCash(int cash) {
            this.cash = cash;
        }

        public int getCredit() {
            return credit;
        }

        public void setCredit(int credit) {
            this.credit = credit;
        }

        public int getBalance() {
            return balance;
        }

        public void setBalance(int balance) {
            this.balance = balance;
        }

        public String getFund() {
            return fund;
        }

        public void setFund(String fund) {
            this.fund = fund;
        }
    }
}
