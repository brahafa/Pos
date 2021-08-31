package com.pos.bringit.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;

import com.pos.bringit.R;
import com.pos.bringit.databinding.DialogPayByCashBinding;

import androidx.annotation.NonNull;


public class PayByCashDialog extends Dialog {

    public PayByCashDialog(@NonNull final Context context, String toPay, String surplus, String defaultPhone, PaymentListener listener) {
        super(context);
        DialogPayByCashBinding binding = DialogPayByCashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.tvConfirm.setEnabled(!defaultPhone.isEmpty());

        binding.tvPriceToPay.setText(String.format("%s%s", toPay, context.getString(R.string.to_pay)));
        binding.tvPriceSurplus.setText(surplus);
        if (!defaultPhone.isEmpty()) {
            binding.tvDefaultNumber.setText(String.format("%s %s", context.getString(R.string.default_phone_text) , defaultPhone));
        }

        binding.chbDontSend.setOnCheckedChangeListener((compoundButton, checked) -> {
            if (defaultPhone.isEmpty())
                binding.tvConfirm.setEnabled(checked || !binding.edtNewNumber.getText().toString().isEmpty());
            binding.edtNewNumber.setEnabled(!checked);
        });

        binding.edtNewNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (defaultPhone.isEmpty())
                    binding.tvConfirm.setEnabled(!binding.edtNewNumber.getText().toString().isEmpty());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.tvConfirm.setOnClickListener(v -> {
            String otherNumber = binding.edtNewNumber.getText().toString();
            otherNumber = binding.chbDontSend.isChecked() ? "-1" : otherNumber;
            listener.onConfirmed(toPay, otherNumber);
            dismiss();
        });
        binding.tvCancel.setOnClickListener(v -> dismiss());

    }

    public interface PaymentListener {
        void onConfirmed(String price, String otherNumber);
    }

}

