package com.wgc.mfaces.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.wgc.mfaces.activity.TestDemo;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by Administrator on 2017/2/13.
 */

public class JpushReceiver extends BroadcastReceiver{
    public static String jstxt;
    @Override
    public void onReceive(Context context, Intent intent) {
        //如果这两个相等，则说明应用已接收到自定义消息
        if (intent.getAction().equals(JPushInterface.ACTION_MESSAGE_RECEIVED)){
            //获取message里的内容
            Bundle bundle=intent.getExtras();
            String title=bundle.getString(JPushInterface.EXTRA_TITLE);
            String message=bundle.getString(JPushInterface.EXTRA_MESSAGE);
            Gson gson = new Gson();
            jstxt=message;
            //initiateSigninMsgGson = gson.toJson(initiateSigninMsg);
            //BaseApplication.json=gson.fromJson(message, InitiateSigninMsg.class);
            Toast.makeText(context,"Message title:"+title+" context:"+message, Toast.LENGTH_LONG).show();
        }
        else if(intent.getAction().equals((JPushInterface.ACTION_NOTIFICATION_OPENED))){
            Log.i("gqf","跳转testdemo");
            // 在这里可以自己写代码去定义用户点击后的行为
            Intent i = new Intent(context, TestDemo.class);  //自定义打开的界面
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);

        }
    }
}
