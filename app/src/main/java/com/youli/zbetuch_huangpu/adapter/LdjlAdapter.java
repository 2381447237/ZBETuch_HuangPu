package com.youli.zbetuch_huangpu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.HorizontalScrollView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.youli.zbetuch_huangpu.R;
import com.youli.zbetuch_huangpu.view.ObserverHScrollView;

/**
 * Created by liutao on 2018/1/8.
 *
 * 个人信息里面的劳动经历
 */

public class LdjlAdapter extends BaseAdapter{

    private RelativeLayout mListviewHead;
    private Context mContext;

    public LdjlAdapter( Context mContext,RelativeLayout mListviewHead) {
        this.mListviewHead = mListviewHead;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return 50;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder=null;

        if(convertView==null){

            convertView= LayoutInflater.from(mContext).inflate(R.layout.item_fragment_ldjl_hvscorll_listview,null);
            holder=new ViewHolder();

            holder.txt1 = (TextView) convertView.findViewById(R.id.tv1);
            holder.txt2= (TextView) convertView.findViewById(R.id.tv2);
            holder.txt3 = (TextView) convertView.findViewById(R.id.tv3);
            holder.txt4 = (TextView) convertView.findViewById(R.id.tv4);
            holder.txt5 = (TextView) convertView.findViewById(R.id.tv5);
            holder.txt6 = (TextView) convertView.findViewById(R.id.tv6);
            holder.txt7 = (TextView) convertView.findViewById(R.id.tv7);
            holder.txt8 = (TextView) convertView.findViewById(R.id.tv8);
            holder.txt9 = (TextView) convertView.findViewById(R.id.tv9);
            holder.txt10 = (TextView) convertView.findViewById(R.id.tv10);
            holder.txt11 = (TextView) convertView.findViewById(R.id.tv11);
            holder.txt12 = (TextView) convertView.findViewById(R.id.tv12);
            holder.txt13 = (TextView) convertView.findViewById(R.id.tv13);


            //列表水平滚动条
            ObserverHScrollView sv= (ObserverHScrollView) convertView.findViewById(R.id.horizontalScrollView1);
            holder.scrollView = (ObserverHScrollView) convertView.findViewById(R.id.horizontalScrollView1);

            //列表表头滚动条
            ObserverHScrollView headSv= (ObserverHScrollView) mListviewHead.findViewById(R.id.horizontalScrollView1);
            headSv.AddOnScrollChangedListener(new OnScrollChangedListenerImp(sv));

            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }

        holder.txt1.setText((position+1)+"");
        holder.txt2.setText("--");
        holder.txt3.setText("000000");
        holder.txt4.setText("财政部");
        holder.txt5.setText("2016-11-15");
        holder.txt6.setText("100.00");
        holder.txt7.setText("200.00");
        holder.txt8.setText("附息式固定利率");
        holder.txt9.setText("12");
        holder.txt10.setText("2.4200");
        holder.txt11.setText("--");
        holder.txt12.setText("--");
        holder.txt13.setText("--");


        return convertView;
    }


    /**
     * 实现接口，获得滑动回调
     */

    private class OnScrollChangedListenerImp implements ObserverHScrollView.OnScrollChangedListener{

        ObserverHScrollView mScrollViewArg;

        public OnScrollChangedListenerImp(ObserverHScrollView mScrollViewArg) {
            this.mScrollViewArg = mScrollViewArg;
        }

        @Override
        public void onScrollChanged(int l, int t, int oldl, int oldt) {
            mScrollViewArg.smoothScrollTo(l,t);
        }
    }

    private class ViewHolder {

        TextView txt1;
        TextView txt2;
        TextView txt3;
        TextView txt4;
        TextView txt5;
        TextView txt6;
        TextView txt7;
        TextView txt8;
        TextView txt9;
        TextView txt10;
        TextView txt11;
        TextView txt12;
        TextView txt13;

        HorizontalScrollView scrollView;
    }

}
