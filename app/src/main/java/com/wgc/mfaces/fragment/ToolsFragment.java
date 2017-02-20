package com.wgc.mfaces.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;


import com.wgc.mfaces.R;
import com.wgc.mfaces.activity.InitiateSigninActivity;
import com.wgc.mfaces.activity.SigninListActivity;


import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ToolsFragment extends Fragment implements View.OnClickListener {
    View toolsView;
    RelativeLayout initialSignin_Relative, inquiry_Signin_Relative;
    @Bind(R.id.toolsf_middle_inquiry_goods_relative)
    RelativeLayout toolsfMiddleInquiryGoodsRelative;
    private Intent mIntent;

    public interface mListener {
        public void changeActivity(
                @SuppressWarnings("rawtypes") Class activityClass);
    }

  //  private SaleFragemnt.mListener mListener;

    public void onAttach(Activity activity) {
        super.onAttach(activity);
   //     mListener = (SaleFragemnt.mListener) activity;

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        toolsView = inflater.inflate(R.layout.fragment_tools, container, false);
        init();
        addListener();
        ButterKnife.bind(this, toolsView);
        return toolsView;
    }

    /**
     * 初始化组件
     */
    private void init() {
        initialSignin_Relative = (RelativeLayout) toolsView.findViewById(R.id.toolsf_middle_initialsignin_relative);
        inquiry_Signin_Relative = (RelativeLayout) toolsView.findViewById(R.id.toolsf_middle_inquiry_signin_relative);
    }

    /**
     * 给组件添加事件
     */
    public void addListener() {
        initialSignin_Relative.setOnClickListener(this);
        inquiry_Signin_Relative.setOnClickListener(this);
    }


    /**
     * 组件的点击事件
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toolsf_middle_initialsignin_relative:
                //发起签到
                startActivity(new Intent(getActivity(),InitiateSigninActivity.class));
               // mListener.changeActivity(InitiateSigninActivity.class);
                break;
            case R.id.toolsf_middle_inquiry_signin_relative:
                //查找签到列表
                startActivity(new Intent(getActivity(),SigninListActivity.class));
              //  mListener.changeActivity(SigninListActivity.class);
                break;

        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.toolsf_middle_inquiry_goods_relative)
    public void onClick() {
       //我的义卖
        // mListener.changeActivity(UserSaleListActivity.class);

    }
}
