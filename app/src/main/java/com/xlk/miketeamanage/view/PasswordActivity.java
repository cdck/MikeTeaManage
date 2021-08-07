package com.xlk.miketeamanage.view;

import android.os.Bundle;
import android.text.InputFilter;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.mmkv.MMKV;
import com.xlk.miketeamanage.R;
import com.xlk.miketeamanage.helper.MaxLengthInputFilter;
import com.xlk.miketeamanage.model.MmkvKey;

import androidx.appcompat.app.AppCompatActivity;
import es.dmoral.toasty.Toasty;

public class PasswordActivity extends AppCompatActivity {

    public static final String extra_modify_admin_pwd = "isModifyAdminPwd";
    private ImageView ivBack;
    private TextView tvTitle;
    private EditText edtPwdOld;
    private EditText edtPwdNew;
    private EditText edtPwdConfirm;
    private Button btnModify;
    private boolean isModifyAdminPwd;
    private MMKV mmkv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        //屏幕常亮
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        initView();
        mmkv = MMKV.defaultMMKV();
        isModifyAdminPwd = getIntent().getBooleanExtra(extra_modify_admin_pwd, true);
        tvTitle.setText(isModifyAdminPwd ? getString(R.string.modify_admin_password) : getString(R.string.modify_pin_password));
        ivBack.setOnClickListener(v -> finish());
        int maxLength = isModifyAdminPwd ? MmkvKey.admin_pwd_length : MmkvKey.pin_pwd_length;
        edtPwdOld.setFilters(new InputFilter[]{new MaxLengthInputFilter(this, maxLength)});
        edtPwdNew.setFilters(new InputFilter[]{new MaxLengthInputFilter(this, maxLength)});
        edtPwdConfirm.setFilters(new InputFilter[]{new MaxLengthInputFilter(this, maxLength)});
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
            if (isModifyAdminPwd) {//判断管理员密码
                if (!oldPwd.equals(mmkv.decodeString(MmkvKey.admin_pwd, MmkvKey.default_admin_pwd))) {
                    Toasty.error(PasswordActivity.this, R.string.err_password, Toast.LENGTH_SHORT, true).show();
                    return;
                }
            } else {//判断锁屏密码
                if (!oldPwd.equals(mmkv.decodeString(MmkvKey.pin_pwd, MmkvKey.default_pin_pwd))) {
                    Toasty.error(PasswordActivity.this, R.string.err_password, Toast.LENGTH_SHORT, true).show();
                    return;
                }
            }
            if (!newPwd.equals(confirmPwd)) {
                Toasty.error(PasswordActivity.this, R.string.err_new_password, Toast.LENGTH_SHORT, true).show();
                return;
            }
            // TODO: 2021/7/30 调用修改密码
            mmkv.encode(isModifyAdminPwd ? MmkvKey.admin_pwd : MmkvKey.pin_pwd, newPwd);
            Toasty.success(PasswordActivity.this, R.string.modify_successfully, Toast.LENGTH_SHORT, true).show();
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