package com.xlk.miketeamanage.view.clean;

import com.xlk.miketeamanage.base.IBasePresenter;
import com.xlk.miketeamanage.base.IBaseView;

/**
 * @author Created by xlk on 2021/8/9.
 * @desc
 */
interface CleanContract {
    interface View extends IBaseView{
        void updateWaterTankA(boolean open);
        void updateWaterTankB(boolean open);
        void updateWaterTankC(boolean open);
        void updateWaterTankD(boolean open);
        void updateWaterTankAll(boolean open);
        void updateWaterPump(boolean open);
    }
    interface Presenter extends IBasePresenter{

        /**
         * 初始化串口
         */
        void initialSerialPort();

        /**
         * 发送命令
         *
         * @param command 指令
         */
        void addCommands(String command);
    }
}
