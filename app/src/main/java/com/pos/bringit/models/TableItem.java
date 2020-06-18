package com.pos.bringit.models;

import com.google.gson.annotations.SerializedName;

public class TableItem {
    @SerializedName("id")
    private String mId;
    @SerializedName("row")
    private int mRow;
    @SerializedName("column")
    private int mColumn;
    @SerializedName("y")
    private int mY;
    @SerializedName("x")
    private int mX;
    @SerializedName("number")
    private String mNumber;
    @SerializedName("type")
    private String mType;
    @SerializedName("proper_x")
    private int mProperX;
    @SerializedName("proper_y")
    private int mProperY;
    @SerializedName("proper_row")
    private double mProperRow;
    @SerializedName("proper_column")
    private double mProperColumn;

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public int getRow() {
        return mRow;
    }

    public void setRow(int row) {
        mRow = row;
    }

    public int getColumn() {
        return mColumn;
    }

    public void setColumn(int column) {
        mColumn = column;
    }

    public int getY() {
        return mY;
    }

    public void setY(int y) {
        mY = y;
    }

    public int getX() {
        return mX;
    }

    public void setX(int x) {
        mX = x;
    }

    public String getNumber() {
        return mNumber;
    }

    public void setNumber(String number) {
        mNumber = number;
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
    }

    public int getProperX() {
        return mProperX;
    }

    public void setProperX(int properX) {
        mProperX = properX;
    }

    public int getProperY() {
        return mProperY;
    }

    public void setProperY(int properY) {
        mProperY = properY;
    }

    public double getProperRow() {
        return mProperRow;
    }

    public void setProperRow(double properRow) {
        mProperRow = properRow;
    }

    public double getProperColumn() {
        return mProperColumn;
    }

    public void setProperColumn(double properColumn) {
        mProperColumn = properColumn;
    }
}
