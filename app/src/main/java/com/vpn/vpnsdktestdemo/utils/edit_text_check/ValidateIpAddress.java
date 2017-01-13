package com.vpn.vpnsdktestdemo.utils.edit_text_check;

import android.support.design.widget.TextInputLayout;
import android.text.Editable;

import com.vpn.vpnsdktestdemo.utils.FormValidationUtils;

/**
 * 功能：验证IP地址
 *
 * Created by yyyu on 2017/1/10.
 */

public class ValidateIpAddress extends ValidationStrategy{

    @Override
    protected void check(TextInputLayout textInputLayout, Editable editable) {
        if (FormValidationUtils.isIpAddress(editable.toString()) || FormValidationUtils.isUrl(editable.toString())){
            textInputLayout.setErrorEnabled(false);
        }else{
            textInputLayout.setError("输入的Ip或URL的格式错误！！！");
            textInputLayout.setErrorEnabled(true);
        }
    }
}
