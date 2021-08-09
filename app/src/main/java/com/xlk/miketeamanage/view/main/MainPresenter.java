package com.xlk.miketeamanage.view.main;

import com.blankj.utilcode.util.LogUtils;
import com.xlk.miketeamanage.base.BasePresenter;
import com.xlk.miketeamanage.helper.SerialPortUtil;
import com.xlk.miketeamanage.model.Command;
import com.xlk.miketeamanage.model.Constant;
import com.xlk.miketeamanage.model.EventMessage;

import es.dmoral.toasty.Toasty;
import top.keepempty.sph.library.SphCmdEntity;

import static com.xlk.miketeamanage.App.appContext;

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
        switch (msg.getType()) {
            case Constant.bus_send: {
                SphCmdEntity sphCmdEntity = (SphCmdEntity) msg.getObjects()[0];
//                LogUtils.i("bus_send=" + sphCmdEntity.commandsHex);
                break;
            }
            case Constant.bus_receive: {
                SphCmdEntity sphCmdEntity = (SphCmdEntity) msg.getObjects()[0];
//                LogUtils.i("bus_receive=" + sphCmdEntity.commandsHex);
                if (sphCmdEntity.commandsHex.startsWith("A1")) {
                    //得到温度信息

                } else if (sphCmdEntity.commandsHex.startsWith(Command.success_drink_one)) {
                    Toasty.success(appContext, "来一杯成功", Toasty.LENGTH_SHORT, true).show();
                }
                break;
            }
            case Constant.bus_complete: {
//                LogUtils.i("bus_complete");
                break;
            }
            default:
                break;
        }
    }

}
