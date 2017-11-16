package com.youli.zbetuch_huangpu.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.google.gson.Gson;
import com.youli.zbetuch_huangpu.R;
import com.youli.zbetuch_huangpu.entity.AdminInfo;
import com.youli.zbetuch_huangpu.utils.MyOkHttpUtils;
import com.youli.zbetuch_huangpu.view.CircleImageView;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.Response;


//首页
public class HomePageActivity extends CheckPermissionsActivity implements View.OnClickListener{
//IMBI

    private Context mContext=this;

    private final int SUCCESS_ADMIN_INFO=10001;
    private final int PROBLEM=10002;
    private final int OVERTIME=10003;//登录超时
    private AdminInfo adminInfo;


    private Intent intent;
    private CircleImageView ivHead;//头像
    public static String adminName;//调查人姓名
    private Button workBtn;

    private TextView tvJdu,tvWdu,tvGdu;//经度，纬度，高度

    //声明AMapLocationClient类对象
    private AMapLocationClient mLocationClient;
    //声明AMapLocationClientOption对象
    private AMapLocationClientOption mLocationOption;
    private Handler mHandler=new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what){

                case SUCCESS_ADMIN_INFO:

                    adminInfo=(AdminInfo)msg.obj;
                    adminName=adminInfo.getNAME();
                    break;

                case PROBLEM:
                    Toast.makeText(mContext, "网络不给力", Toast.LENGTH_SHORT).show();
                    break;
                case OVERTIME:

                    Intent i=new Intent(mContext,OvertimeDialogActivity.class);
                    startActivity(i);

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

        tvJdu= (TextView) findViewById(R.id.main_layout_tv_jdu);
        tvWdu= (TextView) findViewById(R.id.main_layout_tv_wdu);
        tvGdu= (TextView) findViewById(R.id.main_layout_tv_gdu);
        getAdminInfo();

        getGpsInfo();//定位
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

                                        try{
                                            msg.obj=gson.fromJson(resStr,AdminInfo.class);

                                            msg.what=SUCCESS_ADMIN_INFO;
                                        }catch(Exception e){
                                            Log.e("2017/11/13","登录超时了");
                                            msg.what=OVERTIME;

                                        }



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


    private void getGpsInfo(){

        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {

                if(aMapLocation!=null){

                    if(aMapLocation.getErrorCode()==0){
                        //可在其中解析amapLocation获取相应内容。

                        aMapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                        aMapLocation.getLatitude();//获取纬度
                        aMapLocation.getLongitude();//获取经度
                        aMapLocation.getAccuracy();//获取精度信息
                        aMapLocation.getAddress();//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
                        aMapLocation.getCountry();//国家信息
                        aMapLocation.getProvince();//省信息
                        aMapLocation.getCity();//城市信息
                        aMapLocation.getDistrict();//城区信息
                        aMapLocation.getStreet();//街道信息
                        aMapLocation.getStreetNum();//街道门牌号信息
                        aMapLocation.getCityCode();//城市编码
                        aMapLocation.getAdCode();//地区编码
                        aMapLocation.getAoiName();//获取当前定位点的AOI信息
                        aMapLocation.getBuildingId();//获取当前室内定位的建筑物Id
                        aMapLocation.getFloor();//获取当前室内定位的楼层
                        aMapLocation.getGpsAccuracyStatus();//获取GPS的当前状态
                        //获取定位时间
                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Date date = new Date(aMapLocation.getTime());
                        df.format(date);
                        tvJdu.setText("经度:"+(int)aMapLocation.getLongitude());
                        tvWdu.setText("纬度:"+(int)aMapLocation.getLatitude());
                        tvGdu.setText("高度:"+aMapLocation.getAltitude()+"米");
                    }else{
                        //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                        Log.e("AmapError","location Error, ErrCode:"
                                + aMapLocation.getErrorCode() + ", errInfo:"
                                + aMapLocation.getErrorInfo());
                    }
                }

            }
        });

        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);

//        //设置定位模式为AMapLocationMode.Battery_Saving，低功耗模式。
//        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Battery_Saving);

        //设置定位间隔,单位毫秒,默认为2000ms，最低1000ms。
        mLocationOption.setInterval(1000);

        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);

        //设置是否允许模拟位置,默认为true，允许模拟位置
        mLocationOption.setMockEnable(true);

        //单位是毫秒，默认30000毫秒，建议超时时间不要低于8000毫秒。
        mLocationOption.setHttpTimeOut(20000);

        //关闭缓存机制
        mLocationOption.setLocationCacheEnable(false);

        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mLocationClient.stopLocation();//停止定位后，本地定位服务并不会被销毁
        mLocationClient.onDestroy();//销毁定位客户端，同时销毁本地定位服务。
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
    @Override
    public void onBackPressed() {

        final AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setMessage("您确定退出吗?");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                ActivityController.finishAll();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }

}
