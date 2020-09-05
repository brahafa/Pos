package com.pos.bringit.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.pos.bringit.databinding.ItemRvCartDealBinding;
import com.pos.bringit.models.DealItemModel;
import com.pos.bringit.models.ProductItemModel;
import com.pos.bringit.utils.Constants;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CartDealItemsAdapter extends RecyclerView.Adapter<CartDealItemsAdapter.ViewHolder> {
    private Context context;

    private List<DealItemModel> itemList;
    private int freeToppingCount = 0;

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName;
        private View vSelected;
        private RecyclerView rvToppings;

        ViewHolder(ItemRvCartDealBinding binding) {
            super(binding.getRoot());
            tvName = binding.tvItemName;
            vSelected = binding.vSelected;
            rvToppings = binding.rvToppings;
        }
    }

    public CartDealItemsAdapter(Context context, List<DealItemModel> toppings, int freeToppingCount) {
        this.context = context;
        this.itemList = toppings;
        this.freeToppingCount = freeToppingCount;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRvCartDealBinding binding =
                ItemRvCartDealBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        if (!itemList.get(position).getProducts().isEmpty()) {
            ProductItemModel item = itemList.get(position).getProducts().get(0);

            holder.tvName.setText(item.getName().isEmpty() ? itemList.get(position).getTypeName() : item.getName());
            holder.vSelected.setVisibility(itemList.get(position).isSelected() ? View.VISIBLE : View.GONE);

//        todo change types
//        if (item.getChangeType().equals(ORDER_CHANGE_TYPE_NEW))
//            holder.tvName.setBackgroundResource(R.color.mint_green_light_B0FFAE);


            holder.rvToppings.setLayoutManager(new FlexboxLayoutManager(context, FlexDirection.ROW_REVERSE));

            if (item.getTypeName().equals(Constants.BUSINESS_ITEMS_TYPE_PIZZA)) {
                CartToppingAdapter mCartToppingAdapter = new CartToppingAdapter(item.getCategories(), item.getShape(), freeToppingCount);
                holder.rvToppings.setAdapter(mCartToppingAdapter);
            } else {
                CartFillingAdapter mCartFillingAdapter = new CartFillingAdapter(item.getCategories());
                holder.rvToppings.setAdapter(mCartFillingAdapter);
            }
        }
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

}

