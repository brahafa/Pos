package com.pos.bringit.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;

import com.pos.bringit.databinding.DialogPayByCardBinding;

import androidx.annotation.NonNull;


public class PayByCardDialog extends Dialog {

    public PayByCardDialog(@NonNull final Context context, String toPay, PaymentListener listener) {
        super(context);
        DialogPayByCardBinding binding = DialogPayByCardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.tvPriceToPay.setText(toPay + " ש׳׳ח");

        Handler handler = new Handler();
        Runnable runnable = () -> {
            listener.onConfirmed(toPay);
            dismiss();
        };
        handler.postDelayed(runnable, 2 * 1000);

        binding.tvCancel.setOnClickListener(v -> {
            handler.removeCallbacks(runnable);
            dismiss();
        });
    }

    public interface PaymentListener {
        void onConfirmed(String price);
    }

}

