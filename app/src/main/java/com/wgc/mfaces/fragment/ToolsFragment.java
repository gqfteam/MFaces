package com.wgc.mfaces.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.wgc.mfaces.R;
import com.wgc.mfaces.activity.InitiateSigninActivity;
import com.wgc.mfaces.activity.SignInActivity;
import com.wgc.mfaces.activity.SigninListActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ToolsFragment extends Fragment implements View.OnClickListener {
    View toolsView;
    RelativeLayout initialSignin_Relative, inquiry_Signin_Relative;
    @Bind(R.id.toolsf_middle_inquiry_goods_relative)
    RelativeLayout toolsfMiddleInquiryGoodsRelative;
    @Bind(R.id.singIn_relative)
    RelativeLayout singInRelative;
    @Bind(R.id.toolsf_middle_initialsignin_relative)
    RelativeLayout toolsfMiddleInitialsigninRelative;
    @Bind(R.id.toolsf_middle_inquiry_signin_relative)
    RelativeLayout toolsfMiddleInquirySigninRelative;
    private Intent mIntent;

    @OnClick({R.id.singIn_relative, R.id.toolsf_middle_initialsignin_relative, R.id.toolsf_middle_inquiry_signin_relative})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.singIn_relative:
                //签到
                Log.e("Daniel", "---singIn_relative-----");
                startActivity(new Intent(getActivity(), SignInActivity.class));
                break;
            case R.id.toolsf_middle_initialsignin_relative:
                //发起签到
                startActivity(new Intent(getActivity(), InitiateSigninActivity.class));
                break;
            case R.id.toolsf_middle_inquiry_signin_relative:
                //查找签到列表
                startActivity(new Intent(getActivity(), SigninListActivity.class));
                break;
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        toolsView = inflater.inflate(R.layout.fragment_tools, container, false);
        ButterKnife.bind(this, toolsView);
        return toolsView;
    }





    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


}
