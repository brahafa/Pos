package com.pos.bringit.adapters;

import android.graphics.Color;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pos.bringit.R;
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

import static com.pos.bringit.utils.Constants.COLOR_GREEN;
import static com.pos.bringit.utils.Constants.COLOR_ORANGE;
import static com.pos.bringit.utils.Constants.COLOR_PURPLE;
import static com.pos.bringit.utils.Constants.COLOR_RED;
import static com.pos.bringit.utils.Constants.COLOR_YELLOW;
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
        private ImageView ivTag;
        private TextView tvGoToOrder;

        ViewHolder(ItemRvSearchResultsBinding binding) {
            super(binding.getRoot());

            tvName = binding.tvName;
            tvDate = binding.tvDate;
            tvPhone = binding.tvPhone;
            tvAddress = binding.tvAddress;
            tvOrderId = binding.tvOrderNum;
            ivTag = binding.ivTag;
            tvGoToOrder = binding.tvEdit;

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

        binding.getRoot().setBackgroundColor(Color.parseColor(viewType == 0 ? "#fbf6ff" : "#e9ddf3"));

        SpannableString content = new SpannableString("Go");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        binding.tvEdit.setText(content);

        return new ViewHolder(binding);

    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        OrderModel item = itemList.get(position);

        String fullName = "";
        String address = "";
        String phone = "";

        if (item.getClient() != null) {
            if (item.getClient().getFName() != null)
                fullName = item.getClient().getFName();
            if (item.getClient().getLName() != null)
                fullName = fullName + " " + item.getClient().getLName();

            if (item.getClient().getAddress() != null)
                address = item.getClient().getAddress().getCity()
                        + item.getClient().getAddress().getStreet()
                        + item.getClient().getAddress().getHouseNum();

            phone = item.getClient().getPhone();
        }

        String formattedDate = formatDate(item.getOrderTime());

        holder.tvName.setText(fullName);
        holder.tvDate.setText(formattedDate);
        holder.tvPhone.setText(phone);
        holder.tvAddress.setText(address);
        holder.tvOrderId.setText(item.getId());
        holder.ivTag.setImageResource(getTagDrawable(item.getColor()));

        holder.tvGoToOrder.setOnClickListener(v -> adapterCallback.onItemClick(item.getId()));

    }


    private String formatDate(String time) {

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdfIn = new SimpleDateFormat(PATTERN_DATE_FROM_SERVER);
        SimpleDateFormat sdfOut = new SimpleDateFormat("dd/MM/yy");

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

    private int getTagDrawable(String color) {
        if (color != null)
            switch (color) {
                case COLOR_GREEN: //green
                    return R.drawable.ic_icon_tag_green;
                case COLOR_PURPLE: //purple
                    return R.drawable.ic_icon_tag_purple;
                case COLOR_RED: //red
                    return R.drawable.ic_icon_tag_red;
                case COLOR_ORANGE: //orange
                    return R.drawable.ic_icon_tag_orange;
                case COLOR_YELLOW: //yellow
                    return R.drawable.ic_icon_tag_yellow;
                case "#ffffff": //white
                case "":
                default:
                    return R.drawable.ic_icon_tag;
            }
        return R.drawable.ic_icon_tag;
    }

    @Override
    public int getItemViewType(int position) {
        return position % 2;
    }
}

