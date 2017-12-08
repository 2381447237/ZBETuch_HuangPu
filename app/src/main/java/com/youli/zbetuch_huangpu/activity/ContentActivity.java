package com.youli.zbetuch_huangpu.activity;

import android.content.Context;
import android.os.Bundle;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
<<<<<<< HEAD
=======
import android.widget.TextView;
>>>>>>> d64359b51cf226e49a6d83947744451fe0f278b2

import com.youli.zbetuch_huangpu.R;
import com.youli.zbetuch_huangpu.utils.MyOkHttpUtils;
import com.youli.zbetuch_huangpu.utils.SharedPreferencesUtils;

public class ContentActivity extends BaseActivity {
    private WebView myWebView ;
    private String url;
    private TextView tv_neirong;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
    init();
    }
    private void init(){
        myWebView= (WebView) findViewById(R.id.Mywebview);
        tv_neirong= (TextView) findViewById(R.id.tv_neirong);
        String b=getIntent().getStringExtra("Nr");
        tv_neirong.setText(b);

        myWebView.getSettings().setSupportZoom(true);   //放大缩小
        myWebView.getSettings().setBuiltInZoomControls(true);
        myWebView.getSettings().setDefaultZoom(WebSettings.ZoomDensity.MEDIUM);
        int a =  getIntent().getIntExtra("RDInfoo",0);
        url= MyOkHttpUtils.BaseUrl+"/Web/Manage/FrmShowNewsWindows.aspx?News="+a;
        WebSettings settings = myWebView.getSettings();//自适应屏幕
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
<<<<<<< HEAD
        settings.setJavaScriptEnabled(true);
        myWebView.setWebViewClient(new WebViewClient());
        synCookies(this,url);

=======
        settings.setJavaScriptEnabled(true);//自适应Js
        synCookies(this,url);
        myWebView.setWebViewClient(new WebViewClient());
>>>>>>> d64359b51cf226e49a6d83947744451fe0f278b2
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        init();

    }
}
