package com.pos.bringit.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pos.bringit.databinding.ItemRvSearchResultsBinding;
import com.pos.bringit.models.OrderModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import static com.pos.bringit.utils.Constants.PATTERN_DATE_FROM_SERVER;

public class SearchOrderAdapter extends RecyclerView.Adapter<SearchOrderAdapter.ViewHolder> {

    private List<OrderModel> itemList = new ArrayList<>();
    private AdapterCallback adapterCallback;

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvName;
        private TextView tvDate;
        private TextView tvPhone;
        private TextView tvAddress;
        private TextView tvOrderId;
        private TextView tvGoToOrder;

        ViewHolder(ItemRvSearchResultsBinding binding) {
            super(binding.getRoot());

            tvName = binding.tvName;
            tvDate = binding.tvDate;
            tvPhone = binding.tvPhone;
            tvAddress = binding.tvAddress;
            tvOrderId = binding.tvOrderId;
            tvGoToOrder = binding.tvGoToOrder;

        }
    }

    public SearchOrderAdapter(AdapterCallback adapterCallback) {
        this.adapterCallback = adapterCallback;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRvSearchResultsBinding binding =
                ItemRvSearchResultsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);

    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        OrderModel item = itemList.get(position);

        String fullName = item.getClient().getFName() + " " + item.getClient().getLName();
        String formattedDate = formatDate(item.getOrderTime());
        String address = item.getClient().getAddress().getCity()
                + item.getClient().getAddress().getStreet()
                + item.getClient().getAddress().getHouseNum();

        holder.tvName.setText(fullName);
        holder.tvDate.setText(formattedDate);
        holder.tvPhone.setText(item.getClient().getPhone());
        holder.tvAddress.setText(address);
        holder.tvOrderId.setText(item.getId());

        holder.tvGoToOrder.setOnClickListener(v -> adapterCallback.onItemClick(item.getId()));

    }


    private String formatDate(String time) {

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdfIn = new SimpleDateFormat(PATTERN_DATE_FROM_SERVER);
        SimpleDateFormat sdfOut = new SimpleDateFormat("dd/MM/yy\nHH:mm");

        try {
            calendar.setTime(sdfIn.parse(time));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return sdfOut.format(calendar.getTime());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public interface AdapterCallback {
        void onItemClick(String orderId);
    }

    public void updateList(List<OrderModel> newList) {
        OrderItemsDiffCallback diffCallback = new OrderItemsDiffCallback(itemList, newList);
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);

        itemList.clear();
        itemList.addAll(newList);
        diffResult.dispatchUpdatesTo(this);
    }

}

