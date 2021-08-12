package com.xlk.miketeamanage.view.main;

import com.xlk.miketeamanage.base.IBasePresenter;
import com.xlk.miketeamanage.base.IBaseView;

import java.util.function.IntPredicate;

/**
 * @author Created by xlk on 2021/7/30.
 * @desc
 */
interface MainContract {
    interface View extends IBaseView{
        /**
         * 更新温度
         * @param temp 温度
         */
        void updateTemp(float temp);

        void errToast(boolean errA, boolean errB, boolean errC, boolean errD);
    }
    interface Presenter extends IBasePresenter{
        void initialSerialPort();

        void addCommands(String command);
    }
}
