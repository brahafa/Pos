package com.pos.bringit.models.response;

import com.google.gson.annotations.SerializedName;
import com.pos.bringit.models.UserDetailsModel;

public class UserDetailsResponse {

    @SerializedName("user")
    private UserDetailsModel mUser;

    public UserDetailsModel getUser() {
        return mUser;
    }

    public void setUser(UserDetailsModel mUser) {
        this.mUser = mUser;
    }
}
