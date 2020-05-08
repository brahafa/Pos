package com.pos.bringit.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pos.bringit.R;
import com.pos.bringit.databinding.ItemRvMenuBinding;
import com.pos.bringit.databinding.ItemRvMenuEndBinding;
import com.pos.bringit.models.BreadcrumbModel;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import static com.pos.bringit.utils.Constants.ITEM_TYPE_DEAL;
import static com.pos.bringit.utils.Constants.ITEM_TYPE_FOLDER;
import static com.pos.bringit.utils.Constants.ITEM_TYPE_FOLDER_END;
import static com.pos.bringit.utils.Constants.ITEM_TYPE_FOOD;

public class MenuAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<BreadcrumbModel> itemList = new ArrayList<>();
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

    public MenuAdapter(AdapterCallback adapterCallback) {
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
        BreadcrumbModel item = itemList.get(position);
        int viewType = getItemViewType(position);
        if (viewType == ITEM_TYPE_FOLDER) {
            ((MenuViewHolder) holder).tvName.setText(item.getName());
            ((MenuViewHolder) holder).itemView.setOnClickListener(v -> adapterCallback.onItemClick(item.getId()));
        } else {
            ((MenuEndViewHolder) holder).tvName.setText(item.getName());
            ((MenuEndViewHolder) holder).itemView.setOnClickListener(
                    viewType == ITEM_TYPE_FOLDER_END ? v -> adapterCallback.onItemClick(item.getId()) : null);
            ((MenuEndViewHolder) holder).tvName.setBackgroundResource(getBackgroundRes(viewType));

        }

    }

    private int getBackgroundRes(int viewType) {
        int backgroundRes = R.drawable.background_menu_end;
        switch (viewType) {
            case ITEM_TYPE_FOOD:
                backgroundRes = R.drawable.background_item_food;
                break;
            case ITEM_TYPE_DEAL:
                backgroundRes = R.drawable.background_item_deal;
                break;
        }
        return backgroundRes;
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public interface AdapterCallback {
        void onItemClick(String folderId);
    }

    public void updateList(List<BreadcrumbModel> newList) {
        MenuItemsDiffCallback diffCallback = new MenuItemsDiffCallback(itemList, newList);
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);

        itemList.clear();
        itemList.addAll(newList);
        diffResult.dispatchUpdatesTo(this);
    }

    public void addItem(BreadcrumbModel item) {
        itemList.add(item);
        notifyItemInserted(getItemCount() - 1);
    }

    public void removeLast() {
        itemList.remove(getItemCount() - 1);
        notifyItemRemoved(getItemCount() - 2);
    }

    @Override
    public int getItemViewType(int position) {
        return (position == getItemCount() - 1) ? itemList.get(position).getType() : ITEM_TYPE_FOLDER;
    }
}

