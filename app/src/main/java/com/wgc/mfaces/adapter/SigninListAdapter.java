package com.wgc.mfaces.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;
import com.wgc.mfaces.R;
import com.wgc.mfaces.activity.CameraPreviewActivity;
import com.wgc.mfaces.model.InitiateSigninMsg;
import com.wgc.mfaces.model.SigninInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by jyq on 2016/9/21.
 */
public class SigninListAdapter extends BaseAdapter {
    private ArrayList<SigninInfo> datas;//数据源
    private Context mContext;
    private LayoutInflater layoutInflater;
    private LocationClient mLocationClient = null;
    private boolean islocation=false;
    double lat_a;
    double lat_b;
    double lng_a;
    double lng_b;
    private final double EARTH_RADIUS = 6378137.0;
    InitiateSigninMsg initiateSigninMsg;
    private BDLocationListener myListener = new MyLocationListener();
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


    public void update(ArrayList<SigninInfo> datas) {
        this.datas = datas;
        this.notifyDataSetChanged();
    }

    public SigninListAdapter(Context context, ArrayList<SigninInfo> datas) {
        this.mContext = context;
        this.datas = datas;
        this.layoutInflater = LayoutInflater.from(context);
        mLocationClient = new LocationClient(context); // 声明LocationClient类
        mLocationClient.registerLocationListener(myListener); // 注册监听函数

    }

    public int getCount() {
        if (datas == null) {
            return 0;
        }
        return datas.size();
    }

    public Object getItem(int arg0) {
        return datas.get(arg0);
    }

    public long getItemId(int arg0) {
        return arg0;
    }


    public View getView(int arg0, View arg1, ViewGroup arg2) {
        ViewHolder mHolder;
        if (arg1 == null) {

            arg1 = layoutInflater.inflate(R.layout.temporary_list_item,
                    null);
            mHolder = new ViewHolder(arg1);
            //设置地点本框获取焦点 从而实现跑马灯效果
            mHolder.tvPlace.setSelected(true);
            mHolder.tvPlace.setClickable(true);
            //mHolder.tvPlace.refreshDrawableState();
            arg1.setTag(mHolder);

        } else {
            mHolder = (ViewHolder) arg1.getTag();
        }

        //测试获取焦点问题   后期可以删除
        mHolder.btSigninButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("TAGLocatoion","开始签到");
                initLocation();
                Log.d("TAGLocatoion","开始定位");
                mLocationClient.start();
                mLocationClient.requestLocation();



            }
        });

        //模拟数据  后期可以删除
        if (arg0==0){
            mHolder.buttonPanel.setVisibility(View.VISIBLE);
            mHolder.imgPanel.setVisibility(View.GONE);


        }else if(arg0==2) {
            mHolder.imgSignature.setImageResource(R.mipmap.signinlist_fail);
            mHolder.buttonPanel.setVisibility(View.GONE);
            mHolder.imgPanel.setVisibility(View.VISIBLE);
        }else{
            mHolder.buttonPanel.setVisibility(View.GONE);
            mHolder.imgPanel.setVisibility(View.VISIBLE);

        }


        return arg1;
    }


    static class ViewHolder {
        @Bind(R.id.name)
        TextView tvName;
        @Bind(R.id.time)
        TextView tvTime;
        @Bind(R.id.place)
        TextView tvPlace;
        @Bind(R.id.purpose)
        TextView tvPurpose;
        @Bind(R.id.returnTime)
        TextView tvReturnTime;
        @Bind(R.id.signin_button)
        Button btSigninButton;
        @Bind(R.id.signature)
        ImageView imgSignature;
        @Bind(R.id.buttonPanel)
        RelativeLayout buttonPanel;
        @Bind(R.id.imgPanel)
        RelativeLayout imgPanel;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
    private void initLocation() {
        Log.d("TAGLocatoion","地址初始化");
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
            // loactionTxt=sb.toString();
            Toast.makeText(mContext,sb.toString(),Toast.LENGTH_SHORT).show();
            //locationTx.setText(loactionTxt);
            Log.d("TAGLocatoion","la----"+la+"----lb"+lb);
           /* double l=gps2m(la,lb,initiateSigninMsg.getA(),initiateSigninMsg.getB());
           // lTxt.setText("距离："+l);
            if(l<Double.valueOf(initiateSigninMsg.getSigninScope())){
                islocation=true;
            }*/
            islocation=true;
            if(islocation){
                mContext.startActivity(new Intent(mContext, CameraPreviewActivity.class));
            }else{
                Toast.makeText(mContext,"对不起，您不在签到范围内",Toast.LENGTH_SHORT).show();
            }
            mLocationClient.stop();
        }
    }

}
