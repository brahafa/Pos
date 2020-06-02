package com.pos.bringit.models.response;

import com.google.gson.annotations.SerializedName;
import com.pos.bringit.models.AutocompleteModel;

import java.util.ArrayList;
import java.util.List;

public class SearchStreetsResponse {

    @SerializedName("status")
    private boolean mStatus;
    @SerializedName("streets")
    private List<AutocompleteModel> mStreetsList = new ArrayList<>();

    public boolean isStatus() {
        return mStatus;
    }

    public void setStatus(boolean status) {
        mStatus = status;
    }


    public List<AutocompleteModel> getStreetsList() {
        return mStreetsList;
    }

    public void setStreetsList(List<AutocompleteModel> mStreetsList) {
        this.mStreetsList = mStreetsList;
    }
}
