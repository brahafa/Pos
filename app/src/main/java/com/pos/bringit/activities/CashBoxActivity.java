package com.pos.bringit.activities;

import android.os.Bundle;
import android.view.View;

import com.pos.bringit.adapters.SearchOrderAdapter;
import com.pos.bringit.databinding.ActivityCashBoxBinding;
import com.pos.bringit.dialog.CashboxDialog;
import com.pos.bringit.dialog.PasswordDialog;
import com.pos.bringit.models.FinanceItem;
import com.pos.bringit.models.SearchFilterModel;
import com.pos.bringit.models.TransactionItem;
import com.pos.bringit.network.Request;
import com.pos.bringit.utils.MyExceptionHandler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

public class CashBoxActivity extends AppCompatActivity {

    private ActivityCashBoxBinding binding;

    private FinanceItem currentSession;

    private SearchOrderAdapter mSearchOrderAdapter = new SearchOrderAdapter(orderId -> {
    });

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
        Request.getInstance().getLastFinanceSessions(this, response -> {
            if (response.getData().get(0).getClosedAt() == null) {
                currentSession = response.getData().get(0);
                response.getData().remove(0);
                binding.tvOpenCloseDay.setText("Close day");
            }
            //todo set remaining data to list

        });
    }

    private void initListeners() {
        binding.tvBack.setOnClickListener(v -> finish());
        binding.tvOpenCloseDay.setOnClickListener(view -> openPasswordDialog());

    }


    public void openCashboxDialog(String financeId) {
        CashboxDialog cashboxDialog = new CashboxDialog(this, price -> {
            TransactionItem transactionItem = new TransactionItem();
            transactionItem.setFinanceSessionId(Integer.parseInt(financeId));
            transactionItem.setAmount(Double.parseDouble(price));
            Request.getInstance().createFinanceTransaction(this, transactionItem, isDataSuccess -> {
            });

        });
        cashboxDialog.setCancelable(true);
        cashboxDialog.show();
    }

    public void openPasswordDialog() {
        PasswordDialog passwordDialog = new PasswordDialog(this);
        passwordDialog.setCancelable(true);
        passwordDialog.setCancelButton(true);
        passwordDialog.setOtherWorker(true);
        passwordDialog.show();

        passwordDialog.setOnDismissListener(dialog -> {
            if (passwordDialog.getWorker() != null) {
                if (currentSession != null && currentSession.getClosedAt() == null) {
                    currentSession.setClosedBy(passwordDialog.getWorker().getName());
                    currentSession.setSessionId();
                    Request.getInstance().closeFinanceSession(this, currentSession, response -> {
                        currentSession = null;
                        binding.tvOpenCloseDay.setText("Open day");
                        //todo set data
                    });
                } else {
                    FinanceItem financeItem = new FinanceItem();
                    financeItem.setOpenedBy(passwordDialog.getWorker().getName());

                    Request.getInstance().openFinanceSession(this, financeItem, response -> {
                        currentSession = response.getData();
                        binding.tvOpenCloseDay.setText("Close day");
                        openCashboxDialog(response.getData().getFinanceId());
                        //todo set data
                    });
                }
            }
        });
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
