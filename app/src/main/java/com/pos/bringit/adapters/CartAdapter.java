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
import com.pos.bringit.models.CategoryModel;
import com.pos.bringit.models.InnerProductsModel;
import com.pos.bringit.models.ProductItemModel;
import com.pos.bringit.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.Group;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static com.pos.bringit.utils.Constants.BUSINESS_ITEMS_TYPE_DEAL;
import static com.pos.bringit.utils.Constants.BUSINESS_ITEMS_TYPE_PIZZA;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    private Context context;
    private final String type;
    private List<ProductItemModel> itemList;
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

    public CartAdapter(Context context, String type, AdapterCallback adapterCallback) {
        this.context = context;
        this.type = type;
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
        ProductItemModel item = itemList.get(position);

        holder.itemView.setBackgroundResource(item.getTypeName().equals(BUSINESS_ITEMS_TYPE_DEAL)
                ? R.drawable.selector_cart_deal_bg
                : R.drawable.selector_cart_food_bg);
        holder.tvName.setText(item.getName());
        holder.tvPrice.setText(countPrice(item) + " ₪");


        switch (item.getTypeName()) {
            case BUSINESS_ITEMS_TYPE_DEAL:
                holder.rvToppings.setLayoutManager(new LinearLayoutManager(context));

                CartDealItemsAdapter mCartDealItemsAdapter =
                        new CartDealItemsAdapter(context, item.getDealItems(), 0);// fixme count free toppings in deal
                holder.rvToppings.setAdapter(mCartDealItemsAdapter);
                break;
            case BUSINESS_ITEMS_TYPE_PIZZA:
                holder.rvToppings.setLayoutManager(new FlexboxLayoutManager(context, FlexDirection.ROW_REVERSE));

                CartToppingAdapter mCartToppingAdapter = new CartToppingAdapter(item.getCategories(), item.getShape());
                holder.rvToppings.setAdapter(mCartToppingAdapter);
                break;
            default:
                holder.rvToppings.setLayoutManager(new FlexboxLayoutManager(context, FlexDirection.ROW_REVERSE));

                CartFillingAdapter mCartFillingAdapter = new CartFillingAdapter(item.getCategories());
                holder.rvToppings.setAdapter(mCartFillingAdapter);
                break;
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

    public void updateList(List<ProductItemModel> newList) {
        CartItemsDiffCallback diffCallback = new CartItemsDiffCallback(itemList, newList);
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);

        itemList.clear();
        itemList.addAll(newList);
        diffResult.dispatchUpdatesTo(this);
    }

    public void addItem(ProductItemModel item) {
        itemList.add(item);
        notifyItemInserted(getItemCount() - 1);
        selectedPos = getItemCount() - 1;
    }

    public void duplicateItem(ProductItemModel duplicateItem) {
        ProductItemModel item = duplicateItem.clone();

        itemList.add(item);
        notifyItemInserted(getItemCount() - 1);
        selectedPos = getItemCount() - 1;
        adapterCallback.onItemDuplicated();
    }

    public void editItem(ProductItemModel item) {
        itemList.set(selectedPos, item);
        notifyItemChanged(selectedPos);
    }

    private void removeItem(int position, boolean isActive) {
        itemList.remove(position);
        notifyItemRemoved(position);
        if (position < selectedPos) selectedPos--;
        adapterCallback.onActiveItemRemoved(isActive);
    }

    private double countPrice(ProductItemModel item) {
        double totalPriceSum = 0;

        totalPriceSum += type.equals(Constants.NEW_ORDER_TYPE_DELIVERY)
                ? item.getDeliveryPrice()
                : item.getNotDeliveryPrice();

//        toppings price
        if (!item.getCategories().isEmpty())
            for (CategoryModel category : item.getCategories())
                for (int i = 0; i < category.getProducts().size(); i++) {
                    InnerProductsModel topping = category.getProducts().get(i);
//                        checking of fixed price
                    if (category.getCategoryHasFixedPrice() == 1 && i < category.getProductsFixedPrice())
                        totalPriceSum += category.getFixedPrice();
                    else
                        totalPriceSum += topping.getPrice();
                }

//        deal items price //todo learn how to count deal price right
//        if (!item.getDealItems().isEmpty()) {
//            for (DealItemModel dealItem : item.getDealItems()) {
//
//                if (!dealItem.getProducts().isEmpty()) {
//                    ProductItemModel dealProduct = dealItem.getProducts().get(0);
//
//                    totalPriceSum += type.equals(Constants.NEW_ORDER_TYPE_DELIVERY)
//                            ? dealProduct.getDeliveryPrice()
//                            : dealProduct.getNotDeliveryPrice();
//
////                deal toppings price
//                    if (!dealProduct.getCategories().isEmpty())
//                        for (CategoryModel category : dealProduct.getCategories())
//                            for (InnerProductsModel topping : category.getProducts())
//                                totalPriceSum += topping.getPrice();
//                }
//            }
//        }

        return totalPriceSum;
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public List<ProductItemModel> getItems() {
        return itemList;
    }

    public interface AdapterCallback {
        void onItemClick(ProductItemModel fatherItem);

        void onItemDuplicated();

        void onActiveItemRemoved(boolean isActive);
    }

}

