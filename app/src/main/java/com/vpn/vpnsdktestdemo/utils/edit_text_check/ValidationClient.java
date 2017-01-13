package com.vpn.vpnsdktestdemo.utils.edit_text_check;

import android.support.design.widget.TextInputLayout;

/**
 * 功能：
 *
 * Created by yyyu on 2017/1/10.
 */

public class ValidationClient {

    private ValidationStrategy mValidationStrategy;

    public ValidationClient(){

    }

   public void check(TextInputLayout textInputLayout){
        mValidationStrategy.checkEdit(textInputLayout);
    }

    public void setmValidationStrategy(ValidationStrategy mValidationStrategy) {
        this.mValidationStrategy = mValidationStrategy;
    }
}
