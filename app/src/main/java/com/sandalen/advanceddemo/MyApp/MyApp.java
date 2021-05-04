package com.sandalen.advanceddemo.MyApp;

import android.app.Application;
import android.content.res.Configuration;

import androidx.annotation.NonNull;

/*
回调函数都运行于UI线程中
*
共享全局状态
* */
public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
    }

    //屏幕方向变化 系统语言更改 会执行
    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }
}
