package com.youli.zbetuch_huangpu.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.RelativeLayout;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.youli.zbetuch_huangpu.R;
import com.youli.zbetuch_huangpu.entity.LdjlInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liutao on 2018/1/5.
 *
 * 劳动经历
 */

public class LdjlFragment extends BaseVpFragment{

    private View view;

     private List<LdjlInfo> data=new ArrayList<>();




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view=LayoutInflater.from(getContext()).inflate(R.layout.fragment_ldjl,container,false);

        isViewCreated=true;//标记不能少

        return view;
    }

    @Override
    public void lazyLoadData() {

        if(isViewCreated){//逻辑都写这个里面
            initViews();
        }

    }

    private void initViews() {


        //初始化listview
       // lv = (PullToRefreshListView) view.findViewById(R.id.lv_fragment_ldjl);

        for(int i=1;i<50;i++){

            data.add(new LdjlInfo("工作单位名称"+i,"单位性质"+i,"2018-01-01","2018-01-21","就业类型"+i,"用工形式"+i,"退工原因"+i,
                    "备注"+i,"就业登记日期"+i,"退工登记日期"+i,"就业登记所在地"+i,"退工登记所在地"+i));

        }



    }



}
