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
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.youli.zbetuch_huangpu.R;
import com.youli.zbetuch_huangpu.entity.AdminInfo;
import com.youli.zbetuch_huangpu.entity.InspectorInfo;
import com.youli.zbetuch_huangpu.entity.MyFollowInfo;
import com.youli.zbetuch_huangpu.utils.MyOkHttpUtils;
import com.youli.zbetuch_huangpu.view.CircleImageView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.Response;


//首页
public class HomePageActivity extends CheckPermissionsActivity implements View.OnClickListener{
//IMBI

    private Context mContext=this;


    private final int SUCCESS_ADMIN_INFO=10001;
    private final int PROBLEM=10002;
    private final int OVERTIME=10003;//登录超时
    private final int SUCCESS_TZGG_NUM=10004;//通知公告的数量
    private final int SUCCESS_HYGL_NUM=10005;//会议管理的数量
    private final int SUCCESS_DBGZ_NUM=10006;//待办工作的数量
    private final int SUCCESS_DCDB_NUM=10007;//督察督办的数量
    private final int SUCCESS_WDGZ_NUM=10008;//我的关注的数量

    private List<MyFollowInfo> followData=new ArrayList<>();//我的关注的数据

    public static AdminInfo adminInfo;


    private Intent intent;
    private ImageView notice;
    private CircleImageView ivHead;//头像
    public static String adminName;//调查人姓名
    private Button workBtn;

    private ImageView meetManageIv;//会议管理
    private ImageView needWorkIv;//待办工作
    private ImageView inspectorIv;//督察督办
    private ImageView myFollowIv;//我的关注
    private TextView tvJdu,tvWdu,tvGdu;//经度，纬度，高度

    private TextView wdgzNumTv;//我的关注的数量
    private TextView tzggNumTv;//通知公告的数量
    private TextView hyglNumTv;//会议管理的数量
    private TextView dbgzNumTv;//待办工作的数量
    private TextView dcdbNumTv;//督察督办的数量

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

                    getNum("WDGZ");//我的关注

                    break;

                case PROBLEM:
                    Toast.makeText(mContext, "网络不给力", Toast.LENGTH_SHORT).show();
                    break;
                case OVERTIME:

                    Intent i=new Intent(mContext,OvertimeDialogActivity.class);
                    startActivity(i);

                    break;

                case SUCCESS_WDGZ_NUM://我的关注的数量

                    followData=((List<MyFollowInfo>)(msg.obj));

                    if(followData.size()!=0) {
                        wdgzNumTv.setVisibility(View.VISIBLE);
                        wdgzNumTv.setText(followData.get(0).getRecordCount()+"");
                    }else{
                        wdgzNumTv.setVisibility(View.GONE);
                    }
                    getNum("TZGG");//通知公告

                    break;

                case SUCCESS_TZGG_NUM://通知公告的数量

                    if(!TextUtils.equals("0",""+msg.obj)) {
                        tzggNumTv.setText("" + msg.obj);
                        tzggNumTv.setVisibility(View.VISIBLE);
                    }else{
                        tzggNumTv.setVisibility(View.GONE);
                    }
                    getNum("HYGL");//会议管理

                    break;

                case SUCCESS_HYGL_NUM://会议管理的数量
                    if(!TextUtils.equals("0",""+msg.obj)) {
                        hyglNumTv.setText("" + msg.obj);
                        hyglNumTv.setVisibility(View.VISIBLE);
                    }else {
                        hyglNumTv.setVisibility(View.GONE);
                    }
                    getNum("DBGZ");//待办工作
                    break;

                case SUCCESS_DBGZ_NUM://待办工作的数量
                    if(!TextUtils.equals("0",""+msg.obj)) {
                        dbgzNumTv.setText("" + msg.obj);
                        dbgzNumTv.setVisibility(View.VISIBLE);
                    }else{
                        dbgzNumTv.setVisibility(View.GONE);
                    }
                    getNum("DCDB");//督察督办
                    break;

                case SUCCESS_DCDB_NUM://督察督办的数量
                    if(!TextUtils.equals("0",""+msg.obj)) {
                        dcdbNumTv.setText("" + msg.obj);
                        dcdbNumTv.setVisibility(View.VISIBLE);
                    }else{
                        dcdbNumTv.setVisibility(View.GONE);
                    }

