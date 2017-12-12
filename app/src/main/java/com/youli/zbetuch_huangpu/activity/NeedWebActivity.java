package com.youli.zbetuch_huangpu.activity;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.youli.zbetuch_huangpu.R;
import com.youli.zbetuch_huangpu.adapter.CommonAdapter;
import com.youli.zbetuch_huangpu.entity.AppendixInfo;
import com.youli.zbetuch_huangpu.entity.CommonViewHolder;
import com.youli.zbetuch_huangpu.entity.StayInfo;
import com.youli.zbetuch_huangpu.utils.AlertDialogUtils;
import com.youli.zbetuch_huangpu.utils.MyDateUtils;
import com.youli.zbetuch_huangpu.utils.MyOkHttpUtils;
import com.youli.zbetuch_huangpu.utils.SharedPreferencesUtils;
import com.youli.zbetuch_huangpu.view.MyListView;

import java.util.ArrayList;
import java.util.List;

public class NeedWebActivity extends BaseActivity implements View.OnClickListener{

    private Context mContext=this;

    private WebView myWeb;//工作内容

    private StayInfo sInfo;

    private TextView tvName;//工作名称
    private TextView tvFounder;//创建人
    private TextView tvFinishTime;//完成时间
    private TextView tvCreatTime;//创建时间

    private Button btnFinish,btnNoFinish;

    private List<AppendixInfo> appendixData=new ArrayList<>();//附件的数据
    private MyListView appendixLv;
    private CommonAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_need_web);

        sInfo= (StayInfo) getIntent().getSerializableExtra("StayInfo");

        initView();
    }
    private void initView(){


        tvName= (TextView) findViewById(R.id.tv_need_web_name);
        tvFounder= (TextView) findViewById(R.id.tv_need_web_founder);
        tvFinishTime= (TextView) findViewById(R.id.tv_need_web_finish_time);
        tvCreatTime= (TextView) findViewById(R.id.tv_need_web_create_time);

        tvName.setText(sInfo.getTitle());
        if(HomePageActivity.adminInfo.getID()==sInfo.getCreate_staff()){
            tvFounder.setText(HomePageActivity.adminName);
        }
        tvFinishTime.setText(MyDateUtils.stringToYMDHMS(sInfo.getNotice_time()));
        tvCreatTime.setText(MyDateUtils.stringToYMDHMS(sInfo.getCreate_date()));


        appendixLv= (MyListView) findViewById(R.id.lv_need_web_appendix);
        appendixLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(mContext,"下载"+appendixData.get(position),Toast.LENGTH_SHORT).show();

            }
        });

        for(int i=1;i<6;i++){

            appendixData.add(new AppendixInfo("附件"+i));

        }

        LvSetAdapter(appendixData);
        btnFinish= (Button) findViewById(R.id.btn_need_web_finish);
        btnFinish.setOnClickListener(this);
        btnNoFinish= (Button) findViewById(R.id.btn_need_web_no_finish);
        btnNoFinish.setOnClickListener(this);

        myWeb= (WebView) findViewById(R.id.wv_need_web);
        int intent=getIntent().getIntExtra("RDInfoo",0);
        String url= MyOkHttpUtils.BaseUrl+"/Web/Manage/Frm_Work_Info_ForWorkId.aspx?WorkId="+intent;
        myWeb.setWebViewClient(new WebViewClient());
        WebSettings settings = myWeb.getSettings();//自适应屏幕
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setJavaScriptEnabled(true);//自适应Js
        myWeb.getSettings().setTextSize(WebSettings.TextSize.LARGEST);//设置字体大小
        myWeb.setBackgroundColor(Color.parseColor("#AFCEEC")); // 设置背景色
        myWeb.getBackground().setAlpha(255); // 设置填充透明度 范围：0-255
        myWeb.loadDataWithBaseURL(null,sInfo.getDoc(),"text/html", "UTF-8", null);

    }



    private void LvSetAdapter(List<AppendixInfo> list){

        if(adapter==null){

            adapter=new CommonAdapter<AppendixInfo>(mContext,list,R.layout.item_meet_detail_appendix) {
                @Override
                public void convert(CommonViewHolder holder, AppendixInfo item, int position) {

                    TextView name=holder.getView(R.id.item_meet_detail_appendix_name);
                    name.setText(item.getFILE_NAME());
                    name.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);//下划线

                }
            };

            appendixLv.setAdapter(adapter);

        }else{


            adapter.notifyDataSetChanged();

        }

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.btn_need_web_finish://已完成

                Toast.makeText(mContext,"已完成",Toast.LENGTH_SHORT).show();

                break;


            case R.id.btn_need_web_no_finish://无法完成

               showNoFinishDialog();//无法完成的对话框

                break;
        }

    }

    private void showNoFinishDialog(){



        final AlertDialogUtils noFinishDialog=new AlertDialogUtils(mContext,R.layout.dialog_show_no_finish);

        noFinishDialog.showAlertDialog();

       final EditText etMark= (EditText) noFinishDialog.getAduView().findViewById(R.id.dialog_no_finish_et);



        Button sureBtn= (Button) noFinishDialog.getAduView().findViewById(R.id.dialog_no_finish_ensure_btn);
        sureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String   markStr=etMark.getText().toString().trim();

                Toast.makeText(mContext,"=="+markStr,Toast.LENGTH_SHORT).show();

                noFinishDialog.dismissAlertDialog();
            }
        });

        Button cancelBtn= (Button) noFinishDialog.getAduView().findViewById(R.id.dialog_no_finish_cancel_btn);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                noFinishDialog.dismissAlertDialog();
            }
        });
    }

}
