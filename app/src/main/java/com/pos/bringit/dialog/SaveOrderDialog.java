package com.pos.bringit.dialog;

import android.app.Dialog;
import android.content.Context;

import com.pos.bringit.databinding.DialogSaveOrderBinding;

import androidx.annotation.NonNull;

public class SaveOrderDialog extends Dialog {

    public SaveOrderDialog(@NonNull final Context context, ChooseListener listener) {
        super(context);
        DialogSaveOrderBinding binding = DialogSaveOrderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.tvYes.setOnClickListener(v -> {
            listener.onChoose(true);
            dismiss();
        });
        binding.tvNo.setOnClickListener(v -> {
            listener.onChoose(false);
            dismiss();
        });
        binding.ivClose.setOnClickListener(v -> dismiss());

    }

    public interface ChooseListener {
        void onChoose(boolean isYes);
    }

}

