package com.xlk.miketeamanage;

import android.app.Application;

import com.blankj.utilcode.util.LogUtils;
import com.tencent.mmkv.MMKV;

/**
 * @author Created by xlk on 2021/7/30.
 * @desc
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        LogUtils.Config config = LogUtils.getConfig();
        config.setLog2FileSwitch(true);
        config.setDir(getFilesDir());
        config.setSaveDays(7);
        String initialize = MMKV.initialize(this);
        LogUtils.i("mmkv 目录："+initialize);

    }
}
