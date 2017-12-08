package com.youli.zbetuch_huangpu.activity;

import android.content.Context;
import android.os.Bundle;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.youli.zbetuch_huangpu.R;
import com.youli.zbetuch_huangpu.utils.MyOkHttpUtils;
import com.youli.zbetuch_huangpu.utils.SharedPreferencesUtils;

public class ContentActivity extends BaseActivity {
    private WebView myWebView ;
    private String url;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
    init();
    }
    private void init(){
        myWebView= (WebView) findViewById(R.id.Mywebview);
        int a =  getIntent().getIntExtra("RDInfoo",0);
        url= MyOkHttpUtils.BaseUrl+"/Web/Manage/FrmShowNewsWindows.aspx?News="+a;
        WebSettings settings = myWebView.getSettings();
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setJavaScriptEnabled(true);
        myWebView.setWebViewClient(new WebViewClient());
        synCookies(this,url);

        myWebView.loadUrl(url);


    }
    /**
     * 同步一下cookie
     */
    public static void synCookies(Context context, String url) {
        CookieSyncManager.createInstance(context);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        cookieManager.removeSessionCookie();//移除
        String cookies =SharedPreferencesUtils.getString("cookie");
        cookieManager.setCookie(url, cookies);//cookies是在HttpClient中获得的cookie
        CookieSyncManager.getInstance().sync();
    }


}
