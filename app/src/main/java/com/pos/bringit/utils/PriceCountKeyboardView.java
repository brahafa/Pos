package com.pos.bringit.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pos.bringit.databinding.PriceCountKeyboardBinding;

import androidx.annotation.Nullable;

public class PriceCountKeyboardView extends LinearLayout implements View.OnClickListener {
    private final PriceCountKeyboardBinding binding;
    KeyPressListener keyPressListener;

    public PriceCountKeyboardView(Context context) {
        super(context);
        binding = PriceCountKeyboardBinding.inflate(LayoutInflater.from(context), this, true);
        initViews();
    }

    public PriceCountKeyboardView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        binding = PriceCountKeyboardBinding.inflate(LayoutInflater.from(context), this, true);
        initViews();
    }

    public PriceCountKeyboardView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        binding = PriceCountKeyboardBinding.inflate(LayoutInflater.from(context), this, true);
        initViews();
    }

    public void keyListener(KeyPressListener keyPressListener) {
        this.keyPressListener = keyPressListener;
    }


    private void initViews() {
        binding.key0.setOnClickListener(this);
        binding.key1.setOnClickListener(this);
        binding.key2.setOnClickListener(this);
        binding.key3.setOnClickListener(this);
        binding.key4.setOnClickListener(this);
        binding.key5.setOnClickListener(this);
        binding.key6.setOnClickListener(this);
        binding.key7.setOnClickListener(this);
        binding.key8.setOnClickListener(this);
        binding.key9.setOnClickListener(this);
        binding.keyDot.setOnClickListener(this);
        binding.keyClear.setOnClickListener(v -> keyPressListener.onKeyPress("X"));
        binding.key20.setOnClickListener(v -> keyPressListener.onPricePress(20));
        binding.key50.setOnClickListener(v -> keyPressListener.onPricePress(50));
        binding.key100.setOnClickListener(v -> keyPressListener.onPricePress(100));
        binding.key200.setOnClickListener(v -> keyPressListener.onPricePress(200));
    }

    @Override
    public void onClick(View v) {
        keyPressListener.onKeyPress(((TextView) v).getText().toString());
    }

    public interface KeyPressListener {

        void onKeyPress(String keyTxt);

        void onPricePress(int keyValue);
    }
}
