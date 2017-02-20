package com.wgc.mfaces.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;
import com.google.gson.Gson;
import com.wgc.mfaces.R;
import com.wgc.mfaces.application.MyApplication;
import com.wgc.mfaces.receiver.JpushReceiver;

import java.util.List;

import cn.jpush.api.JPushClient;
import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.Notification;

public class TestDemo extends AppCompatActivity {
    Button btn;
    Button location;
    Button face;
    double lat_a;
    double lat_b;
    double lng_a;
    double lng_b;

    TextView lTxt;
    public LocationClient mLocationClient = null;
    public BDLocationListener myListener = new MyLocationListener();
    TextView jpTxt;
    TextView locationTx;
    boolean islocation=false;
    public static boolean isface=false;

    public static  double faced=0;
    TextView fTxt;
    private final double EARTH_RADIUS = 6378137.0;
    private double gps2m(double lat_a, double lng_a, double lat_b, double lng_b) {
        double radLat1 = (lat_a * Math.PI / 180.0);
        double radLat2 = (lat_b * Math.PI / 180.0);
        double a = radLat1 - radLat2;
        double b = (lng_a - lng_b) * Math.PI / 180.0;
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
                + Math.cos(radLat1) * Math.cos(radLat2)
                * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000) / 10000;
        return s;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if(faced!=0){
            fTxt.setText("人脸相似度"+faced);
        }
    }

    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);// 可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");// 可选，默认gcj02，设置返回的定位结果坐标系
        int span = 1000;
        option.setScanSpan(span);// 可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);// 可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);// 可选，默认false,设置是否使用gps
        option.setLocationNotify(true);// 可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);// 可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);// 可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);// 可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);// 可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);// 可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        mLocationClient.setLocOption(option);
    }
    InitiateSigninMsg initiateSigninMsg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_demo);
        btn= (Button) findViewById(R.id.btnaa);
        location= (Button) findViewById(R.id.location);
        face= (Button) findViewById(R.id.face);
        locationTx=(TextView)findViewById(R.id.locationTxt);
        mLocationClient = new LocationClient(getApplicationContext()); // 声明LocationClient类
        mLocationClient.registerLocationListener(myListener); // 注册监听函数
        initLocation();
        jpTxt=(TextView)findViewById(R.id.jpTxt);
        jpTxt.setText(JpushReceiver.jstxt);
        fTxt=(TextView)findViewById(R.id.fTxt);
        Gson g=new Gson();
        initiateSigninMsg=g.fromJson(JpushReceiver.jstxt, InitiateSigninMsg.class);
        lTxt=(TextView)findViewById(R.id.lTxt);

        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLocationClient.start();
            }
        });
        face.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(TestDemo.this,CameraPreviewActivity.class);
                //startActivityForResult(i,1234);
                startActivity(i);
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isface) {

                    if(islocation) {
                        new Thread(runable).start();
                    }
                    else {
                        Toast.makeText(getApplicationContext(),"超出定位距离",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(getApplicationContext(),"人脸识别失败",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 4321:
                Double facenunm=data.getDoubleExtra("facenum",0);
                Toast.makeText(this,"相似程度"+facenunm,Toast.LENGTH_SHORT).show();
                if(facenunm>90){
                    isface=true;
                }else{
                    isface=false;
                }
                break;

        }


    }

    /**
     * 发送推送
     */
    Runnable runable=new Runnable() {
        @Override
        public void run() {



            JPushClient jPushClient = new JPushClient(MyApplication.master_Secret,MyApplication.appKey);

//            PushPayload payLoad = PushPayload.newBuilder()
//                    .setPlatform(Platform.all())
//                    .setAudience(Audience.alias("软工131"))
//                    .setAudience(Audience.all()) //这是接收对象，即谁可以接收到该推送
//                    .setNotification(Notification.alert("收到新的签到信息")) //下发通知
//                    .setMessage(Message.content(initiateSigninMsgGson))
//                    .build();

            PushPayload payLoad = PushPayload.newBuilder()
                    .setPlatform(Platform.all())
                    .setAudience(Audience.alias(MyApplication.JPtag))//这是接收对象，只有别名为软工131的可以接收到该推送
                    .setNotification(Notification.alert("收到新的签到信息")) //下发通知
                    .setMessage(Message.content("已签到！"))
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
    };
    String loactionTxt;
    public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            double la;
            double lb;
            // Receive Location
            StringBuffer sb = new StringBuffer(256);
            sb.append("time : ");
            sb.append(location.getTime());
            sb.append("\nerror code : ");
            sb.append(location.getLocType());
            la=location.getLatitude();

            sb.append("\nlatitude : ");
            sb.append(location.getLatitude());
            lb=location.getLongitude();
            sb.append("\nlontitude : ");
            sb.append(location.getLongitude());
            sb.append("\nradius : ");
            sb.append(location.getRadius());
            if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
                sb.append("\nspeed : ");
                sb.append(location.getSpeed());// 单位：公里每小时
                sb.append("\nsatellite : ");
                sb.append(location.getSatelliteNumber());
                sb.append("\nheight : ");
                sb.append(location.getAltitude());// 单位：米
                sb.append("\ndirection : ");
                sb.append(location.getDirection());// 单位度
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
                sb.append("\ndescribe : ");
                sb.append("gps定位成功");

            } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
                // 运营商信息
                sb.append("\noperationers : ");
                sb.append(location.getOperators());
                sb.append("\ndescribe : ");
                sb.append("网络定位成功");
            } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
                sb.append("\ndescribe : ");
                sb.append("离线定位成功，离线定位结果也是有效的");
            } else if (location.getLocType() == BDLocation.TypeServerError) {
                sb.append("\ndescribe : ");
                sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
            } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                sb.append("\ndescribe : ");
                sb.append("网络不同导致定位失败，请检查网络是否通畅");
            } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                sb.append("\ndescribe : ");
                sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
            }
            sb.append("\nlocationdescribe : ");
            sb.append(location.getLocationDescribe());// 位置语义化信息
            List<Poi> list = location.getPoiList();// POI数据
            if (list != null) {
                sb.append("\npoilist size = : ");
                sb.append(list.size());
                for (Poi p : list) {
                    sb.append("\npoi= : ");
                    sb.append(p.getId() + " " + p.getName() + " " + p.getRank());
                }
            }
            Log.i("BaiduLocationApiDem", sb.toString());
            loactionTxt=sb.toString();
            Toast.makeText(getApplicationContext(),loactionTxt,Toast.LENGTH_SHORT).show();
            locationTx.setText(loactionTxt);
            double l=gps2m(la,lb,initiateSigninMsg.getA(),initiateSigninMsg.getB());
            lTxt.setText("距离："+l);
            if(l<Double.valueOf(initiateSigninMsg.getSigninScope())){
                islocation=true;
            }
            mLocationClient.stop();
        }
    }
}
