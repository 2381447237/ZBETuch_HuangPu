package com.youli.zbetuch_huangpu.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

import com.youli.zbetuch_huangpu.R;
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

public class MeetDetailActivity extends BaseActivity{

    private WebView web;

    private int MeetingId;

    private ProgressDialog progressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meet_detail);

        MeetingId=getIntent().getIntExtra("mId",0);

        showDialog();

        initViews();

    }

    private void initViews(){

        web= (WebView) findViewById(R.id.wv_meet_detail);

        web.getSettings().setJavaScriptEnabled(true);
        web.getSettings().setAllowFileAccess(true);
        web.getSettings().setPluginState(WebSettings.PluginState.ON);
        web.getSettings().setSupportZoom(true);
        web.getSettings().setBuiltInZoomControls(true);
        web.getSettings().setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
        web.getSettings().setUseWideViewPort(true);
        web.getSettings().setLoadWithOverviewMode(true);
        // 加载数据
        web.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    if (progressDialog != null && progressDialog.isShowing()) {
                        progressDialog.dismiss();
                        progressDialog = null;
                    }
                }
            }
        });
        web.setWebViewClient(new WebViewClient());


        synCookies(this,MyOkHttpUtils.BaseUrl+"/Web/Manage/Frm_Meeting_Info_ForMeetingId.aspx?MeetingId="+MeetingId);

        web.loadUrl(MyOkHttpUtils.BaseUrl+"/Web/Manage/Frm_Meeting_Info_ForMeetingId.aspx?MeetingId="+MeetingId);

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

    private void showDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setIcon(android.R.drawable.ic_dialog_info);
        progressDialog.setTitle("加载提示");
        progressDialog.setMessage("信息加载中，请稍后。。。");
        progressDialog.show();
    }

        public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == event.KEYCODE_BACK) && web.canGoBack()) {
            web.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
