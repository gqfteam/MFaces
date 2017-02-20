package com.wgc.mfaces.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.wgc.mfaces.R;
import com.wgc.mfaces.model.SigninInfo;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by jyq on 2016/9/21.
 */
public class SigninListAdapter extends BaseAdapter {
    private ArrayList<SigninInfo> datas;//数据源
    private Context mContext;
    private LayoutInflater layoutInflater;



    public void update(ArrayList<SigninInfo> datas) {
        this.datas = datas;
        this.notifyDataSetChanged();
    }

    public SigninListAdapter(Context context, ArrayList<SigninInfo> datas) {
        this.mContext = context;
        this.datas = datas;
        this.layoutInflater = LayoutInflater.from(context);

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
                System.out.println("----button----");
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


}
