package com.pos.bringit.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;

import com.pos.bringit.R;
import com.pos.bringit.databinding.DialogPaidBinding;

import androidx.annotation.NonNull;


public class PaidDialog extends Dialog {

    public PaidDialog(@NonNull final Context context, String toPay, boolean isCard) {
        super(context);
        DialogPaidBinding binding = DialogPaidBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.ivPaid.setBackgroundResource(isCard
                ? R.drawable.background_payment_success_card
                : R.drawable.background_payment_success_cash
        );
        binding.tvTitleSuccess.setText(isCard
                ? "כרטיס עבר בהצלחה!"
                : "תשלום מזומן התקבל !"
        );
        binding.tvPriceToPay.setText(toPay + " ש׳׳ח");

        Handler handler = new Handler();
        handler.postDelayed(this::dismiss, 1000);

    }
}

