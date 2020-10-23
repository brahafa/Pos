package com.pos.bringit.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pos.bringit.databinding.ItemRvCartFillingBinding;
import com.pos.bringit.models.CategoryModel;
import com.pos.bringit.models.InnerProductsModel;

import java.util.ArrayList;
import java.util.List;

public class CartFillingAdapter extends RecyclerView.Adapter<CartFillingAdapter.ViewHolder> {

    private List<InnerProductsModel> itemList;
    private CategoryModel category;

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName;

        ViewHolder(ItemRvCartFillingBinding binding) {
            super(binding.getRoot());
            tvName = binding.tvFillingName;
        }
    }

    public CartFillingAdapter(CategoryModel category) {
        this.category = category;

        itemList = new ArrayList<>();
        for (InnerProductsModel oldItem : category.getProducts()) {
            boolean isNew = true;
            for (InnerProductsModel groupItem : itemList) {
                if (groupItem.getName().equals(oldItem.getName()) &&
                        groupItem.getPrice() == oldItem.getPrice()) {
                    isNew = false;
                    break;
                }
            }
            if (isNew) itemList.add(oldItem);
        }

//        for (CategoryModel category : categories) {
//            itemList.addAll(category.getProducts());
//        }
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
        if (category.getCategoryHasFixedPrice() && item.getCategoryId().equals(category.getId()))
            if (category.getProducts().indexOf(item) < category.getProductsFixedPrice())
                price = category.getFixedPrice();

        String multiplier = item.getCount() > 1 ? " x" + item.getCount() : "";

        holder.tvName.setText(String.format("%s %s ₪%s", item.getName(), price, multiplier));
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

}

