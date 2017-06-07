package com.llf.basemodel.base;

import android.app.Application;
import android.content.Context;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.tencent.bugly.crashreport.CrashReport;

/**
 * Created by llf on 2017/2/27.
 * 基础的Application
 */

public class BaseApplication extends Application {
    public static RefWatcher getRefWatcher(Context context) {
        BaseApplication application = (BaseApplication) context.getApplicationContext();
        return application.refWatcher;
    }

    public static BaseApplication instance;
    private RefWatcher refWatcher;

    @Override
    public void onCreate() {
       super.onCreate();
        instance = this;

        /**
         * 第三个参数发布时设置为false
         */
        CrashReport.initCrashReport(getApplicationContext());
        //检查内存泄漏
        refWatcher = LeakCanary.install(this);
    }

    @Override
    public void onLowMemory() {
        android.os.Process.killProcess(android.os.Process.myPid());
        super.onLowMemory();
    }
}
