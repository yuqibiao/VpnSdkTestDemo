package com.vpn.vpnsdktestdemo.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.vpn.vpnsdktestdemo.utils.ActivityHolder;

/**
 *
 * 基类Activity
 * 
 * @author yyyu
 *
 */
public abstract class BaseActivity extends AppCompatActivity{

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityHolder.addActivity(this);
        setContentView(setLayoutId());
        init();
    }

    /**
     * 初始化方法
     *
     */
    public void init(){
    	initBefore();
        initView();
        initListener();
        initData();
    }

	/**
     *
     * 钩子方法
     * 设置Activity的布局文件
     *
     * @return
     */
    protected abstract int setLayoutId();
    
    /**
     * 初始化变量
     * 
     */
    protected void initBefore() {
		
	}

    /**
     * 初始化布局文件
     *
     */
    protected abstract  void initView();


    /**
     * 初始化监听
     */
    protected  abstract void initListener();

    /**
     * 初始化数据 ， 空实现，不强制要求子类重写。
     */
    protected  void initData(){

    }

    /**
     *
     * findViewById
     *
     * @param id
     * @param <T>
     * @return
     */
    protected  <T extends View>T  getView(int id){
        return (T) findViewById(id);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityHolder.removeActivity(this);
    }
}
