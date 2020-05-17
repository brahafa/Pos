package com.pos.bringit.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.pos.bringit.R;
import com.pos.bringit.databinding.ItemRvKitchenCartBinding;
import com.pos.bringit.models.CartModel;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.Group;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.pos.bringit.utils.Constants.ORDER_CHANGE_TYPE_DELETED;
import static com.pos.bringit.utils.Constants.ORDER_CHANGE_TYPE_NEW;

public class CartKitchenAdapter extends RecyclerView.Adapter<CartKitchenAdapter.ViewHolder> {

    private Context context;
    private List<CartModel> itemList;
    private AdapterCallback adapterCallback;
    private int selectedPos = 0;

    private ViewHolder lastView = null;

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivDelete;
        private TextView tvCancel;
        private TextView tvName;
        private ImageView ivDuplicate;
        private RecyclerView rvToppings;
        private TextView tvComment;
        private Group gSelected;
        private View vDeleted;

        ViewHolder(ItemRvKitchenCartBinding binding) {
            super(binding.getRoot());
            ivDelete = binding.ivDelete;
            ivDuplicate = binding.ivDuplicate;
            tvName = binding.tvItemName;
            tvCancel = binding.tvItemCancel;
            rvToppings = binding.rvToppings;
            tvComment = binding.tvComment;
            gSelected = binding.gSelected;
            vDeleted = binding.vDeleted;
        }
    }

    public CartKitchenAdapter(Context context, AdapterCallback adapterCallback) {
        this.context = context;
        this.itemList = new ArrayList<>();
        this.adapterCallback = adapterCallback;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRvKitchenCartBinding binding =
                ItemRvKitchenCartBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        CartModel item = itemList.get(position);

        holder.itemView.setBackgroundResource(item.getObject_type().equals("Deal")
                ? R.drawable.selector_cart_deal_bg
                : R.drawable.selector_cart_food_bg);
        holder.tvName.setText(item.getName());


        if (item.getObject_type().equals("Deal")) {
            holder.rvToppings.setLayoutManager(new LinearLayoutManager(context));
            CartDealItemsAdapter mCartDealItemsAdapter = new CartDealItemsAdapter(context, item.getDealItems());
            holder.rvToppings.setAdapter(mCartDealItemsAdapter);
        } else {
            holder.rvToppings.setLayoutManager(new FlexboxLayoutManager(context, FlexDirection.ROW_REVERSE));

            if (item.getItem_filling() != null) {
                CartFillingAdapter mCartFillingAdapter = new CartFillingAdapter(item.getItem_filling());
                holder.rvToppings.setAdapter(mCartFillingAdapter);
            } else {
                CartToppingAdapter mCartToppingAdapter = new CartToppingAdapter(item.getToppings());
                holder.rvToppings.setAdapter(mCartToppingAdapter);
            }
        }

        selectItem(holder, selectedPos == position);

        holder.ivDuplicate.setOnClickListener(v -> adapterCallback.onItemDuplicated(item));
        holder.ivDelete.setOnClickListener(v -> {

            holder.vDeleted.setVisibility(View.VISIBLE);
            holder.ivDelete.setVisibility(View.GONE);
            holder.tvCancel.setText("החזר להזמנה");

            adapterCallback.onItemRemoved(item, true);
        });
        holder.tvCancel.setOnClickListener(v -> {
            if (holder.tvCancel.getText().equals("החזר להזמנה")) {
                holder.vDeleted.setVisibility(View.GONE);
                holder.ivDelete.setVisibility(View.VISIBLE);
                holder.tvCancel.setText("ביטול");

                adapterCallback.onItemRemoved(item, false);
            }
        });
        holder.itemView.setOnClickListener(v -> {
            adapterCallback.onItemClick(item);

            selectedPos = position;
            selectItem(holder, true);

        });
    }

    private void selectItem(ViewHolder holder, boolean isSelected) {
        holder.itemView.setSelected(isSelected);
        holder.gSelected.setVisibility(isSelected ? View.VISIBLE : View.GONE);

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

    public void editItem(CartModel newItem) {

        CartModel oldItem = itemList.get(selectedPos);

        editToppings(oldItem, newItem);

        for (int i = 0; i < newItem.getDealItems().size(); i++) {
            CartModel oldDealItem = oldItem.getDealItems().get(i);
            CartModel newDealItem = newItem.getDealItems().get(i);

            editToppings(oldDealItem, newDealItem);

            if (!newDealItem.equals(oldDealItem)) {
                oldDealItem.setChangeType(ORDER_CHANGE_TYPE_DELETED);
                adapterCallback.onItemRemoved(oldDealItem.clone(), true);
                adapterCallback.onItemAdded(newDealItem, true);

                oldDealItem.setObjectId(newDealItem.getObjectId());
                oldDealItem.setName(newDealItem.getName());
                oldDealItem.setChangeType(ORDER_CHANGE_TYPE_NEW);
            }

            oldDealItem.setSelected(newDealItem.isSelected());


        }


        itemList.set(selectedPos, oldItem);

        notifyItemChanged(selectedPos);
    }

    private void editToppings(CartModel oldItem, CartModel newItem) {
        for (CartModel newTopping : newItem.getToppings()) {
            for (CartModel oldTopping : oldItem.getToppings()) {
                if (oldTopping.equals(newTopping)) {
                    if (oldTopping.getChangeType().equals(ORDER_CHANGE_TYPE_DELETED)) {
                        oldTopping.setChangeType(ORDER_CHANGE_TYPE_NEW);
                        adapterCallback.onItemAdded(newTopping, true);
                    }
                }
            }
            if (!oldItem.getToppings().contains(newTopping)) {
                newTopping.setChangeType(ORDER_CHANGE_TYPE_NEW);
                adapterCallback.onItemAdded(newTopping, true);
                oldItem.getToppings().add(newTopping);
            }
        }

        for (CartModel oldTopping : oldItem.getToppings()) {
            if (!newItem.getToppings().contains(oldTopping)) {
                oldTopping.setChangeType(ORDER_CHANGE_TYPE_DELETED);
                adapterCallback.onItemRemoved(oldTopping, true);
            }
        }
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public interface AdapterCallback {
        void onItemClick(CartModel fatherItem);

        void onItemDuplicated(CartModel item);

        void onItemRemoved(CartModel item, boolean isRemoved);

        void onItemAdded(CartModel item, boolean isAdded);
    }

}

