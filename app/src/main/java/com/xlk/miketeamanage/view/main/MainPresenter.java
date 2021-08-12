package com.xlk.miketeamanage.view.main;

import com.blankj.utilcode.util.LogUtils;
import com.xlk.miketeamanage.App;
import com.xlk.miketeamanage.base.BasePresenter;
import com.xlk.miketeamanage.helper.DataDisposal;
import com.xlk.miketeamanage.helper.SerialPortUtil;
import com.xlk.miketeamanage.model.Constant;
import com.xlk.miketeamanage.model.EventMessage;
import top.keepempty.sph.library.SphCmdEntity;

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
                    //A6 AAAAAAAA A6 CFFC
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

}
