package com.youli.zbetuch_huangpu.activity;

import android.content.Context;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.youli.zbetuch_huangpu.R;
import com.youli.zbetuch_huangpu.adapter.CommonAdapter;
import com.youli.zbetuch_huangpu.entity.CommonViewHolder;
import com.youli.zbetuch_huangpu.entity.InspectorInfo;
import com.youli.zbetuch_huangpu.entity.MyFollowInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者: zhengbin on 2017/12/5.
 * <p>
 * 邮箱:2381447237@qq.com
 * <p>
 * github:2381447237
 *
 * 我的关注
 */

public class MyFollowActivity extends BaseActivity{

    private Context mContext=this;

    private PullToRefreshListView lv;
    private List<MyFollowInfo> data=new ArrayList<>();
    private CommonAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_follow);

        initViews();
    }

    private void initViews(){

        lv= (PullToRefreshListView) findViewById(R.id.lv_my_follow);
        lv.setMode(PullToRefreshBase.Mode.BOTH);

        for(int i=1;i<50;i++){

            data.add(new MyFollowInfo("张"+i+"丰","123456789012345678"));

        }

        LvSetAdapter(data);

    }

    //http://web.youli.pw:8088/Json/First/Get_Attention.aspx?page=0&rows=20
    private void LvSetAdapter(List<MyFollowInfo> list){


        if(adapter==null){

            adapter=new CommonAdapter<MyFollowInfo>(mContext,list,R.layout.item_my_follow_lv) {
                @Override
                public void convert(CommonViewHolder holder, MyFollowInfo item, int position) {

                    TextView noTv=holder.getView(R.id.item_my_follow_number_tv);//编号
                    noTv.setText((position+1)+"");
                    TextView nameTv=holder.getView(R.id.item_my_follow_name_tv);//姓名
                    nameTv.setText(item.getName());
                    TextView sfzTv=holder.getView(R.id.item_my_follow_sfz_tv);//身份证
                    sfzTv.setText(item.getSfz());


                    LinearLayout ll = holder.getView(R.id.item_my_follow_ll);
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
