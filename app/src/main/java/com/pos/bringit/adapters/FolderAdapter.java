package com.pos.bringit.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pos.bringit.databinding.ItemRvFolderBinding;
import com.pos.bringit.databinding.ItemRvFoodBinding;
import com.pos.bringit.models.FolderItemModel;
import com.pos.bringit.utils.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.Group;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import static com.pos.bringit.utils.Utils.getImageRes;

public class FolderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final String TYPE_ITEM = "item";
    private final String TYPE_FOLDER = "folder";

    private List<FolderItemModel> itemList = new ArrayList<>();
    private String type;
    private AdapterCallback adapterCallback;

    class FoodViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivIcon;
        private TextView tvName;
        private TextView tvPrize;
        private Group gOutOfStock;

        FoodViewHolder(ItemRvFoodBinding binding) {
            super(binding.getRoot());
            ivIcon = binding.ivFoodPic;
            tvName = binding.tvFoodName;
            tvPrize = binding.tvFoodPrize;
            gOutOfStock = binding.gOutOfStock;
        }
    }

    class FolderViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName;

        FolderViewHolder(ItemRvFolderBinding binding) {
            super(binding.getRoot());
            tvName = binding.tvFolderName;
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
//        food
        if (getItemViewType(position) == 0) {
            FoodViewHolder holderFood = (FoodViewHolder) holder;

            holderFood.gOutOfStock.setVisibility(item.getInInventory().equals("0") ? View.VISIBLE : View.GONE);
            holderFood.itemView.setEnabled(!item.getInInventory().equals("0"));

//            holderFood.itemView.setBackgroundResource(
//                    item.getTypeName().equals(BUSINESS_ITEMS_TYPE_DEAL)
//                            ? R.drawable.background_item_deal
//                            : R.drawable.background_item_food);
            holderFood.ivIcon.setImageResource(getImageRes(item.getTypeName()));
            holderFood.tvName.setText(item.getName());
            holderFood.tvPrize.setText(String.format(Locale.US, "%.2f ₪",
                    type.equals(Constants.NEW_ORDER_TYPE_DELIVERY)
                            ? item.getDeliveryPrice()
                            : item.getNotDeliveryPrice()));
            holderFood.itemView.setOnClickListener(v -> {
                item.setPrice(type.equals(Constants.NEW_ORDER_TYPE_DELIVERY)
                        ? item.getDeliveryPrice()
                        : item.getNotDeliveryPrice());
                adapterCallback.onItemClick(item);
            });
        }
//        folder
        else {
            FolderViewHolder holderFolder = (FolderViewHolder) holder;

            holderFolder.tvName.setText(item.getName());
            holderFolder.itemView.setOnClickListener(v -> adapterCallback.onFolderClick(item.getId()));

        }

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public interface AdapterCallback {
        void onItemClick(FolderItemModel item);

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

