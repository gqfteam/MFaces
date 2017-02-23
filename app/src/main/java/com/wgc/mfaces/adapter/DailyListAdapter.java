package com.wgc.mfaces.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wgc.mfaces.R;

/**
 * Created by lyt on 2016/9/22.
 */
public class DailyListAdapter extends BaseAdapter {
    Context context;
//    ArrayList<HashMap<String,String>> class_list;

    public DailyListAdapter(Context context){
        this.context=context;
//        this.class_list=class_list;
    }


    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        GridHolder holder;
        if(convertView==null){
            holder = new GridHolder();
            convertView= LayoutInflater.from(context).inflate(R.layout.daily_list_item,null);
            holder.course_tv = (TextView)convertView.findViewById(R.id.course_tv);
            holder.signInPerson_tv = (TextView)convertView.findViewById(R.id.signInPerson_tv);
            holder.signCount_tv = (TextView)convertView.findViewById(R.id.signCount_tv);
            convertView.setTag(holder);
        }
        else {
            holder = (GridHolder) convertView.getTag();
        }
        holder.course_tv.setText("高数");
        holder.signInPerson_tv.setText("发起签到人:方大爷");
        holder.signCount_tv.setText("签到次数:0/3");


        return convertView;
    }

    class GridHolder {
        TextView course_tv;
        TextView signInPerson_tv;
        TextView signCount_tv;



    }

}
