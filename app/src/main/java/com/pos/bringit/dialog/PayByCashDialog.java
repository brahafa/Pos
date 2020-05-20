package com.pos.bringit.dialog;

import android.app.Dialog;
import android.content.Context;

import com.pos.bringit.databinding.DialogPayByCashBinding;

import androidx.annotation.NonNull;


public class PayByCashDialog extends Dialog {

    public PayByCashDialog(@NonNull final Context context, String toPay, String surplus, PaymentListener listener) {
        super(context);
        DialogPayByCashBinding binding = DialogPayByCashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.tvPriceToPay.setText(toPay + " ש׳׳ח");
        binding.tvPriceSurplus.setText(surplus);

        binding.tvConfirm.setOnClickListener(v -> {
            listener.onConfirmed(toPay);
            dismiss();
        });
        binding.tvCancel.setOnClickListener(v -> dismiss());

    }

    public interface PaymentListener {
        void onConfirmed(String price);
    }

}

