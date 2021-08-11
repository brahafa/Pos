package com.pos.bringit.activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.pos.bringit.adapters.SearchOrderAdapter;
import com.pos.bringit.databinding.ActivitySearchOrderBinding;
import com.pos.bringit.dialog.ChooseColorDialog;
import com.pos.bringit.dialog.ChooseDateDialog;
import com.pos.bringit.models.SearchFilterModel;
import com.pos.bringit.network.Request;
import com.pos.bringit.network.RequestHelper;
import com.pos.bringit.utils.Constants;
import com.pos.bringit.utils.MyExceptionHandler;
import com.pos.bringit.utils.Utils;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import static com.pos.bringit.utils.Constants.COLOR_GREEN;
import static com.pos.bringit.utils.Constants.COLOR_ORANGE;
import static com.pos.bringit.utils.Constants.COLOR_PURPLE;
import static com.pos.bringit.utils.Constants.COLOR_RED;
import static com.pos.bringit.utils.Constants.COLOR_YELLOW;

public class SearchOrderActivity extends AppCompatActivity {

    private ActivitySearchOrderBinding binding;

    private String mStartDate = "";
    private String mEndDate = "";
    private ArrayList<String> mColors = new ArrayList<String>() {{
        add(COLOR_GREEN);
        add(COLOR_PURPLE);
        add(COLOR_RED);
        add(COLOR_ORANGE);
        add(COLOR_YELLOW);
    }};

    private SearchOrderAdapter mSearchOrderAdapter = new SearchOrderAdapter(this::openOrderPage);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchOrderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Thread.setDefaultUncaughtExceptionHandler(new MyExceptionHandler(this));

        initListeners();
        initRV();

        fillSearch();

    }


    private void initListeners() {
        binding.tvBack.setOnClickListener(v -> finish());
        binding.tvSearch.setOnClickListener(v -> onSearchClick());
        binding.edtDate.setOnClickListener(v -> openChooseDate());
        binding.ivClearDate.setOnClickListener(v -> clearDate());
        binding.llHolderTag.setOnClickListener(v -> openChooseColorDialog());

        binding.edtDate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                binding.ivClearDate.setVisibility(s.toString().isEmpty() ? View.GONE : View.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void initRV() {
        binding.rvClocks.setLayoutManager(new LinearLayoutManager(this));
        binding.rvClocks.setAdapter(mSearchOrderAdapter);
    }

    private void clearAllFieldsButOrderId() {
        binding.edtName.setText("");
        binding.edtPhone.setText("");
        binding.edtAddress.setText("");
        binding.edtDate.setText("");
        binding.edtInvoiceNumber.setText("");
        mStartDate = "";
        mEndDate = "";
    }

    private void clearAllFieldsButInvoiceNum() {
        binding.edtName.setText("");
        binding.edtPhone.setText("");
        binding.edtAddress.setText("");
        binding.edtDate.setText("");
        mStartDate = "";
        mEndDate = "";
    }

    private void fillSearch() {
        new RequestHelper().getAllOrdersFromDb(this,
                response -> mSearchOrderAdapter.updateList(response.getOrders()));
    }

    private void searchByFilters(SearchFilterModel filters) {
        binding.gPb.setVisibility(View.VISIBLE);
        Request.getInstance().searchByFilters(this, filters, response -> {
            mSearchOrderAdapter.updateList(response.getOrdersList());
            binding.gPb.setVisibility(View.GONE);
        });
    }

    private void onSearchClick() {
        SearchFilterModel filters = new SearchFilterModel();

        String name = binding.edtName.getText().toString();
        String phone = binding.edtPhone.getText().toString();
        String address = binding.edtAddress.getText().toString();

        String orderId = binding.edtOrderId.getText().toString();
        String invoiceNum = binding.edtInvoiceNumber.getText().toString();

        if (!name.isEmpty()) filters.setClient(name);
        if (!phone.isEmpty()) filters.setPhone(phone);
        if (!address.isEmpty()) filters.setStreet(address);
        if (!orderId.isEmpty()) filters.setOrderId(orderId);
        if (!invoiceNum.isEmpty()) filters.setInvoiceNumber(invoiceNum);
        if (!mStartDate.isEmpty()) filters.setStartDate(mStartDate);
        if (!mEndDate.isEmpty()) filters.setEndDate(mEndDate);
        if (!mColors.isEmpty()) filters.setColors(mColors);

        if (filters.isEmpty()) Utils.openAlertDialog(this, "Enter at least one search criteria", "Warning");
        else {
            if (filters.containsOrderId()) {
                clearAllFieldsButOrderId();
                Toast.makeText(this, "Searching only by order id, other parameters are ignored", Toast.LENGTH_LONG).show();
            } else if (filters.containsInvoiceNumber()) {
                clearAllFieldsButInvoiceNum();
                Toast.makeText(this, "Searching only by Invoice number, other parameters are ignored", Toast.LENGTH_LONG).show();
            }
            searchByFilters(filters);
        }

    }

    private void openChooseDate() {
        ChooseDateDialog d = new ChooseDateDialog(this, mStartDate, mEndDate,
                (startDate, endDate, showDate) -> {
                    mStartDate = startDate;
                    mEndDate = endDate;
                    binding.edtDate.setText(showDate);
                });
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(d.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        d.getWindow().setAttributes(lp);
        d.show();
    }

    private void openChooseColorDialog() {
        ChooseColorDialog d = new ChooseColorDialog(this, mColors, colors -> {
            mColors = colors;
            updateTags();
        });
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(d.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        d.getWindow().setAttributes(lp);
        d.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        d.show();
    }

    private void openOrderPage(String orderId) {
        Intent intent = new Intent(this, CreateOrderActivity.class);
        intent.putExtra("type", Constants.NEW_ORDER_TYPE_ITEM);
        intent.putExtra("item_id", orderId);
        intent.putExtra("table_id", "");
        startActivity(intent);
    }

    private void clearDate() {
        binding.edtDate.setText("");
        mStartDate = "";
        mEndDate = "";
    }

    private void updateTags() {
        binding.ivTagGreen.setVisibility(mColors.contains(COLOR_GREEN) ? View.VISIBLE : View.GONE);
        binding.ivTagPurple.setVisibility(mColors.contains(COLOR_PURPLE) ? View.VISIBLE : View.GONE);
        binding.ivTagRed.setVisibility(mColors.contains(COLOR_RED) ? View.VISIBLE : View.GONE);
        binding.ivTagOrange.setVisibility(mColors.contains(COLOR_ORANGE) ? View.VISIBLE : View.GONE);
        binding.ivTagYellow.setVisibility(mColors.contains(COLOR_YELLOW) ? View.VISIBLE : View.GONE);
    }


}
