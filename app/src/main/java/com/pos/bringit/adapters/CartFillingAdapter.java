package com.pos.bringit.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pos.bringit.databinding.ItemRvCartFillingBinding;
import com.pos.bringit.models.CategoryModel;
import com.pos.bringit.models.InnerProductsModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import static com.pos.bringit.utils.Constants.ORDER_CHANGE_TYPE_DELETED;
import static com.pos.bringit.utils.Constants.ORDER_CHANGE_TYPE_NEW;

public class CartFillingAdapter extends RecyclerView.Adapter<CartFillingAdapter.ViewHolder> {

    private List<InnerProductsModel> itemList;
    private CategoryModel category;

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName;
        private TextView tvPrice;
        private View vDeleted;
        private View vAdded;

        ViewHolder(ItemRvCartFillingBinding binding) {
            super(binding.getRoot());
            tvName = binding.tvFillingName;
            tvPrice = binding.tvToppingPrice;
            vDeleted = binding.vDeleted;
            vAdded = binding.vAdded;
        }
    }

    public CartFillingAdapter(CategoryModel category) {
        this.category = category;

        itemList = new ArrayList<>();
        List<InnerProductsModel> products = category.getProducts();

        if (category.getCategoryHasFixedPrice()) {
            int size = Math.min(products.size(), category.getProductsFixedPrice());
            for (int i = 0; i < size; i++) {
                InnerProductsModel fixedItem = products.get(i).clone();
                fixedItem.setPrice((int) category.getFixedPrice());
                fixedItem.setCount(1);
                itemList.add(fixedItem);
            }
        }

        for (int i = itemList.size(); i < products.size(); i++) {
            InnerProductsModel oldItem = products.get(i);
            boolean isNew = true;
            for (InnerProductsModel groupItem : itemList) {
                if (groupItem.getName().equals(oldItem.getName())) {
                    oldItem.setCount(oldItem.getCount() - 1);
                    if (groupItem.getPrice() == oldItem.getPrice()) {
                        isNew = false;
                        oldItem.setCount(oldItem.getCount() + 1);
                        break;
                    }
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
//        if (category.getCategoryHasFixedPrice() && item.getCategoryId().equals(category.getId()))
//            if (category.getProducts().indexOf(item) < category.getProductsFixedPrice())
//                price = category.getFixedPrice();

        String multiplier = item.getCount() > 1 ? " x" + item.getCount() : "";

        holder.tvName.setText(String.format(Locale.US, "%s %s", item.getName(), multiplier));
        holder.tvPrice.setText(String.format(Locale.US, "%.2f ₪", price));

        holder.vDeleted.setVisibility(item.getChangeType().equals(ORDER_CHANGE_TYPE_DELETED) ? View.VISIBLE : View.GONE);
        holder.vAdded.setVisibility(item.getChangeType().equals(ORDER_CHANGE_TYPE_NEW) ? View.VISIBLE : View.GONE);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

}

