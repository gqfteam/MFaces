package com.wgc.mfaces.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;


import com.wgc.mfaces.R;
import com.wgc.mfaces.fragment.DailyListFragment;
import com.wgc.mfaces.fragment.TemporaryListFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by JYQ on 2016-09-22.
 * 签到列表
 */
public class SigninListActivity extends FragmentActivity {
    @Bind(R.id.signinLlist_top_back_textview)
    TextView tv_back;
    @Bind(R.id.signinLlist_top_edit_textview)
    TextView tv_edit;
    @Bind(R.id.signinList_top_selectList_radioGroup)
    RadioGroup rg_selectList;
    @Bind(R.id.signinList_top_temporary_radioButton)
    RadioButton rb_temporary;
    @Bind(R.id.signinList_top_daily_radioButton)
    RadioButton rb_daily;
    private FragmentTransaction ft;
    private DailyListFragment dailyListFragment;
    private TemporaryListFragment temporaryListFragment;
    private boolean isSignintRad=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signinlist);
        ButterKnife.bind(this);
        init();
    }

    public void init(){
       /*
       * 控制radio的选中和fragment的显示
       */
        if(isSignintRad){
            //消息
            rb_temporary.setChecked(true);
            rb_daily.setChecked(false);
            temporaryListFragment=new TemporaryListFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.signinlist, temporaryListFragment).commit();

        }else{
            //联系人
            rb_temporary.setChecked(false);
            rb_daily.setChecked(true);
            dailyListFragment=new DailyListFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.signinlist, dailyListFragment).commit();
        }
    }
    @OnClick({R.id.signinList_top_temporary_radioButton, R.id.signinList_top_daily_radioButton})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.signinList_top_temporary_radioButton:

                isSignintRad=true;
                if (temporaryListFragment != null) {
                    showFragment(temporaryListFragment);
                } else {
                    temporaryListFragment = new TemporaryListFragment();
                    getSupportFragmentManager().beginTransaction()
                            .add(R.id.signinlist, temporaryListFragment).commit();
                }
                break;
            case R.id.signinList_top_daily_radioButton:

                isSignintRad=false;
                if (dailyListFragment != null) {
                    showFragment(dailyListFragment);
                } else {
                    dailyListFragment = new DailyListFragment();
                    getSupportFragmentManager().beginTransaction()
                            .add(R.id.signinlist, dailyListFragment).commit();
                }
                break;
        }
    }
    private void showFragment(Fragment index) {
        ft = getSupportFragmentManager().beginTransaction();

        for (int i = 0; i < getSupportFragmentManager().getFragments().size(); i++) {
            Fragment f = getSupportFragmentManager().getFragments().get(i);
            if (f == index) {
                ft.show(f);
            } else {
                ft.hide(f);
            }

        }
        ft.commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
