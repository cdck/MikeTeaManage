package com.xlk.miketeamanage.view.clean;


import android.widget.Toast;

import com.blankj.utilcode.util.LogUtils;
import com.xlk.miketeamanage.base.BasePresenter;
import com.xlk.miketeamanage.helper.SerialPortUtil;
import com.xlk.miketeamanage.model.Command;
import com.xlk.miketeamanage.model.Constant;
import com.xlk.miketeamanage.model.EventMessage;

import es.dmoral.toasty.Toasty;
import top.keepempty.sph.library.SerialPortConfig;
import top.keepempty.sph.library.SerialPortHelper;
import top.keepempty.sph.library.SphCmdEntity;
import top.keepempty.sph.library.SphResultCallback;

import static com.xlk.miketeamanage.App.appContext;

/**
 * @author Created by xlk on 2021/8/9.
 * @desc
 */
class CleanPresenter extends BasePresenter<CleanContract.View> implements CleanContract.Presenter {
    private SerialPortUtil instance;

    public CleanPresenter(CleanContract.View view) {
        super(view);
    }

    @Override
    public void initialSerialPort() {
//        SerialPortConfig serialPortConfig = new SerialPortConfig();
//        serialPortConfig.path = Constant.debugPath;
//        serialPortConfig.baudRate = Constant.baudRate;
//        serialPortConfig.dataBits = Constant.dataBits;
//        serialPortConfig.stopBits = Constant.stopBits;
//        serialPortHelper = new SerialPortHelper(32, true, serialPortConfig);
//        boolean openDevice = serialPortHelper.openDevice();
//        Toast.makeText(appContext, "是否成功开启串口:" + openDevice, Toast.LENGTH_SHORT).show();
//        serialPortHelper.setSphResultCallback(new SphResultCallback() {
//            @Override
//            public void onSendData(SphCmdEntity sendCom) {
//                LogUtils.d("发送命令：" + sendCom.commandsHex);
//            }
//
//            @Override
//            public void onReceiveData(SphCmdEntity data) {
//                LogUtils.d("收到命令：" + data.commandsHex);
//            }
//
//            @Override
//            public void onComplete() {
//                LogUtils.d("完成");
//            }
//        });
        instance = SerialPortUtil.getInstance();
        if (!instance.isOpenDevice()) {
            instance.openDevice();
        }
    }

    @Override
    public void addCommands(String command) {
        instance.addCommands(command);
    }

    @Override
    protected void busEvent(EventMessage msg) {
        switch (msg.getType()) {
            case Constant.bus_send: {
                SphCmdEntity sphCmdEntity = (SphCmdEntity) msg.getObjects()[0];
//                LogUtils.i("bus_send=" + sphCmdEntity.commandsHex);
                break;
            }
            case Constant.bus_receive: {
                SphCmdEntity sphCmdEntity = (SphCmdEntity) msg.getObjects()[0];
//                LogUtils.i("bus_receive=" + sphCmdEntity.commandsHex);
                if (sphCmdEntity.commandsHex.equals("AE11AA15CFFC")) {
                    LogUtils.i("成功启动清洗第1缸");
                    mView.updateWaterTankA(true);
                }else if(sphCmdEntity.commandsHex.equals("AE1100BFCFFC")) {
                    LogUtils.i("成功关闭清洗第1缸");
                    mView.updateWaterTankA(false);
                }else if(sphCmdEntity.commandsHex.equals("AE22AA26CFFC")) {
                    LogUtils.i("成功启动清洗第2缸");
                    mView.updateWaterTankB(true);
                }else if(sphCmdEntity.commandsHex.equals("AE22008CCFFC")) {
                    LogUtils.i("成功关闭清洗第2缸");
                    mView.updateWaterTankB(false);
                }else if(sphCmdEntity.commandsHex.equals("AE33AA37CFFC")) {
                    LogUtils.i("成功启动清洗第3缸");
                    mView.updateWaterTankC(true);
                }else if(sphCmdEntity.commandsHex.equals("AE33009DCFFC")) {
                    LogUtils.i("成功关闭清洗第3缸");
                    mView.updateWaterTankC(false);
                }else if(sphCmdEntity.commandsHex.equals("AE44AA40CFFC")) {
                    LogUtils.i("成功启动清洗第4缸");
                    mView.updateWaterTankD(true);
                }else if(sphCmdEntity.commandsHex.equals("AE4400EACFFC")) {
                    LogUtils.i("成功关闭清洗第4缸");
                    mView.updateWaterTankD(false);
                }else if(sphCmdEntity.commandsHex.equals("AE55AA51CFFC")) {
                    LogUtils.i("成功启动清洗水泵");
                    mView.updateWaterPump(true);
                }else if(sphCmdEntity.commandsHex.equals("AE5500FBCFFC")) {
                    LogUtils.i("成功关闭清洗水泵");
                    mView.updateWaterPump(false);
                }else if(sphCmdEntity.commandsHex.equals("AEAAAAAECFFC")) {
                    LogUtils.i("成功启动清洗所有水缸");
                    mView.updateWaterTankAll(true);
                }else if(sphCmdEntity.commandsHex.equals("AEAA0004CFFC")) {
                    LogUtils.i("成功关闭清洗所有水缸");
                    mView.updateWaterTankAll(false);
                }
                break;
            }
            case Constant.bus_complete: {
                LogUtils.i("bus_complete");
                break;
            }
            default:
                break;
        }
    }
}
