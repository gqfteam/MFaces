package com.wgc.mfaces.activity;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.wgc.mfaces.fragment.FristShowFragment;
import com.wgc.mfaces.R;
import com.wgc.mfaces.fragment.ToolsFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends FragmentActivity {

    FragmentManager fragmentManager = getSupportFragmentManager();

    FragmentTransaction fragmentTransaction;
    //首页面
    FristShowFragment fristShowActivity;
    //签到
    ToolsFragment toolsFragment;
    //主菜单的
    @Bind(R.id.main_title_rb_menu)
    RadioButton menuRb;
    FrameLayout mfrmeLayout;
    RadioGroup radioGroup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        radioGroup= (RadioGroup) findViewById(R.id.main_bottom_rg);
       // mfrmeLayout= (FrameLayout) findViewById(R.id.main_title_myframe);
        //manager=getSupportFragmentManager();
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(RadioGroup arg0, int arg1) {
                // TODO Auto-generated method stub

                switch (arg1) {
                    case R.id.main_title_rb_menu:
                        initDataMenu();
                       Log.d("TAG","aaaaaaa2");
                        /* //从fragmentmanager里面查询对应的frament，如果找不到新创建一个
                        fristShowActivity=(FristShowFragment) mAdapter.instantiateItem(mfrmeLayout, 0);
                        //设置frmeLayout
                        mAdapter.setPrimaryItem(mfrmeLayout, 0, fristShowActivity);
                        //提交更新
                        mAdapter.finishUpdate(mfrmeLayout);*/
                       /* if(merchantframent!=null){
                            merchantframent.closePopuWindow();
                        }*/

                        break;


                    case R.id.main_title_rb_write:
                            initDataSign();
                        break;

                    default:

                        break;
                }


        };
    });
        //设置默认第一个被选中
        menuRb.setChecked(true);
    }



    public void initDataMenu() {

        fragmentTransaction = fragmentManager.beginTransaction();
        if (fristShowActivity == null) {
            fristShowActivity = new FristShowFragment();
        }

        fragmentTransaction.add
                (R.id.main_title_myframe, fristShowActivity);

        fragmentTransaction.commit();
    }
    public void initDataSign() {

        fragmentTransaction = fragmentManager.beginTransaction();
        if (toolsFragment == null) {
            toolsFragment = new ToolsFragment();
        }

        fragmentTransaction.add
                (R.id.main_title_myframe, toolsFragment);

        fragmentTransaction.commit();
    }
}