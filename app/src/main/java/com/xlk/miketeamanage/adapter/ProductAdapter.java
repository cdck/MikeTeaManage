package com.xlk.miketeamanage.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.xlk.miketeamanage.model.bean.ProductBean;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * @author Created by xlk on 2021/8/7.
 * @desc
 */
public class ProductAdapter extends BaseQuickAdapter<ProductBean, BaseViewHolder> {
    public ProductAdapter(int layoutResId, @Nullable List<ProductBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder holder, ProductBean productBean) {

    }
}
