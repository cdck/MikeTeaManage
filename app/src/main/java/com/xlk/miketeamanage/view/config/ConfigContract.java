package com.xlk.miketeamanage.view.config;

import com.xlk.miketeamanage.base.IBasePresenter;
import com.xlk.miketeamanage.base.IBaseView;

/**
 * @author Created by xlk on 2021/7/30.
 * @desc
 */
interface ConfigContract {
    interface View extends IBaseView{}
    interface Presenter extends IBasePresenter{
        /**
         * 发送命令
         * @param command 指令
         */
        void addCommands(String command);
        void initialSerialPort();
    }
}
