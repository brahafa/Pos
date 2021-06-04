package com.pos.bringit.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.pos.bringit.adapters.SearchOrderAdapter;
import com.pos.bringit.databinding.ActivitySearchOrderBinding;
import com.pos.bringit.dialog.ChooseDateDialog;
import com.pos.bringit.utils.Constants;
import com.pos.bringit.utils.MyExceptionHandler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

public class SearchOrderActivity extends AppCompatActivity {

    private ActivitySearchOrderBinding binding;

    private String mStartDate;
    private String mEndDate;

    private SearchOrderAdapter mSearchOrderAdapter = new SearchOrderAdapter(this::openOrderPage);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchOrderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Thread.setDefaultUncaughtExceptionHandler(new MyExceptionHandler(this));

        initListeners();
        initRV();

    }

    private void initListeners() {
        binding.holderBack.setOnClickListener(v -> finish());
        binding.tvSearch.setOnClickListener(v -> onSearchClick());
        binding.edtDate.setOnClickListener(v -> openChooseDate());

    }

    private void onSearchClick() {
        //todo when server is ready
        binding.ivEmptyPlaceholder.setVisibility(View.GONE);
    }

    private void initRV() {
        binding.rvClocks.setLayoutManager(new LinearLayoutManager(this));
        binding.rvClocks.setAdapter(mSearchOrderAdapter);
    }


    private void getWorkerClocks(String interval) {
        binding.gPb.setVisibility(View.VISIBLE);
//        Request.getInstance().getWorkerClocksByID(this, workerId, interval, response -> {
//            binding.gPb.setVisibility(View.GONE);
//        });
    }

    private void openChooseDate() {
        ChooseDateDialog d = new ChooseDateDialog(this, mStartDate, mEndDate,
                (startDate, endDate) -> {
                    mStartDate = startDate;
                    mEndDate = endDate;
                    binding.edtDate.setText(String.format("%s - %s", mEndDate, mStartDate));
                });
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(d.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        d.getWindow().setAttributes(lp);
        d.show();
    }

    private void openOrderPage(String orderId) {
        Intent intent = new Intent(this, CreateOrderActivity.class);
        intent.putExtra("type", Constants.NEW_ORDER_TYPE_ITEM);
        intent.putExtra("item_id", orderId);
        intent.putExtra("table_id", "");
        startActivity(intent);
    }

}
