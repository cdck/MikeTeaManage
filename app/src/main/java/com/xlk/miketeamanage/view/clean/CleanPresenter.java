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
//                SphCmdEntity sphCmdEntity = (SphCmdEntity) msg.getObjects()[0];
//                LogUtils.i("bus_receive=" + sphCmdEntity.commandsHex);
//                if (sphCmdEntity.commandsHex.contains(Command.success_product)) {
//                    Toasty.success(appContext, "开启清洗成功", Toasty.LENGTH_SHORT, true).show();
//                }
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
