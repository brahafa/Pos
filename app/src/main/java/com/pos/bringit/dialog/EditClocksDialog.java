package com.pos.bringit.dialog;

import android.app.Dialog;
import android.content.Context;

import com.pos.bringit.databinding.DialogEditClocksBinding;
import com.pos.bringit.models.ClocksModel;
import com.pos.bringit.models.ClocksSendModel;
import com.pos.bringit.utils.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import androidx.annotation.NonNull;

import static com.pos.bringit.utils.Constants.PATTERN_DATE_FROM_SERVER;


public class EditClocksDialog extends Dialog {

    private DialogEditClocksBinding binding;
    private Context mContext;
    private SaveClocksListener mListener;
    private Calendar calendarStart = Calendar.getInstance();
    private Calendar calendarEnd = Calendar.getInstance();

    public EditClocksDialog(@NonNull final Context context, ClocksModel model, SaveClocksListener listener) {
        super(context);
        mContext = context;
        mListener = listener;
        binding = DialogEditClocksBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        configureView(model);

        fillInfo();

        binding.tvSave.setOnClickListener(v -> {
            fillModel(model);
        });

    }

    private void configureView(ClocksModel model) {


        if (model.getStartTime() != null && model.getEndTime() != null) {
            SimpleDateFormat sdfIn = new SimpleDateFormat(PATTERN_DATE_FROM_SERVER);
            try {
                calendarStart.setTime(sdfIn.parse(model.getStartTime()));
                calendarEnd.setTime(sdfIn.parse(model.getEndTime()));
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

        binding.npEndTimeHour.setMinValue(0);
        binding.npEndTimeHour.setMaxValue(23);

        binding.npEndTimeMin.setMinValue(0);
        binding.npEndTimeMin.setMaxValue(59);

        binding.npEndTimeSec.setMinValue(0);
        binding.npEndTimeSec.setMaxValue(59);

        binding.cvStartCalendar.setOnDateChangeListener((view, year, month, dayOfMonth) -> calendarStart.set(year, month, dayOfMonth));
        binding.cvEndCalendar.setOnDateChangeListener((view, year, month, dayOfMonth) -> calendarEnd.set(year, month, dayOfMonth));

        binding.npStartTimeHour.setOnValueChangedListener((picker, oldVal, newVal) -> {
            if (oldVal == 23 && newVal == 0) {
                calendarStart.add(Calendar.DAY_OF_MONTH, 1);
                binding.cvStartCalendar.setDate(calendarStart.getTimeInMillis());
            } else if (oldVal == 0 && newVal == 23) {
                calendarStart.add(Calendar.DAY_OF_MONTH, -1);
                binding.cvStartCalendar.setDate(calendarStart.getTimeInMillis());
            }
        });

        binding.npEndTimeHour.setOnValueChangedListener((picker, oldVal, newVal) -> {
            if (oldVal == 23 && newVal == 0) {
                calendarEnd.add(Calendar.DAY_OF_MONTH, 1);
                binding.cvEndCalendar.setDate(calendarEnd.getTimeInMillis());
            } else if (oldVal == 0 && newVal == 23) {
                calendarEnd.add(Calendar.DAY_OF_MONTH, -1);
                binding.cvEndCalendar.setDate(calendarEnd.getTimeInMillis());
            }
        });


    }


    private void fillInfo() {

        binding.cvStartCalendar.setDate(calendarStart.getTimeInMillis());
        binding.cvEndCalendar.setDate(calendarEnd.getTimeInMillis());

        binding.npStartTimeHour.setValue(calendarStart.get((Calendar.HOUR_OF_DAY)));
        binding.npStartTimeMin.setValue(calendarStart.get((Calendar.MINUTE)));
        binding.npStartTimeSec.setValue(calendarStart.get((Calendar.SECOND)));

        binding.npEndTimeHour.setValue(calendarEnd.get((Calendar.HOUR_OF_DAY)));
        binding.npEndTimeMin.setValue(calendarEnd.get((Calendar.MINUTE)));
        binding.npEndTimeSec.setValue(calendarEnd.get((Calendar.SECOND)));


    }

    private void fillModel(ClocksModel model) {

//        calendarStart.setTimeInMillis(binding.cvStartCalendar.getDate());
        calendarStart.set(Calendar.HOUR_OF_DAY, binding.npStartTimeHour.getValue());
        calendarStart.set(Calendar.MINUTE, binding.npStartTimeMin.getValue());
        calendarStart.set(Calendar.SECOND, binding.npStartTimeSec.getValue());

//        calendarEnd.setTimeInMillis(binding.cvEndCalendar.getDate());
        calendarEnd.set(Calendar.HOUR_OF_DAY, binding.npEndTimeHour.getValue());
        calendarEnd.set(Calendar.MINUTE, binding.npEndTimeMin.getValue());
        calendarEnd.set(Calendar.SECOND, binding.npEndTimeSec.getValue());

        if (calendarEnd.getTimeInMillis() > calendarStart.getTimeInMillis()) {
            SimpleDateFormat sdfOut = new SimpleDateFormat(PATTERN_DATE_FROM_SERVER);

            ClocksSendModel sendModel = new ClocksSendModel(
                    model.getId(),
                    model.getWorkerId(),
                    sdfOut.format(calendarStart.getTime()),
                    sdfOut.format(calendarEnd.getTime()));

            mListener.onSaved(sendModel);
            dismiss();
        } else {
            Utils.openAlertDialog(mContext, "End date must be after start date", "Warning");
        }
    }

    public interface SaveClocksListener {
        void onSaved(ClocksSendModel model);
    }

}

