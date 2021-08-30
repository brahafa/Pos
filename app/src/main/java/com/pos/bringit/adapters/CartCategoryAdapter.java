package com.pos.bringit.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pos.bringit.databinding.ItemRvCartCategoryBinding;
import com.pos.bringit.models.BusinessModel;
import com.pos.bringit.models.CategoryModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.pos.bringit.utils.Constants.BUSINESS_TOPPING_TYPE_LAYER;

public class CartCategoryAdapter extends RecyclerView.Adapter<CartCategoryAdapter.ViewHolder> {

    private Context context;
    private List<CategoryModel> itemList;
    private String pizzaType;

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName;
        private RecyclerView rvToppings;
        private RecyclerView rvLayers;

        ViewHolder(ItemRvCartCategoryBinding binding) {
            super(binding.getRoot());
            tvName = binding.tvCategoryName;
            rvToppings = binding.rvToppings;
            rvLayers = binding.rvLayers;
        }
    }

    public CartCategoryAdapter(Context context, List<CategoryModel> categories, String pizzaType) {
        this.context = context;
        this.itemList = categories;
        this.pizzaType = pizzaType;
    }

    public CartCategoryAdapter(Context context, List<CategoryModel> categories) {
        this.context = context;
        this.itemList = categories;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRvCartCategoryBinding binding =
                ItemRvCartCategoryBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        CategoryModel item = itemList.get(position);

        if (!item.getProducts().isEmpty()) {
            holder.rvToppings.setVisibility(View.VISIBLE);
            holder.tvName.setVisibility(View.VISIBLE);

            holder.tvName.setText(item.getName());

            holder.rvToppings.setLayoutManager(new LinearLayoutManager(context));

            if (item.isToppingDivided()) {
                CartToppingAdapter mCartToppingAdapter = new CartToppingAdapter(item, pizzaType);
                holder.rvToppings.setAdapter(mCartToppingAdapter);

                if (BusinessModel.getInstance().getTopping_method_display().equals(BUSINESS_TOPPING_TYPE_LAYER)) {

                    holder.rvLayers.setVisibility(View.VISIBLE);
                    holder.rvLayers.setLayoutManager(new LinearLayoutManager(context));
                    CartLayerAdapter mCartLayerAdapter = new CartLayerAdapter(item.getLayerPrices());
                    holder.rvLayers.setAdapter(mCartLayerAdapter);
                }
            } else {
                CartFillingAdapter mCartFillingAdapter = new CartFillingAdapter(item);
                holder.rvToppings.setAdapter(mCartFillingAdapter);
            }

        }
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public List<CategoryModel> getItems() {
        return itemList;
    }

}

