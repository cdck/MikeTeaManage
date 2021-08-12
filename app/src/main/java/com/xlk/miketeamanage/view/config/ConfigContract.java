package com.xlk.miketeamanage.view.config;

import com.xlk.miketeamanage.base.IBasePresenter;
import com.xlk.miketeamanage.base.IBaseView;

/**
 * @author Created by xlk on 2021/7/30.
 * @desc
 */
interface ConfigContract {
    interface View extends IBaseView {
        /**
         * 更新启动制冷
         * @param open
         */
        void updateTemperature(boolean open);
    }

    interface Presenter extends IBasePresenter {
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

        void exit();
    }
}
