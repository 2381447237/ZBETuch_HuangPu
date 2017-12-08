package com.youli.zbetuch_huangpu.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.youli.zbetuch_huangpu.R;
import com.youli.zbetuch_huangpu.adapter.CommonAdapter;
import com.youli.zbetuch_huangpu.entity.CommonViewHolder;
import com.youli.zbetuch_huangpu.entity.InspectorInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者: zhengbin on 2017/12/4.
 * <p>
 * 邮箱:2381447237@qq.com
 * <p>
 * github:2381447237
 *
 * 督察督办详情界面
 */

public class InspectorDetailActivity extends BaseActivity{

    private Context mContext=this;

    private PullToRefreshListView lv;
    private List<InspectorInfo> data=new ArrayList<>();
    private CommonAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspector_detail);
        initViews();

    }

    private void initViews(){


        lv= (PullToRefreshListView) findViewById(R.id.lv_inspector_detail);
        lv.setMode(PullToRefreshBase.Mode.BOTH);


        for(int i=1;i<5;i++){

            //data.add(new InspectorInfo("备注"+i,"未完成","张三"+i,""+i,"2017-12-04"));

        }

        LvSetAdapter(data);


    }


    private void LvSetAdapter(List<InspectorInfo> list){


        if(adapter==null){

            adapter=new CommonAdapter<InspectorInfo>(mContext,list,R.layout.item_inspector_lv) {
                @Override
                public void convert(CommonViewHolder holder, InspectorInfo item, int position) {

                    TextView noTv=holder.getView(R.id.item_inspector_number_tv);//编号
                    noTv.setText((position+1)+"");
                    TextView nameTv=holder.getView(R.id.item_inspector_name_tv);//工作名称
                    nameTv.setText(item.getTitle());
                    TextView createTimeTv=holder.getView(R.id.item_inspector_create_time_tv);//创建时间
                    createTimeTv.setText(item.getCreate_date());
                    TextView comTimeTv=holder.getView(R.id.item_inspector_com_time_tv);//完成时间
                    comTimeTv.setText(item.getNotice_time());
                    TextView stateTv=holder.getView(R.id.item_inspector_state_tv);//完成状态
                    stateTv.setText(item.getType());

                    LinearLayout ll = holder.getView(R.id.item_inspector_ll);
                    if (position % 2 == 0){
                        ll.setBackgroundResource(R.drawable.selector_questionnaire_click_blue);
                    }else {
                        ll.setBackgroundResource(R.drawable.selector_questionnaire_click_white);
                    }

                }
            };

            lv.setAdapter(adapter);

        }else{

            adapter.notifyDataSetChanged();

        }


    }
}
