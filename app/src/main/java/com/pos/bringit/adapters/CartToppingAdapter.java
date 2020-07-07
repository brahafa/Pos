package com.pos.bringit.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pos.bringit.R;
import com.pos.bringit.databinding.ItemRvCartToppingBinding;
import com.pos.bringit.models.CartModel;
import com.pos.bringit.utils.Constants;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import static com.pos.bringit.utils.Constants.ORDER_CHANGE_TYPE_DELETED;
import static com.pos.bringit.utils.Constants.ORDER_CHANGE_TYPE_NEW;
import static com.pos.bringit.utils.Constants.PIZZA_TYPE_BL;
import static com.pos.bringit.utils.Constants.PIZZA_TYPE_BR;
import static com.pos.bringit.utils.Constants.PIZZA_TYPE_FULL;
import static com.pos.bringit.utils.Constants.PIZZA_TYPE_LH;
import static com.pos.bringit.utils.Constants.PIZZA_TYPE_RH;
import static com.pos.bringit.utils.Constants.PIZZA_TYPE_TL;
import static com.pos.bringit.utils.Constants.PIZZA_TYPE_TR;

public class CartToppingAdapter extends RecyclerView.Adapter<CartToppingAdapter.ViewHolder> {

    private List<CartModel> itemList;
    private String pizzaType;
    private int freeToppingCount = 0;

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName;
        private ImageView ivType;
        private ImageView ivTypeRect;
        private ImageView ivTypeSlice;
        private View vDeleted;
        private View vAdded;

        ViewHolder(ItemRvCartToppingBinding binding) {
            super(binding.getRoot());
            tvName = binding.tvToppingName;
            ivType = binding.ivToppingLocation;
            ivTypeRect = binding.ivToppingLocationRect;
            ivTypeSlice = binding.ivToppingLocationSlice;
            vDeleted = binding.vDeleted;
            vAdded = binding.vAdded;
        }
    }

    public CartToppingAdapter(List<CartModel> toppings, String pizzaType) {
        this.itemList = toppings;
        this.pizzaType = pizzaType;
    }

    public CartToppingAdapter(List<CartModel> toppings, String pizzaType, int freeToppingCount) {
        this.itemList = toppings;
        this.pizzaType = pizzaType;
        this.freeToppingCount = freeToppingCount;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRvCartToppingBinding binding =
                ItemRvCartToppingBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        CartModel item = itemList.get(position);

        holder.ivType.setVisibility(
                pizzaType.equals(Constants.PIZZA_TYPE_CIRCLE) ? View.VISIBLE : View.GONE);
        holder.ivTypeRect.setVisibility(
                pizzaType.equals(Constants.PIZZA_TYPE_RECTANGLE) ? View.VISIBLE : View.GONE);
        holder.ivTypeSlice.setVisibility(
                pizzaType.equals(Constants.PIZZA_TYPE_ONE_SLICE) ? View.VISIBLE : View.GONE);

        holder.tvName.setText(String.format("%s %s ₪", item.getName(), position >= freeToppingCount ? item.getPrice() : 0));
        if (pizzaType.equals(Constants.PIZZA_TYPE_CIRCLE))
            holder.ivType.setImageResource(getImageRes(item.getToppingLocation()));
        if (pizzaType.equals(Constants.PIZZA_TYPE_RECTANGLE))
            holder.ivTypeRect.setImageResource(getImageResRect(item.getToppingLocation()));

        holder.vDeleted.setVisibility(item.getChangeType().equals(ORDER_CHANGE_TYPE_DELETED) ? View.VISIBLE : View.GONE);
        holder.vAdded.setVisibility(item.getChangeType().equals(ORDER_CHANGE_TYPE_NEW) ? View.VISIBLE : View.GONE);
    }

    private int getImageRes(String viewType) {
        int imageRes = R.drawable.ic_pizza_full_active;
        switch (viewType) {
            case PIZZA_TYPE_FULL:
                imageRes = R.drawable.ic_pizza_full_active;
                break;
            case PIZZA_TYPE_RH:
                imageRes = R.drawable.ic_pizza_rh_active;
                break;
            case PIZZA_TYPE_LH:
                imageRes = R.drawable.ic_pizza_lh_active;
                break;
            case PIZZA_TYPE_TR:
                imageRes = R.drawable.ic_pizza_tr_cart;
                break;
            case PIZZA_TYPE_TL:
                imageRes = R.drawable.ic_pizza_tl_cart;
                break;
            case PIZZA_TYPE_BR:
                imageRes = R.drawable.ic_pizza_br_cart;
                break;
            case PIZZA_TYPE_BL:
                imageRes = R.drawable.ic_pizza_bl_cart;
                break;
        }
        return imageRes;
    }

    private int getImageResRect(String viewType) {
        int imageRes = R.drawable.ic_pizza_full_rect_active;
        switch (viewType) {
            case PIZZA_TYPE_FULL:
                imageRes = R.drawable.ic_pizza_full_rect_active;
                break;
            case PIZZA_TYPE_RH:
                imageRes = R.drawable.ic_pizza_rh_rect_cart;
                break;
            case PIZZA_TYPE_LH:
                imageRes = R.drawable.ic_pizza_lh_rect_cart;
                break;
            case PIZZA_TYPE_TR:
                imageRes = R.drawable.ic_pizza_tr_rect_cart;
                break;
            case PIZZA_TYPE_TL:
                imageRes = R.drawable.ic_pizza_tl_rect_cart;
                break;
            case PIZZA_TYPE_BR:
                imageRes = R.drawable.ic_pizza_br_rect_cart;
                break;
            case PIZZA_TYPE_BL:
                imageRes = R.drawable.ic_pizza_bl_rect_cart;
                break;
        }
        return imageRes;
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public void updateList(List<CartModel> newList) {
        CartToppingItemsDiffCallback diffCallback = new CartToppingItemsDiffCallback(itemList, newList);
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);

        itemList.clear();
        itemList.addAll(newList);
        diffResult.dispatchUpdatesTo(this);
    }

}

