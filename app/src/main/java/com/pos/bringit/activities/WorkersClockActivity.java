package com.pos.bringit.activities;

import android.os.Bundle;

import com.pos.bringit.databinding.ActivityWorkersClockBinding;

import androidx.appcompat.app.AppCompatActivity;

public class WorkersClockActivity extends AppCompatActivity {

    private ActivityWorkersClockBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWorkersClockBinding.inflate(getLayoutInflater());

        initListeners();
        setContentView(binding.getRoot());
    }

    private void initListeners() {
        binding.holderBack.setOnClickListener(v -> finish());

    }


}
