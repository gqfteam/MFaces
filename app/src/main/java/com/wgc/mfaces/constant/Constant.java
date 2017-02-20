package com.wgc.mfaces.constant;


import com.wgc.mfaces.R;
import com.wgc.mfaces.receiver.JpushReceiver;

/**
 * 数据接口常量，和请求值常量
 * Created by Administrator on 2016/9/18.
 */
public class Constant {

    //请求失败 注册用户已注册
    public static final int REQUEST_FAIL_REGISTERED=2;
    //请求成功
    public static final int REQUEST_SUCCESS=0;
    //请求失败
    public static final int REQUEST_FAIL=1;
    //请求失败 用户未注册
    public static final int REQUEST_FAIL_NOREGISTER=3;

    //Mob 获取短信APPKey
    public static final String MOB_APPKEY="1778fd1a11968";
    //Mob 获取短信APPSECRET
    public static final String MOB_APPSECRET="b5d5eff7f4302fba4e273b3bbb72d759";
    public static final int[] FieldImage = {R.mipmap.myschool,R.mipmap.myschool, R.mipmap.myschool};
    //接口url
    public static final String HOST_URL="http://192.168.3.151:8080/MFaceService/";
    //注册接口url
    public static String userPhone="";
    public static String userPassWord="";
    public static String userType ="";
    public static final String REGISTER_URL=HOST_URL+"userinfo_regiesterUserInfo?params=";
    public static final String PHONE_EXISTS_URL=HOST_URL+"userinfo_isPhoneExists?params=";
    //获取新闻列表
    public static final String getNewsList__URL=HOST_URL+"newsinfo_getNewsList";

    public static String Jpush_AppKey="8a867f6ac13f0d52eb6017f3";
    public static String Jpush_MasterSecret="df28ac8cc76b4a43daa68fca";
    public static String  JpushService=HOST_URL+"jpush_sendJpush";

}
