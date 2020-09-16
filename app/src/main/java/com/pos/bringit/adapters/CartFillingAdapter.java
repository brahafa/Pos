package com.pos.bringit.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pos.bringit.databinding.ItemRvCartFillingBinding;
import com.pos.bringit.models.CategoryModel;
import com.pos.bringit.models.InnerProductsModel;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CartFillingAdapter extends RecyclerView.Adapter<CartFillingAdapter.ViewHolder> {

    private List<InnerProductsModel> itemList;
    private List<CategoryModel> categories;

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName;

        ViewHolder(ItemRvCartFillingBinding binding) {
            super(binding.getRoot());
            tvName = binding.tvFillingName;
        }
    }

    public CartFillingAdapter(List<CategoryModel> categories) {
        itemList = new ArrayList<>();
        this.categories = categories;
        for (CategoryModel category : categories) {
            itemList.addAll(category.getProducts());
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRvCartFillingBinding binding =
                ItemRvCartFillingBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        InnerProductsModel item = itemList.get(position);

        double price = item.getPrice();
        for (CategoryModel category : categories)
            if (category.getCategoryHasFixedPrice() == 1 && item.getCategoryId().equals(category.getId()))
                if (category.getProducts().indexOf(item) < category.getProductsFixedPrice())
                    price = category.getFixedPrice();

        holder.tvName.setText(String.format("%s %s ₪", item.getName(), price));
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

}

