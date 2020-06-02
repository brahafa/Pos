package com.pos.bringit.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pos.bringit.databinding.ItemRvAutocompleteBinding;
import com.pos.bringit.models.AutocompleteModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AutocompleteAdapter extends RecyclerView.Adapter<AutocompleteAdapter.ViewHolder> {

    private List<AutocompleteModel> itemList;
    private AdapterCallback adapterCallback;


    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvName;

        ViewHolder(ItemRvAutocompleteBinding binding) {
            super(binding.getRoot());

            tvName = binding.tvName;

        }
    }

    public AutocompleteAdapter(List<AutocompleteModel> itemList, AdapterCallback adapterCallback) {
        this.itemList = itemList;
        this.adapterCallback = adapterCallback;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRvAutocompleteBinding binding =
                ItemRvAutocompleteBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        AutocompleteModel item = itemList.get(position);

        holder.tvName.setText(item.getName());

        holder.itemView.setOnClickListener(v -> adapterCallback.onItemSelected(item));

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public interface AdapterCallback {
        void onItemSelected(AutocompleteModel orderItem);
    }

    public void updateList(List<AutocompleteModel> newList) {
        itemList.clear();
        itemList.addAll(newList);
        notifyDataSetChanged();
    }

    public void clearList() {
        itemList.clear();
        notifyDataSetChanged();
    }

}

