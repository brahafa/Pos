package com.pos.bringit.dialog;

import android.app.Dialog;
import android.content.Context;

import com.pos.bringit.databinding.DialogToDeliveryManBinding;

import androidx.annotation.NonNull;


public class ToDeliveryManDialog extends Dialog {

    public ToDeliveryManDialog(@NonNull final Context context) {
        super(context);
        DialogToDeliveryManBinding binding = DialogToDeliveryManBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

    }
}

