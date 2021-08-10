package com.xlk.miketeamanage.helper;

import android.widget.Toast;

import com.blankj.utilcode.util.LogUtils;
import com.xlk.miketeamanage.model.Constant;
import com.xlk.miketeamanage.model.EventMessage;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

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
    private static SerialPortHelper helper;

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
        return helper.isOpenDevice();
    }

    public void openDevice() {
        boolean openDevice = helper.openDevice();
        Toast.makeText(appContext, "是否成功开启串口:" + openDevice, Toast.LENGTH_SHORT).show();
        LogUtils.i("是否成功开启串口：" + openDevice);
    }

    private SerialPortUtil() {
        SerialPortConfig serialPortConfig = new SerialPortConfig();
        serialPortConfig.path = Constant.path;
        serialPortConfig.baudRate = Constant.baudRate;
        serialPortConfig.dataBits = Constant.dataBits;
        serialPortConfig.stopBits = Constant.stopBits;
        serialPortConfig.parity = Constant.parity;
        helper = new SerialPortHelper(16);
        helper.setConfigInfo(serialPortConfig);
        openDevice();
        helper.setSphResultCallback(new SphResultCallback() {
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
                    receive(data.commandsHex);
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
        if (helper == null || !helper.isOpenDevice()) {
            LogUtils.e("异常：串口未打开");
            return;
        }
        LogUtils.e("addCommands：" + command);
        helper.addCommands(command);
    }

    public void closeDevice() {
        helper.closeDevice();
    }

    private ArrayList<String> strs = new ArrayList<>();

    /**
     * 一个一个字符的判断拼接，遇到连续的CFFC时则组成一条指令
     * e.g AA00EECFFCEEAAAACFFCAAAADNDKCF
     */
    private void receive(String result) {
        char[] chars = result.toCharArray();
        String temp = "";
        for (char c : chars) {
            strs.add(new String(new char[]{c}));
            for (String s : strs) {
                temp += s;
            }
            if (temp.endsWith("CFFC")) {
                commands.add(temp);
                temp = "";
                strs.clear();
            }
        }
    }

    public void log() {
        List<String> temps = new ArrayList<>(commands);
        System.out.println("截取的指令：");
        for (String str : temps) {
            System.out.println(str);
        }
    }

    //    private LinkedBlockingDeque<String> commands = new LinkedBlockingDeque<>();
    private List<String> commands = new ArrayList<>();

//    class SendThread extends Thread {
//        @Override
//        public void run() {
//            while (true) {
//                try {
//                    if (!commands.isEmpty()) {
//                        String take = commands.take();
//                        LogUtils.e("线程中持续发送数据");
//                        EventBus.getDefault().post(new EventMessage.Builder().type(Constant.bus_receive).objects(take).build());
//                    }
//                    Thread.sleep(200);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }

}
