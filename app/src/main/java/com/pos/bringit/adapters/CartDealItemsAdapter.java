package com.pos.bringit.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.pos.bringit.R;
import com.pos.bringit.databinding.ItemRvCartDealBinding;
import com.pos.bringit.models.CartModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import static com.pos.bringit.utils.Constants.ORDER_CHANGE_TYPE_NEW;

public class CartDealItemsAdapter extends RecyclerView.Adapter<CartDealItemsAdapter.ViewHolder> {
    private Context context;

    private List<CartModel> itemList;

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

    public CartDealItemsAdapter(Context context, List<CartModel> toppings) {
        this.context = context;
        itemList = toppings;
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
        CartModel item = itemList.get(position);

        holder.tvName.setText(item.getName());
        holder.vSelected.setVisibility(item.isSelected() ? View.VISIBLE : View.GONE);

        if (item.getChangeType().equals(ORDER_CHANGE_TYPE_NEW))
            holder.tvName.setBackgroundResource(R.color.mint_green_light_B0FFAE);


        holder.rvToppings.setLayoutManager(new FlexboxLayoutManager(context, FlexDirection.ROW_REVERSE));
        if (item.getItem_filling() != null) {
            CartFillingAdapter mCartFillingAdapter = new CartFillingAdapter(item.getItem_filling());
            holder.rvToppings.setAdapter(mCartFillingAdapter);
        } else {
            CartToppingAdapter mCartToppingAdapter = new CartToppingAdapter(item.getToppings());
            holder.rvToppings.setAdapter(mCartToppingAdapter);
        }
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

}

