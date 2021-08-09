package com.xlk.miketeamanage.server;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.blankj.utilcode.util.LogUtils;
import com.xlk.miketeamanage.model.Constant;
import com.xlk.miketeamanage.model.EventMessage;

import org.greenrobot.eventbus.EventBus;

import androidx.annotation.Nullable;
import top.keepempty.sph.library.SerialPortConfig;
import top.keepempty.sph.library.SerialPortHelper;
import top.keepempty.sph.library.SphCmdEntity;
import top.keepempty.sph.library.SphResultCallback;

/**
 * @author Created by xlk on 2021/8/7.
 * @desc
 */
public class BackServer extends Service implements SphResultCallback {
    private SerialPortHelper serialPortHelper;
    public static boolean isOpen;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        isOpen = true;
        SerialPortConfig serialPortConfig = new SerialPortConfig();
        serialPortConfig.path = Constant.path;
        serialPortConfig.baudRate = Constant.baudRate;
        serialPortConfig.dataBits = Constant.dataBits;
        serialPortConfig.stopBits = Constant.stopBits;
        serialPortHelper = new SerialPortHelper(32, true, serialPortConfig);
        serialPortHelper.openDevice();
        // 数据接收回调
        serialPortHelper.setSphResultCallback(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isOpen = false;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onSendData(SphCmdEntity sendCom) {
        LogUtils.i("onSendData sendCom=" + sendCom.commandsHex);
        EventBus.getDefault().post(new EventMessage.Builder().type(Constant.bus_send).objects(sendCom).build());
    }

    @Override
    public void onReceiveData(SphCmdEntity data) {
        LogUtils.i("SphCmdEntity data=" + data.commandsHex);
        EventBus.getDefault().post(new EventMessage.Builder().type(Constant.bus_receive).objects(data).build());
    }

    @Override
    public void onComplete() {
        LogUtils.i("onComplete");
        EventBus.getDefault().post(new EventMessage.Builder().type(Constant.bus_complete).build());
    }
}
