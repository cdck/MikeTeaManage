package com.xlk.miketeamanage.view.main;

import com.xlk.miketeamanage.base.BasePresenter;
import com.xlk.miketeamanage.helper.SerialPortUtil;
import com.xlk.miketeamanage.model.EventMessage;

/**
 * @author Created by xlk on 2021/7/30.
 * @desc
 */
class MainPresenter extends BasePresenter<MainContract.View> implements MainContract.Presenter {
    private final String TAG = "MainPresenter-->";
    private SerialPortUtil helper;

    public MainPresenter(MainContract.View view) {
        super(view);
    }

    @Override
    public void initialSerialPort() {
        helper = SerialPortUtil.getInstance();
    }

    @Override
    public void addCommands(String command) {
        helper.addCommands(command);
    }

    @Override
    protected void busEvent(EventMessage msg) {

    }

}
