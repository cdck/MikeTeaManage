package com.xlk.miketeamanage.view;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.mmkv.MMKV;
import com.xlk.miketeamanage.R;
import com.xlk.miketeamanage.model.MmkvKey;

import androidx.appcompat.app.AppCompatActivity;
import es.dmoral.toasty.Toasty;

public class PasswordActivity extends AppCompatActivity {

    private ImageView ivBack;
    private TextView tvTitle;
    private EditText edtPwdOld;
    private EditText edtPwdNew;
    private EditText edtPwdConfirm;
    private Button btnModify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        initView();
        tvTitle.setText(getString(R.string.modify_password));
        MMKV mmkv = MMKV.defaultMMKV();
        ivBack.setOnClickListener(v -> finish());
        btnModify.setOnClickListener(v -> {
            String oldPwd = edtPwdOld.getText().toString().trim();
            String newPwd = edtPwdNew.getText().toString().trim();
            String confirmPwd = edtPwdConfirm.getText().toString().trim();
            if (oldPwd.isEmpty()) {
                Toasty.warning(PasswordActivity.this, R.string.please_enter_current_password_first, Toast.LENGTH_SHORT, true).show();
                return;
            }
            if (newPwd.isEmpty()) {
                Toasty.warning(PasswordActivity.this, R.string.please_enter_new_password_first, Toast.LENGTH_SHORT, true).show();
                return;
            }
            if (confirmPwd.isEmpty()) {
                Toasty.warning(PasswordActivity.this, R.string.please_enter_confirm_password_first, Toast.LENGTH_SHORT, true).show();
                return;
            }
            if (!oldPwd.equals(mmkv.decodeString(MmkvKey.password))) {
                Toasty.error(PasswordActivity.this, R.string.err_password, Toast.LENGTH_SHORT, true).show();
                return;
            }
            if (!newPwd.equals(confirmPwd)) {
                Toasty.error(PasswordActivity.this, R.string.err_new_password, Toast.LENGTH_SHORT, true).show();
                return;
            }
            // TODO: 2021/7/30 调用修改密码
            mmkv.encode(MmkvKey.password, newPwd);
            finish();
        });
    }

    private void initView() {
        ivBack = (ImageView) findViewById(R.id.iv_back);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        edtPwdOld = (EditText) findViewById(R.id.edt_pwd_old);
        edtPwdNew = (EditText) findViewById(R.id.edt_pwd_new);
        edtPwdConfirm = (EditText) findViewById(R.id.edt_pwd_confirm);
        btnModify = (Button) findViewById(R.id.btn_modify);
    }
}