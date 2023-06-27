package com.example.shopping.app;

import android.app.Application;
import android.content.Context;

import com.example.shopping.bean.User;
import com.zhy.http.okhttp.OkHttpUtils;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import okhttp3.ConnectionSpec;
import okhttp3.OkHttpClient;

public class MyApplication extends Application {
    private static Context mContext;
    private static User me;
    @Override
    public void onCreate() {
        super.onCreate();

        initOkHttpClient();

    }
    // 获取全局上下文
    public static Context getContext() {
        return mContext;
    }

    public static User getMe() {
        return me;
    }

    public static void setMe(User me) {
        MyApplication.me = me;
    }

    private void initOkHttpClient(){

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                .addInterceptor(new LoggerInterceptor("TAG"))
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                //其他配置
                .build();

        OkHttpUtils.initClient(okHttpClient);
    }
}
