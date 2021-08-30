package com.pos.bringit.activities;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.pos.bringit.adapters.WorkerClocksAdapter;
import com.pos.bringit.databinding.ActivityWorkersClockBinding;
import com.pos.bringit.dialog.AutoHideDialog;
import com.pos.bringit.dialog.EditClocksDialog;
import com.pos.bringit.dialog.EditTimeDialog;
import com.pos.bringit.models.ClocksModel;
import com.pos.bringit.models.WorkerModel;
import com.pos.bringit.network.Request;
import com.pos.bringit.utils.MyExceptionHandler;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

public class WorkersClockActivity extends AppCompatActivity {

    private final String INTERVAL_THIS_WEEK = "THIS_WEEK";
    private final String INTERVAL_THIS_MONTH = "THIS_MONTH";
    private final String INTERVAL_LAST_MONTH = "LAST_MONTH";
    private final String INTERVAL_LAST_MONTH_2 = "LAST_MONTH_2";
    private final String INTERVAL_LAST_MONTH_3 = "LAST_MONTH_3";

    private ActivityWorkersClockBinding binding;

    private WorkerClocksAdapter mClocksAdapter = new WorkerClocksAdapter(this::openEditClocks);

    private String workerId;
    private String interval = INTERVAL_THIS_WEEK;
    private boolean isEnd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWorkersClockBinding.inflate(getLayoutInflater());

        Thread.setDefaultUncaughtExceptionHandler(new MyExceptionHandler(this));

        workerId = WorkersClockActivityArgs.fromBundle(getIntent().getExtras()).getWorkerId();

        initListeners();
        initRV();

        configureView();

        getWorkerClocks();
        setContentView(binding.getRoot());
    }

    private void initListeners() {
        binding.tvBack.setOnClickListener(v -> finish());
        binding.tvStart.setOnClickListener(v -> startEndWorkerClock());
        binding.tvEdit.setOnClickListener(v -> openEditDialog());

        binding.tvWeek.setOnClickListener(view -> updateClocks(INTERVAL_THIS_WEEK));
        binding.tvMonth.setOnClickListener(view -> updateClocks(INTERVAL_THIS_MONTH));
        binding.tvLastMonth.setOnClickListener(view -> updateClocks(INTERVAL_LAST_MONTH));
        binding.tvLastMonth2.setOnClickListener(view -> updateClocks(INTERVAL_LAST_MONTH_2));
        binding.tvLastMonth3.setOnClickListener(view -> updateClocks(INTERVAL_LAST_MONTH_3));

//        binding.tlMonths.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                switch (tab.getPosition()) {
//                    case 0:
//                        interval = INTERVAL_LAST_MONTH_3;
//                        binding.tvAddTime.setVisibility(View.GONE);
//                        break;
//                    case 1:
//                        interval = INTERVAL_LAST_MONTH_2;
//                        binding.tvAddTime.setVisibility(View.GONE);
//                        break;
//                    case 2:
//                        interval = INTERVAL_LAST_MONTH;
//                        binding.tvAddTime.setVisibility(View.GONE);
//                        break;
//                    case 3:
//                        interval = INTERVAL_THIS_MONTH;
//                        binding.tvAddTime.setVisibility(View.VISIBLE);
//                        break;
//                    case 4:
//                    default:
//                        interval = INTERVAL_THIS_WEEK;
//                        binding.tvAddTime.setVisibility(View.VISIBLE);
//                        break;
//                }
//                getWorkerClocks(interval);
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });
    }

    private void updateClocks(String interval) {
        this.interval = interval;
        getWorkerClocks(interval);
        updatePeriodButtons(interval);
    }

    private void updatePeriodButtons(String interval) {
        binding.tvWeek.setActivated(interval.equals(INTERVAL_THIS_WEEK));
        binding.tvMonth.setActivated(interval.equals(INTERVAL_THIS_MONTH));
        binding.tvLastMonth.setActivated(interval.equals(INTERVAL_LAST_MONTH));
        binding.tvLastMonth2.setActivated(interval.equals(INTERVAL_LAST_MONTH_2));
        binding.tvLastMonth3.setActivated(interval.equals(INTERVAL_LAST_MONTH_3));
    }

    private void initRV() {
        binding.rvClocks.setLayoutManager(new LinearLayoutManager(this));
        binding.rvClocks.setAdapter(mClocksAdapter);
    }

    private void configureView() {

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdfMonth = new SimpleDateFormat("MMMM");

        calendar.add(Calendar.MONTH, -1);
        String last_month = sdfMonth.format(calendar.getTime());
        calendar.add(Calendar.MONTH, -1);
        String last_month_2 = sdfMonth.format(calendar.getTime());
        calendar.add(Calendar.MONTH, -1);
        String last_month_3 = sdfMonth.format(calendar.getTime());

        binding.tvLastMonth.setText(last_month);
        binding.tvLastMonth2.setText(last_month_2);
        binding.tvLastMonth3.setText(last_month_3);

        binding.tvWeek.setActivated(true);

    }

    private void getWorkerClocks() {
        getWorkerClocks(INTERVAL_THIS_WEEK);
    }

    private void getWorkerClocks(String interval) {
        binding.gPb.setVisibility(View.VISIBLE);
        Request.getInstance().getWorkerClocksByID(this, workerId, interval, response -> {
            binding.gPb.setVisibility(View.GONE);
            Collections.reverse(response.getClocks());
            fillWorkerInfo(response.getWorker());
            fillClocks(response.getClocks());
            setStartEndButtons(response.getClocks());
        });
    }

    private void setStartEndButtons(List<ClocksModel> clocks) {
        if (!clocks.isEmpty() && clocks.get(0).getEndTime() == null) {
            binding.tvStart.setText("End");
            isEnd = true;
        } else {
            binding.tvStart.setText("Start");
            isEnd = false;
        }
    }

    private void openInfoDialog() {
        String text = isEnd ? "You finished working day" : "You started working day";
        AutoHideDialog autoHideDialog = new AutoHideDialog(this, text);
        autoHideDialog.setCancelable(false);
        autoHideDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        autoHideDialog.show();

    }

    private void startEndWorkerClock() {
        binding.gPb.setVisibility(View.VISIBLE);
        Request.getInstance().startOrEndWorkerClockByID(this, workerId, isEnd, isDataSuccess -> {
            if (!isDataSuccess) binding.gPb.setVisibility(View.GONE);
            else {
                openInfoDialog();
                getWorkerClocks(interval);
            }
        });
    }

    private void enableClocksEditing() {
        mClocksAdapter.setEdit(true);
    }

    private void fillClocks(List<ClocksModel> clocks) {
        mClocksAdapter.updateList(clocks);
    }

    private void fillWorkerInfo(WorkerModel worker) {
        binding.tvName.setText(worker.getName());
    }

    private void openEditClocks(ClocksModel clocksModel) {
        EditClocksDialog d = new EditClocksDialog(this, clocksModel, model -> {
            binding.gPb.setVisibility(View.VISIBLE);
            Request.getInstance().editWorkerClock(this, model, isDataSuccess -> {
                if (!isDataSuccess) binding.gPb.setVisibility(View.GONE);
                else getWorkerClocks(interval);
            });
        });
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(d.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        d.getWindow().setAttributes(lp);
        d.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        d.show();
    }

    private void openEditDialog() {
        EditTimeDialog d = new EditTimeDialog(this, isAdd -> {
            if (isAdd) openEditClocks(new ClocksModel(workerId));
            else enableClocksEditing();
        });
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(d.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        d.getWindow().setAttributes(lp);
        d.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        d.show();
    }

}
