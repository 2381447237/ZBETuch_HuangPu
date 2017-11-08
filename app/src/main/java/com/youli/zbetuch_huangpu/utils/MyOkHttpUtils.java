package com.youli.zbetuch_huangpu.utils;

import android.util.Log;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by liutao on 2017/9/21.
 */

public class MyOkHttpUtils {

    public static final String BaseUrl="http://web.youli.pw:8088";
    public static OkHttpClient okHttpClient=null;

    static String cookies=SharedPreferencesUtils.getString("cookie");

    //懒汉
    private static synchronized OkHttpClient getInstance(){

          if(okHttpClient==null){

              okHttpClient=new OkHttpClient();

          }

          return  okHttpClient;
    }


    /**
     * OKHttp 同步 Get
     *
     * @param url 请求网址
     * @return 获取到数据返回Response，若未获取到数据返回null
     */

    public static Response okHttpGet(String url){

        getInstance();

        Request request=new Request.Builder().addHeader("cookie",cookies).url(url).build();



        Response response=null;

        try {
            response=okHttpClient.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return response;

    }

    /**
     * OKHttp 异步 Post
     *
     * @param url 请求网址
     * @return 获取到数据返回Response，若未获取到数据返回null
     */
    public static Response okHttpPost(String url, HashMap<String,String> paramsMap){

        getInstance();

        FormBody.Builder builder = new FormBody.Builder();

        for(String key:paramsMap.keySet()){
            builder.add(key,paramsMap.get(key));
        }

        RequestBody requestBody=builder.build();

        Request request=new Request.Builder().addHeader("cookie",cookies).url(url).post(requestBody).build();

        Response response=null;

        try {
            response=okHttpClient.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return response;

    };

    public static Response okHttpPost(String url,String userName){

        getInstance();

        RequestBody requestBody=new FormBody.Builder().add("sfz", userName)
                .build();

        Request request=new Request.Builder().addHeader("cookie",cookies).url(url).post(requestBody).build();

        Response response=null;

        try {
            response=okHttpClient.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return response;

    };

}
