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
import com.pos.bringit.databinding.ItemRvCartBinding;
import com.pos.bringit.models.CartFillingModel;
import com.pos.bringit.models.CartModel;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.Group;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    private Context context;
    private List<CartModel> itemList;
    private AdapterCallback adapterCallback;
    private int selectedPos = 0;

    private ViewHolder lastView = null;

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivDelete;
        private TextView tvPrice;
        private TextView tvName;
        private ImageView ivDuplicate;
        private RecyclerView rvToppings;
        private TextView tvComment;
        private Group gSelected;

        ViewHolder(ItemRvCartBinding binding) {
            super(binding.getRoot());
            ivDelete = binding.ivDelete;
            ivDuplicate = binding.ivDuplicate;
            tvName = binding.tvItemName;
            tvPrice = binding.tvItemPrice;
            rvToppings = binding.rvToppings;
            tvComment = binding.tvComment;
            gSelected = binding.gSelected;
        }
    }

    public CartAdapter(Context context, AdapterCallback adapterCallback) {
        this.context = context;
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

        holder.itemView.setBackgroundResource(item.getObject_type().equals("Deal")
                ? R.drawable.selector_cart_deal_bg
                : R.drawable.selector_cart_food_bg);
        holder.tvName.setText(item.getName());
        holder.tvPrice.setText(countPrice(item) + " ₪");


        if (item.getObject_type().equals("Deal")) {
            holder.rvToppings.setLayoutManager(new LinearLayoutManager(context));
            CartDealItemsAdapter mCartDealItemsAdapter =
                    new CartDealItemsAdapter(context, item.getDealItems(),
                            Integer.parseInt(item.getValueJson().getTopping().get(0).getQuantity()));
            holder.rvToppings.setAdapter(mCartDealItemsAdapter);
        } else {
            holder.rvToppings.setLayoutManager(new FlexboxLayoutManager(context, FlexDirection.ROW_REVERSE));

            if (item.getItem_filling() != null) {
                CartFillingAdapter mCartFillingAdapter = new CartFillingAdapter(item.getItem_filling());
                holder.rvToppings.setAdapter(mCartFillingAdapter);
            } else {
                CartToppingAdapter mCartToppingAdapter = new CartToppingAdapter(item.getToppings(), item.getPizzaType());
                holder.rvToppings.setAdapter(mCartToppingAdapter);
            }
        }

        selectItem(holder, selectedPos == position);


        holder.ivDuplicate.setOnClickListener(v -> duplicateItem(item));
        holder.ivDelete.setOnClickListener(
                v -> removeItem(holder.getAdapterPosition(), selectedPos == holder.getAdapterPosition()));
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

    public void duplicateItem(CartModel duplicateItem) {
        CartModel item = duplicateItem.clone();

        int newPosition = itemList.isEmpty()
                ? duplicateItem.getPosition()
                : itemList.get(getItemCount() - 1).getPosition() + 1;
        item.setPosition(newPosition);

        for (int i = 0; i < item.getToppings().size(); i++) {
            CartModel topping = item.getToppings().get(i);
            topping.setFatherId(item.getCartId());
            topping.setPosition(newPosition * 1000 + 1000 + i);
        }
        for (int i = 0; i < item.getDealItems().size(); i++) {
            CartModel dealItem = item.getDealItems().get(i);
            dealItem.setFatherId(item.getCartId());
            dealItem.setPosition(newPosition * 100 + 100 + i);

            for (int j = 0; j < dealItem.getToppings().size(); j++) {
                CartModel toppingDeal = dealItem.getToppings().get(j);
                toppingDeal.setFatherId(dealItem.getCartId());
                toppingDeal.setPosition(dealItem.getPosition() * 1000 + 1000 + j);
            }
        }
        itemList.add(item);
        notifyItemInserted(getItemCount() - 1);
        selectedPos = getItemCount() - 1;
        adapterCallback.onItemDuplicated();

    }

    public void editItem(CartModel item) {
        itemList.set(selectedPos, item);
        notifyItemChanged(selectedPos);
    }

    private void removeItem(int position, boolean isActive) {
        itemList.remove(position);
        notifyItemRemoved(position);
        if (position < selectedPos) selectedPos--;
        adapterCallback.onActiveItemRemoved(isActive);
    }

    private double countPrice(CartModel item) {
        double totalPriceSum = 0;

        totalPriceSum += item.getPrice();

        if (item.getItem_filling() != null) {
            for (CartFillingModel itemFilling : item.getItem_filling()) {
                totalPriceSum += itemFilling.getPrice();
            }
        }
        if (!item.getToppings().isEmpty()) {
            for (CartModel itemTopping : item.getToppings()) {
                totalPriceSum += itemTopping.getPrice();
            }
        }
        if (!item.getDealItems().isEmpty()) {
            for (CartModel itemDeal : item.getDealItems()) {
                totalPriceSum += itemDeal.getPrice();

                if (!itemDeal.getToppings().isEmpty()) {
                    int freeToppingsCount = Integer.parseInt(item.getValueJson().getTopping().get(0).getQuantity());
                    if (itemDeal.getToppings().size() > freeToppingsCount) {
                        for (int i = 0; i < itemDeal.getToppings().size() - freeToppingsCount; i++) {
                            totalPriceSum += itemDeal.getToppings().get(i).getPrice();
                        }
                    }
                }
            }
        }

        return totalPriceSum;
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public List<CartModel> getItems() {
        return itemList;
    }

    public interface AdapterCallback {
        void onItemClick(CartModel fatherItem);

        void onItemDuplicated();

        void onActiveItemRemoved(boolean isActive);
    }

}

