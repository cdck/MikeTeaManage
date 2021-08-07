package com.xlk.miketeamanage.base;

import android.os.Bundle;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * @author Created by xlk on 2021/7/30.
 * @desc
 */
public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity implements IBaseView{
    protected T presenter;

    protected abstract int getLayoutId();

    protected abstract T initPresenter();

    protected abstract void init(Bundle savedInstanceState);
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        //屏幕常亮
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        initView();
        presenter = initPresenter();
        init(savedInstanceState);
    }

    protected void initView(){

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }
}
