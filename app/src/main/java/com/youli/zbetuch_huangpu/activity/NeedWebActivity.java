package com.youli.zbetuch_huangpu.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.youli.zbetuch_huangpu.R;
import com.youli.zbetuch_huangpu.utils.MyOkHttpUtils;
import com.youli.zbetuch_huangpu.utils.SharedPreferencesUtils;

public class NeedWebActivity extends BaseActivity {
    private WebView myWeb;
    private TextView tv_daiban;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_need_web);
        initView();
    }
    private void initView(){
        tv_daiban= (TextView) findViewById(R.id.tv_daiban);
        String Db=getIntent().getStringExtra("Db");
        tv_daiban.setText(Db);
        myWeb= (WebView) findViewById(R.id.MyWeb);
        myWeb.getSettings().setSupportZoom(true);   //放大缩小
        myWeb.getSettings().setBuiltInZoomControls(true);
        myWeb.getSettings().setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
        int intent=getIntent().getIntExtra("RDInfoo",0);
        String url= MyOkHttpUtils.BaseUrl+"/Web/Manage/Frm_Work_Info_ForWorkId.aspx?WorkId="+intent;
        myWeb.setWebViewClient(new WebViewClient());
        WebSettings settings = myWeb.getSettings();//自适应屏幕
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setJavaScriptEnabled(true);//自适应Js
        synCookies(this,url);
        myWeb.loadUrl(url);
    }


    /**
     * 同步一下cookie
     */
    public static void synCookies(Context context, String url) {
        CookieSyncManager.createInstance(context);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);

        cookieManager.removeSessionCookie();//移除
        String cookies = SharedPreferencesUtils.getString("cookie");
        cookieManager.setCookie(url, cookies);//cookies是在HttpClient中获得的cookie
        CookieSyncManager.getInstance().sync();
    }
}
