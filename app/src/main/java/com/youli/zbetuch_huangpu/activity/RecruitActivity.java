package com.youli.zbetuch_huangpu.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.youli.zbetuch_huangpu.R;
import com.youli.zbetuch_huangpu.adapter.CommonAdapter;
import com.youli.zbetuch_huangpu.entity.CommonViewHolder;
import com.youli.zbetuch_huangpu.entity.MyFollowInfo;
import com.youli.zbetuch_huangpu.entity.TubeInfo;
import com.youli.zbetuch_huangpu.utils.MyOkHttpUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Response;

public class RecruitActivity extends BaseActivity {
    private Context mContext=RecruitActivity.this;
    private PullToRefreshListView lv;
    private CommonAdapter adapter;  //apapter
    private List<TubeInfo> data=new ArrayList<>();
    private int PageIndex;

    private final int SUCCEED=10001;
    private final int SUCCEED_NODATA=10002;
    private final int SUCCEED_FOLLOW=10003;
    private final int SUCCEED_PINFO=10004;
    private final int  PROBLEM=10005;
    private final int OVERTIME=10006;//登录超时

    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            Intent i;

            switch (msg.what){
                case SUCCEED:
                    if(PageIndex==0) {
                        data.clear();
                    }
                    data.addAll((List<TubeInfo>)msg.obj);
                    LvSetAdapter(data);
                    break;

                case PROBLEM:
                    Toast.makeText(mContext,"网络不给力",Toast.LENGTH_SHORT).show();
                    if(lv.isRefreshing()) {
                        lv.onRefreshComplete();//停止刷新或加载更多
                    }
                    break;

                case SUCCEED_NODATA:
                    if(lv.isRefreshing()) {
                        lv.onRefreshComplete();//停止刷新或加载更多
                    }
                    break;


                case OVERTIME:
                    i=new Intent(mContext,OvertimeDialogActivity.class);
                    startActivity(i);
                    break;


            }

        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recruit);
        initView();
    }
    private void initView(){
        lv= (PullToRefreshListView) findViewById(R.id.lv_my_zpgl);
        lv.setMode(PullToRefreshBase.Mode.BOTH);
        lv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                //刷新
                PageIndex=0;
                initDatas(PageIndex);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                //加载更多
                PageIndex++;
                initDatas(PageIndex);
            }});

        initDatas(PageIndex);
    }


    //网络请求数据
    private void initDatas(final int pIndex){
        //http://192.168.11.205:8088/Json/JobFail/GetJobFails.aspx?page=0&rows=10
        new Thread(new Runnable() {
            @Override
            public void run() {
                String url= MyOkHttpUtils.BaseUrl+"/Json/JobFail/GetJobFails.aspx?page="+pIndex+"&rows=10";
                Response response=MyOkHttpUtils.okHttpGet(url);
                Log.e("2018-1-29",url);
                Message msg=Message.obtain();
                if(response!=null){
                    try {
                        String meetStr=response.body().string();
                        if (!TextUtils.equals(meetStr,"")&&!TextUtils.equals(meetStr,"[]")){
                            Gson gson=new Gson();
                              msg.obj=gson.fromJson(meetStr,new TypeToken<List<TubeInfo>>(){}.getType());
                            msg.what=SUCCEED;
                        }else {
                            msg.what=SUCCEED_NODATA;
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.e("2017/11/13","登录超时了");
                        msg.what=OVERTIME;
                    }
                }else{
                    msg.what=PROBLEM;
                }
                mHandler.sendMessage(msg);
                }
        }).start();
    }

    private void LvSetAdapter(final List<TubeInfo> list){

        if(adapter==null){
         adapter=new CommonAdapter<TubeInfo>(mContext,list,R.layout.zpgl_item) {

             @Override
             public void convert(CommonViewHolder holder, TubeInfo item, int position) {
                 TextView tv1=holder.getView(R.id.tv_item_zpgl1);
                 tv1.setText((position+1)+"");
                 Log.e("aaa",""+(position+1));
                 TextView tv2=holder.getView(R.id.tv_item_zpgl2);
                 tv1.setText(item.getJOBFAIRNAME());
                 TextView tv3=holder.getView(R.id.tv_item_zpgl3);
                 tv1.setText(item.getJOBFAIRDATA());
                 TextView tv4=holder.getView(R.id.tv_item_zpgl4);
                 tv1.setText(item.getJOBFAILADDRESS());

                 LinearLayout ll = holder.getView(R.id.item_my_follow_l11);
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
        if(lv.isRefreshing()) {
            lv.onRefreshComplete();//停止刷新或加载更多
        }
    }
}
