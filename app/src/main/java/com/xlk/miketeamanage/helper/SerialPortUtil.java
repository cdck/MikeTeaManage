package com.xlk.miketeamanage.helper;

import android.widget.Toast;

import com.blankj.utilcode.util.LogUtils;
import com.xlk.miketeamanage.model.Constant;
import com.xlk.miketeamanage.model.EventMessage;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import top.keepempty.sph.library.SerialPortConfig;
import top.keepempty.sph.library.SerialPortHelper;
import top.keepempty.sph.library.SphCmdEntity;
import top.keepempty.sph.library.SphResultCallback;

import static com.xlk.miketeamanage.App.appContext;

/**
 * @author Created by xlk on 2021/8/7.
 * @desc
 */
public class SerialPortUtil {
    private static SerialPortUtil instance;
    private static SerialPortHelper serialPortHelper;

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

    public boolean isOpenDevice() {
        return serialPortHelper.isOpenDevice();
    }

    public void openDevice() {
        boolean openDevice = serialPortHelper.openDevice();
        Toast.makeText(appContext, "是否成功开启串口:" + openDevice, Toast.LENGTH_SHORT).show();
        LogUtils.i("是否成功开启串口：" + openDevice);
    }

    private SerialPortUtil() {
        SerialPortConfig serialPortConfig = new SerialPortConfig();
        serialPortConfig.path = Constant.path;
        serialPortConfig.baudRate = Constant.baudRate;
        serialPortConfig.dataBits = Constant.dataBits;
        serialPortConfig.stopBits = Constant.stopBits;
        serialPortHelper = new SerialPortHelper(9, true, serialPortConfig);
        openDevice();
        serialPortHelper.setSphResultCallback(new SphResultCallback() {
            @Override
            public void onSendData(SphCmdEntity sendCom) {
                if (sendCom != null) {
                    LogUtils.d("发送命令：" + sendCom.commandsHex);
                    EventBus.getDefault().post(new EventMessage.Builder().type(Constant.bus_send).objects(sendCom).build());
                } else {
                    LogUtils.e("发送命令：但是数据为空");
                }
            }

            @Override
            public void onReceiveData(SphCmdEntity data) {
                if (data != null) {
                    LogUtils.d("收到命令：" + data.commandsHex);
                    EventBus.getDefault().post(new EventMessage.Builder().type(Constant.bus_receive).objects(data).build());
                } else {
                    LogUtils.e("收到命令：但是数据为空");
                }
            }

            @Override
            public void onComplete() {
                LogUtils.d("完成");
                EventBus.getDefault().post(new EventMessage.Builder().type(Constant.bus_complete).build());
            }
        });
    }

    public void addCommands(String command) {
        if (serialPortHelper == null || !serialPortHelper.isOpenDevice()) {
            Toasty.error(appContext, "串口未打开", Toasty.LENGTH_SHORT, true).show();
            return;
        }
        serialPortHelper.addCommands(command);
    }

    public void closeDevice() {
        serialPortHelper.closeDevice();
    }

    private ArrayList<String> commands = new ArrayList<>();
    private String preLast = "";


    private void sss(String result) {
        //AA00EECFFCEEAAAACFFCAAAADNDKCFFCDKD
        if (result.contains("CFFC")) {
            String pre = result.substring(0, result.indexOf("CFFC") + 4);
            if (!preLast.isEmpty()) {
                commands.add(preLast += pre);
            }
            String next = result.substring(result.indexOf("CFFC") + 4);
            if (next.contains("CFFC")) {
                String next1 = next.substring(0, next.indexOf("CFFC") + 4);
                commands.add(next1);
//                next1.substring()
            }
        }
    }
}
