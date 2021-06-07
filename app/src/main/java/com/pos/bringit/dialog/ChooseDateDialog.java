package com.pos.bringit.dialog;

import android.app.Dialog;
import android.content.Context;

import com.pos.bringit.databinding.DialogChooseDateBinding;
import com.pos.bringit.utils.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import androidx.annotation.NonNull;

import static com.pos.bringit.utils.Constants.PATTERN_DATE_TO_SEND;
import static com.pos.bringit.utils.Constants.PATTERN_DATE_TO_SHOW;


public class ChooseDateDialog extends Dialog {

    private DialogChooseDateBinding binding;
    private Context mContext;
    private SaveDateListener mListener;
    private Calendar calendarStart = Calendar.getInstance();
    private Calendar calendarEnd = Calendar.getInstance();

    public ChooseDateDialog(@NonNull final Context context, String startDate, String endDate, SaveDateListener listener) {
        super(context);
        mContext = context;
        mListener = listener;
        binding = DialogChooseDateBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        configureView(startDate, endDate);

        fillInfo();

        binding.tvSave.setOnClickListener(v -> fillModel());

    }

    private void configureView(String startDate, String endDate) {


        if (startDate != null && endDate != null) {
            SimpleDateFormat sdfIn = new SimpleDateFormat(PATTERN_DATE_TO_SHOW);
            try {
                calendarStart.setTime(sdfIn.parse(startDate));
                calendarEnd.setTime(sdfIn.parse(endDate));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        binding.cvStartCalendar.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            calendarStart.set(year, month, dayOfMonth);
            fillStringDates();
        });
        binding.cvEndCalendar.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            calendarEnd.set(year, month, dayOfMonth);
            fillStringDates();
        });

    }


    private void fillInfo() {

        binding.cvStartCalendar.setDate(calendarStart.getTimeInMillis());
        binding.cvEndCalendar.setDate(calendarEnd.getTimeInMillis());
        fillStringDates();
    }

    private void fillModel() {

        if (calendarEnd.getTimeInMillis() >= calendarStart.getTimeInMillis()) {

            String startDate = generateStringDateToSend(calendarStart);
            String endDate = generateStringDateToSend(calendarEnd);

            mListener.onSaved(startDate, endDate, binding.tvDate.getText().toString());
            dismiss();
        } else {
            Utils.openAlertDialog(mContext, "End date must be after start date", "Warning");
        }
    }

    private String generateStringDate(Calendar calendar) {
        SimpleDateFormat sdfOut = new SimpleDateFormat(PATTERN_DATE_TO_SHOW);
        return sdfOut.format(calendar.getTime());
    }

    private String generateStringDateToSend(Calendar calendar) {
        SimpleDateFormat sdfOut = new SimpleDateFormat(PATTERN_DATE_TO_SEND);
        return sdfOut.format(calendar.getTime());
    }

    private void fillStringDates() {
        binding.tvDate.setText(String.format("%s - %s", generateStringDate(calendarEnd), generateStringDate(calendarStart)));
    }

    public interface SaveDateListener {
        void onSaved(String startDate, String endDate, String showDate);
    }

}

