package com.pos.bringit.activities;

import android.os.Bundle;
import android.view.View;

import com.pos.bringit.adapters.SearchOrderAdapter;
import com.pos.bringit.databinding.ActivityCashBoxBinding;
import com.pos.bringit.models.SearchFilterModel;
import com.pos.bringit.network.Request;
import com.pos.bringit.utils.MyExceptionHandler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

public class CashBoxActivity extends AppCompatActivity {

    private ActivityCashBoxBinding binding;

    private SearchOrderAdapter mSearchOrderAdapter = new SearchOrderAdapter(orderId -> {});

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCashBoxBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Thread.setDefaultUncaughtExceptionHandler(new MyExceptionHandler(this));

        initListeners();
        initRV();

        getLastSessions();

    }

    private void getLastSessions() {

    }

    private void initListeners() {
        binding.tvBack.setOnClickListener(v -> finish());
        binding.tvOpenCloseDay.setOnClickListener(view -> openCloseDay());

    }

    private void openCloseDay() {
    }


    private void initRV() {
        binding.rvBalance.setLayoutManager(new LinearLayoutManager(this));
        binding.rvBalance.setAdapter(mSearchOrderAdapter);
    }


    private void searchByFilters(SearchFilterModel filters) {
        binding.gPb.setVisibility(View.VISIBLE);
        Request.getInstance().searchByFilters(this, filters, response -> {
            mSearchOrderAdapter.updateList(response.getOrdersList());
            binding.gPb.setVisibility(View.GONE);
        });
    }



}
