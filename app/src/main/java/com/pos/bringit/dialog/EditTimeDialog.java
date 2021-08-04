package com.pos.bringit.dialog;

import android.app.Dialog;
import android.content.Context;

import com.pos.bringit.databinding.DialogEditTimeBinding;

import androidx.annotation.NonNull;

public class EditTimeDialog extends Dialog {

    public EditTimeDialog(@NonNull final Context context, ConfirmChangesListener listener) {
        super(context);
        DialogEditTimeBinding binding = DialogEditTimeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.tvAdd.setOnClickListener(v -> {
            listener.onChoose(true);
            dismiss();
        });
        binding.tvEdit.setOnClickListener(v -> {
            listener.onChoose(false);
            dismiss();
        });
        binding.ivClose.setOnClickListener(v -> dismiss());

    }

    public interface ConfirmChangesListener {
        void onChoose(boolean isAdd);
    }

}

