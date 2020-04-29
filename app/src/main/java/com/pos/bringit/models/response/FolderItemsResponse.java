package com.pos.bringit.models.response;

import com.google.gson.annotations.SerializedName;
import com.pos.bringit.models.BreadcrumbModel;
import com.pos.bringit.models.FolderItemModel;
import com.pos.bringit.models.ShortcutModel;

import java.util.List;

public class FolderItemsResponse {

    @SerializedName("status")
    private boolean mStatus;
    @SerializedName("breadcrumbs")
    private List<BreadcrumbModel> mBreadcrumbs;
    @SerializedName("shortcuts")
    private List<ShortcutModel> mShortcuts;
    @SerializedName("items")
    private List<FolderItemModel> mItems;

    public boolean isStatus() {
        return mStatus;
    }

    public void setStatus(boolean status) {
        mStatus = status;
    }

    public List<BreadcrumbModel> getBreadcrumbs() {
        return mBreadcrumbs;
    }

    public void setBreadcrumbs(List<BreadcrumbModel> breadcrumbs) {
        mBreadcrumbs = breadcrumbs;
    }

    public List<ShortcutModel> getShortcuts() {
        return mShortcuts;
    }

    public void setShortcuts(List<ShortcutModel> shortcuts) {
        mShortcuts = shortcuts;
    }

    public List<FolderItemModel> getItems() {
        return mItems;
    }

    public void setItems(List<FolderItemModel> items) {
        mItems = items;
    }

}
