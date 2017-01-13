package com.vpn.vpnsdktestdemo.utils.edit_text_check;

import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

/**
 * 功能：
 *
 * Created by yyyu on 2017/1/10.
 */

public abstract class ValidationStrategy {


    protected abstract void check(TextInputLayout textInputLayout , Editable editable);

    /**
     *TextInputLayout 输入提示（检测）
     *
     */
    public void checkEdit(final TextInputLayout textInputLayout){
        EditText editText =  textInputLayout.getEditText();
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {
                check(textInputLayout, s);
            }
        });
    }

}
