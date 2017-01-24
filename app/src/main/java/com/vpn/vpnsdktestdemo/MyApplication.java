package com.vpn.vpnsdktestdemo;

import android.app.Application;
import android.util.Log;

import sslvpn.sdk.VpnClient;

/**
 * 功能：自定义Application
 * <p>
 * Created by yyyu on 2017/1/10.
 */

public class MyApplication extends Application {

    private static final String TAG = "MyApplication";

    @Override
    public void onCreate() {
        super.onCreate();
        /**
         * VPN 状态的回调
         *
         */
        VpnClient.getInstances().setOnVpnCmdStateListener(new VpnClient.OnVpnCmdStateListener() {
            @Override
            public void onVpnCmdState(int i){
            //0启动VpnService
            // 1重新认证
            // 2下载配置文件
            // 3启动VPN
            // 4 启动保活
            // 5账号在别处登录
            // 6用户不在线
            // 7虚IP地址已用尽
            // 8保活失败
            // 9保活成功
            // 10已取消VPN授权
            // 11被管理员踢下线
            // 12VPN进程已结束
            // 13取得VPN授权
            // 14用户注销结束
                Log.e(TAG, "onVpnCmdState: =====VPN状态"+i );
            }
        });
    }

}
