package com.youli.zbetuch_huangpu.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.HorizontalScrollView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.youli.zbetuch_huangpu.R;
import com.youli.zbetuch_huangpu.adapter.CommonAdapter;
import com.youli.zbetuch_huangpu.adapter.LdjlAdapter;
import com.youli.zbetuch_huangpu.view.ObserverHScrollView;

/**
 * Created by liutao on 2018/1/5.
 *
 * 劳动经历
 */

public class LdjlFragment extends BaseVpFragment{

    private View view;

    private LdjlAdapter adapter;

    /**
     * 列表表头容器
     **/
    private RelativeLayout mListviewHead;
    /**
     * 列表ListView
     **/
    private ListView lv;

    /**
     * 列表ListView水平滚动条
     **/
    private HorizontalScrollView mHorizontalScrollView;

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
        //初始化列表表头
        mListviewHead = (RelativeLayout) view.findViewById(R.id.head);
        //下面这个两个属性必须同时设置，不然点击头部是不能滑动的
        mListviewHead.setFocusable(true);//将控件设置成可获取焦点状态,默认是无法获取焦点的,只有设置成true,才能获取控件的点击事件
        mListviewHead.setClickable(true);//设置为true时，表明控件可以点击
        mListviewHead.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.table_header));
        mListviewHead.setOnTouchListener(mHeadListTouchLinstener);//头部设置触摸事件同时把触摸事件传递给水平滑动控件
        mHorizontalScrollView = (HorizontalScrollView) mListviewHead.findViewById(R.id.horizontalScrollView1);

        //初始化listview
        lv = (ListView) view.findViewById(R.id.lv_fragment_ldjl);
        adapter = new LdjlAdapter(getActivity(),mListviewHead);
        lv.setOnTouchListener(mHeadListTouchLinstener);
        lv.setAdapter(adapter);

    }

    /**
     * 列头/Listview触摸事件监听器<br>
     * 当在列头 和 listView控件上touch时，将这个touch的事件分发给 ScrollView
     */
    private View.OnTouchListener mHeadListTouchLinstener = new View.OnTouchListener() {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            mHorizontalScrollView.onTouchEvent(event);
            return false;
        }
    };

}
