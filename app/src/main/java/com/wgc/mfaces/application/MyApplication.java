package com.wgc.mfaces.application;

import android.app.Application;

import com.zhy.http.okhttp.OkHttpUtils;

import java.util.concurrent.TimeUnit;

import cn.jpush.android.api.JPushInterface;
import okhttp3.OkHttpClient;

public class MyApplication extends Application
{
    public static String JPtag="";
    public static String master_Secret = "df28ac8cc76b4a43daa68fca";
    public static  String appKey = "8a867f6ac13f0d52eb6017f3";
    @Override
    public void onCreate()
    {
        super.onCreate();

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .addInterceptor(new LoggerInterceptor("TAG"))
                  .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                  .readTimeout(10000L, TimeUnit.MILLISECONDS)
                  //其他配置
                 .build();

        OkHttpUtils.initClient(okHttpClient);
        //极光推送配置
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);

    }
}