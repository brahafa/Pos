package com.pos.bringit.fragments;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.pos.bringit.adapters.PaymentAdapter;
import com.pos.bringit.databinding.FragmentPaymentBinding;
import com.pos.bringit.dialog.PaidDialog;
import com.pos.bringit.dialog.PayByCardDialog;
import com.pos.bringit.dialog.PayByCashDialog;
import com.pos.bringit.models.PaymentDetailsModel;
import com.pos.bringit.models.PaymentModel;
import com.pos.bringit.utils.PriceCountKeyboardView;
import com.pos.bringit.utils.Utils;

import java.util.Locale;

import static com.pos.bringit.utils.Constants.PAYMENT_METHOD_CARD;
import static com.pos.bringit.utils.Constants.PAYMENT_METHOD_CASH;

public class PaymentFragment extends Fragment {

    private FragmentPaymentBinding binding;
    private Context mContext;

    private PaymentDetailsModel mPaymentDetails;

    private String mToPayPrice = "";
    private double mPaidPrice;

    private OnPaymentMethodChosenListener listener;


    private TextWatcher surplusCountWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String toPay = binding.tvToPayPrice.getText().toString();
            double surplusValue = mPaidPrice - Double.parseDouble(toPay.isEmpty() ? "0" : toPay);
            binding.tvSurplusPrice.setText(String.format(Locale.US, "%.2f", surplusValue > 0 ? surplusValue : 0.00));
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private PaymentAdapter mPaymentAdapter = new PaymentAdapter();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPaymentBinding.inflate(inflater, container, false);

        mPaymentDetails = PaymentFragmentArgs.fromBundle(getArguments()).getPaymentDetails();

        initRV();
        initListeners();

        setData();

