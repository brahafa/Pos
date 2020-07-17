package com.pos.bringit.activities;

import android.os.Bundle;

import com.pos.bringit.databinding.ActivityWorkersClockBinding;
import com.pos.bringit.models.ClocksModel;
import com.pos.bringit.models.WorkerModel;
import com.pos.bringit.network.Request;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class WorkersClockActivity extends AppCompatActivity {

    private ActivityWorkersClockBinding binding;

    private String workerId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWorkersClockBinding.inflate(getLayoutInflater());

        workerId = WorkersClockActivityArgs.fromBundle(getIntent().getExtras()).getWorkerId();

        initListeners();

        getWorkerClocks();
        setContentView(binding.getRoot());
    }

    private void initListeners() {
        binding.holderBack.setOnClickListener(v -> finish());

    }

    private void getWorkerClocks() {
        Request.getInstance().getWorkerClocksByID(this, workerId, response -> {
            fillWorkerInfo(response.getWorker());
            if (!response.getClocks().isEmpty()) {
                fillClocks(response.getClocks());
            }
        });
    }

    private void fillClocks(List<ClocksModel> clocks) {

    }

    private void fillWorkerInfo(WorkerModel worker) {
        binding.tvName.setText(worker.getName());
    }



}
