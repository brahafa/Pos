package com.pos.bringit.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pos.bringit.databinding.ItemRvDealInnerBinding;
import com.pos.bringit.models.DealInnerModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import static com.pos.bringit.utils.Utils.getImageRes;

public class DealAdapter extends RecyclerView.Adapter<DealAdapter.ViewHolder> {

    private List<DealInnerModel> itemList;
    private AdapterCallback adapterCallback;

    private int lastPos = 0;

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivIcon;
        private TextView tvName;
        private ImageView ivCheck;

        ViewHolder(ItemRvDealInnerBinding binding) {
            super(binding.getRoot());
            ivIcon = binding.ivFoodPic;
            tvName = binding.tvFoodName;
            ivCheck = binding.ivCheck;
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

        holder.ivIcon.setImageResource(getImageRes(item.getDealItem().getTypeName()));
        holder.ivIcon.setColorFilter(Color.argb(255, 255, 255, 255));
        holder.tvName.setText(item.getDealItem().getTypeName());
        holder.ivCheck.setVisibility(item.isComplete() ? View.VISIBLE : View.GONE);
        holder.itemView.setSelected(item.isSelected());

        holder.itemView.setOnClickListener(v -> {
            holder.itemView.setSelected(true);
            item.setSelected(true);
            if (lastPos != position) itemList.get(lastPos).setSelected(false);
            notifyItemChanged(lastPos);
            lastPos = position;
            adapterCallback.onItemClick(position);
        });
    }


    public void markComplete(int position) {
        if (!itemList.get(position).isComplete()) {
            itemList.get(position).setComplete(true);
            notifyItemChanged(position);
        }
    }


    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public interface AdapterCallback {
        void onItemClick(int position);
    }

}

