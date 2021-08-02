package com.xlk.miketeamanage.view.config;

import com.xlk.miketeamanage.base.BasePresenter;
import com.xlk.miketeamanage.model.EventMessage;

/**
 * @author Created by xlk on 2021/7/30.
 * @desc
 */
class ConfigPresenter extends BasePresenter<ConfigContract.View> implements ConfigContract.Presenter {
    public ConfigPresenter(ConfigContract.View view) {
        super(view);
    }

    @Override
    protected void busEvent(EventMessage msg) {

    }
}
