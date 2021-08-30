package com.pos.bringit.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pos.bringit.databinding.ItemRvDrinkBinding;
import com.pos.bringit.models.ProductItemModel;

import java.util.ArrayList;
import java.util.List;

import static com.pos.bringit.utils.Utils.getImageRes;

public class DrinkAdapter extends RecyclerView.Adapter<DrinkAdapter.ViewHolder> {

    private List<ProductItemModel> itemList;
    private AdapterCallback adapterCallback;

    private int lastPos = -1;

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivIcon;
        private TextView tvName;

        ViewHolder(ItemRvDrinkBinding binding) {
            super(binding.getRoot());
            ivIcon = binding.ivDrinkPic;
            tvName = binding.tvDrinkName;
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
        ProductItemModel item = itemList.get(position);

        holder.tvName.setText(item.getName());
        holder.ivIcon.setImageResource(getImageRes(item.getTypeName()));
        holder.ivIcon.setColorFilter(Color.argb(255, 255, 255, 255));
//        holder.tvPrize.setText(
//                type.equals(Constants.NEW_ORDER_TYPE_DELIVERY) ?
//                        item.getDeliveryPrice() + " ₪" :
//                item.getPickupPrice() + " ₪");
        selectItem(holder, item.isSelected());
        if (item.isSelected()) lastPos = position;

        holder.itemView.setOnClickListener(v -> {
            if (!item.isSelected()) adapterCallback.onItemClick(item);

            item.setSelected(true);
            selectItem(holder, true);
            if (lastPos != position && lastPos != -1) {
                itemList.get(lastPos).setSelected(false);
                notifyItemChanged(lastPos);
            }
            lastPos = position;

        });
    }

    private void selectItem(ViewHolder holder, boolean isSelected) {
        holder.itemView.setSelected(isSelected);
        holder.ivIcon.setSelected(isSelected);
        holder.tvName.setSelected(isSelected);
    }

    public void updateList(List<ProductItemModel> newList) {
        itemList.clear();
        itemList.addAll(newList);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public interface AdapterCallback {
        void onItemClick(ProductItemModel item);
    }

}

