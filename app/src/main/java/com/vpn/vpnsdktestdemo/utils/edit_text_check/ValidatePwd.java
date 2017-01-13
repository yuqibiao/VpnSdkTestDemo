package com.vpn.vpnsdktestdemo.utils.edit_text_check;

import android.support.design.widget.TextInputLayout;
import android.text.Editable;

/**
 * 功能：验证密码
 *
 * Created by yyyu on 2017/1/10.
 */

public class ValidatePwd extends ValidationStrategy{

    @Override
    protected void check(TextInputLayout textInputLayout, Editable editable) {
        if (editable.length()<6){
            textInputLayout.setError("密码不能小于6位！！！");
            textInputLayout.setErrorEnabled(true);
        }else {
            textInputLayout.setErrorEnabled(false);
        }
    }
}
