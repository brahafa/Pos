package com.pos.bringit.dialog;

import android.app.Dialog;
import android.content.Context;
import android.widget.TextView;

import com.pos.bringit.databinding.PasswordDialogBinding;
import com.pos.bringit.models.WorkerModel;
import com.pos.bringit.network.Request;

import androidx.annotation.NonNull;


public class PasswordDialog extends Dialog {

    private final PasswordDialogBinding binding;
    private int passwordIndex = -1;
    private TextView[] passwordTVs;
    private boolean isOtherWorker;
    private WorkerModel mWorker;

    public PasswordDialog(@NonNull final Context context) {
        super(context);
        binding = PasswordDialogBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        passwordTVs = new TextView[]{binding.tv1, binding.tv2, binding.tv3, binding.tv4};

        binding.numberKeyboardView.keyListener(keyTxt -> {
            if (!keyTxt.equals("X")) {
                if (passwordIndex == 3) {
                    return;
                }
                passwordIndex++;
                passwordTVs[passwordIndex].setText(keyTxt);
                if (passwordIndex == 3) {
                    if (isOtherWorker) {
                        Request.getInstance().otherWorkerLogin(context, getThePassword(), response -> {
                            if (response.isStatus()) {
                                mWorker = response.getUser();
                                PasswordDialog.this.dismiss();
                            } else {
                                initErrorState();
                            }
                        });
                    } else {
                        Request.getInstance().settingsLogin(context, getThePassword(), isDataSuccess -> {
                            if (isDataSuccess) {
                                PasswordDialog.this.dismiss();
                            } else {
                                initErrorState();
                            }
                        });
                    }
                }
            } else if (passwordIndex >= 0) {
                if (passwordIndex == 3) {
                    initSuccessState();
                }
                passwordTVs[passwordIndex].setText("");
                passwordIndex--;
            }
        });

    }

    public void setOtherWorker(boolean otherWorker) {
        isOtherWorker = otherWorker;
    }

    public WorkerModel getWorker() {
        return mWorker;
    }

    private String getThePassword() {
        return binding.tv1.getText().toString()
                + binding.tv2.getText().toString()
                + binding.tv3.getText().toString()
                + binding.tv4.getText().toString();
    }

    private void initErrorState() {
        binding.tv1.setActivated(true);
        binding.tv2.setActivated(true);
        binding.tv3.setActivated(true);
        binding.tv4.setActivated(true);
    }

    private void initSuccessState() {
        binding.tv1.setActivated(false);
        binding.tv2.setActivated(false);
        binding.tv3.setActivated(false);
        binding.tv4.setActivated(false);
    }
}

