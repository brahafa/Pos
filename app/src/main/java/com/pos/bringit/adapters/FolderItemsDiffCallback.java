package com.pos.bringit.adapters;

import com.pos.bringit.models.FolderItemModel;

import java.util.List;

import androidx.recyclerview.widget.DiffUtil;

public class FolderItemsDiffCallback extends DiffUtil.Callback {

    private List<FolderItemModel> oldList;
    private List<FolderItemModel> newList;

    public FolderItemsDiffCallback(List<FolderItemModel> oldList, List<FolderItemModel> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).getId().equals(newList.get(newItemPosition).getId());
    }

    @Override //todo fix here
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).getName().equals(newList.get(newItemPosition).getName()) &&
                oldList.get(oldItemPosition).getFatherId().equals(newList.get(newItemPosition).getFatherId());
    }
}
