package com.pos.bringit.activities;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.tabs.TabLayout;
import com.pos.bringit.R;
import com.pos.bringit.adapters.WorkerClocksAdapter;
import com.pos.bringit.databinding.ActivityWorkersClockBinding;
import com.pos.bringit.dialog.EditClocksDialog;
import com.pos.bringit.models.ClocksModel;
import com.pos.bringit.models.WorkerModel;
import com.pos.bringit.network.Request;
import com.pos.bringit.utils.MyExceptionHandler;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

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
        binding.holderBack.setOnClickListener(v -> finish());
        binding.tvStart.setOnClickListener(v -> startEndWorkerClock(true));
        binding.tvEnd.setOnClickListener(v -> startEndWorkerClock(false));
        binding.tvAddTime.setOnClickListener(v -> openEditClocks(new ClocksModel(workerId)));

        binding.tlMonths.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        interval = INTERVAL_LAST_MONTH_3;
                        binding.tvAddTime.setVisibility(View.GONE);
                        break;
                    case 1:
                        interval = INTERVAL_LAST_MONTH_2;
                        binding.tvAddTime.setVisibility(View.GONE);
                        break;
                    case 2:
                        interval = INTERVAL_LAST_MONTH;
                        binding.tvAddTime.setVisibility(View.GONE);
                        break;
                    case 3:
                        interval = INTERVAL_THIS_MONTH;
                        binding.tvAddTime.setVisibility(View.VISIBLE);
                        break;
                    case 4:
                    default:
                        interval = INTERVAL_THIS_WEEK;
                        binding.tvAddTime.setVisibility(View.VISIBLE);
                        break;
                }
                getWorkerClocks(interval);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
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

        binding.tlMonths.getTabAt(2).setText(last_month);
        binding.tlMonths.getTabAt(1).setText(last_month_2);
        binding.tlMonths.getTabAt(0).setText(last_month_3);

        binding.tlMonths.getTabAt(4).select();

        Typeface typeFaceTime = ResourcesCompat.getFont(this, R.font.assistant_extra_bold);
        Typeface typeFaceDate = ResourcesCompat.getFont(this, R.font.assistant_regular);

        binding.tvTime.setTypeface(typeFaceTime);
        binding.tvDate.setTypeface(typeFaceDate);

    }

    private void getWorkerClocks() {
        getWorkerClocks(INTERVAL_THIS_WEEK);
    }

    private void getWorkerClocks(String interval) {
        binding.gPb.setVisibility(View.VISIBLE);
        Request.getInstance().getWorkerClocksByID(this, workerId, interval, response -> {
            binding.gPb.setVisibility(View.GONE);
            fillWorkerInfo(response.getWorker());
            fillClocks(response.getClocks());
        });
    }

    private void startEndWorkerClock(boolean start) {
        binding.gPb.setVisibility(View.VISIBLE);
        Request.getInstance().startOrEndWorkerClockByID(this, workerId, start, isDataSuccess -> {
            if (!isDataSuccess) binding.gPb.setVisibility(View.GONE);
            else getWorkerClocks(interval);
        });
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
        d.show();
    }

}
