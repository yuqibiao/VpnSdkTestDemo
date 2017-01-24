package com.vpn.vpnsdktestdemo.ui.activity;

import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.vpn.vpnsdktestdemo.R;

import sslvpn.sdk.VpnClient;

/**
 * 功能：主页Activity
 *
 * @author yyyu
 */
public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected int setLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        Toolbar tbMain = getView(R.id.tb_main);
        setSupportActionBar(tbMain);
    }

    @Override
    protected void initListener() {

    }

    /**
     * 退出登录按钮点击事件
     *
     * @param view
     */
    public void toLogout(View view){
        VpnClient vpnClient = VpnClient.getInstances();
        vpnClient.stopVpn(this);
        vpnClient.setOnVpnStopListener(new VpnClient.OnVpnStopListener() {
            @Override
            public void onStop() {
                Log.e(TAG, "onStop: ==============");
                finish();
            }
        });
    }

    long currentTime ;
    @Override
    public void onBackPressed() {

        if (System.currentTimeMillis() - currentTime > 2 * 1000) {
            Toast.makeText(this, "再按一次退出VPN", Toast.LENGTH_SHORT).show();
        } else {
            super.onBackPressed();
        }
        currentTime = System.currentTimeMillis();
    }



}
