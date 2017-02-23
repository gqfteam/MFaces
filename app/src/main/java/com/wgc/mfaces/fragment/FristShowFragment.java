package com.wgc.mfaces.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.wgc.mfaces.R;
import com.wgc.mfaces.adapter.NewsListAdapter;
import com.wgc.mfaces.constant.Constant;
import com.wgc.mfaces.model.NewsInfo;
import com.wgc.mfaces.model.ResultMessage;
import com.wgc.mfaces.model.Sentence;
import com.wgc.mfaces.okgo.BaseHttpUtil;
import com.wgc.mfaces.okgo.HttpStringCallBack;
import com.wgc.mfaces.view.CarouselView;
import com.wgc.mfaces.view.SearchView;
import com.wgc.mfaces.view.VerticalScrollTextView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/1/29.
 */
public class FristShowFragment  extends Fragment{
    View mView;
    //
    /* @Bind(R.id.schoolinfo_ScrollView)
     ScrollView mSchoolinfoScrollView;
     @Bind(R.id.schoolinfo_top_address_tv)
     TextView schoolinfoTopAddressTv;
     @Bind(R.id.schoolinfo_top_address_iv)
     ImageView schoolinfoTopAddressIv;
     @Bind(R.id.schoolinfo_top_search_sv)
     SearchView schoolinfoTopSearchSv;
     @Bind(R.id.schoolinfo_top_qcord_iv)
     ImageView schoolinfoTopQcordIv;
     @Bind(R.id.schoolinfo_top_chat_iv)
     ImageView mSchoolinfoTopChatIv;
     @Bind(R.id.schoolinfo_top_carousel_cv)
     CarouselView mSchoolinfoTopCarouselCv;
     @Bind(R.id.schoolinfo_top_netteach_rb)
     RadioButton schoolinfoTopNetteachRb;
     @Bind(R.id.schoolinfo_top_zhxg_rb)
     RadioButton schoolinfoTopZhxgRb;
     @Bind(R.id.schoolinfo_top_netservice_rb)
     RadioButton schoolinfoTopNetserviceRb;
     @Bind(R.id.schoolinfo_top_classrooom_rb)
     RadioButton schoolinfoTopClassrooomRb;
     @Bind(R.id.schoolinfo_top_phone_rb)
     RadioButton schoolinfoTopPhoneRb;
     @Bind(R.id.schoolinfo_top_classinfo_rb)
     RadioButton schoolinfoTopClassinfoRb;
     @Bind(R.id.schoolinfo_top_scoresearch_rb)
     RadioButton schoolinfoTopScoresearchRb;
     @Bind(R.id.schoolinfo_top_yikatongfuwu_rb)
     RadioButton schoolinfoTopYikatongfuwuRb;
     @Bind(R.id.schoolinfo_top_xiaoli_rb)
     RadioButton schoolinfoTopXiaoliRb;
     @Bind(R.id.schoolinfo_top_lixiao_rb)
     RadioButton schoolinfoTopLixiaoRb;
     @Bind(R.id.schoolinfo_top_radiogroup_rg)
     RadioGroup schoolinfoTopRadiogroupRg;
     *//* @Bind(R.id.schoolinfo_moddle_newslist_vstv)*//*
    VerticalScrollTextView mNewListVerticalScrollTextView;
    //设置listview数据
    @Bind(R.id.schoolinfo_moddle_infolist_lv)
    ListView schoolinfoModdleInfolistLv;*/
    WebView webview;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.webview_layout, container, false);
        webview = (WebView) mView.findViewById(R.id.webview);
        ButterKnife.bind(this, mView);
        //  mNewListVerticalScrollTextView = (VerticalScrollTextView) mview.findViewById(R.id.schoolinfo_moddle_newslist_vstv);
        initDisplay();

        return mView;

    }

  /*  *//*初始化数据*//*
    public void initDatas() {
        mSchoolinfoTopCarouselCv.setImagesRes(Constant.FieldImage);
        //初始话列表数据
        initNewsList();



    }*/



    /*初始化显示信息*/
    public void initDisplay() {

        WebSettings webSettings = webview.getSettings();
        //设置WebView属性，能够执行Javascript脚本
        webSettings.setJavaScriptEnabled(true);
        //设置可以访问文件
        webSettings.setAllowFileAccess(true);
        //设置支持缩放
        webSettings.setBuiltInZoomControls(true);
        //加载需要显示的网页
        webview.loadUrl("file:///android_asset/hkd/main.html");
        //设置Web视图
        // webview.setWebViewClient(new webViewClient ());


        //initDatas();
        // mSchoolinfoScrollView.setOnTouchListener(new TouchListenerImpl());
    }
    //Web视图


    //设置回退
    //覆盖Activity类的onKeyDown(int keyCoder,KeyEvent event)方法
 /*   @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webview.canGoBack()) {
            webview.goBack(); //goBack()表示返回WebView的上一页面
            return true;
        }

        return false;
    }*/
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
/*
    @OnClick({R.id.schoolinfo_top_address_tv, R.id.schoolinfo_top_address_iv, R.id.schoolinfo_top_search_sv, R.id.schoolinfo_top_chat_iv, R.id.schoolinfo_top_carousel_cv, R.id.schoolinfo_top_netteach_rb, R.id.schoolinfo_top_zhxg_rb, R.id.schoolinfo_top_netservice_rb, R.id.schoolinfo_top_classrooom_rb, R.id.schoolinfo_top_phone_rb, R.id.schoolinfo_top_classinfo_rb, R.id.schoolinfo_top_scoresearch_rb, R.id.schoolinfo_top_yikatongfuwu_rb, R.id.schoolinfo_top_xiaoli_rb, R.id.schoolinfo_top_lixiao_rb, R.id.schoolinfo_top_radiogroup_rg})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.schoolinfo_top_address_tv:
                break;
            case R.id.schoolinfo_top_address_iv:
                break;
            case R.id.schoolinfo_top_search_sv:
                break;
            case R.id.schoolinfo_top_chat_iv:
                //startActivity(new Intent(getActivity(), ContactActivity.class));
                break;
            case R.id.schoolinfo_top_carousel_cv:
                break;
            case R.id.schoolinfo_top_netteach_rb:
                break;
            case R.id.schoolinfo_top_zhxg_rb:
                break;
            case R.id.schoolinfo_top_netservice_rb:
                break;
            case R.id.schoolinfo_top_classrooom_rb:
                break;
            case R.id.schoolinfo_top_phone_rb:
                break;
            case R.id.schoolinfo_top_classinfo_rb:
                break;
            case R.id.schoolinfo_top_scoresearch_rb:
                break;
            case R.id.schoolinfo_top_yikatongfuwu_rb:
                break;
            case R.id.schoolinfo_top_xiaoli_rb:
                break;
            case R.id.schoolinfo_top_lixiao_rb:
                break;
            case R.id.schoolinfo_top_radiogroup_rg:
                break;
        }
    }

    *//*//***//*
     * 新闻设置滚动条的数据
     *//*
    public void initNewsList() {
        Log.d("TAG","开始请求数据");
        Log.d("TAG",Constant.getNewsList__URL);
        BaseHttpUtil baseHttpUtil=new BaseHttpUtil();
        baseHttpUtil.doGet(Constant.getNewsList__URL, new HttpStringCallBack() {
            @Override
            public void onSuccess(Object result) {
                Log.d("TAG","请求数据");

                String res = (String) result;
                Log.d("TAG",res);
                Gson gson = new Gson();
                //获取初始化的数据
                ResultMessage resultMessage = gson.fromJson(res, ResultMessage.class);
                ArrayList<NewsInfo> data = (ArrayList<NewsInfo>) resultMessage.getDatas();
                NewsListAdapter adapter=new NewsListAdapter(getActivity(),data);
                schoolinfoModdleInfolistLv.setAdapter(adapter);

             *//*   List lst = new ArrayList<Sentence>();
                if (data.size() > 3) {
                    for (int i = 0; i < 3; i++) {
                        Sentence sen = new Sentence(i, i + "、" + data.get(i).getNewsTitle());
                        lst.add(i, sen);
                    }
                }else{
                    for (int i = 0; i < data.size(); i++) {
                        Sentence sen = new Sentence(i, i + "、" + data.get(i).getNewsTitle());
                        lst.add(i, sen);

                    }
                }
                //给View传递数据
                Log.d("TAG", mNewListVerticalScrollTextView + "---" + lst);

                mNewListVerticalScrollTextView.setList(lst);
                //更新View
                mNewListVerticalScrollTextView.updateUI();*//*//**//**//*


            }

            @Override
            public void onFailure(int code, String msg) {

            }
        });
        *//*OkHttpUtils.post().url(Constant.getNewsList__URL).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Log.d("TAG","请求失败");
            }

            @Override
            public void onResponse(String response, int id) {
                Log.d("TAG","请求数据");
                Log.d("TAG",response);
                String res = response;
                Gson gson = new Gson();
                //获取初始化的数据
                ResultMessage resultMessage = gson.fromJson(res, ResultMessage.class);
                ArrayList<NewsInfo> data = (ArrayList<NewsInfo>) resultMessage.getDatas();
                NewsListAdapter adapter=new NewsListAdapter(getActivity(),data);
                schoolinfoModdleInfolistLv.setAdapter(adapter);

                List lst = new ArrayList<Sentence>();
                if (data.size() > 3) {
                    for (int i = 0; i < 3; i++) {
                        Sentence sen = new Sentence(i, i + "、" + data.get(i).getNewsTitle());
                        lst.add(i, sen);
                    }
                }else{
                    for (int i = 0; i < data.size(); i++) {
                        Sentence sen = new Sentence(i, i + "、" + data.get(i).getNewsTitle());
                        lst.add(i, sen);

                    }
                }
                //给View传递数据
                Log.d("TAG", mNewListVerticalScrollTextView + "---" + lst);

                mNewListVerticalScrollTextView.setList(lst);
                //更新View
                mNewListVerticalScrollTextView.updateUI();*//*//**//*
            }
        });*//*


*//*
//创建okHttpClient对象
        OkHttpClient mOkHttpClient = new OkHttpClient();
//创建一个Request
        final Request request = new Request.Builder()
                .url(Constant.getNewsList__URL)
                .build();
//new call
        Call call = mOkHttpClient.newCall(request);
//请求加入调度
        call.enqueue(new Callback()
        {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("TAG","请求数据");
                Log.d("TAG",response.body().string());
                String res = response.body().string();
                Gson gson = new Gson();
                ResultMessage resultMessage = gson.fromJson(res, ResultMessage.class);
                ArrayList<NewsInfo> data = (ArrayList<NewsInfo>) resultMessage.getDatas();
                NewsListAdapter adapter=new NewsListAdapter(getActivity(),data);
                schoolinfoModdleInfolistLv.setAdapter(adapter);

                List lst = new ArrayList<Sentence>();
                if (data.size() > 3) {
                    for (int i = 0; i < 3; i++) {
                        Sentence sen = new Sentence(i, i + "、" + data.get(i).getNewsTitle());
                        lst.add(i, sen);
                    }
                }else{
                    for (int i = 0; i < data.size(); i++) {
                        Sentence sen = new Sentence(i, i + "、" + data.get(i).getNewsTitle());
                        lst.add(i, sen);

                    }
                }
                //给View传递数据
                Log.d("TAG", mNewListVerticalScrollTextView + "---" + lst);

                mNewListVerticalScrollTextView.setList(lst);
                //更新View
                mNewListVerticalScrollTextView.updateUI();*//*//*
*//*
            }


        });




       //






        //设置新闻列表
    }
*/
    /**
     * 设置ScrollVie滚动的监听时间
     */
    //判断是否滑动到底部，防止重复加载
    @Override
    public void onResume() {
        super.onResume();
        //重新设置flag
        flag = true;
    }

    boolean flag = true;

   /* private class TouchListenerImpl implements View.OnTouchListener {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:

                    break;
                case MotionEvent.ACTION_MOVE:
                    int scrollY = view.getScrollY();
                    int height = view.getHeight();
                    int scrollViewMeasuredHeight = mSchoolinfoScrollView.getChildAt(0).getMeasuredHeight();
                    if (scrollY == 0) {
                        System.out.println("滑动到了顶端 view.getScrollY()=" + scrollY);
                    }

                    if (flag) {
                        if ((scrollY + height) == scrollViewMeasuredHeight) {
                            flag = false;
                            System.out.println("滑动到了底部 scrollY=" + scrollY);
                            System.out.println("滑动到了底部 height=" + height);
                            System.out.println("滑动到了底部 scrollViewMeasuredHeight=" + scrollViewMeasuredHeight);
                            // startActivity(new Intent(getActivity(), SchoolinfoNewListActivity.class));

                        }
                    }
                    break;

                default:
                    break;
            }
            return false;
        }

    }*/

    ;
    private class webViewClient extends WebViewClient {
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}