                    break;
            }

        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        //注册事件
        EventBus.getDefault().register(this);

        markStr="HomePageActivity";//通知权限的标记， 这句一定不能少

        initViews();


    }


    private void initViews(){
        notice= (ImageView) findViewById(R.id.notice);
        notice.setOnClickListener(this);
        ivHead= (CircleImageView) findViewById(R.id.iv_activity_homepage_head);
        ivHead.setOnClickListener(this);
        meetManageIv= (ImageView) findViewById(R.id.homepage_meet_manage_iv);
        meetManageIv.setOnClickListener(this);
        needWorkIv= (ImageView) findViewById(R.id.homepage_need_work_iv);
        needWorkIv.setOnClickListener(this);
        inspectorIv= (ImageView) findViewById(R.id.homepage_inspector_iv);
        inspectorIv.setOnClickListener(this);
        myFollowIv= (ImageView) findViewById(R.id.homepage_my_follow_iv);
        myFollowIv.setOnClickListener(this);
        workBtn = (Button) findViewById(R.id.main_layout_work_btn);
        workBtn.setOnClickListener(this);
        tvJdu= (TextView) findViewById(R.id.main_layout_tv_jdu);
        tvWdu= (TextView) findViewById(R.id.main_layout_tv_wdu);
        tvGdu= (TextView) findViewById(R.id.main_layout_tv_gdu);

        wdgzNumTv= (TextView) findViewById(R.id.wdgz_num_tv);
        tzggNumTv= (TextView) findViewById(R.id.tzgg_num_tv);
        hyglNumTv= (TextView) findViewById(R.id.hygl_num_tv);
        dbgzNumTv= (TextView) findViewById(R.id.dbgz_num_tv);
        dcdbNumTv= (TextView) findViewById(R.id.dcdb_num_tv);


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

        //取消注册事件
        EventBus.getDefault().unregister(this);

        mLocationClient.stopLocation();//停止定位后，本地定位服务并不会被销毁
        mLocationClient.onDestroy();//销毁定位客户端，同时销毁本地定位服务。
    }

    //这里我们的ThreadMode设置为MAIN，事件的处理会在UI线程中执行
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getNum(final String mark){//获取通知公告，会议管理，待办工作，督察督办的数量
        //我的关注的数量:
       // http://web.youli.pw:8088/Json/First/Get_Attention.aspx?page=0&rows=1
        //通知公告的数量:
        //http://web.youli.pw:8088/Json/First/Get_News_Count.aspx
        //会议管理的数量：
        //http://web.youli.pw:8088/Json/First/Get_Meeting_Check.aspx
        //待办工作：
        //http://web.youli.pw:8088/Json/First/Get_Work_Notice_Check.aspx
        //督察督办
        //http://web.youli.pw:8088/Json/First/Get_Work_Notice_Done.aspx
        new Thread(new Runnable() {
            @Override
            public void run() {

                String url = null;

                if(TextUtils.equals(mark,"TZGG")) {

                    url = MyOkHttpUtils.BaseUrl + "/Json/First/Get_News_Count.aspx";
                }else if(TextUtils.equals(mark,"HYGL")){
                    url = MyOkHttpUtils.BaseUrl + "/Json/First/Get_Meeting_Check.aspx";
                }else if(TextUtils.equals(mark,"DBGZ")){
                    url = MyOkHttpUtils.BaseUrl + "/Json/First/Get_Work_Notice_Check.aspx";
                }else if(TextUtils.equals(mark,"DCDB")){
                    url = MyOkHttpUtils.BaseUrl + "/Json/First/Get_Work_Notice_Done.aspx";
                }else if(TextUtils.equals(mark,"WDGZ")){
                    url = MyOkHttpUtils.BaseUrl + "/Json/First/Get_Attention.aspx?page=0&rows=1";
                }



                Response response=MyOkHttpUtils.okHttpGet(url);

                Message msg=Message.obtain();

                if(response!=null){

                    if(response.body()!=null){

                        try {
                            String resStr=response.body().string();

                            if(!TextUtils.equals(resStr,"")){

                                try{


                                    if(TextUtils.equals(mark,"WDGZ")){
                                        Gson gson=new Gson();
                                        msg.obj=gson.fromJson(resStr,new TypeToken<List<MyFollowInfo>>(){}.getType());
                                        msg.what = SUCCESS_WDGZ_NUM;
                                    }else {
                                        msg.obj=resStr;
                                        if (TextUtils.equals(mark, "TZGG")) {
                                            msg.what = SUCCESS_TZGG_NUM;
                                        } else if (TextUtils.equals(mark, "HYGL")) {
                                            msg.what = SUCCESS_HYGL_NUM;
                                        } else if (TextUtils.equals(mark, "DBGZ")) {
                                            msg.what = SUCCESS_DBGZ_NUM;
                                        } else if (TextUtils.equals(mark, "DCDB")) {
                                            msg.what = SUCCESS_DCDB_NUM;
                                        }
                                    }
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


        }).start();

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

            //通知公告
            case R.id.notice:
                intent=new Intent(this,NoticeBulletin.class);
                startActivity(intent);
                break;

            case R.id.homepage_meet_manage_iv://会议管理
                intent = new Intent(this,MeetManageActivity.class);
                startActivity(intent);
                break;
            case R.id.homepage_need_work_iv://待办工作
                intent = new Intent(this,NeedWorkActivity.class);
                startActivity(intent);
                break;
            case R.id.homepage_inspector_iv://督察督办
                intent = new Intent(this,InspectorActivity.class);
                startActivity(intent);
                break;
            case R.id.homepage_my_follow_iv://我的关注
                intent = new Intent(this,MyFollowActivity.class);
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
