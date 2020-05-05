package com.pos.bringit.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pos.bringit.R;
import com.pos.bringit.databinding.ItemRvDealInnerBinding;
import com.pos.bringit.models.DealInnerModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DealAdapter extends RecyclerView.Adapter<DealAdapter.ViewHolder> {
    private final String TYPE_ITEM = "item";
    private final String TYPE_FOLDER = "folder";

    private final String ITEM_TYPE_PIZZA = "Food";
    private final String ITEM_TYPE_DRINK = "Drink";
    private final String ITEM_TYPE_TOPPING = "Topping";
    private final String ITEM_TYPE_ADDITIONAL_OFFER = "AdditionalOffer";
    private final String ITEM_TYPE_DEAL = "Deal";

    private List<DealInnerModel> itemList;
    private AdapterCallback adapterCallback;

    private View lastView = null;

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivIcon;
        private TextView tvName;
        private View vSelected;

        ViewHolder(ItemRvDealInnerBinding binding) {
            super(binding.getRoot());
            ivIcon = binding.ivFoodPic;
            tvName = binding.tvFoodName;
            vSelected = binding.vSelected;
        }
    }

    public DealAdapter(List<DealInnerModel> itemList, AdapterCallback adapterCallback) {
        this.itemList = itemList;
        this.adapterCallback = adapterCallback;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRvDealInnerBinding binding =
                ItemRvDealInnerBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        DealInnerModel item = itemList.get(position);

        holder.ivIcon.setImageResource(getImageRes(item.getObjectType()));
        holder.tvName.setText(item.getName());

        holder.itemView.setOnClickListener(v -> {
            holder.vSelected.setVisibility(View.VISIBLE);
            if (lastView != null && lastView != holder.vSelected) lastView.setVisibility(View.GONE);
            lastView = holder.vSelected;
            adapterCallback.onItemClick(item.getObjectType(), position);
        });


    }

    private int getImageRes(String objectType) {
        int resID;

        switch (objectType) {
            case ITEM_TYPE_PIZZA:
                resID = R.drawable.ic_pizza;
                break;
            case ITEM_TYPE_DRINK:
                resID = R.drawable.ic_drink;
                break;
            case ITEM_TYPE_ADDITIONAL_OFFER:
                resID = R.drawable.ic_food;
                break;
            case ITEM_TYPE_DEAL:
                resID = R.drawable.ic_deal;
                break;
            case ITEM_TYPE_TOPPING:
            default:
                resID = R.drawable.ic_topping;

        }
        return resID;
    }


    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public interface AdapterCallback {
        void onItemClick(String type, int position);
    }

}

