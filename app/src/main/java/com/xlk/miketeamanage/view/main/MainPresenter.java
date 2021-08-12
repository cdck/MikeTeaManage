package com.xlk.miketeamanage.view.main;

import com.blankj.utilcode.util.LogUtils;
import com.xlk.miketeamanage.App;
import com.xlk.miketeamanage.R;
import com.xlk.miketeamanage.base.BasePresenter;
import com.xlk.miketeamanage.helper.DataDisposal;
import com.xlk.miketeamanage.helper.SerialPortUtil;
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

/**
 * @author Created by xlk on 2021/7/30.
 * @desc
 */
class MainPresenter extends BasePresenter<MainContract.View> implements MainContract.Presenter {
    private final String TAG = "MainPresenter-->";
    private SerialPortUtil helper;
//    private SerialPortHelper helper;

    public MainPresenter(MainContract.View view) {
        super(view);
    }

    @Override
    public void initialSerialPort() {
        helper = SerialPortUtil.getInstance();
//        SerialPortConfig serialPortConfig = new SerialPortConfig();
//        serialPortConfig.mode = 0;
//        serialPortConfig.path = Constant.path;
//        serialPortConfig.baudRate = Constant.baudRate;
//        serialPortConfig.dataBits = Constant.dataBits;
//        serialPortConfig.stopBits = Constant.stopBits;
//        serialPortConfig.parity = Constant.parity;
//        helper = new SerialPortHelper(16);
//        helper.setConfigInfo(serialPortConfig);
//        boolean isOpen = helper.openDevice();
//        LogUtils.i("是否成功打开串口：" + isOpen);
//        helper.setSphResultCallback(new SphResultCallback() {
//            @Override
//            public void onSendData(SphCmdEntity sendCom) {
//                if (sendCom != null) {
//                    LogUtils.d("发送命令：" + sendCom.commandsHex);
//                } else {
//                    LogUtils.e("发送命令：但是数据为空");
//                }
//            }
//
//            @Override
//            public void onReceiveData(SphCmdEntity data) {
//                if (data != null) {
//                    LogUtils.d("收到命令：" + data.commandsHex);
//                    receive(data.commandsHex);
//                } else {
//                    LogUtils.e("收到命令：但是数据为空");
//                }
//            }
//
//            @Override
//            public void onComplete() {
//                LogUtils.d("完成");
//                EventBus.getDefault().post(new EventMessage.Builder().type(Constant.bus_complete).build());
//            }
//        });
    }

    @Override
    public void addCommands(String command) {
        helper.addCommands(command);
    }

    @Override
    protected void busEvent(EventMessage msg) {
        switch (msg.getType()) {
            case Constant.bus_send: {
                SphCmdEntity sphCmdEntity = (SphCmdEntity) msg.getObjects()[0];
                break;
            }
            case Constant.bus_receive: {
                SphCmdEntity sphCmdEntity = (SphCmdEntity) msg.getObjects()[0];
                if (sphCmdEntity.commandsHex.startsWith("A1") && sphCmdEntity.commandsHex.length() > 6) {
                    //得到温度信息
                    String high8 = sphCmdEntity.commandsHex.substring(2, 4);
                    String low8 = sphCmdEntity.commandsHex.substring(4, 6);
                    float wendu = DataDisposal.combineFloat(high8, low8);
                    LogUtils.i("高8位：" + high8 + ",低8位：" + low8 + ",wendu=" + wendu);
                    mView.updateTemp(wendu);
                }
                if (sphCmdEntity.commandsHex.startsWith("A6") && sphCmdEntity.commandsHex.endsWith("CFFC")) {
                    //A6AAAAAAAAA6CFFC
                    //AA AA AA AA
                    String result = sphCmdEntity.commandsHex.substring(2, 10);
                    String high8 = sphCmdEntity.commandsHex.substring(2, 4);
                    String low8 = sphCmdEntity.commandsHex.substring(4, 6);
                    boolean errA = false;
                    boolean errB = false;
                    boolean errC = false;
                    boolean errD = false;
                    if (result.contains("AA")) {
//                        Toasty.error(App.appContext, R.string.device_yichang,Toasty.LENGTH_LONG,true).show();
                        App.isCanUse = false;
                        LogUtils.i("缸异常信息："+result);
                        if (result.substring(0, 2).equals("AA")) {
                            LogUtils.i("第一缸异常");
                            errA = true;
                        }
                        if (result.substring(2, 4).equals("AA")) {
                            LogUtils.i("第2缸异常");
                            errB = true;
                        }
                        if (result.substring(4, 6).equals("AA")) {
                            LogUtils.i("第一缸异常");
                            errC = true;
                        }
                        if (result.substring(6,8).equals("AA")) {
                            LogUtils.i("第一缸异常");
                            errD = true;
                        }
                    }
                    mView.errToast(errA, errB, errC, errD);
                }
                break;
            }
            case Constant.bus_complete: {
                break;
            }
            default:
                break;
        }
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
        helper.log();
//        List<String> temps = new ArrayList<>(commands);
//        System.out.println("截取的指令：");
//        for (String str : temps) {
//            System.out.println(str);
//        }
    }

    //    private LinkedBlockingDeque<String> commands = new LinkedBlockingDeque<>();
    private List<String> commands = new ArrayList<>();
}
