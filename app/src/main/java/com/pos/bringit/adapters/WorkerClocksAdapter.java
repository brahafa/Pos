package com.pos.bringit.adapters;

import android.graphics.Color;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pos.bringit.databinding.ItemRvWorkerTimesBinding;
import com.pos.bringit.models.ClocksModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import static com.pos.bringit.utils.Constants.PATTERN_DATE_FROM_SERVER;

public class WorkerClocksAdapter extends RecyclerView.Adapter<WorkerClocksAdapter.ViewHolder> {

    private List<ClocksModel> itemList = new ArrayList<>();
    private AdapterCallback adapterCallback;

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvStartTime;
        private TextView tvEndTime;
        private TextView tvTimeSpent;
        private TextView tvApproval;
        private TextView tvEdit;

        ViewHolder(ItemRvWorkerTimesBinding binding) {
            super(binding.getRoot());

            tvStartTime = binding.tvTimeStart;
            tvEndTime = binding.tvTimeEnd;
            tvTimeSpent = binding.tvTimeSpent;
            tvApproval = binding.tvApproval;
            tvEdit = binding.tvEdit;

        }
    }

    public WorkerClocksAdapter(AdapterCallback adapterCallback) {
        this.adapterCallback = adapterCallback;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRvWorkerTimesBinding binding =
                ItemRvWorkerTimesBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);

    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        ClocksModel item = itemList.get(position);

        String startTime = formatDate(item.getStartTime());
        String endTime = (item.getEndTime() != null) ? formatDate(item.getEndTime()) : "-";
        String timeSpent = (item.getEndTime() != null) ? countTimeSpent(item.getStartTime(), item.getEndTime()) : "-";

        holder.tvStartTime.setText(startTime);
        holder.tvEndTime.setText(endTime);
        holder.tvTimeSpent.setText(timeSpent);

        holder.tvStartTime.setTextColor(Color.parseColor("#2D2D2D"));
        holder.tvEndTime.setTextColor(Color.parseColor("#2D2D2D"));

        holder.tvEdit.setEnabled(item.getEndTime() != null && (!item.getApproveStart().equals("3") || !item.getApproveEnd().equals("3")));

//        set status
        if (item.getStartTime() != null && item.getEndTime() != null) {
//        waiting for approval
            if (item.getApproveStart().equals("4") || item.getApproveEnd().equals("4")) {
                SpannableStringBuilder builder = new SpannableStringBuilder();
                builder.append("ידנית" + "\n");

                String awaitingApprovalText = "ממתין לאישור מנהל";
                SpannableString yellowSpannable = new SpannableString(awaitingApprovalText);
                yellowSpannable.setSpan(new ForegroundColorSpan(Color.parseColor("#DBB34F")), 0, awaitingApprovalText.length(), 0);
                builder.append(yellowSpannable);

                holder.tvApproval.setText(builder, TextView.BufferType.SPANNABLE);
            }
//        approved
            else if (item.getApproveStart().equals("2") && item.getApproveEnd().equals("2"))
                holder.tvApproval.setText("אוטומטי");
            else {
                SpannableStringBuilder builder = new SpannableStringBuilder();
                builder.append("ידנית" + "\n");

                String approvedText = "מאושר";
                String declinedText = "דחה";
                String editedByManagerText = "Edited by manager";

                SpannableString purpleSpannable = new SpannableString(approvedText);
                purpleSpannable.setSpan(new ForegroundColorSpan(Color.parseColor("#503E9D")), 0, approvedText.length(), 0);
                SpannableString yellowSpannable = new SpannableString(declinedText);
                yellowSpannable.setSpan(new ForegroundColorSpan(Color.parseColor("#DBB34F")), 0, declinedText.length(), 0);
                SpannableString greenSpannable = new SpannableString(editedByManagerText);
                greenSpannable.setSpan(new ForegroundColorSpan(Color.parseColor("#419D3E")), 0, editedByManagerText.length(), 0);

                if ((item.getApproveStart().equals("3") || item.getApproveEnd().equals("3")))
                    builder.append(greenSpannable);
                else if ((item.getApproveStart().equals("0") || item.getApproveEnd().equals("0")))
                    builder.append(yellowSpannable);
                else if ((item.getApproveStart().equals("1") || item.getApproveEnd().equals("1")))
                    builder.append(purpleSpannable);

                holder.tvApproval.setText(builder, TextView.BufferType.SPANNABLE);
            }

            if (item.getApproveStart().equals("4")) holder.tvStartTime.setTextColor(Color.parseColor("#E93746"));
            if (item.getApproveEnd().equals("4")) holder.tvEndTime.setTextColor(Color.parseColor("#E93746"));
        }


        holder.tvEdit.setOnClickListener(v -> adapterCallback.onItemEdit(item));

    }

    private String countTimeSpent(String startTime, String endTime) {
        Calendar calendarStart = Calendar.getInstance();
        Calendar calendarEnd = Calendar.getInstance();
        SimpleDateFormat sdfIn = new SimpleDateFormat(PATTERN_DATE_FROM_SERVER);
        try {
            calendarStart.setTime(sdfIn.parse(startTime));
            calendarEnd.setTime(sdfIn.parse(endTime));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        double timeSpent = TimeUnit.MILLISECONDS.toMinutes(calendarEnd.getTimeInMillis() - calendarStart.getTimeInMillis());

        return String.format("%.1f", timeSpent / 60);
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
        void onItemEdit(ClocksModel item);
    }

    public void updateList(List<ClocksModel> newList) {
        ClockItemsDiffCallback diffCallback = new ClockItemsDiffCallback(itemList, newList);
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);

        itemList.clear();
        itemList.addAll(newList);
        diffResult.dispatchUpdatesTo(this);
    }

}

