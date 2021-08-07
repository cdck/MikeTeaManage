package com.xlk.miketeamanage.helper;

import android.util.Log;

import com.blankj.utilcode.util.LogUtils;
import com.xlk.miketeamanage.model.Constant;
import com.xlk.miketeamanage.model.EventMessage;

import org.greenrobot.eventbus.EventBus;

import top.keepempty.sph.library.SerialPortConfig;
import top.keepempty.sph.library.SerialPortHelper;
import top.keepempty.sph.library.SphCmdEntity;
import top.keepempty.sph.library.SphResultCallback;

/**
 * @author Created by xlk on 2021/8/7.
 * @desc
 */
public class SerialPortUtil {
    private static SerialPortUtil instance;
    private final SerialPortHelper serialPortHelper;

    public static SerialPortUtil getInstance() {
        if (instance == null) {
            synchronized (SerialPortUtil.class) {
                if (instance == null) {
                    instance = new SerialPortUtil();
                }
            }
        }
        return instance;
    }

    private SerialPortUtil() {
        SerialPortConfig serialPortConfig = new SerialPortConfig();
        serialPortConfig.path = Constant.path;
        serialPortConfig.baudRate = Constant.baudRate;
        serialPortConfig.dataBits = Constant.dataBits;
        serialPortConfig.stopBits = Constant.stopBits;
        serialPortHelper = new SerialPortHelper(32, true, serialPortConfig);
        serialPortHelper.openDevice();
        serialPortHelper.setSphResultCallback(new SphResultCallback() {
            @Override
            public void onSendData(SphCmdEntity sendCom) {
                LogUtils.d("发送命令：" + sendCom.commandsHex);
                EventBus.getDefault().post(new EventMessage.Builder().type(Constant.bus_send).objects(sendCom).build());
            }

            @Override
            public void onReceiveData(SphCmdEntity data) {
                LogUtils.d("收到命令：" + data.commandsHex);
                EventBus.getDefault().post(new EventMessage.Builder().type(Constant.bus_receive).objects(data).build());
            }

            @Override
            public void onComplete() {
                LogUtils.d("完成");
                EventBus.getDefault().post(new EventMessage.Builder().type(Constant.bus_complete).build());
            }
        });
    }

    public void addCommands(String command) {
        if (!serialPortHelper.isOpenDevice()) {
            throw new IllegalStateException("please open device first");
        }
        serialPortHelper.addCommands(command);
    }
}
