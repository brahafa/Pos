package com.pos.bringit.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.EditorInfo;

import androidx.appcompat.app.AppCompatActivity;

import com.pos.bringit.databinding.ActivityLoginBinding;
import com.pos.bringit.network.Request;
import com.pos.bringit.utils.MyExceptionHandler;

import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLoginBinding.inflate(getLayoutInflater());

        Thread.setDefaultUncaughtExceptionHandler(new MyExceptionHandler(this));

        initListener();

        setContentView(binding.getRoot());
    }

    private void initListener() {
        binding.ivClearName.setOnClickListener(v -> {
            binding.edtUsername.setText("");
            correctUsername();
        });

        binding.ivClearPassword.setOnClickListener(v -> {
            binding.edtPassword.setText("");
            correctPassword();
        });
        binding.tvGo.setOnClickListener(this::signIn);

        binding.edtPassword.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                signIn();
                return true;
            }
            return false;
        });
    }

    private void signIn() {
        Pattern patternEmail = android.util.Patterns.EMAIL_ADDRESS;
        String email = binding.edtUsername.getText().toString().trim();
        String pass = binding.edtPassword.getText().toString();

        if (email.isEmpty()) errorInUsername();
        if (pass.isEmpty()) errorInPassword();

        if (!patternEmail.matcher(email).matches())
            errorInUsername();
        else {
            Request.getInstance().logIn(this,
                    "pedro123", //    binding.edtPassword.getText().toString(), //todo for business id 4
                    "pashtet1@gmail.com", //     binding.edtUsername.getText().toString().trim(), //todo for business id 4
                    isDataSuccess -> {
                        if (isDataSuccess) {
//                                saveData(Constants.USER_ALREADY_CONNECTED_PREF, true);
                            openMainActivity();
                        } else {
                            errorInPassword();
                            errorInUsername();
                        }

                    });
        }
    }

    private void errorInPassword() {
        binding.ivClearPassword.setVisibility(View.VISIBLE);
        binding.edtPassword.setActivated(true);
    }

    private void errorInUsername() {
        binding.ivClearName.setVisibility(View.VISIBLE);
        binding.edtUsername.setActivated(true);
    }

    public void correctPassword() {
        binding.ivClearPassword.setVisibility(View.GONE);
        binding.edtPassword.setActivated(false);
    }

    public void correctUsername() {
        binding.ivClearName.setVisibility(View.GONE);
        binding.edtUsername.setActivated(false);
    }

    private void openMainActivity() {
        Intent i = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(i);
        finish();
    }

    private void signIn(View v) {
        signIn();
    }
}
