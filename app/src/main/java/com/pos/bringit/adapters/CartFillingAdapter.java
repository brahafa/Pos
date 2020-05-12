package com.pos.bringit.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pos.bringit.databinding.ItemRvCartFillingBinding;
import com.pos.bringit.models.CartFillingModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CartFillingAdapter extends RecyclerView.Adapter<CartFillingAdapter.ViewHolder> {

    private List<CartFillingModel> itemList;

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName;

        ViewHolder(ItemRvCartFillingBinding binding) {
            super(binding.getRoot());
            tvName = binding.tvFillingName;
        }
    }

    public CartFillingAdapter(List<CartFillingModel> fillings) {
        itemList = fillings;
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
        CartFillingModel item = itemList.get(position);

        holder.tvName.setText(String.format("%s %s", item.getName(), item.getPrice()));
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

}

