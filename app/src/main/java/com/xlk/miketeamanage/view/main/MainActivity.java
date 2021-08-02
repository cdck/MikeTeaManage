package com.xlk.miketeamanage.view.main;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.SnackbarUtils;
import com.tencent.mmkv.MMKV;
import com.xlk.miketeamanage.R;
import com.xlk.miketeamanage.base.BaseActivity;
import com.xlk.miketeamanage.model.MmkvKey;
import com.xlk.miketeamanage.view.config.ConfigActivity;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import es.dmoral.toasty.Toasty;

public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View {

    private ConstraintLayout main_root_view;
    private RelativeLayout a, b, c, d;
    private TextView a_temp, b_temp, c_temp, d_temp;
    private TextView a_content, b_content, c_content, d_content;
    private Button a_btn, b_btn, c_btn, d_btn;
    private MMKV mmkv;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        main_root_view = findViewById(R.id.main_root_view);

        a = findViewById(R.id.a);
        a_temp = findViewById(R.id.a_temp);
        a_content = findViewById(R.id.a_content);
        a_btn = findViewById(R.id.a_btn);
        b = findViewById(R.id.b);
        b_temp = findViewById(R.id.b_temp);
        b_content = findViewById(R.id.b_content);
        b_btn = findViewById(R.id.b_btn);
        c = findViewById(R.id.c);
        c_temp = findViewById(R.id.c_temp);
        c_content = findViewById(R.id.c_content);
        c_btn = findViewById(R.id.c_btn);
        d = findViewById(R.id.d);
        d_temp = findViewById(R.id.d_temp);
        d_content = findViewById(R.id.d_content);
        d_btn = findViewById(R.id.d_btn);
    }

    @Override
    protected MainPresenter initPresenter() {
        return new MainPresenter(this);
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mmkv = MMKV.defaultMMKV();
        String password = mmkv.decodeString(MmkvKey.password);
        if (password == null || password.isEmpty()) {
            mmkv.encode(MmkvKey.password, "123456");
        }
    }

    @Override
    public void onBackPressed() {

    }

    public void jump2set(View view) {
        new MaterialDialog.Builder(this)
                .iconRes(R.drawable.ic_warring)
                .title(R.string.warring)
                .content(R.string.warring_conent)
                .inputRange(3, 8)
                .inputType(InputType.TYPE_TEXT_VARIATION_PASSWORD)
                .input(R.string.warring_input_hint, R.string.waarring_input_prefill, new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                        LogUtils.e("onInput 输入的内容：" + input);
                        if (input.length() <= 8 && input.length() >= 3) {
                            dialog.getActionButton(DialogAction.POSITIVE).setEnabled(true);
                        } else {
                            dialog.getActionButton(DialogAction.POSITIVE).setEnabled(false);
                        }
                    }
                })
                .positiveText(R.string.define)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        LogUtils.e("点击确定");
                        if (dialog.getInputEditText().getText().toString().equals(mmkv.decodeString(MmkvKey.password))) {
                            startActivity(new Intent(MainActivity.this, ConfigActivity.class));
                        } else {
                            Toasty.error(MainActivity.this, R.string.wrong_password, Toast.LENGTH_SHORT, true).show();
                        }
                    }
                })
                .negativeText(R.string.cancel)
                .show();
    }
}