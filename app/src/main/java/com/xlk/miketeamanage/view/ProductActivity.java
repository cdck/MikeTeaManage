package com.xlk.miketeamanage.view;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.xlk.miketeamanage.R;
import com.xlk.miketeamanage.adapter.ProductAdapter;
import com.xlk.miketeamanage.model.bean.ProductBean;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

public class ProductActivity extends AppCompatActivity {

    private ImageView ivBack;
    private TextView tvTitle;
    private RecyclerView rvProduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        initView();
        ivBack.setOnClickListener(v->finish());
        tvTitle.setText(getString(R.string.product_list));
        initAdapter();
    }

    private void initAdapter() {
        List<ProductBean> productBeans = new ArrayList<>();
        new ProductAdapter(R.layout.item_product_layout,productBeans);
    }

    private void initView() {
        ivBack = (ImageView) findViewById(R.id.iv_back);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        rvProduct = (RecyclerView) findViewById(R.id.rv_product);
    }
}