        return binding.getRoot();
    }

    private void setData() {
        binding.tvTitleToPay.setSelected(true);

        double totalPriceToPay = Double.parseDouble(mPaymentDetails.getTotal());
        double totalPrice = totalPriceToPay + countedPayments();

        binding.tvTotalPrice.setText(String.format(Locale.US, "%.2f", totalPrice));
        binding.tvRemainingPrice.setText(String.format(Locale.US, "%.2f", totalPriceToPay));
        binding.tvToPayPrice.setText(String.format(Locale.US, "%.2f", totalPriceToPay));
        binding.tvPaidPrice.setText(String.format(Locale.US, "%.2f", totalPriceToPay));

        mPaymentAdapter.updateList(mPaymentDetails.getPayments());
    }

    private double countedPayments() {
        double sum = 0;
        for (PaymentModel payment : mPaymentDetails.getPayments()) {
            sum += Double.parseDouble(payment.getPrice());
        }
        return sum;
    }


    private void initRV() {
        binding.rvPayments.setLayoutManager(new LinearLayoutManager(mContext));
        binding.rvPayments.setAdapter(mPaymentAdapter);

    }

    private void initListeners() {
        binding.priceCountKeyboardView.keyListener(new PriceCountKeyboardView.KeyPressListener() {
            @Override
            public void onKeyPress(String keyTxt) {
                if (!binding.tvTitlePaid.isSelected()) {
                    if (keyTxt.equals("X")) {
                        if (!mToPayPrice.isEmpty())
                            mToPayPrice = mToPayPrice.substring(0, mToPayPrice.length() - 1);
                    } else {
                        mToPayPrice = mToPayPrice.concat(keyTxt);
                    }

                    mPaidPrice = 0;
                    binding.tvToPayPrice.setText(mToPayPrice);
                    binding.tvPaidPrice.setText(mToPayPrice);
                } else {
                    String paidPrice = binding.tvPaidPrice.getText().toString();
                    if (keyTxt.equals("X")) {
                        if (!paidPrice.isEmpty())
                            paidPrice = paidPrice.substring(0, paidPrice.length() - 1);
                    } else {
                        paidPrice = paidPrice.concat(keyTxt);
                    }

                    mPaidPrice = Double.parseDouble(paidPrice.isEmpty() ? "0" : paidPrice);
                    binding.tvPaidPrice.setText(paidPrice);
                }
            }

            @Override
            public void onPricePress(int keyValue) {
                mPaidPrice += keyValue;
                binding.tvPaidPrice.setText(String.valueOf(mPaidPrice));
            }
        });
        binding.tvTitleToPay.setOnClickListener(v -> selectField(false));
        binding.tvTitlePaid.setOnClickListener(v -> selectField(true));

        binding.tvPayByCash.setOnClickListener(v -> {
            if (checkRemaining()) openPayByCashDialog();
        });
        binding.tvPayByCard.setOnClickListener(v -> {
            if (checkRemaining()) openPayByCardDialog();
        });

        binding.tvToPayPrice.addTextChangedListener(surplusCountWatcher);
        binding.tvPaidPrice.addTextChangedListener(surplusCountWatcher);

    }

    private void selectField(boolean isPaidField) {
        binding.tvTitleToPay.setSelected(!isPaidField);
        binding.tvTitlePaid.setSelected(isPaidField);
        binding.priceCountKeyboardView.setActivated(isPaidField);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
        try {
            listener = (OnPaymentMethodChosenListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement OnPaymentMethodChosenListener");
        }
    }

    private void openPayByCashDialog() {
        String surplus = binding.tvSurplusPrice.getText().toString();
        String toPay = binding.tvToPayPrice.getText().toString();
        PayByCashDialog dialog = new PayByCashDialog(mContext, toPay, surplus, price -> {
            mPaymentAdapter.addItem(new PaymentModel(price, PAYMENT_METHOD_CASH));
            editRemaining(price);
            openPaidDialog(price, false);
        });
        dialog.setCancelable(false);
        dialog.show();
    }

    private void openPayByCardDialog() {
        String toPay = binding.tvToPayPrice.getText().toString();
        PayByCardDialog dialog = new PayByCardDialog(mContext, toPay, price -> {
            mPaymentAdapter.addItem(new PaymentModel(price, PAYMENT_METHOD_CARD));
            editRemaining(price);
            openPaidDialog(price, true);
        });
        dialog.setCancelable(false);
        dialog.show();
    }

    private void openPaidDialog(String price, boolean isCard) {
        PaidDialog paidDialog = new PaidDialog(mContext, price, isCard);
        paidDialog.setCancelable(false);
        paidDialog.setOnDismissListener(dialog -> {
            listener.onPaid(isCard ? PAYMENT_METHOD_CARD : PAYMENT_METHOD_CASH,
                    Double.parseDouble(binding.tvRemainingPrice.getText().toString()));
            if (Double.parseDouble(binding.tvRemainingPrice.getText().toString()) == 0)
                getActivity().onBackPressed();
        });
        paidDialog.show();
    }

    private void editRemaining(String price) {
        double remaining = Double.parseDouble(binding.tvRemainingPrice.getText().toString());
        double current = Double.parseDouble(price);
        remaining -= current;

        mToPayPrice = "";
        mPaidPrice = 0;

        binding.tvRemainingPrice.setText(String.format(Locale.US, "%.2f", remaining));
        binding.tvToPayPrice.setText(String.format(Locale.US, "%.2f", remaining));
        binding.tvPaidPrice.setText(String.format(Locale.US, "%.2f", remaining));
    }

    private boolean checkRemaining() {
        double remaining = Double.parseDouble(binding.tvRemainingPrice.getText().toString());
        double current = Double.parseDouble(binding.tvToPayPrice.getText().toString());
        if (remaining - current < 0) {
            Utils.openAlertDialog(mContext, "You can't pay more than remaining price", "Warning");

            mToPayPrice = "";
            mPaidPrice = 0;

            binding.tvRemainingPrice.setText(String.format(Locale.US, "%.2f", remaining));
            binding.tvToPayPrice.setText(String.format(Locale.US, "%.2f", remaining));
            binding.tvPaidPrice.setText(String.format(Locale.US, "%.2f", remaining));
            return false;
        }
        return true;
    }

    public interface OnPaymentMethodChosenListener {
        void onPaid(String paymentMethod, double priceRemaining);
    }
}
