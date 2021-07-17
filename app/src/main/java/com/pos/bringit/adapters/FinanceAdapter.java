package com.pos.bringit.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pos.bringit.databinding.ItemRvFinanceSesionsBinding;
import com.pos.bringit.models.FinanceItem;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import static com.pos.bringit.utils.Constants.PATTERN_DATE_FROM_SERVER;

public class FinanceAdapter extends RecyclerView.Adapter<FinanceAdapter.ViewHolder> {

    private List<FinanceItem> itemList = new ArrayList<>();

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvName;
        private TextView tvSDate;
        private TextView tvSTime;
        private TextView tvFund;
        private TextView tvBalance;
        private TextView tvCredit;
        private TextView tvCash;
        private TextView tvFDate;
        private TextView tvFTime;
        private TextView tvFName;

        ViewHolder(ItemRvFinanceSesionsBinding binding) {
            super(binding.getRoot());

            tvName = binding.tvName;
            tvSDate = binding.tvSDate;
            tvSTime = binding.tvSTime;
            tvFund = binding.tvFund;
            tvBalance = binding.tvBalance;
            tvCredit = binding.tvCredit;
            tvCash = binding.tvCash;
            tvFDate = binding.tvFDate;
            tvFTime = binding.tvFTime;
            tvFName = binding.tvFName;

        }
    }

    public FinanceAdapter() {
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRvFinanceSesionsBinding binding =
                ItemRvFinanceSesionsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);

    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        FinanceItem item = itemList.get(position);

        String sName = item.getOpenedBy();
        String fName = item.getClosedBy();
        String fund = item.getSum().getFund();
        String balance = String.valueOf(item.getSum().getBalance());
        String credit = String.valueOf(item.getSum().getCredit());
        String cash = String.valueOf(item.getSum().getCash());

        String formattedDate = formatDate(item.getOpenedAt());
        String formattedTime = formatTime(item.getOpenedAt());
        String formattedFDate = formatDate(item.getClosedAt());
        String formattedFTime = formatTime(item.getClosedAt());

        holder.tvName.setText(sName);
        holder.tvSDate.setText(formattedDate);
        holder.tvSTime.setText(formattedTime);

        holder.tvFund.setText(fund);
        holder.tvBalance.setText(balance);
        holder.tvCredit.setText(credit);
        holder.tvCash.setText(cash);

        holder.tvFDate.setText(formattedFDate);
        holder.tvFTime.setText(formattedFTime);
        holder.tvFName.setText(fName);

    }


    private String formatDate(String time) {

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdfIn = new SimpleDateFormat(PATTERN_DATE_FROM_SERVER);
        SimpleDateFormat sdfOutDate = new SimpleDateFormat("dd/MM/yy");

        try {
            calendar.setTime(sdfIn.parse(time));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return sdfOutDate.format(calendar.getTime());
    }

    private String formatTime(String time) {

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdfIn = new SimpleDateFormat(PATTERN_DATE_FROM_SERVER);
        SimpleDateFormat sdfOutTime = new SimpleDateFormat("HH:mm");

        try {
            calendar.setTime(sdfIn.parse(time));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return sdfOutTime.format(calendar.getTime());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public void updateList(List<FinanceItem> newList) {
        FinanceItemsDiffCallback diffCallback = new FinanceItemsDiffCallback(itemList, newList);
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);

        itemList.clear();
        itemList.addAll(newList);
        diffResult.dispatchUpdatesTo(this);
    }

}

