package com.xlk.miketeamanage;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;

import com.blankj.utilcode.util.LogUtils;
import com.tencent.mmkv.MMKV;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @author Created by xlk on 2021/7/30.
 * @desc
 */
public class App extends Application {
    public static Context appContext;

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = this;
        LogUtils.Config config = LogUtils.getConfig();
        config.setLog2FileSwitch(true);
        config.setDir(getFilesDir());
        config.setSaveDays(7);
        String initialize = MMKV.initialize(this);
        LogUtils.i("mmkv 目录：" + initialize);
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
                LogUtils.d("onActivityCreated ## " + activity);
            }

            @Override
            public void onActivityStarted(@NonNull Activity activity) {
                LogUtils.d("onActivityStarted ## " + activity);
            }

            @Override
            public void onActivityResumed(@NonNull Activity activity) {
                LogUtils.d("onActivityResumed ## " + activity);
            }

            @Override
            public void onActivityPaused(@NonNull Activity activity) {
                LogUtils.d("onActivityPaused ## " + activity);
            }

            @Override
            public void onActivityStopped(@NonNull Activity activity) {
                LogUtils.d("onActivityStopped ## " + activity);
            }

            @Override
            public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {
                LogUtils.d("onActivitySaveInstanceState ## " + activity);
            }

            @Override
            public void onActivityDestroyed(@NonNull Activity activity) {
                LogUtils.d("onActivityDestroyed ## " + activity);
            }
        });
    }
}
