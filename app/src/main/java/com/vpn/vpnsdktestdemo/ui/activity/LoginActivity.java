package com.vpn.vpnsdktestdemo.ui.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.vpn.vpnsdktestdemo.R;
import com.vpn.vpnsdktestdemo.ui.dialog.LoadingDialog;
import com.vpn.vpnsdktestdemo.utils.LogicUtils;
import com.vpn.vpnsdktestdemo.utils.edit_text_check.ValidateIpAddress;
import com.vpn.vpnsdktestdemo.utils.edit_text_check.ValidatePwd;
import com.vpn.vpnsdktestdemo.utils.edit_text_check.ValidationClient;

import sslvpn.sdk.IVpnCallBack;
import sslvpn.sdk.VpnClient;

import static sslvpn.sdk.VpnClient.getInstances;

/**
 * 功能：登录界面
 *
 * Created by yyyu on 2017/1/10.
 */

public class LoginActivity extends BaseActivity {

    private static final String TAG = "LoginActivity";
    private LoginType loginType;
    private CheckBox cbRemember;
    private CheckBox cbCompatibility;
    private LogicUtils logicUtils;
    private TextInputLayout tilPwdAddress;
    private TextInputLayout tilCertAddress;
    private TextInputLayout tilUsername;
    private EditText etCertPath;
    private TextInputLayout tilPwd;
    private TextInputLayout tilCertPwd;
    private ValidationClient validationClient;
    private ImageButton ibFileChoice;
    private VpnClient vpnClient;

