package com.wgc.mfaces.activity;

/**
 * 柳亚婷
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;


import com.wgc.mfaces.R;
import com.wgc.mfaces.adapter.InitialSigninShowClassAdapter;
import com.wgc.mfaces.constant.Constant;
import com.wgc.mfaces.okgo.BaseHttpUtil;
import com.wgc.mfaces.okgo.HttpStringCallBack;

import java.util.ArrayList;
import java.util.HashMap;

/*import cn.jpush.api.JPushClient;
import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.Notification;*/

public class InitiateSigninActivity extends AppCompatActivity implements View.OnClickListener {
    ArrayList<HashMap<String,String>> classList;
    InitialSigninShowClassAdapter showClassAdapter;
    ListView showclass_listview;
    TextView back_textview,cancel_btn,initialsignin_btn;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initiate_signin);
        classList=new ArrayList<HashMap<String,String>>();
        HashMap<String,String> map=new HashMap<String,String>();
        map.put("年级","软工131班");
        map.put("人数","32人");
        classList.add(map);

        map=new HashMap<String,String>();
        map.put("年级","软工132班");
        map.put("人数","35人");
        classList.add(map);

        map=new HashMap<String,String>();
        map.put("年级","软工133班");
        map.put("人数","33人");
        classList.add(map);

        map=new HashMap<String,String>();
        map.put("年级","软工134班");
        map.put("人数","30人");
        classList.add(map);

        map=new HashMap<String,String>();
        map.put("年级","软件135班");
        map.put("人数","28人");
        classList.add(map);

        init();
        showClassAdapter=new InitialSigninShowClassAdapter(this,classList);
        showclass_listview.setAdapter(showClassAdapter);
        addListener();
    }

    /**
     * 初始化组件
     */
    public void init(){
        showclass_listview= (ListView) findViewById(R.id.initialsignin_bottom_showclass_listview);
        back_textview= (TextView) findViewById(R.id.initialsignin_top_back_textview);
        cancel_btn= (TextView) findViewById(R.id.initialsignin_bottom_cancel_btn);
        //发起签到
        initialsignin_btn= (TextView) findViewById(R.id.initialsignin_bottom_initialsignin_btn);

    }


    /**
     * 点击事件
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.initialsignin_bottom_cancel_btn:
                finish();
                break;

            case R.id.initialsignin_top_back_textview:
                finish();
                break;
            case R.id.initialsignin_bottom_initialsignin_btn:
               // new Thread(runable).start();
                sendInitialsignin();
                break;
        }

    }

    /**
     * 给组件添加点击事件
     */
    public void addListener(){
        back_textview.setOnClickListener(this);
        cancel_btn.setOnClickListener(this);
        initialsignin_btn.setOnClickListener(this);
    }

    /**
     * 发起签到
     */
    public void sendInitialsignin(){
      // startActivity(new Intent(InitiateSigninActivity.this,JpushActivity.class));
        BaseHttpUtil baseHttpUtil=new BaseHttpUtil();

        baseHttpUtil.doGet(Constant.JpushService, new HttpStringCallBack() {
            @Override
            public void onSuccess(Object result) {
                Log.d("TAG",result.toString());
            }

            @Override
            public void onFailure(int code, String msg) {

            }
        });

    }
    /**
     * 发送推送
   /* Runnable runable=new Runnable() {
        @Override
        public void run() {
          *//*  String master_Secret = "5b7b0a004f2d3296a7593213";
            String appKey = "48b5158a67342fb7eab0e5b4";*//*

            JPushClient jPushClient = new JPushClient(master_Secret, appKey);

            PushPayload payLoad = PushPayload.newBuilder()
                    .setPlatform(Platform.all())
                    .setAudience(Audience.all()) //这是接收对象，即谁可以接收到该推送
                    .setNotification(Notification.alert("收到新的签到信息")) //下发通知
                    .setMessage(Message.content("点击进入签到页面！"))
                    .build();
            try {
                PushResult result = jPushClient.sendPush(payLoad);
                System.out.println("success");
                System.out.println(result.msg_id);
                System.out.println(result.sendno);
            } catch (APIConnectionException e) {
                // TODO Auto-generated catch block
                System.out.println("connection error");
                e.printStackTrace();
            } catch (APIRequestException e) {
                // TODO Auto-generated catch block
                System.out.println("request error");
                e.printStackTrace();
            }
        }
    };*/

}
