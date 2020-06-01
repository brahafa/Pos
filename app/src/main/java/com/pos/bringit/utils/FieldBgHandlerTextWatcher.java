package com.pos.bringit.utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

public class FieldBgHandlerTextWatcher implements TextWatcher {

    private EditText mEditText;
    private TextView mTitle;

    public FieldBgHandlerTextWatcher(EditText editText, TextView title) {
        mEditText = editText;
        mTitle = title;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        mEditText.setActivated(s.toString().isEmpty());
        mTitle.setActivated(s.toString().isEmpty());
    }

    @Override
    public void afterTextChanged(Editable s) {
    }
}