package com.wgc.mfaces.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;


import com.wgc.mfaces.R;
import com.wgc.mfaces.adapter.SigninListAdapter;
import com.wgc.mfaces.model.SigninInfo;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * jyq
 * 临时签到列表
 */
public class TemporaryListFragment extends Fragment {

    @Bind(R.id.temporary_list)
    ListView temporaryList;
    SigninListAdapter adapter;
    SigninInfo signinInfo;
    ArrayList<SigninInfo> datas;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_temporary_list, container, false);
        ButterKnife.bind(this, view);
        init();
        temporaryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("-----list-----"+position);
            }
        });
        return view;
    }

    public void init() {
        signinInfo=new SigninInfo();
//        signinInfo.setName("rose");
//        signinInfo.setTime("15:24");
//        signinInfo.setPlace("河南省洛阳市河南科技大学西苑校区10号楼512");
//        signinInfo.setPurpose("上课");
        datas=new ArrayList<SigninInfo>();
        datas.add(signinInfo);
        datas.add(signinInfo);
        datas.add(signinInfo);
        datas.add(signinInfo);
        datas.add(signinInfo);
        datas.add(signinInfo);
        adapter=new SigninListAdapter(getActivity(),datas);
        temporaryList.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

}
