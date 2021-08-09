package com.pos.bringit.dialog;

import android.app.Dialog;
import android.content.Context;

import com.pos.bringit.databinding.DialogFutureOrderBinding;
import com.pos.bringit.utils.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import androidx.annotation.NonNull;

import static com.pos.bringit.utils.Constants.PATTERN_DATE_FROM_SERVER;

public class FutureOrderDialog extends Dialog {

    private DialogFutureOrderBinding binding;
    private Context mContext;
    private SaveClocksListener mListener;
    private Calendar calendarStart = Calendar.getInstance();

    public FutureOrderDialog(@NonNull final Context context, String futureTime, SaveClocksListener listener) {
        super(context);
        mContext = context;
        mListener = listener;
        binding = DialogFutureOrderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        configureView(futureTime);

        fillInfo();

        binding.tvSave.setOnClickListener(v -> fillModel());
        binding.ivClose.setOnClickListener(v -> dismiss());

    }

    private void configureView(String futureTime) {


        if (futureTime != null) {
            SimpleDateFormat sdfIn = new SimpleDateFormat(PATTERN_DATE_FROM_SERVER);
            try {
                calendarStart.setTime(sdfIn.parse(futureTime));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        binding.npStartTimeHour.setMinValue(0);
        binding.npStartTimeHour.setMaxValue(23);

        binding.npStartTimeMin.setMinValue(0);
        binding.npStartTimeMin.setMaxValue(59);

        binding.npStartTimeSec.setMinValue(0);
        binding.npStartTimeSec.setMaxValue(59);

        binding.cvFutureCalendar.setOnDateChangeListener((view, year, month, dayOfMonth) -> calendarStart.set(year, month, dayOfMonth));

        binding.npStartTimeHour.setOnValueChangedListener((picker, oldVal, newVal) -> {
            if (oldVal == 23 && newVal == 0) {
                calendarStart.add(Calendar.DAY_OF_MONTH, 1);
                binding.cvFutureCalendar.setDate(calendarStart.getTimeInMillis());
            } else if (oldVal == 0 && newVal == 23) {
                calendarStart.add(Calendar.DAY_OF_MONTH, -1);
                binding.cvFutureCalendar.setDate(calendarStart.getTimeInMillis());
            }
        });
    }


    private void fillInfo() {

        binding.cvFutureCalendar.setDate(calendarStart.getTimeInMillis());

        binding.npStartTimeHour.setValue(calendarStart.get((Calendar.HOUR_OF_DAY)));
        binding.npStartTimeMin.setValue(calendarStart.get((Calendar.MINUTE)));
        binding.npStartTimeSec.setValue(calendarStart.get((Calendar.SECOND)));

    }

    private void fillModel() {

//        calendarStart.setTimeInMillis(binding.cvStartCalendar.getDate());
        calendarStart.set(Calendar.HOUR_OF_DAY, binding.npStartTimeHour.getValue());
        calendarStart.set(Calendar.MINUTE, binding.npStartTimeMin.getValue());
        calendarStart.set(Calendar.SECOND, binding.npStartTimeSec.getValue());

        if (calendarStart.getTimeInMillis() >= Calendar.getInstance().getTimeInMillis()) {
            SimpleDateFormat sdfOut = new SimpleDateFormat(PATTERN_DATE_FROM_SERVER);
            mListener.onSaved(sdfOut.format(calendarStart.getTime()));
            dismiss();
        } else {
            Utils.openAlertDialog(mContext, "Choose date in future", "Warning");
        }
    }

    public interface SaveClocksListener {
        void onSaved(String futureTime);
    }

}

