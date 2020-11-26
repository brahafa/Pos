package com.pos.bringit.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pos.bringit.R;
import com.pos.bringit.databinding.ItemRvCartToppingBinding;
import com.pos.bringit.models.BusinessModel;
import com.pos.bringit.models.CategoryModel;
import com.pos.bringit.models.InnerProductsModel;
import com.pos.bringit.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import static com.pos.bringit.utils.Constants.BUSINESS_TOPPING_TYPE_FIXED;
import static com.pos.bringit.utils.Constants.BUSINESS_TOPPING_TYPE_LAYER;
import static com.pos.bringit.utils.Constants.BUSINESS_TOPPING_TYPE_QUARTER;
import static com.pos.bringit.utils.Constants.PIZZA_TYPE_BL;
import static com.pos.bringit.utils.Constants.PIZZA_TYPE_BR;
import static com.pos.bringit.utils.Constants.PIZZA_TYPE_FULL;
import static com.pos.bringit.utils.Constants.PIZZA_TYPE_LH;
import static com.pos.bringit.utils.Constants.PIZZA_TYPE_RH;
import static com.pos.bringit.utils.Constants.PIZZA_TYPE_TL;
import static com.pos.bringit.utils.Constants.PIZZA_TYPE_TR;

public class CartToppingAdapter extends RecyclerView.Adapter<CartToppingAdapter.ViewHolder> {

    private List<InnerProductsModel> itemList;
    private CategoryModel category;
    private String pizzaType;

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

    public CartToppingAdapter(CategoryModel category, String pizzaType) {
        itemList = new ArrayList<>();
        this.category = category;
        itemList.addAll(category.getProducts());
        this.pizzaType = pizzaType;
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
        InnerProductsModel item = itemList.get(position);

        double price = item.getPrice();
        if (item.getLocation() != null)
            switch (BusinessModel.getInstance().getTopping_method_display()) {
                case BUSINESS_TOPPING_TYPE_QUARTER:
                    switch (item.getLocation()) {
                        case PIZZA_TYPE_TR:
                        case PIZZA_TYPE_TL:
                        case PIZZA_TYPE_BR:
                        case PIZZA_TYPE_BL:
                            price = ((double) item.getPrice()) / 4; //quarter price
                            break;
                        case PIZZA_TYPE_RH:
                        case PIZZA_TYPE_LH:
                            price = ((double) item.getPrice()) / 2; //half price
                            break;
                        case PIZZA_TYPE_FULL:
                        default:
                            price = item.getPrice();
                            break;
                    }
                    break;
                case BUSINESS_TOPPING_TYPE_LAYER:
                    break;
                case BUSINESS_TOPPING_TYPE_FIXED:
                default:
                    price = item.getPrice();
                    break;
            }

//        handle category fix price
        if (category.getCategoryHasFixedPrice())
            if (category.getProducts().indexOf(item) < category.getProductsFixedPrice())
                price = category.getFixedPrice();

        holder.ivType.setVisibility(
                pizzaType.equals(Constants.PIZZA_TYPE_CIRCLE) ? View.VISIBLE : View.GONE);
        holder.ivTypeRect.setVisibility(
                pizzaType.equals(Constants.PIZZA_TYPE_RECTANGLE) ? View.VISIBLE : View.GONE);
        holder.ivTypeSlice.setVisibility(
                pizzaType.equals(Constants.PIZZA_TYPE_ONE_SLICE) ? View.VISIBLE : View.GONE);

        if (BusinessModel.getInstance().getTopping_method_display().equals(BUSINESS_TOPPING_TYPE_LAYER))
            holder.tvName.setText(item.getName());
        else
            holder.tvName.setText(String.format("%s %s ₪", item.getName(), price));

        switch (pizzaType) {
            case Constants.PIZZA_TYPE_CIRCLE:
                holder.ivType.setImageResource(getImageCircleRes(item.getLocation()));
                break;
            case Constants.PIZZA_TYPE_RECTANGLE:
                holder.ivTypeRect.setImageResource(getImageResRect(item.getLocation()));
                break;
        }

        //todo understand what ot do with change types
//        holder.vDeleted.setVisibility(item.getChangeType().equals(ORDER_CHANGE_TYPE_DELETED) ? View.VISIBLE : View.GONE);
//        holder.vAdded.setVisibility(item.getChangeType().equals(ORDER_CHANGE_TYPE_NEW) ? View.VISIBLE : View.GONE);
    }

    private int getImageCircleRes(String viewType) {
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

}

