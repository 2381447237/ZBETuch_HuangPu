package com.youli.zbetuch_huangpu.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.youli.zbetuch_huangpu.R;
import com.youli.zbetuch_huangpu.entity.AdminInfo;
import com.youli.zbetuch_huangpu.utils.MyOkHttpUtils;
import com.youli.zbetuch_huangpu.view.CircleImageView;

import java.io.IOException;

import okhttp3.Response;


//首页
public class HomePageActivity extends BaseActivity implements View.OnClickListener{


    private Context mContext=this;

    private final int SUCCESS_ADMIN_INFO=10001;
    private final int PROBLEM=10002;

    private AdminInfo adminInfo;


    private Intent intent;
    private CircleImageView ivHead;//头像
    private Button workBtn;

    private Handler mHandler=new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what){

                case SUCCESS_ADMIN_INFO:

                    adminInfo=(AdminInfo)msg.obj;

                    break;

                case PROBLEM:
                    Toast.makeText(mContext, "网络不给力", Toast.LENGTH_SHORT).show();
                    break;
            }

        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        initViews();


    }


    private void initViews(){

        ivHead= (CircleImageView) findViewById(R.id.iv_activity_homepage_head);
        ivHead.setOnClickListener(this);
        workBtn = (Button) findViewById(R.id.main_layout_work_btn);
        workBtn.setOnClickListener(this);

        getAdminInfo();
    }

    //获取管理员的信息
    //http://web.youli.pw:8088/Json/Get_Staff.aspx
    private void getAdminInfo(){

        new Thread(

                new Runnable() {
                    @Override
                    public void run() {

                        String url= MyOkHttpUtils.BaseUrl+"/Json/Get_Staff.aspx";

                        Response response=MyOkHttpUtils.okHttpGet(url);

                        Message msg=Message.obtain();

                        if(response!=null){

                            if(response.body()!=null){

                                try {
                                    String resStr=response.body().string();

                                    Log.e("2017/11/8",resStr);

                                    if(!TextUtils.equals(resStr,"")){

                                        Gson gson=new Gson();

                                        msg.obj=gson.fromJson(resStr,AdminInfo.class);

                                        msg.what=SUCCESS_ADMIN_INFO;

                                    }

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            }else{
                                msg.what=PROBLEM;
                            }

                        }else{

                            msg.what=PROBLEM;

                        }

                        mHandler.sendMessage(msg);

                    }
                }

        ).start();

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.iv_activity_homepage_head:
                if(adminInfo!=null) {
                    intent = new Intent(this, AdminInfoActivity.class);
                    intent.putExtra("ADMININFO", adminInfo);
                    startActivity(intent);
                }
                break;

            //九宫格的Button按钮点击
            case R.id.main_layout_work_btn:
                intent=new Intent(this,FunctionListActivity.class);
                startActivity(intent);

                break;

        }

    }


}
