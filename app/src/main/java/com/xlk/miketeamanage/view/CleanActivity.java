package com.xlk.miketeamanage.view;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.switchmaterial.SwitchMaterial;
import com.xlk.miketeamanage.R;

import androidx.appcompat.app.AppCompatActivity;

public class CleanActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clean);
        //屏幕常亮
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        initView();
        tvTitle.setText(getString(R.string.clean));

    }

    private void initView() {
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

        cbAll.setOnCheckedChangeListener(this);
        cbA.setOnCheckedChangeListener(this);
        cbB.setOnCheckedChangeListener(this);
        cbC.setOnCheckedChangeListener(this);
        cbD.setOnCheckedChangeListener(this);
        cbWaterPump.setOnCheckedChangeListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back: {
                finish();
                break;
            }
            case R.id.rl_cb_all: {
                cbAll.setChecked(!cbAll.isChecked());
                break;
            }
            case R.id.rl_cb_a: {
                cbA.setChecked(!cbA.isChecked());
                break;
            }
            case R.id.rl_cb_b: {
                cbB.setChecked(!cbB.isChecked());
                break;
            }
            case R.id.rl_cb_c: {
                cbC.setChecked(!cbC.isChecked());
                break;
            }
            case R.id.rl_cb_d: {
                cbD.setChecked(!cbD.isChecked());
                break;
            }
            case R.id.rl_cb_water_pump: {
                cbWaterPump.setChecked(!cbWaterPump.isChecked());
                break;
            }
            default:
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.cb_all: {
                break;
            }
            case R.id.cb_a: {
                break;
            }
            case R.id.cb_b: {
                break;
            }
            case R.id.cb_c: {
                break;
            }
            case R.id.cb_d: {
                break;
            }
            case R.id.cb_water_pump: {
                break;
            }
            default:
                break;
        }
    }
}