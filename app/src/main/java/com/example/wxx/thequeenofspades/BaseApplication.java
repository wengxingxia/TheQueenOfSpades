package com.example.wxx.thequeenofspades;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

/**
 * 自定义Application
 * 作者：wengxingxia
 * 时间：2017/5/18 0018 09:53
 */

public class BaseApplication extends Application {

    private static Context mContext;
    private static Handler mHandler;

    @Override
    public void onCreate() {
        super.onCreate();

        mContext = this.getApplicationContext();

        mHandler = new Handler();
    }

    public static Context getContext() {
        return mContext;
    }

    public static Handler getHandler() {
        return mHandler;
    }
}
