package com.pos.bringit.dialog;

import android.app.Dialog;
import android.content.Context;

import com.pos.bringit.databinding.DialogConfirmChangesBinding;

import androidx.annotation.NonNull;

public class ConfirmChangesDialog extends Dialog {

    public ConfirmChangesDialog(@NonNull final Context context, String text, ConfirmChangesListener listener) {
        super(context);
        DialogConfirmChangesBinding binding = DialogConfirmChangesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.tvTitle.setText(text);

        binding.tvYes.setOnClickListener(v -> {
            listener.onConfirmed();
            dismiss();
        });
        binding.ivClose.setOnClickListener(v -> dismiss());

    }

    public interface ConfirmChangesListener {
        void onConfirmed();
    }

}

