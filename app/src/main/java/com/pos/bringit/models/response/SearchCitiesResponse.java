package com.pos.bringit.models.response;

import com.google.gson.annotations.SerializedName;
import com.pos.bringit.models.AutocompleteModel;

import java.util.ArrayList;
import java.util.List;

public class SearchCitiesResponse {

    @SerializedName("status")
    private boolean mStatus;
    @SerializedName("cities")
    private List<AutocompleteModel> mCitiesList = new ArrayList<>();

    public boolean isStatus() {
        return mStatus;
    }

    public void setStatus(boolean status) {
        mStatus = status;
    }


    public List<AutocompleteModel> getCitiesList() {
        return mCitiesList;
    }

    public void setCitiesList(List<AutocompleteModel> mCitiesList) {
        this.mCitiesList = mCitiesList;
    }
}
