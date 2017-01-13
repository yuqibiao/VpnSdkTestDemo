package com.vpn.vpnsdktestdemo.ui.dialog;

import android.content.Context;
import android.widget.TextView;

import com.vpn.vpnsdktestdemo.R;
import com.vpn.vpnsdktestdemo.utils.DimensChange;

/**
 * 功能：加载Dialog
 *
 * Created by yyyu on 2017/1/11.
 */

public class LoadingDialog extends BaseDialog{

    private TextView tvTip;

    public LoadingDialog(Context context) {
        super(context);
        lp.width = DimensChange.dp2px(context , 100);
        lp.height = DimensChange.dp2px(context , 100);
        lp.dimAmount = 0.7f;
        getWindow().setAttributes(lp);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_loading;
    }

    @Override
    protected void initView() {
        tvTip = getView(R.id.tv_tip);
    }

    @Override
    protected void initListener() {

    }

    /**
     * 设置加载提示
     *
     */
    public void setTip(String tipStr){
        tvTip.setText(tipStr);
    }
}
