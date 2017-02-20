package com.wgc.mfaces.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wgc.mfaces.R;
import com.wgc.mfaces.model.NewsInfo;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/2/4.
 * 新闻列表的adapter
 */

public class NewsListAdapter extends BaseAdapter {
    private ArrayList<NewsInfo> mListDatas;
    private LayoutInflater layoutInflater;
    private Context mContext;


    public NewsListAdapter(Context context, ArrayList<NewsInfo> listDatas) {
        this.mListDatas = listDatas;
        this.mContext = context;
        layoutInflater = LayoutInflater.from(context);

    }

    @Override
    public int getCount() {
        return mListDatas.size();
    }

    @Override
    public Object getItem(int i) {
        return mListDatas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if(view == null){
            view = layoutInflater.inflate(R.layout.newsinfo_list_adapter,null);
             viewHolder=new ViewHolder(view);
            view.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) view.getTag();
        }
        viewHolder.newsinfoNewsTitleTv.setText(mListDatas.get(i).getNewsTitle());
        viewHolder.newsinfoLeftNewsTimeTv.setText(mListDatas.get(i).getNewsTitle());
        return view;
    }


    static class ViewHolder {
        //标题
        @Bind(R.id.newsinfo_news_title_tv)
        TextView newsinfoNewsTitleTv;
        //图片1
        @Bind(R.id.newsinfo_left_news_iv)
        ImageView newsinfoLeftNewsIv;
        //图片2
        @Bind(R.id.newsinfo_moddle_news_iv)
        ImageView newsinfoModdleNewsIv;
        @Bind(R.id.newsinfo_right_news_iv)
        //图片3
        ImageView newsinfoRightNewsIv;
        @Bind(R.id.newsinfo_left_news_time_tv)
        TextView newsinfoLeftNewsTimeTv;
        //新闻时间
        @Bind(R.id.newsinfo_right_news_time_tv)
        TextView newsinfoRightNewsTimeTv;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
