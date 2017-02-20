package com.wgc.mfaces.activity;

/**
 * Created by Administrator on 2017/2/17.
 */


        import android.os.Bundle;
        import android.os.Handler;
        import android.os.Message;
        import android.support.v7.app.AppCompatActivity;
        import android.util.Log;
        import android.view.View;
        import android.widget.AdapterView;
        import android.widget.ArrayAdapter;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.Spinner;
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

        import java.util.ArrayList;
        import java.util.List;
        import java.util.Set;

        import cn.jpush.android.api.JPushInterface;
        import cn.jpush.android.api.TagAliasCallback;
        import cn.jpush.api.JPushClient;
        import cn.jpush.api.common.resp.APIConnectionException;
        import cn.jpush.api.common.resp.APIRequestException;
        import cn.jpush.api.push.PushResult;
        import cn.jpush.api.push.model.Platform;
        import cn.jpush.api.push.model.PushPayload;
        import cn.jpush.api.push.model.audience.Audience;



public class JpushActivity extends AppCompatActivity {
    //接口地址,根据本机的IP地址改动
    //private final String url="http://192.168.0.128/push/test.php";
    //返回的结果
    private TextView tvResult;
    private Button btSend; //发送推送请求
    private Spinner mSpinner; //选择推送方式
    //推送种类
    private String pushType;
    //spinner的适配器
    private ArrayAdapter<String> adapter;
    //设置alias的按钮
    private Button btSetAlias;
    //显示用户设置的alias
    private TextView tvAlias;
    private String alias;

    public LocationClient mLocationClient = null;
    public BDLocationListener myListener = new MyLocationListener2();


    //更新UI
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            StringBuffer sb = (StringBuffer) msg.obj;
            tvResult.setText(sb.toString());
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jpush);
        mSpinner= (Spinner) findViewById(R.id.push_type);
        tvResult= (TextView) findViewById(R.id.tv_result);
        btSend= (Button) findViewById(R.id.bt_send);
        btSetAlias= (Button) findViewById(R.id.bt_set);
        tvAlias= (TextView) findViewById(R.id.tv_alias);
        //spinner内的文字
       // String[] strings=getResources().getStringArray(R.array.push_type);
        String[] strings={"aa","bbb","ccc"};
        List<String> list=new ArrayList<>();
        for(String s:strings){
            list.add(s);
        }
        mLocationClient = new LocationClient(getApplicationContext()); // 声明LocationClient类
        mLocationClient.registerLocationListener(myListener); // 注册监听函数
        initLocation();


        adapter=new ArrayAdapter<String>(JpushActivity.this,android.R.layout.simple_spinner_item,list);
        //第三步：为适配器设置下拉列表下拉时的菜单样式。
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //第四步：将适配器添加到下拉列表上
        mSpinner.setAdapter(adapter);

        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                pushType=adapter.getItem(i);
                    /* 将mySpinner 显示*/
                adapterView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                adapterView.setVisibility(View.VISIBLE);
            }
        });

        //设置alias
        btSetAlias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alias=((EditText)findViewById(R.id.et_set_alias)).getText().toString();
                //调用SDK接口
                JPushInterface.setAlias(getBaseContext(),alias, new TagAliasCallback() {
                    @Override
                    public void gotResult(int i, String s, Set<String> set) {
                        tvAlias.setText("当前alias："+alias);
                        MyApplication.JPtag=alias;
                        Toast.makeText(JpushActivity.this, "设置成功", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        btSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mLocationClient.start();
                /*tvResult*/

            }
        });
    }
    private void sendRequest() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                String alias=((EditText)findViewById(R.id.et_alias)).getText().toString();


                JPushClient jPushClient = new JPushClient(MyApplication.master_Secret, MyApplication.appKey);

                //            PushPayload payLoad = PushPayload.newBuilder()
                //                    .setPlatform(Platform.all())
                //                    .setAudience(Audience.alias("软工131"))
                //                    .setAudience(Audience.all()) //这是接收对象，即谁可以接收到该推送
                //                    .setNotification(Notification.alert("收到新的签到信息")) //下发通知
                //                    .setMessage(Message.content(initiateSigninMsgGson))
                //                    .build();

                PushPayload payLoad = PushPayload.newBuilder()
                        .setPlatform(Platform.all())
                        .setAudience(Audience.alias(alias))//这是接收对象，只有别名为软工131的可以接收到该推送
                        .setNotification(cn.jpush.api.push.model.notification.Notification.alert("收到新的签到信息")) //下发通知.alert("收到新的签到信息")
                        .setMessage(cn.jpush.api.push.model.Message.content(initiateSigninMsgGson))
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
        }).start();
    }
    String initiateSigninMsgGson;
    public void  generateInitiateSigninMsg(double a,double b){
        InitiateSigninMsg initiateSigninMsg=new InitiateSigninMsg();
        initiateSigninMsg.setSigninStime("20");
        initiateSigninMsg.setSigninScope("20");
        initiateSigninMsg.setSigninLocal("大教室");
        initiateSigninMsg.setSigninClass(new String[]{"软工131","软工132"});
        initiateSigninMsg.setA(a);
        initiateSigninMsg.setB(b);
        // 利用gson对象生成json字符串

        Gson gson = new Gson();

        initiateSigninMsgGson = gson.toJson(initiateSigninMsg);

    }
    public class MyLocationListener2 implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // Receive Location
            double la;
            double lb;
            StringBuffer sb = new StringBuffer(256);
            sb.append("time : ");
            sb.append(location.getTime());
            sb.append("\nerror code : ");
            sb.append(location.getLocType());
            sb.append("\nlatitude : ");
            sb.append(location.getLatitude());
            la=location.getLatitude();

            sb.append("\nlontitude : ");
            sb.append(location.getLongitude());
            lb=location.getLongitude();

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
            //loactionTxt=sb.toString();
            //Toast.makeText(getApplicationContext(),loactionTxt,Toast.LENGTH_SHORT).show();
            //locationTx.setText(loactionTxt);
            //发送推送
            generateInitiateSigninMsg(la,lb);
            sendRequest();
            mLocationClient.stop();



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
}
