package com.pos.bringit.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pos.bringit.databinding.ItemRvMenuBinding;
import com.pos.bringit.databinding.ItemRvMenuEndBinding;
import com.pos.bringit.models.FolderItemModel;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

public class FolderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<FolderItemModel> itemList = new ArrayList<>();
    private AdapterCallback adapterCallback;

    class MenuViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName;

        MenuViewHolder(ItemRvMenuBinding binding) {
            super(binding.getRoot());
            tvName = binding.tvMenuName;
        }
    }

    class MenuEndViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName;

        MenuEndViewHolder(ItemRvMenuEndBinding binding) {
            super(binding.getRoot());
            tvName = binding.tvMenuName;
        }
    }

    public FolderAdapter(AdapterCallback adapterCallback) {
        this.adapterCallback = adapterCallback;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 0) {
            ItemRvMenuBinding binding =
                    ItemRvMenuBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new MenuViewHolder(binding);
        } else {
            ItemRvMenuEndBinding binding =
                    ItemRvMenuEndBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new MenuEndViewHolder(binding);
        }

    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        FolderItemModel item = itemList.get(position);
        if (getItemViewType(position) == 0) {
            ((MenuViewHolder) holder).tvName.setText(item.getName());
            ((MenuViewHolder) holder).itemView.setOnClickListener(v -> adapterCallback.onItemClick(item.getId()));
        } else {
            ((MenuEndViewHolder) holder).tvName.setText(item.getName());
        }

    }


    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public interface AdapterCallback {
        void onItemClick(String folderId);
    }

    public void updateList(List<FolderItemModel> newList) {
        FolderItemsDiffCallback diffCallback = new FolderItemsDiffCallback(itemList, newList);
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);

        itemList.clear();
        itemList.addAll(newList);
        diffResult.dispatchUpdatesTo(this);
    }

    @Override
    public int getItemViewType(int position) {
        return (position == getItemCount() - 1) ? 1 : 0;
    }
}

