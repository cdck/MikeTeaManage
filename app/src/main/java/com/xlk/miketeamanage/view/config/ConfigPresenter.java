package com.xlk.miketeamanage.view.config;

import com.blankj.utilcode.util.LogUtils;
import com.xlk.miketeamanage.base.BasePresenter;
import com.xlk.miketeamanage.model.EventMessage;

import top.keepempty.sph.library.SerialPortHelper;
import top.keepempty.sph.library.SphCmdEntity;
import top.keepempty.sph.library.SphResultCallback;

/**
 * @author Created by xlk on 2021/7/30.
 * @desc
 */
class ConfigPresenter extends BasePresenter<ConfigContract.View> implements ConfigContract.Presenter, SphResultCallback {

    private SerialPortHelper serialPortHelper;

    public ConfigPresenter(ConfigContract.View view) {
        super(view);
    }

    @Override
    protected void busEvent(EventMessage msg) {

    }

    @Override
    public void initialSerialPort() {
        serialPortHelper = new SerialPortHelper(32, true);
        if (!serialPortHelper.isOpenDevice()) {
            serialPortHelper.openDevice("dev/ttyS0", 11520);
        }
        serialPortHelper.setSphResultCallback(this);
    }

    @Override
    public void addCommands(String command) {
        serialPortHelper.addCommands(command);
    }

    @Override
    public void onSendData(SphCmdEntity sendCom) {
        LogUtils.i("onSendData sendCom=" + sendCom);
    }

    @Override
    public void onReceiveData(SphCmdEntity data) {
        LogUtils.i("onReceiveData data=" + data);
    }

    @Override
    public void onComplete() {
        LogUtils.i("onComplete");
    }
}
