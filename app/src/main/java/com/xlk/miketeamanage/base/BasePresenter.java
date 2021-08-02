package com.xlk.miketeamanage.base;

import com.xlk.miketeamanage.model.EventMessage;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * @author Created by xlk on 2021/7/30.
 * @desc
 */
public abstract class BasePresenter<T extends IBaseView>{
    public T mView;

    public BasePresenter(T view) {
        mView = view;
        register();
    }

    public void register() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    public void unregister() {
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    public void onDestroy() {
        unregister();
    }

    /**
     * EventBus发送的消息交给子类去处理
     *
     * @param msg 消息数据
     */
    protected abstract void busEvent(EventMessage msg) ;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMessage(EventMessage msg)   {
        busEvent(msg);
    }
}
