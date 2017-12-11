package com.youli.zbetuch_huangpu.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.youli.zbetuch_huangpu.R;
import com.youli.zbetuch_huangpu.entity.MeetInfo;
import com.youli.zbetuch_huangpu.utils.MyDateUtils;
import com.youli.zbetuch_huangpu.utils.MyOkHttpUtils;
import com.youli.zbetuch_huangpu.utils.SharedPreferencesUtils;

/**
 * 作者: zhengbin on 2017/12/2.
 * <p>
 * 邮箱:2381447237@qq.com
 * <p>
 * github:2381447237
 *
 * 会议详情界面
 */

public class MeetDetailActivity extends BaseActivity implements View.OnClickListener{

    private Context mContext=this;

    private int MeetingId;

    private MeetInfo meetInfo;

   // private ProgressDialog progressDialog;

    private WebView web;//会议内容
    private TextView tvTitle;//会议名称
    private TextView tvNotifier;//通知人
    private TextView tvMtime;//会议时间
    private TextView tvAddress;//会议地点
    private TextView tvPTime;//发布时间

    private Button btnIsRead;//已读或未读

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meet_detail);

        MeetingId=getIntent().getIntExtra("mId",0);

        meetInfo= (MeetInfo) getIntent().getSerializableExtra("meetInfo");

      //  showDialog();

        initViews();

    }

    private void initViews(){

        btnIsRead= (Button) findViewById(R.id.btn_meet_detail_isread);
        btnIsRead.setOnClickListener(this);

        tvTitle= (TextView) findViewById(R.id.tv_meet_detail_name);
         tvNotifier= (TextView) findViewById(R.id.tv_meet_detail_notifier);
         tvMtime= (TextView) findViewById(R.id.tv_meet_detail_mtime);
         tvAddress= (TextView) findViewById(R.id.tv_meet_detail_address);
         tvPTime= (TextView) findViewById(R.id.tv_meet_detail_ptime);

        tvTitle.setText(meetInfo.getTITLE());
        tvNotifier.setText(meetInfo.getCREATE_STAFF_NAME());
        tvMtime.setText(MyDateUtils.stringToYMDHMS(meetInfo.getMEETING_TIME()));
        tvAddress.setText(meetInfo.getMEETING_ADD());
        tvPTime.setText(MyDateUtils.stringToYMDHMS(meetInfo.getCREATE_DATE()));

        web= (WebView) findViewById(R.id.wv_meet_detail);

        web.getSettings().setJavaScriptEnabled(true);
        web.getSettings().setAllowFileAccess(true);
        web.getSettings().setPluginState(WebSettings.PluginState.ON);
//        web.getSettings().setSupportZoom(true);
//        web.getSettings().setBuiltInZoomControls(true);
//        web.getSettings().setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
        web.getSettings().setUseWideViewPort(true);
        web.getSettings().setLoadWithOverviewMode(true);
        web.getSettings().setTextSize(WebSettings.TextSize.LARGEST);//设置字体大小
        // 加载数据
//        web.setWebChromeClient(new WebChromeClient() {
//            @Override
//            public void onProgressChanged(WebView view, int newProgress) {
//                if (newProgress == 100) {
//                    if (progressDialog != null && progressDialog.isShowing()) {
//                        progressDialog.dismiss();
//                        progressDialog = null;
//                    }
//                }
//            }
//        });
        web.setWebViewClient(new WebViewClient());


        //synCookies(this,MyOkHttpUtils.BaseUrl+"/Web/Manage/Frm_Meeting_Info_ForMeetingId.aspx?MeetingId="+MeetingId);
        //web.loadUrl(MyOkHttpUtils.BaseUrl+"/Web/Manage/Frm_Meeting_Info_ForMeetingId.aspx?MeetingId="+MeetingId);


        web.setBackgroundColor(Color.parseColor("#AFCEEC")); // 设置背景色
        web.getBackground().setAlpha(255); // 设置填充透明度 范围：0-255
        web.loadDataWithBaseURL(null,meetInfo.getDOC(),"text/html", "UTF-8", null);
    }

    /**
     * 同步一下cookie
     */
    private  void synCookies(Context context, String url) {
        CookieSyncManager.createInstance(context);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        cookieManager.removeSessionCookie();//移除
        String cookies =SharedPreferencesUtils.getString("cookie");
        cookieManager.setCookie(url, cookies);//cookies是在HttpClient中获得的cookie
        CookieSyncManager.getInstance().sync();
    }

//    private void showDialog() {
//        progressDialog = new ProgressDialog(this);
//        progressDialog.setIcon(android.R.drawable.ic_dialog_info);
//        progressDialog.setTitle("加载提示");
//        progressDialog.setMessage("信息加载中，请稍后。。。");
//        progressDialog.show();
//    }

        public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == event.KEYCODE_BACK) && web.canGoBack()) {
            web.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.btn_meet_detail_isread:

                Toast.makeText(mContext,"未读",Toast.LENGTH_SHORT).show();

                break;



        }

    }
}
