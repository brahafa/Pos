package com.pos.bringit.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pos.bringit.R;
import com.pos.bringit.databinding.ItemRvCartBinding;
import com.pos.bringit.models.CartModel;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    private List<CartModel> itemList;
    private AdapterCallback adapterCallback;
    private int selectedPos = 0;

    private ViewHolder lastView = null;

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivDelete;
        private TextView tvPrice;
        private TextView tvName;
        private ImageView ivDuplicate;

        ViewHolder(ItemRvCartBinding binding) {
            super(binding.getRoot());
            ivDelete = binding.ivDelete;
            ivDuplicate = binding.ivDelete;
            tvName = binding.tvItemName;
            tvPrice = binding.tvItemPrice;
        }
    }

    public CartAdapter(AdapterCallback adapterCallback) {
        this.itemList = new ArrayList<>();
        this.adapterCallback = adapterCallback;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRvCartBinding binding =
                ItemRvCartBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        CartModel item = itemList.get(position);

        holder.itemView.setBackgroundResource(item.getType().equals("Deal")
                ? R.drawable.selector_cart_deal_bg
                : R.drawable.selector_cart_food_bg);
        holder.tvName.setText(item.getName());
        holder.tvPrice.setText(item.getPrice());
        if (selectedPos == position) selectItem(holder, true);

        holder.ivDelete.setOnClickListener(v -> removeItem(holder.getAdapterPosition()));
        holder.itemView.setOnClickListener(v -> {
            adapterCallback.onItemClick(item.getObjectId());

            selectedPos = position;
            selectItem(holder, true);

        });
    }

    private void selectItem(ViewHolder holder, boolean isSelected) {
        holder.itemView.setSelected(isSelected);

        if (lastView != null && lastView != holder) selectItem(lastView, false);
        lastView = holder;
    }

    public void updateList(List<CartModel> newList) {
        CartItemsDiffCallback diffCallback = new CartItemsDiffCallback(itemList, newList);
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);

        itemList.clear();
        itemList.addAll(newList);
        diffResult.dispatchUpdatesTo(this);
    }

    public void addItem(CartModel item) {
        itemList.add(item);
        notifyItemInserted(getItemCount() - 1);
        selectedPos = getItemCount() - 1;
    }

    public void removeItem(int position) {
        itemList.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public interface AdapterCallback {
        void onItemClick(String objectId);
    }

}

