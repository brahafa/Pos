package com.pos.bringit.activities;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import com.pos.bringit.adapters.FinanceAdapter;
import com.pos.bringit.databinding.ActivityCashBoxBinding;
import com.pos.bringit.dialog.CashboxDialog;
import com.pos.bringit.dialog.PasswordDialog;
import com.pos.bringit.models.FinanceItem;
import com.pos.bringit.models.TransactionItem;
import com.pos.bringit.network.Request;
import com.pos.bringit.utils.MyExceptionHandler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import static com.pos.bringit.utils.Constants.PATTERN_DATE_FROM_SERVER;

public class CashBoxActivity extends AppCompatActivity {

    private ActivityCashBoxBinding binding;

    private FinanceItem currentSession;

    private FinanceAdapter mFinanceAdapter = new FinanceAdapter();

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

    private void setInfo() {
        binding.tvVName.setText(currentSession == null ? "" : currentSession.getOpenedBy());
        binding.tvVSDate.setText(currentSession == null ? "" : formatDate(currentSession.getOpenedAt()));
        binding.tvVSTime.setText(currentSession == null ? "" : formatTime(currentSession.getOpenedAt()));

        binding.tvVFund.setText(currentSession == null ? "" : currentSession.getSum().getFund());
        binding.tvVBalance.setText(currentSession == null ? "" : String.valueOf(currentSession.getSum().getBalance()));
        binding.tvVCredit.setText(currentSession == null ? "" : String.valueOf(currentSession.getSum().getCredit()));
        binding.tvVCash.setText(currentSession == null ? "" : String.valueOf(currentSession.getSum().getCash()));

        binding.tvVFDate.setText(currentSession == null || currentSession.getClosedAt() == null ? "" : formatDate(currentSession.getClosedAt()));
        binding.tvVFTime.setText(currentSession == null || currentSession.getClosedAt() == null ? "" : formatTime(currentSession.getClosedAt()));
        binding.tvVFName.setText(currentSession == null || currentSession.getClosedBy() == null ? "" : currentSession.getClosedBy());
    }

    private void getLastSessions() {
        Request.getInstance().getLastFinanceSessions(this, response -> {
            if (response.getData().get(0).getClosedAt() == null) {
                currentSession = response.getData().get(0);
                response.getData().remove(0);
                binding.tvOpenCloseDay.setText("Close day");
            }

            setInfo();
            mFinanceAdapter.updateList(response.getData());

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
                if (isDataSuccess) binding.tvVFund.setText(price);
            });

        });
        cashboxDialog.setCancelable(true);
        cashboxDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        cashboxDialog.show();
    }

    public void openPasswordDialog() {
        PasswordDialog passwordDialog = new PasswordDialog(this);
        passwordDialog.setCancelable(true);
        passwordDialog.setCancelButton(true);
        passwordDialog.setOtherWorker(true);
        passwordDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        passwordDialog.show();

        passwordDialog.setOnDismissListener(dialog -> {
            if (passwordDialog.getWorker() != null) {
                if (currentSession != null && currentSession.getClosedAt() == null) {
                    currentSession.setClosedBy(passwordDialog.getWorker().getName());
                    currentSession.setSessionId();
                    Request.getInstance().closeFinanceSession(this, currentSession, response -> {
                        currentSession = null;
                        binding.tvOpenCloseDay.setText("Open day");
                        setInfo();
                    });
                } else {
                    FinanceItem financeItem = new FinanceItem();
                    financeItem.setOpenedBy(passwordDialog.getWorker().getName());

                    Request.getInstance().openFinanceSession(this, financeItem, response -> {
                        currentSession = response.getData();
                        binding.tvOpenCloseDay.setText("Close day");
                        openCashboxDialog(response.getData().getFinanceId());
                        setInfo();
                    });
                }
            }
        });
    }


    private void initRV() {
        binding.rvBalance.setLayoutManager(new LinearLayoutManager(this));
        binding.rvBalance.setAdapter(mFinanceAdapter);
    }

    private String formatDate(String time) {

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdfIn = new SimpleDateFormat(PATTERN_DATE_FROM_SERVER);
        SimpleDateFormat sdfOutDate = new SimpleDateFormat("dd/MM/yy");

        try {
            calendar.setTime(sdfIn.parse(time));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return sdfOutDate.format(calendar.getTime());
    }


    private String formatTime(String time) {

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdfIn = new SimpleDateFormat(PATTERN_DATE_FROM_SERVER);
        SimpleDateFormat sdfOutTime = new SimpleDateFormat("HH:mm");

        try {
            calendar.setTime(sdfIn.parse(time));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return sdfOutTime.format(calendar.getTime());
    }
}
