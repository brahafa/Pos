package com.pos.bringit.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;

import com.pos.bringit.databinding.DialogAutoHideBinding;

import androidx.annotation.NonNull;


public class AutoHideDialog extends Dialog {

    public AutoHideDialog(@NonNull final Context context, String text) {
        super(context);
        DialogAutoHideBinding binding = DialogAutoHideBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.tvTitleSuccess.setText(text);

        Handler handler = new Handler();
        handler.postDelayed(this::dismiss, 1000);
    }
}

