package com.xlk.miketeamanage.view;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.LogUtils;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.tencent.mmkv.MMKV;
import com.xlk.miketeamanage.GlideEngine;
import com.xlk.miketeamanage.R;
import com.xlk.miketeamanage.model.MmkvKey;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import es.dmoral.toasty.Toasty;

public class ProductSetupActivity extends AppCompatActivity {

    public static final String extra_product = "extra_product";
    private ImageView ivBack;
    private TextView tvTitle;
    private SwitchMaterial cbOpen;
    private EditText edtName;
    private ImageView ivBg;
    private EditText edtCapacityA;
    private EditText edtCapacityB;
    private EditText edtCapacityC;
    private EditText edtCapacityD;
    private EditText edtWaterPumpCapacity;
    private Button btnModify;
    /**
     * 当前产品id
     */
    private String currentProduct;
    private RelativeLayout rl_cb;
    private MMKV mmkv;
    private String newProductImgPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_setup);
        //屏幕常亮
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        initView();
        tvTitle.setText(R.string.set_product);
        ivBack.setOnClickListener(v -> finish());
        currentProduct = getIntent().getStringExtra(extra_product);
        LogUtils.i("当前产品：" + currentProduct);
        mmkv = MMKV.mmkvWithID(currentProduct);
        initDataFromMmkv();
        ivBg.setOnClickListener(v -> {
            PictureSelector.create(this)
                    .openGallery(PictureMimeType.ofImage())//相册 媒体类型 PictureMimeType.ofAll()、ofImage()、ofVideo()、ofAudio()
                    .imageEngine(GlideEngine.createGlideEngine()) // 请参考Demo GlideEngine.java
                    .selectionMode(PictureConfig.SINGLE)//单选or多选 PictureConfig.SINGLE PictureConfig.MULTIPLE
//                    .isSingleDirectReturn(true)//PictureConfig.SINGLE模式下是否直接返回
//                    .isCamera(true)//列表是否显示拍照按钮
                    .forResult(PictureConfig.CHOOSE_REQUEST);
        });
        rl_cb.setOnClickListener(v -> {
            boolean checked = !cbOpen.isChecked();
            cbOpen.setChecked(checked);
            mmkv.encode(MmkvKey.product_open, checked);
        });
        btnModify.setOnClickListener(v -> {
            String name = edtName.getText().toString();
            String capacityA = edtCapacityA.getText().toString().trim();
            String capacityB = edtCapacityB.getText().toString().trim();
            String capacityC = edtCapacityC.getText().toString().trim();
            String capacityD = edtCapacityD.getText().toString().trim();
            String waterPumpCapacity = edtWaterPumpCapacity.getText().toString().trim();
            if (name.isEmpty() || capacityA.isEmpty() || capacityB.isEmpty() || capacityC.isEmpty() || capacityD.isEmpty() || waterPumpCapacity.isEmpty()) {
                Toasty.error(ProductSetupActivity.this, R.string.incomplete_filling, Toasty.LENGTH_SHORT, true).show();
                return;
            }
            mmkv.encode(MmkvKey.product_name, name);
            mmkv.encode(MmkvKey.product_capacity_a, Integer.parseInt(capacityA));
            mmkv.encode(MmkvKey.product_capacity_b, Integer.parseInt(capacityB));
            mmkv.encode(MmkvKey.product_capacity_c, Integer.parseInt(capacityC));
            mmkv.encode(MmkvKey.product_capacity_d, Integer.parseInt(capacityD));
            mmkv.encode(MmkvKey.water_pump_capacity, Integer.parseInt(waterPumpCapacity));
            if (!newProductImgPath.isEmpty()) {
                mmkv.encode(MmkvKey.product_img, newProductImgPath);
            }
            Toasty.success(ProductSetupActivity.this, R.string.save_successfully, Toast.LENGTH_SHORT, true).show();
            finish();
        });
    }

    private void initDataFromMmkv() {
        cbOpen.setChecked(mmkv.decodeBool(MmkvKey.product_open));
        edtName.setText(mmkv.decodeString(MmkvKey.product_name));
        String path = mmkv.decodeString(MmkvKey.product_img);
        if (path != null && !path.isEmpty()) {
            LogUtils.i("产品图路径=" + path);
            Drawable drawable = Drawable.createFromPath(path);
            ivBg.setImageDrawable(drawable);
        }
        edtCapacityA.setText(String.valueOf(mmkv.decodeInt(MmkvKey.product_capacity_a)));
        edtCapacityB.setText(String.valueOf(mmkv.decodeInt(MmkvKey.product_capacity_b)));
        edtCapacityC.setText(String.valueOf(mmkv.decodeInt(MmkvKey.product_capacity_c)));
        edtCapacityD.setText(String.valueOf(mmkv.decodeInt(MmkvKey.product_capacity_d)));
        edtWaterPumpCapacity.setText(String.valueOf(mmkv.decodeInt(MmkvKey.water_pump_capacity)));
    }

    private void initView() {
        ivBack = (ImageView) findViewById(R.id.iv_back);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        rl_cb = (RelativeLayout) findViewById(R.id.rl_cb);
        cbOpen = (SwitchMaterial) findViewById(R.id.cb_open);
        edtName = (EditText) findViewById(R.id.edt_name);
        ivBg = (ImageView) findViewById(R.id.iv_bg);
        edtCapacityA = (EditText) findViewById(R.id.edt_capacity_a);
        edtCapacityB = (EditText) findViewById(R.id.edt_capacity_b);
        edtCapacityC = (EditText) findViewById(R.id.edt_capacity_c);
        edtCapacityD = (EditText) findViewById(R.id.edt_capacity_d);
        edtWaterPumpCapacity = (EditText) findViewById(R.id.edt_water_pump_capacity);
        btnModify = (Button) findViewById(R.id.btn_modify);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 结果回调
                    List<LocalMedia> result = PictureSelector.obtainMultipleResult(data);
                    if (result != null && !result.isEmpty()) {
                        LocalMedia localMedia = result.get(0);
                        newProductImgPath = localMedia.getPath();
                        LogUtils.i("选择图片的路径：" + newProductImgPath);
                        Drawable drawable = Drawable.createFromPath(newProductImgPath);
                        ivBg.setImageDrawable(drawable);
                    }
                    break;
                default:
                    break;
            }
        }
    }
}