package com.wgc.mfaces.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


import com.wgc.mfaces.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * jyq
 * 临时签到列表
 */
public class DailyListFragment extends Fragment {

    @Bind(R.id.daily_list)
    ListView dailyList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_daily_list, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
