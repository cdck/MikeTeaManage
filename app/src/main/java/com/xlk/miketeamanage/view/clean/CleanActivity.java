package com.xlk.miketeamanage.view.clean;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.switchmaterial.SwitchMaterial;
import com.xlk.miketeamanage.R;
import com.xlk.miketeamanage.base.BaseActivity;
import com.xlk.miketeamanage.model.Command;

import androidx.appcompat.app.AppCompatActivity;

public class CleanActivity extends BaseActivity<CleanPresenter> implements CleanContract.View, View.OnClickListener {

    private ImageView ivBack;
    private TextView tvTitle;
    private RelativeLayout rlCbAll;
    private SwitchMaterial cbAll;
    private RelativeLayout rlCbA;
    private SwitchMaterial cbA;
    private RelativeLayout rlCbB;
    private SwitchMaterial cbB;
    private RelativeLayout rlCbC;
    private SwitchMaterial cbC;
    private RelativeLayout rlCbD;
    private SwitchMaterial cbD;
    private RelativeLayout rlCbWaterPump;
    private SwitchMaterial cbWaterPump;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_clean;
    }

    @Override
    protected CleanPresenter initPresenter() {
        return new CleanPresenter(this);
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        tvTitle.setText(getString(R.string.clean));
        presenter.initialSerialPort();
    }

    @Override
    protected void initView() {
        ivBack = (ImageView) findViewById(R.id.iv_back);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        rlCbAll = (RelativeLayout) findViewById(R.id.rl_cb_all);
        cbAll = (SwitchMaterial) findViewById(R.id.cb_all);
        rlCbA = (RelativeLayout) findViewById(R.id.rl_cb_a);
        cbA = (SwitchMaterial) findViewById(R.id.cb_a);
        rlCbB = (RelativeLayout) findViewById(R.id.rl_cb_b);
        cbB = (SwitchMaterial) findViewById(R.id.cb_b);
        rlCbC = (RelativeLayout) findViewById(R.id.rl_cb_c);
        cbC = (SwitchMaterial) findViewById(R.id.cb_c);
        rlCbD = (RelativeLayout) findViewById(R.id.rl_cb_d);
        cbD = (SwitchMaterial) findViewById(R.id.cb_d);
        rlCbWaterPump = (RelativeLayout) findViewById(R.id.rl_cb_water_pump);
        cbWaterPump = (SwitchMaterial) findViewById(R.id.cb_water_pump);
        ivBack.setOnClickListener(this);
        rlCbAll.setOnClickListener(this);
        rlCbA.setOnClickListener(this);
        rlCbB.setOnClickListener(this);
        rlCbC.setOnClickListener(this);
        rlCbD.setOnClickListener(this);
        rlCbWaterPump.setOnClickListener(this);
    }

    @Override
    public void updateWaterTankAll(boolean open) {
        cbAll.setChecked(open);
        updateWaterTankA(open);
        updateWaterTankB(open);
        updateWaterTankC(open);
        updateWaterTankD(open);
    }

    @Override
    public void updateWaterTankA(boolean open) {
        cbA.setChecked(open);
    }

    @Override
    public void updateWaterTankB(boolean open) {
        cbB.setChecked(open);
    }

    @Override
    public void updateWaterTankC(boolean open) {
        cbC.setChecked(open);
    }

    @Override
    public void updateWaterTankD(boolean open) {
        cbD.setChecked(open);
    }

    @Override
    public void updateWaterPump(boolean open) {
        cbWaterPump.setChecked(open);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back: {
                finish();
                break;
            }
            case R.id.rl_cb_all: {
                boolean checked = !cbAll.isChecked();
                if (checked) {
                    presenter.addCommands(Command.cleanWaterTank("AA", "AA"));
//                    presenter.addCommands("AEAAAAAECFFC");
                } else {
                    presenter.addCommands(Command.cleanWaterTank("AA", "00"));
//                    presenter.addCommands("AE1100BFCFFC");
                }
//                cbAll.setChecked(checked);
                break;
            }
            case R.id.rl_cb_a: {
                boolean checked = !cbA.isChecked();
                if (checked) {
                    presenter.addCommands(Command.cleanWaterTank("11", "AA"));
//                    presenter.addCommands("AE11AA15CFFC");
                } else {
                    presenter.addCommands(Command.cleanWaterTank("11", "00"));
//                    presenter.addCommands("AE1100BFCFFC");
                }
//                cbA.setChecked(checked);
                break;
            }
            case R.id.rl_cb_b: {
                boolean checked = !cbB.isChecked();
                if (checked) {
                    presenter.addCommands(Command.cleanWaterTank("22", "AA"));
                } else {
                    presenter.addCommands(Command.cleanWaterTank("22", "00"));
                }
//                cbB.setChecked(checked);
                break;
            }
            case R.id.rl_cb_c: {
                boolean checked = !cbC.isChecked();
                if (checked) {
                    presenter.addCommands(Command.cleanWaterTank("33", "AA"));
                } else {
                    presenter.addCommands(Command.cleanWaterTank("33", "00"));
                }
//                cbC.setChecked(checked);
                break;
            }
            case R.id.rl_cb_d: {
                boolean checked = !cbD.isChecked();
                if (checked) {
                    presenter.addCommands(Command.cleanWaterTank("44", "AA"));
                } else {
                    presenter.addCommands(Command.cleanWaterTank("44", "00"));
                }
//                cbD.setChecked(checked);
                break;
            }
            case R.id.rl_cb_water_pump: {
                boolean checked = !cbWaterPump.isChecked();
                if (checked) {
                    presenter.addCommands(Command.cleanWaterTank("55", "AA"));
                } else {
                    presenter.addCommands(Command.cleanWaterTank("55", "00"));
                }
//                cbWaterPump.setChecked(checked);
                break;
            }
            default:
                break;
        }
    }
}