package com.arny.pik.utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class SimpleTextWatcher implements TextWatcher {
    private EditText et;
    private SetTextListener listener;

    public interface SetTextListener {
        String setText();

        void afterTextChanged();
    }

    // Pass the EditText instance to TextWatcher by constructor
    public SimpleTextWatcher(EditText et, SetTextListener listener) {
        this.et = et;
        this.listener = listener;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        // Unregister self before setText
        et.removeTextChangedListener(this);
        s.replace(0, s.length(), listener.setText());
        // Re-register self after setText
        et.addTextChangedListener(this);
        listener.afterTextChanged();
    }
}