package com.xlk.miketeamanage.view.config;

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
class ConfigPresenter extends BasePresenter<ConfigContract.View> implements ConfigContract.Presenter {


    private SerialPortUtil helper;


    public ConfigPresenter(ConfigContract.View view) {
        super(view);
    }

    @Override
    protected void busEvent(EventMessage msg) {
        switch (msg.getType()) {
            case Constant.bus_send: {
//                SphCmdEntity sphCmdEntity = (SphCmdEntity) msg.getObjects()[0];
//                LogUtils.i("bus_send=" + sphCmdEntity.commandsHex);
                break;
            }
            case Constant.bus_receive: {
                SphCmdEntity sphCmdEntity = (SphCmdEntity) msg.getObjects()[0];
                if (sphCmdEntity.commandsHex.equals(Command.success_temperature)) {
                    mView.updateTemperature(true);
                }else if(sphCmdEntity.commandsHex.equals("AD00ADCFFC")) {
                    mView.updateTemperature(false);
                }
                else if (sphCmdEntity.commandsHex.equals(Command.success_temperature_temp)) {
                    Toasty.success(appContext, "设置启动或停止制冷温度成功", Toasty.LENGTH_SHORT, true).show();
                    LogUtils.i("设置启动或停止制冷温度成功");
                } else if (sphCmdEntity.commandsHex.equals(Command.success_clean_speed)) {
                    Toasty.success(appContext, "设置清洗速度成功", Toasty.LENGTH_SHORT, true).show();
                    LogUtils.i("设置清洗速度成功");
                } else if (sphCmdEntity.commandsHex.equals(Command.success_discharge_speed)) {
                    Toasty.success(appContext, "设置出料速度成功", Toasty.LENGTH_SHORT, true).show();
                    LogUtils.i("设置出料速度成功");
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

    @Override
    public void initialSerialPort() {
        helper = SerialPortUtil.getInstance();
    }

    @Override
    public void addCommands(String command) {
        helper.addCommands(command);
    }

    @Override
    public void exit() {
        LogUtils.e("关闭串口");
        helper.closeDevice();
    }
}
