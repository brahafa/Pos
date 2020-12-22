package com.pos.bringit.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.pos.bringit.databinding.PasswordDialogBinding;
import com.pos.bringit.models.WorkerModel;
import com.pos.bringit.network.Request;
import com.pos.bringit.utils.Constants;
import com.pos.bringit.utils.SharedPrefs;
import com.pos.bringit.utils.Utils;


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

        binding.ivClose.setOnClickListener(v -> PasswordDialog.this.dismiss());

        binding.tvVersion.setOnLongClickListener(v -> {
            binding.tvVersion.setText(Utils.getVersionApp(getContext()));
            return false;
        });
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
                        Request.getInstance().settingsLogin(context, getThePassword(), response -> {
                            if (response.isStatus()) {
                                mWorker = response.getUser();
                                if (response.getUser().getPermissions().getPos().equals("1")) {
                                    SharedPrefs.saveData(Constants.NAME_PREF, response.getUser().getName());
//                                  SharedPrefs.saveData(Constants.ROLE_PREF, response.getUser().getRole());
                                    PasswordDialog.this.dismiss();
                                } else
                                    Utils.openPermissionAlertDialog(context);
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
        binding.ivClose.setVisibility(isOtherWorker ? View.VISIBLE : View.GONE);
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