    private static final  int SHOW_TOAST = 0;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case SHOW_TOAST:{
                    Toast.makeText(LoginActivity.this, ""+msg.obj.toString(), Toast.LENGTH_SHORT).show();
                    break;
                }
            }
        }
    };

    private enum LoginType{
        BY_PWD , BY_CERT
    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initBefore() {
        super.initBefore();
        logicUtils = LogicUtils.getInstance(this);
        vpnClient = getInstances();
        validationClient = new ValidationClient();
        loginType = LoginType.BY_PWD;
    }

    @Override
    protected void initView() {
        Toolbar tbLogin = getView(R.id.tb_login);
        setSupportActionBar(tbLogin);

        tilPwdAddress = getView(R.id.til_pwd_address);
        tilCertAddress = getView(R.id.til_cert_address);
        validationClient.setmValidationStrategy(new ValidateIpAddress());
        validationClient.check(tilPwdAddress);
        validationClient.check(tilCertAddress);

        tilUsername = getView(R.id.til_username);
        etCertPath = getView(R.id.et_cert_path);

        tilPwd = getView(R.id.til_pwd);
        tilCertPwd = getView(R.id.til_cert_pwd);
        validationClient.setmValidationStrategy(new ValidatePwd());
       // validationClient.check(tilPwd);
       // validationClient.check(tilCertPwd);

        ibFileChoice = getView(R.id.ib_file_choice);
        cbRemember = getView(R.id.cb_remember);
        cbCompatibility = getView(R.id.cb_compatibility);

        inflateIfRem();
    }

    /**
     * 记住密码后自动填充
     */
    private void inflateIfRem() {
        String[] pwdLoginInfo = logicUtils.getByPwdLoginInfo();
        String[] certLoginInfo = logicUtils.getByCertLoginInfo();
        if (logicUtils.isRemember()){
            cbRemember.setChecked(true);
        }
        if (pwdLoginInfo != null){
            tilPwdAddress.getEditText().setText(pwdLoginInfo[0]);
            tilUsername.getEditText().setText(pwdLoginInfo[1]);
            tilPwd.getEditText().setText(pwdLoginInfo[2]);
        }
        if(certLoginInfo != null){
            tilCertAddress.getEditText().setText(certLoginInfo[0]);
            etCertPath.setText(certLoginInfo[1]);
            tilCertPwd.getEditText().setText(certLoginInfo[2]);
        }
    }

    @Override
    protected void initListener() {
        //---选择证书
        ibFileChoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(LoginActivity.this , FileExportActivity.class) , 0);
            }
        });
    }

    /**
     * 登录按钮点击事件
     *
     * @param view
     */
    public void toLogin(View view){

        if(vpnClient.getVPNConnState() == VpnClient.VPN_CONNECTED){//已经连接
            Toast.makeText(this, "VPN 已经连接", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(LoginActivity.this , MainActivity.class));
            LoginActivity.this.finish();
            return;
        }

        final LoadingDialog dialog = new LoadingDialog(this);
        //模式 0：普通模式 1：兼容模式
        String oldvers;
        if (cbCompatibility.isEnabled()){
            oldvers =Integer.toString(1);
        }else{
            oldvers =Integer.toString(0);
        }
        switch (loginType){
            case BY_PWD:{//---户名密码的方式登录
                if(tilPwdAddress.isErrorEnabled()
                        ||tilUsername.isErrorEnabled()){
                    Toast.makeText(this, "输入有误！！", Toast.LENGTH_SHORT).show();
                    break;
                }

                dialog.show();
                //服务器地址
                final String server = tilPwdAddress.getEditText().getText().toString() ;
                //认证类型 0：用户和密码认证	1：证书认证	2：TF卡加密认证
                String authType = Integer.toString(0);//此处为用户名密码认证，固定为0
                //用户名
                final String vpnUsername = tilUsername.getEditText().getText().toString();
                //密码
                final String vpnPsw = tilPwd.getEditText().getText().toString();
                String[] authInfo={oldvers, server, authType, vpnUsername, vpnPsw};

                vpnClient.startVpn(authInfo, LoginActivity.this, new IVpnCallBack() {
                    @Override
                    public void vpnCallBack(int status ,  String s) {
                        Log.e(TAG, "vpnCallBack: ===i="+status+"result :" +s );
                        if(status==200){
                            startActivity(new Intent(LoginActivity.this , MainActivity.class));
                            LoginActivity.this.finish();
                            if(cbRemember.isChecked()){
                                logicUtils.saveLoginByPwdInfo(server , vpnUsername , vpnPsw);
                            }else{
                                logicUtils.removeLoginByPwdInfo();
                            }

                        }else{
                            Message msg = new Message();
                            msg.what = SHOW_TOAST;
                            msg.obj = "错误："+s;
                            mHandler.sendMessage(msg);
                        }
                        dialog.dismiss();
                    }
                });
                break;
            }
            case BY_CERT:{//----证书的方式登录
                if(tilCertAddress.isErrorEnabled()){
                    Toast.makeText(this, "输入有误！！", Toast.LENGTH_SHORT).show();
                    break;
                }

                dialog.show();

                //服务器地址
                final String server = tilCertAddress.getEditText().getText().toString() ;
                //证书地址
                final String certPath = etCertPath.getText().toString();
                //此处为证书认证，固定为1
                String authType = Integer.toString(1);
                //证书密码
                final String certPsw = tilCertPwd.getEditText().getText().toString();
                String[] authinfo={oldvers, server, authType, certPath, certPsw};
                vpnClient.startVpn(authinfo, this, new IVpnCallBack() {
                    @Override
                    public void vpnCallBack(int status, String s) {
                        Log.e(TAG, "vpnCallBack: ===i="+status+"result :" +s );
                        if(status==200){
                            startActivity(new Intent(LoginActivity.this , MainActivity.class));
                            LoginActivity.this.finish();
                            if(cbRemember.isChecked()){
                                logicUtils.saveLoginByCertInfo(server , certPath , certPsw);
                            }else{
                                logicUtils.removeLoginByCertInfo();
                            }

                        }else{
                            Message msg = new Message();
                            msg.what = SHOW_TOAST;
                            msg.obj = "错误："+s;
                            mHandler.sendMessage(msg);
                        }
                        dialog.dismiss();
                    }
                });
                break;
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode){
            case FileExportActivity.FILE_CHOICE_RESULT_CODE:{
                etCertPath.setText(data.getStringExtra(FileExportActivity.FILE_CHOICE_RESULT));
                //Toast.makeText(this, ""+data.getStringExtra(FileExportActivity.FILE_CHOICE_RESULT), Toast.LENGTH_SHORT).show();
                //Log.e(TAG, "onActivityResult: ===path:  "+data.getStringExtra(FileExportActivity.FILE_CHOICE_RESULT) );
                break;
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.login_by_psw:{//通过用户名密码登录
                getView(R.id.pt_by_pwd).setVisibility(View.VISIBLE);
                getView(R.id.pt_by_cert).setVisibility(View.GONE);
                loginType = LoginType.BY_PWD;
                break;
            }
            case R.id.login_by_cert:{//通过证书方式登录
                getView(R.id.pt_by_pwd).setVisibility(View.GONE);
                getView(R.id.pt_by_cert).setVisibility(View.VISIBLE);
                loginType = LoginType.BY_CERT;
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_login_way , menu);
        return true;
    }
}
