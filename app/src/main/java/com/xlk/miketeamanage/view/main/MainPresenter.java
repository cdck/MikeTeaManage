package com.xlk.miketeamanage.view.main;

import com.xlk.miketeamanage.base.BasePresenter;
import com.xlk.miketeamanage.model.EventMessage;

/**
 * @author Created by xlk on 2021/7/30.
 * @desc
 */
class MainPresenter extends BasePresenter<MainContract.View> implements MainContract.Presenter {
    public MainPresenter(MainContract.View view) {
        super(view);
    }

    @Override
    protected void busEvent(EventMessage msg) {

    }
}
