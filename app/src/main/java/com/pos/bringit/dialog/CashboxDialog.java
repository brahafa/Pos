package com.pos.bringit.dialog;

import android.app.Dialog;
import android.content.Context;

import com.pos.bringit.databinding.CashboxDialogBinding;

import androidx.annotation.NonNull;


public class CashboxDialog extends Dialog {

    private final CashboxDialogBinding binding;

    public CashboxDialog(@NonNull final Context context, CashboxListener listener) {
        super(context);
        binding = CashboxDialogBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.ivClose.setOnClickListener(v -> CashboxDialog.this.dismiss());

        binding.numberKeyboardView.keyListener(keyTxt -> {
            String s = binding.tvAmount.getText().toString();

            if (!keyTxt.equals("X")) s = s.concat(keyTxt);
            else s = s.substring(0, s.length() - 1);

            binding.tvAmount.setText(s);
        });
        binding.tvConfirm.setOnClickListener(view -> {
            listener.onConfirmed(binding.tvAmount.getText().toString());
            dismiss();
        });

    }

    public interface CashboxListener {
        void onConfirmed(String price);
    }
}

