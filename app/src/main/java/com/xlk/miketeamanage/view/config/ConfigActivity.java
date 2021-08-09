package com.xlk.miketeamanage.view.config;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.PhoneUtils;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.tencent.mmkv.MMKV;
import com.xlk.miketeamanage.R;
import com.xlk.miketeamanage.base.BaseActivity;
import com.xlk.miketeamanage.model.Command;
import com.xlk.miketeamanage.model.MmkvKey;
import com.xlk.miketeamanage.view.clean.CleanActivity;
import com.xlk.miketeamanage.view.PasswordActivity;
import com.xlk.miketeamanage.view.ProductSetupActivity;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import es.dmoral.toasty.Toasty;

import static com.xlk.miketeamanage.helper.DataDisposal.float2int;

/**
 * @author xlk
 */
public class ConfigActivity extends BaseActivity<ConfigPresenter> implements ConfigContract.View, View.OnClickListener {

    private ImageView ivBack;
    private TextView tvTitle;
    private RelativeLayout rlDevId;
    private TextView tvDevId;
    private TextView tvDevNumber;
    private RelativeLayout rlStartupTemperature;
    private TextView tvStartupTemperature;
    private RelativeLayout rlStoppingTemperature;
    private TextView tvStoppingTemperature;
    private RelativeLayout rlStartCooling;
    private SwitchMaterial cbStartCooling;
    private RelativeLayout rlCleaningSpeed;
    private TextView tvCleaningSpeed;
    private RelativeLayout rlDischargeSpeed;
    private TextView tvDischargeSpeed;
    private RelativeLayout rlModifyPassword;
    private RelativeLayout rlProductsASet, rlProductsBSet;
    private RelativeLayout rlCleaningModule;
    private SwitchMaterial cbOpenDebug;
    private RelativeLayout rlExitApp;
    private RelativeLayout rlDevNumber;
    private RelativeLayout rlOpenDebug;
    private RelativeLayout rl_check_update;
    private TextView tv_version;
    private RelativeLayout rl_modify_pin_password;
    private MMKV mmkv;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_config;
    }

    @Override
    protected void initView() {
        ivBack = (ImageView) findViewById(R.id.iv_back);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        rlDevId = (RelativeLayout) findViewById(R.id.rl_dev_id);
        tvDevId = (TextView) findViewById(R.id.tv_dev_id);
        rlDevNumber = (RelativeLayout) findViewById(R.id.rl_dev_number);
        tvDevNumber = (TextView) findViewById(R.id.tv_dev_number);
        rlStartupTemperature = (RelativeLayout) findViewById(R.id.rl_startup_temperature);
        tvStartupTemperature = (TextView) findViewById(R.id.tv_startup_temperature);
        rlStoppingTemperature = (RelativeLayout) findViewById(R.id.rl_stopping_temperature);
        tvStoppingTemperature = (TextView) findViewById(R.id.tv_stopping_temperature);
        rlStartCooling = (RelativeLayout) findViewById(R.id.rl_start_cooling);
        cbStartCooling = (SwitchMaterial) findViewById(R.id.cb_start_cooling);
        rlCleaningSpeed = (RelativeLayout) findViewById(R.id.rl_cleaning_speed);
        tvCleaningSpeed = (TextView) findViewById(R.id.tv_cleaning_speed);
        rlDischargeSpeed = (RelativeLayout) findViewById(R.id.rl_discharge_speed);
        tvDischargeSpeed = (TextView) findViewById(R.id.tv_discharge_speed);
        rlModifyPassword = (RelativeLayout) findViewById(R.id.rl_modify_password);
        rl_modify_pin_password = (RelativeLayout) findViewById(R.id.rl_modify_pin_password);
        rlProductsASet = (RelativeLayout) findViewById(R.id.rl_products_a_set);
        rlProductsBSet = (RelativeLayout) findViewById(R.id.rl_products_b_set);
        rlCleaningModule = (RelativeLayout) findViewById(R.id.rl_cleaning_module);
        rl_check_update = (RelativeLayout) findViewById(R.id.rl_check_update);
        tv_version = (TextView) findViewById(R.id.tv_version);
        rlOpenDebug = (RelativeLayout) findViewById(R.id.rl_open_debug);
        cbOpenDebug = (SwitchMaterial) findViewById(R.id.cb_open_debug);
        rlExitApp = (RelativeLayout) findViewById(R.id.rl_exit_app);
        ivBack.setOnClickListener(this);
        rlDevId.setOnClickListener(this);
        rlDevNumber.setOnClickListener(this);
        rlStartupTemperature.setOnClickListener(this);
        rlStoppingTemperature.setOnClickListener(this);
        rlStartCooling.setOnClickListener(this);
        rlCleaningSpeed.setOnClickListener(this);
        rlDischargeSpeed.setOnClickListener(this);
        rlModifyPassword.setOnClickListener(this);
        rl_modify_pin_password.setOnClickListener(this);
        rlProductsASet.setOnClickListener(this);
        rlProductsBSet.setOnClickListener(this);
        rlCleaningModule.setOnClickListener(this);
        rl_check_update.setOnClickListener(this);
        rlOpenDebug.setOnClickListener(this);
        rlExitApp.setOnClickListener(this);
    }

    @Override
    protected ConfigPresenter initPresenter() {
        return new ConfigPresenter(this);
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mmkv = MMKV.defaultMMKV();
        initDataFromMmkv();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, 1);
        } else {
            String serial = PhoneUtils.getSerial();
            LogUtils.e("本机的序列号：" + serial);
            tvDevId.setText(serial);
        }
        tv_version.setText(AppUtils.getAppVersionName());
        presenter.initialSerialPort();
    }

    private void initDataFromMmkv() {
        tvStartupTemperature.setText(String.valueOf(mmkv.decodeInt(MmkvKey.refrigeration_launch_temp, MmkvKey.default_refrigeration_launch_temp)));
        tvStoppingTemperature.setText(String.valueOf(mmkv.decodeInt(MmkvKey.refrigeration_stop_temp, MmkvKey.default_refrigeration_stop_temp)));
        tvCleaningSpeed.setText(String.valueOf(mmkv.decodeInt(MmkvKey.clean_speed, MmkvKey.default_clean_speed)));
        tvDischargeSpeed.setText(String.valueOf(mmkv.decodeInt(MmkvKey.discharge_speed, MmkvKey.default_discharge_speed)));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                String serial = PhoneUtils.getSerial();
                LogUtils.e("本机的序列号：" + serial);
                tvDevId.setText(serial);
            }
        }
    }

    @Override
    public void updateTemperature() {
        cbStartCooling.setChecked(!cbStartCooling.isChecked());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //返回
            case R.id.iv_back: {
                finish();
                break;
            }
            //设备编号
            case R.id.rl_dev_id: {
                break;
            }
            //设备名称
            case R.id.rl_dev_number: {
                devNumberDialog();
                break;
            }
            //制冷启动温度
            case R.id.rl_startup_temperature: {
                startupTemperatureDialog(true);
                break;
            }
            //制冷停止温度
            case R.id.rl_stopping_temperature: {
                startupTemperatureDialog(false);
                break;
            }
            //启动制冷
            case R.id.rl_start_cooling: {//启动制冷
                boolean checked = !cbStartCooling.isChecked();
                if (checked) {
                    //启动制冷
                    presenter.addCommands(Command.temperature("AA"));
                } else {
                    //关闭制冷
                    presenter.addCommands(Command.temperature("00"));
                }
                break;
            }
            //清洗速度
            case R.id.rl_cleaning_speed: {//清洗速度
                cleaningSpeedDialog();
                break;
            }
            //出料速度
            case R.id.rl_discharge_speed: {
                dischargeSpeedDialog();
                break;
            }
            //检查更新
            case R.id.rl_check_update: {
                break;
            }
            //更改管理员密码
            case R.id.rl_modify_password: {
                Intent intent = new Intent(ConfigActivity.this, PasswordActivity.class);
                intent.putExtra(PasswordActivity.extra_modify_admin_pwd, true);
                startActivity(intent);
                break;
            }
            //更改锁屏密码
            case R.id.rl_modify_pin_password: {
                Intent intent = new Intent(ConfigActivity.this, PasswordActivity.class);
                intent.putExtra(PasswordActivity.extra_modify_admin_pwd, false);
                startActivity(intent);
                break;
            }
            //产品一设置
            case R.id.rl_products_a_set: {
                Intent intent = new Intent(ConfigActivity.this, ProductSetupActivity.class);
                intent.putExtra(ProductSetupActivity.extra_product, MmkvKey.product_a);
                startActivity(intent);
                break;
            }
            //产品二设置
            case R.id.rl_products_b_set: {
                Intent intent = new Intent(ConfigActivity.this, ProductSetupActivity.class);
                intent.putExtra(ProductSetupActivity.extra_product, MmkvKey.product_b);
                startActivity(intent);
                break;
            }
            //清洗模块
            case R.id.rl_cleaning_module: {
                startActivity(new Intent(ConfigActivity.this, CleanActivity.class));
                break;
            }
            //打开调试
            case R.id.rl_open_debug: {
                cbOpenDebug.setChecked(!cbOpenDebug.isChecked());
                break;
            }
            //退出APP
            case R.id.rl_exit_app: {
                presenter.exit();
                AppUtils.exitApp();
                break;
            }
            default:
                break;
        }
    }

    private void devNumberDialog() {
        new MaterialDialog.Builder(this)
                .title(R.string.equipment_number)
                .inputRange(0, 512)//可输入的字符数区间
                .cancelable(false)//设置点击外部不可隐藏弹框
                .alwaysCallInputCallback()//开启一直监听输入
                .input(R.string.please_enter_content, R.string.waarring_input_prefill, (dialog, input) -> {
                    LogUtils.e("onInput 输入的内容：" + input);
                    if (input.length() <= 512 && input.length() >= 0) {
                        dialog.getActionButton(DialogAction.POSITIVE).setEnabled(true);
                    } else {
                        dialog.getActionButton(DialogAction.POSITIVE).setEnabled(false);
                    }
                })
                .positiveText(R.string.define)
                .onPositive((dialog, which) -> {
                    LogUtils.e("点击确定");
                    String string = dialog.getInputEditText().getText().toString();
                    tvDevNumber.setText(string);
                })
                .negativeText(R.string.cancel)
                .show();
    }

    /**
     * 展示修改制冷温度弹框
     *
     * @param isStart =true 制冷启动，=false 制冷停止
     */
    private void startupTemperatureDialog(boolean isStart) {
        new MaterialDialog.Builder(this)
                .title(isStart ? R.string.set_cooling_start_temperature : R.string.set_cooling_stop_temperature)
                .inputRange(0, 5)
                .inputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL)
                .cancelable(false)
                .alwaysCallInputCallback()//开启一直监听输入
                .input(R.string.enter_number_hint, R.string.waarring_input_prefill, false, new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                        LogUtils.e("onInput 输入的内容：" + input);
                        if (input.length() > 0) {
                            String string = input.toString();
                            float value = Float.parseFloat(string);
                            if (value > 100) {
                                Toasty.warning(ConfigActivity.this, R.string.the_maximum_value_that_can_be_entered_is_100, Toasty.LENGTH_SHORT, true).show();
//                                Toast.makeText(ConfigActivity.this, R.string.the_maximum_value_that_can_be_entered_is_100, Toast.LENGTH_SHORT).show();
                                dialog.getInputEditText().setText(String.valueOf(100));
                                dialog.getInputEditText().setSelection(String.valueOf(100).length());
                            }
                            if (string.contains(".") && !string.endsWith(".")) {
                                String pre = string.substring(0, string.indexOf("."));
                                String end = string.substring(string.indexOf(".") + 1, string.indexOf(".") + 2);
                                String substring = string.substring(string.indexOf(".") + 1);
                                if (substring.length() > 1) {
                                    Toasty.warning(ConfigActivity.this, R.string.only_one_decimal_place_is_allowed, Toasty.LENGTH_SHORT, true).show();
//                                    Toast.makeText(ConfigActivity.this, R.string.only_one_decimal_place_is_allowed, Toast.LENGTH_SHORT).show();
                                    dialog.getInputEditText().setText(pre + "." + end);
                                    dialog.getInputEditText().setSelection((pre + "." + end).length());
                                }
                            }
                        }
                        if (input.length() <= 5 && input.length() >= 0) {
                            dialog.getActionButton(DialogAction.POSITIVE).setEnabled(true);
                        } else {
                            dialog.getActionButton(DialogAction.POSITIVE).setEnabled(false);
                        }
                    }
                })
                .positiveText(R.string.define)
                .negativeText(R.string.cancel)
                .onPositive((dialog, which) -> {
                    LogUtils.e("点击确定");
                    String string = dialog.getInputEditText().getText().toString();
                    int value = float2int(Float.parseFloat(string));
                    if (isStart) {
                        tvStartupTemperature.setText(string);
                        mmkv.encode(MmkvKey.refrigeration_launch_temp, value);
                        int v = mmkv.decodeInt(MmkvKey.refrigeration_stop_temp);
                        presenter.addCommands(Command.temperatureSetting(value, v));
                    } else {
                        tvStoppingTemperature.setText(string);
                        mmkv.encode(MmkvKey.refrigeration_stop_temp, value);
                        int v = mmkv.decodeInt(MmkvKey.refrigeration_launch_temp);
                        presenter.addCommands(Command.temperatureSetting(v, value));
                    }
                })
                .show();
    }

    /**
     * 展示修改清洗速度弹框
     */
    private void cleaningSpeedDialog() {
        new MaterialDialog.Builder(this)
                .title(R.string.set_cleaning_speed)
                .inputRange(0, 5)
                .inputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL)
                .cancelable(false)
                .alwaysCallInputCallback()//开启一直监听输入
                .input(R.string.enter_number_hint, R.string.waarring_input_prefill, false, new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                        LogUtils.e("onInput 输入的内容：" + input);
                        if (input.length() > 0) {
                            String string = input.toString();
                            float value = Float.parseFloat(string);
                            if (value >= 30) {
                                Toasty.warning(ConfigActivity.this, R.string.the_maximum_value_that_can_be_entered_is_100, Toasty.LENGTH_SHORT, true).show();
//                                Toast.makeText(ConfigActivity.this, R.string.the_maximum_value_that_can_be_entered_is_100, Toast.LENGTH_SHORT).show();
                                dialog.getInputEditText().setText(String.valueOf(30));
                                dialog.getInputEditText().setSelection(String.valueOf(30).length());
                            }
                            if (string.contains(".") && !string.endsWith(".")) {
                                String pre = string.substring(0, string.indexOf("."));
                                String end = string.substring(string.indexOf(".") + 1, string.indexOf(".") + 2);
                                String substring = string.substring(string.indexOf(".") + 1);
                                if (substring.length() > 1) {
                                    Toasty.warning(ConfigActivity.this, R.string.only_one_decimal_place_is_allowed, Toasty.LENGTH_SHORT, true).show();
//                                    Toast.makeText(ConfigActivity.this, R.string.only_one_decimal_place_is_allowed, Toast.LENGTH_SHORT).show();
                                    dialog.getInputEditText().setText(pre + "." + end);
                                    dialog.getInputEditText().setSelection((pre + "." + end).length());
                                }
                            }
                        }
                        if (input.length() <= 5 && input.length() >= 0) {
                            dialog.getActionButton(DialogAction.POSITIVE).setEnabled(true);
                        } else {
                            dialog.getActionButton(DialogAction.POSITIVE).setEnabled(false);
                        }
                    }
                })
                .positiveText(R.string.define)
                .negativeText(R.string.cancel)
                .onPositive((dialog, which) -> {
                    LogUtils.e("点击确定");
                    String string = dialog.getInputEditText().getText().toString();
                    tvCleaningSpeed.setText(string);
                    int value = float2int(Float.parseFloat(string));
                    mmkv.encode(MmkvKey.clean_speed, value);
                    presenter.addCommands(Command.cleanSpeed(value));
                })
                .show();
    }

    /**
     * 展示修改出料速度弹框
     */
    private void dischargeSpeedDialog() {
        new MaterialDialog.Builder(this)
                .title(R.string.set_discharge_speed)
                .inputRange(0, 5)
                .inputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL)
                .cancelable(false)
                .alwaysCallInputCallback()//开启一直监听输入
                .input(R.string.enter_number_hint, R.string.waarring_input_prefill, false, new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                        LogUtils.e("onInput 输入的内容：" + input);
                        if (input.length() > 0) {
                            String string = input.toString();
                            float value = Float.parseFloat(string);
                            if (value >= 30) {
                                Toasty.warning(ConfigActivity.this, R.string.the_maximum_value_that_can_be_entered_is_100, Toasty.LENGTH_SHORT, true).show();
//                                Toast.makeText(ConfigActivity.this, R.string.the_maximum_value_that_can_be_entered_is_100, Toast.LENGTH_SHORT).show();
                                dialog.getInputEditText().setText(String.valueOf(30));
                                dialog.getInputEditText().setSelection(String.valueOf(30).length());
                            }
                            if (string.contains(".") && !string.endsWith(".")) {
                                String pre = string.substring(0, string.indexOf("."));
                                String end = string.substring(string.indexOf(".") + 1, string.indexOf(".") + 2);
                                String substring = string.substring(string.indexOf(".") + 1);
                                if (substring.length() > 1) {
                                    Toasty.warning(ConfigActivity.this, R.string.only_one_decimal_place_is_allowed, Toasty.LENGTH_SHORT, true).show();
//                                    Toast.makeText(ConfigActivity.this, R.string.only_one_decimal_place_is_allowed, Toast.LENGTH_SHORT).show();
                                    dialog.getInputEditText().setText(pre + "." + end);
                                    dialog.getInputEditText().setSelection((pre + "." + end).length());
                                }
                            }
                        }
                        if (input.length() <= 5 && input.length() >= 0) {
                            dialog.getActionButton(DialogAction.POSITIVE).setEnabled(true);
                        } else {
                            dialog.getActionButton(DialogAction.POSITIVE).setEnabled(false);
                        }
                    }
                })
                .positiveText(R.string.define)
                .negativeText(R.string.cancel)
                .onPositive((dialog, which) -> {
                    LogUtils.e("点击确定");
                    String string = dialog.getInputEditText().getText().toString();
                    tvDischargeSpeed.setText(string);
                    int value = float2int(Float.parseFloat(string));
                    mmkv.encode(MmkvKey.discharge_speed, value);
                    presenter.addCommands(Command.dischargeSpeed(value));
                })
                .show();
    }

}