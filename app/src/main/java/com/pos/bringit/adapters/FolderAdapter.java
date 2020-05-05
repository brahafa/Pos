package com.pos.bringit.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pos.bringit.R;
import com.pos.bringit.databinding.ItemRvFolderBinding;
import com.pos.bringit.databinding.ItemRvFoodBinding;
import com.pos.bringit.models.FolderItemModel;
import com.pos.bringit.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

public class FolderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final String TYPE_ITEM = "item";
    private final String TYPE_FOLDER = "folder";

    private final String ITEM_TYPE_PIZZA = "Food";
    private final String ITEM_TYPE_DRINK = "Drink";
    private final String ITEM_TYPE_TOPPING = "Topping";
    private final String ITEM_TYPE_ADDITIONAL_OFFER = "AdditionalOffer";
    private final String ITEM_TYPE_DEAL = "Deal";

    private List<FolderItemModel> itemList = new ArrayList<>();
    private String type;
    private AdapterCallback adapterCallback;

    class FoodViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivIcon;
        private TextView tvName;
        private TextView tvPrize;

        FoodViewHolder(ItemRvFoodBinding binding) {
            super(binding.getRoot());
            ivIcon = binding.ivFoodPic;
            tvName = binding.tvFoodName;
            tvPrize = binding.tvFoodPrize;
        }
    }

    class FolderViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName;
        private TextView tvFolderCount;

        FolderViewHolder(ItemRvFolderBinding binding) {
            super(binding.getRoot());
            tvName = binding.tvFolderName;
            tvFolderCount = binding.tvFolderCount;
        }
    }

    public FolderAdapter(String type, AdapterCallback adapterCallback) {
        this.type = type;
        this.adapterCallback = adapterCallback;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 0) {
            ItemRvFoodBinding binding =
                    ItemRvFoodBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new FoodViewHolder(binding);
        } else {
            ItemRvFolderBinding binding =
                    ItemRvFolderBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new FolderViewHolder(binding);
        }

    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        FolderItemModel item = itemList.get(position);
        if (getItemViewType(position) == 0) {

            ((FoodViewHolder) holder).itemView.setBackgroundResource(
                    item.getObjectType().equals(ITEM_TYPE_DEAL)
                            ? R.drawable.background_item_deal
                            : R.drawable.background_item_food);
            ((FoodViewHolder) holder).ivIcon.setImageResource(getImageRes(item.getObjectType()));
            ((FoodViewHolder) holder).tvName.setText(item.getName());
            ((FoodViewHolder) holder).tvPrize.setText(
                    type.equals(Constants.NEW_ORDER_TYPE_DELIVERY)
                            ? item.getDeliveryPrice() + " ₪"
                            : item.getPickupPrice() + " ₪");
            ((FoodViewHolder) holder).itemView.setOnClickListener(v -> adapterCallback.onItemClick(item.getObjectType(), item.getObjectId()));
        } else {
            ((FolderViewHolder) holder).tvName.setText(item.getName());
//            ((FolderViewHolder) holder).tvFolderCount.setText(item.getFolderCount()); //todo get count from back
            ((FolderViewHolder) holder).itemView.setOnClickListener(v -> adapterCallback.onFolderClick(item.getId()));

        }

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
        void onItemClick(String type, String itemId);

        void onFolderClick(String folderId);
    }

    public void updateList(List<FolderItemModel> newList) {
        FolderItemsDiffCallback diffCallback = new FolderItemsDiffCallback(itemList, newList);
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);

        itemList.clear();
        itemList.addAll(newList);
        diffResult.dispatchUpdatesTo(this);
    }

    @Override
    public int getItemViewType(int position) {
        return (itemList.get(position).getType().equals(TYPE_FOLDER)) ? 1 : 0;
    }
}

