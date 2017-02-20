package com.wgc.mfaces.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.wgc.mfaces.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by lyt on 2016/9/22.
 */
public class InitialSigninShowClassAdapter extends BaseAdapter {
    Context context;
    ArrayList<HashMap<String,String>> class_list;

    public InitialSigninShowClassAdapter(Context context, ArrayList<HashMap<String,String>> class_list){
        this.context=context;
        this.class_list=class_list;
    }


    @Override
    public int getCount() {
        return class_list.size();
    }

    @Override
    public Object getItem(int position) {
        return class_list.get(position);
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
            convertView= LayoutInflater.from(context).inflate(R.layout.initialsignin_showclass_list,null);
            holder.check= (CheckBox) convertView.findViewById(R.id.initialsignin_showclass_check);
            holder.classname= (TextView) convertView.findViewById(R.id.initialsignin_showclass_classname);
            holder.studentnum= (TextView) convertView.findViewById(R.id.initialsignin_showclass_studentnum);
            holder.substance_linearLayout= (LinearLayout) convertView.findViewById(R.id.initialsignin_showclass_substance_linearLayout);
            holder.title_linearLayout= (LinearLayout) convertView.findViewById(R.id.initialsignin_showclass_title_linearLayout);
            holder.titlecheckornot= (TextView) convertView.findViewById(R.id.initialsignin_showclass_titlecheckornot);
            holder.titlesclassname= (TextView) convertView.findViewById(R.id.initialsignin_showclass_titlesclassname);
            holder.titlestudentnum= (TextView) convertView.findViewById(R.id.initialsignin_showclass_titlestudentnum);
            holder.xialapic= (ImageView) convertView.findViewById(R.id.initialsignin_showclass_xialapic);
            convertView.setTag(holder);
        }
        else {
            holder = (GridHolder) convertView.getTag();
        }
        if (position!=0){
            holder.title_linearLayout.setVisibility(View.GONE);
        }

        holder.classname.setText(class_list.get(position).get("年级"));
        holder.studentnum.setText(class_list.get(position).get("人数"));
        return convertView;
    }

    class GridHolder {
        LinearLayout title_linearLayout,substance_linearLayout;
        TextView titlesclassname,titlestudentnum,titlecheckornot,classname,studentnum;
        ImageView xialapic;
        CheckBox check;


    }

}
