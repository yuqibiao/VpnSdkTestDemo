package com.vpn.vpnsdktestdemo.utils;

import android.content.Context;
import android.text.TextUtils;

/**
 * 功能：业务逻辑封装类
 *
 * Created by yyyu on 2017/1/11.
 */

public class LogicUtils {

    private static LogicUtils mLogicUtils;
    private Context mContext;

    private LogicUtils(Context context){
        this.mContext = context;
    }

    public static LogicUtils getInstance(Context mContext){
        if (mLogicUtils == null){
            synchronized (LogicUtils.class){
                if (mLogicUtils == null){
                    mLogicUtils = new LogicUtils(mContext.getApplicationContext());
                }
            }
        }
        return mLogicUtils;
    }

    public boolean isRemember(){
        boolean isRem = false;
        if(getByPwdLoginInfo()!=null || getByCertLoginInfo()!=null){
            isRem = true;
        }
        return  isRem;
    }

    /**
     *
     * 此处测试用明文存储，项目中请将密码加密
     *
     * 记住密码（用户名密码方式）
     */
    private String LOGIN_BY_PWD_INFO = "LOGIN_BY_PWD_INFO";
    public void saveLoginByPwdInfo(String address ,String username , String pwd){
        String loginInfo =address+"</split>"+ username+"</split>"+pwd;
        MySPUtils.put(mContext , LOGIN_BY_PWD_INFO , loginInfo);
    }
    public  void removeLoginByPwdInfo(){
        MySPUtils.remove(mContext , LOGIN_BY_PWD_INFO);
    }

    /**
     *得到登录信息（用户名密码方式）
     *
     * @return null 表示未记住密码
     *                  infos[0]：服务器地址
     *                  infos[1]：用户名
     *                  infos[2]：密码
     */
    public  String[] getByPwdLoginInfo(){
        String infoStr= (String) MySPUtils.get(mContext , LOGIN_BY_PWD_INFO,"");
        if(TextUtils.isEmpty(infoStr)){
            return null;
        }else{
            String[] infos= new String[]{"" ,"" , ""};
            String[] splitStr = infoStr.split("</split>");
            for (int i = 0  ; i <splitStr.length; i++) {
                if(i<3){
                    infos[i] = splitStr[i];
                }else{
                    //---异常
                }
            }
            return infos;
        }
    }


    /**
     * 记住密码（证书方式）
     */
    private String LOGIN_BY_CERT_INFO = "LOGIN_BY_CERT_INFO";
    public void saveLoginByCertInfo(String address ,String certPath , String pwd){
        String loginInfo =address+"</split>"+ certPath+"</split>"+pwd;
        MySPUtils.put(mContext , LOGIN_BY_CERT_INFO , loginInfo);
    }
    public  void removeLoginByCertInfo(){
        MySPUtils.remove(mContext , LOGIN_BY_CERT_INFO);
    }

    /**
     *得到登录信息（用户名密码方式）
     *
     * @return null 表示未记住密码
     *                  infos[0]：服务器地址
     *                  infos[1]：证书路径
     *                  infos[2]：密码
     */
    public  String[] getByCertLoginInfo(){
        String infoStr= (String) MySPUtils.get(mContext , LOGIN_BY_CERT_INFO,"");
        if(TextUtils.isEmpty(infoStr)){
            return null;
        }else{
            String[] infos= new String[]{"" ,"" , ""};
            String[] splitStr = infoStr.split("</split>");
            for (int i = 0  ; i <splitStr.length; i++) {
                if(i<3){
                    infos[i] = splitStr[i];
                }else{
                    //---异常
                }
            }
            return infos;
        }
    }


}
