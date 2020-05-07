package com.pos.bringit.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pos.bringit.databinding.ItemRvDrinkBinding;
import com.pos.bringit.models.BusinessItemModel;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DrinkAdapter extends RecyclerView.Adapter<DrinkAdapter.ViewHolder> {

    private List<BusinessItemModel> itemList;
    private AdapterCallback adapterCallback;

    private ViewHolder lastView = null;

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivIcon;
        private TextView tvName;
        private TextView tvPrize;

        ViewHolder(ItemRvDrinkBinding binding) {
            super(binding.getRoot());
            ivIcon = binding.ivDrinkPic;
            tvName = binding.tvDrinkName;
            tvPrize = binding.tvDrinkPrize;
        }
    }

    public DrinkAdapter(AdapterCallback adapterCallback) {
        this.itemList = new ArrayList<>();
        this.adapterCallback = adapterCallback;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRvDrinkBinding binding =
                ItemRvDrinkBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        BusinessItemModel item = itemList.get(position);

        holder.tvName.setText(item.getName());
        holder.tvPrize.setText(
//                type.equals(Constants.NEW_ORDER_TYPE_DELIVERY) ? //fixme detect type
//                        item.getDeliveryPrice() + " ₪" :
                item.getPickupPrice() + " ₪");

        holder.itemView.setOnClickListener(v -> {
            if (!holder.itemView.isSelected()) adapterCallback.onItemClick(item.getObjectId());

            selectItem(holder, true);
            if (lastView != null && lastView != holder) selectItem(lastView, false);
            lastView = holder;

        });
    }

    private void selectItem(ViewHolder holder, boolean isSelected) {
        holder.itemView.setSelected(isSelected);
        holder.ivIcon.setSelected(isSelected);
        holder.tvName.setSelected(isSelected);
        holder.tvPrize.setSelected(isSelected);
    }

    public void updateList(List<BusinessItemModel> newList) {
        itemList.clear();
        itemList.addAll(newList);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public interface AdapterCallback {
        void onItemClick(int objectId);
    }

}

