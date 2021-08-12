package com.xlk.miketeamanage.view.main;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.andrognito.pinlockview.IndicatorDots;
import com.andrognito.pinlockview.PinLockListener;
import com.andrognito.pinlockview.PinLockView;
import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.LogUtils;
import com.hjq.permissions.XXPermissions;
import com.tencent.mmkv.MMKV;
import com.xlk.miketeamanage.R;
import com.xlk.miketeamanage.base.BaseActivity;
import com.xlk.miketeamanage.model.Command;
import com.xlk.miketeamanage.model.MmkvKey;
import com.xlk.miketeamanage.view.PlayViewActivity;
import com.xlk.miketeamanage.view.config.ConfigActivity;

import java.util.Timer;
import java.util.TimerTask;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import es.dmoral.toasty.Toasty;

public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View {
    private final String TAG = "MainActivity-->";
    private ConstraintLayout main_root_view;
    private RelativeLayout a, b;//, c, d;
    private TextView a_temp, b_temp;//, c_temp, d_temp;
    private TextView a_content, b_content;//, c_content, d_content;
    private Button a_btn, b_btn;//, c_btn, d_btn;
    private MMKV mmkv;
    /**
     * 屏保倒计时任务
     */
    private Timer screenSaverTimer;
    private PinLockView pin_lock_view;
    private IndicatorDots indicator_dots;
    private RelativeLayout lock_view;
    private ImageView profile_image;
    private RelativeLayout click_lock_view;
    private MaterialDialog dialog;
    /**
     * 当前是否锁屏
     */
    private boolean isLocked = false;
    /**
     * 产品1和产品2是否开启
     */
    private boolean isOpenA, isOpenB;
    private Timer queryTempTimer;
    private TextView tv_temperature;
    private PopupWindow errPop;
    private TextView tv_other;
    private boolean isShowErrWindow;
    private View errWindowView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        main_root_view = findViewById(R.id.main_root_view);
        tv_temperature = findViewById(R.id.tv_temperature);
        lock_view = findViewById(R.id.lock_view);
        profile_image = findViewById(R.id.profile_image);
        pin_lock_view = findViewById(R.id.pin_lock_view);
        indicator_dots = findViewById(R.id.indicator_dots);
        click_lock_view = findViewById(R.id.click_lock_view);
        click_lock_view.setOnLongClickListener(v -> {
            hideViewAnim(click_lock_view);
            showViewAnim(lock_view);
            return true;
        });

        a = findViewById(R.id.a);
        a_temp = findViewById(R.id.a_temp);
        a_content = findViewById(R.id.a_content);
        a_btn = findViewById(R.id.a_btn);
        b = findViewById(R.id.b);
        b_temp = findViewById(R.id.b_temp);
        b_content = findViewById(R.id.b_content);
        b_btn = findViewById(R.id.b_btn);
//        c = findViewById(R.id.c);
//        c_temp = findViewById(R.id.c_temp);
//        c_content = findViewById(R.id.c_content);
//        c_btn = findViewById(R.id.c_btn);
//        d = findViewById(R.id.d);
//        d_temp = findViewById(R.id.d_temp);
//        d_content = findViewById(R.id.d_content);
//        d_btn = findViewById(R.id.d_btn);
    }

    @Override
    protected MainPresenter initPresenter() {
        return new MainPresenter(this);
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        mmkv = MMKV.defaultMMKV();
        initLockView();
        presenter.initialSerialPort();
        drinkClick();
    }

    private void initLockView() {
        pin_lock_view.attachIndicatorDots(indicator_dots);
        //设置4位密码
        pin_lock_view.setPinLength(4);
        pin_lock_view.setTextColor(ContextCompat.getColor(this, R.color.white));
        indicator_dots.setIndicatorType(IndicatorDots.IndicatorType.FILL);
        pin_lock_view.setPinLockListener(new PinLockListener() {
            @Override
            public void onComplete(String pin) {
                LogUtils.i("onComplete pin=" + pin);
                if (pin.length() == 4) {
                    if (pin.equals(mmkv.decodeString(MmkvKey.pin_pwd, MmkvKey.default_pin_pwd))) {
                        hideViewAnim(lock_view);
                    } else {
                        lockJitterAnim();
                        pin_lock_view.resetPinLockView();
                        Toasty.error(MainActivity.this, R.string.err_password, Toasty.LENGTH_SHORT, true).show();
                    }
                }
            }

            @Override
            public void onEmpty() {
                LogUtils.i("onEmpty");
            }

            @Override
            public void onPinChange(int pinLength, String intermediatePin) {
                LogUtils.i("onPinChange pinLength=" + pinLength + ",intermediatePin=" + intermediatePin);
            }
        });
    }

    /**
     * 动画的方式展示解锁界面
     */
    private void showViewAnim(View view) {
        pin_lock_view.resetPinLockView();
        TranslateAnimation showAnim = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        showAnim.setDuration(300);
        view.startAnimation(showAnim);
        view.setVisibility(View.VISIBLE);
        showAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (view.getId() == R.id.lock_view) {
                    isLocked = true;
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    /**
     * 动画的方式隐藏解锁界面
     */
    private void hideViewAnim(View view) {
        TranslateAnimation hideAnim = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 1.0f);
        hideAnim.setDuration(300);
        view.startAnimation(hideAnim);
        hideAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.setVisibility(View.GONE);
                if (view.getId() == R.id.lock_view) {
                    isLocked = false;
                    //重新开始倒计时屏保
                    startScreenSaverTimer();
                    showViewAnim(click_lock_view);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private void drinkClick() {
        a_btn.setOnClickListener(v -> {
            if (isShowErrWindow) {
                return;
            }
            MMKV productA = MMKV.mmkvWithID(MmkvKey.product_a);
            int product_capacity_a = productA.decodeInt(MmkvKey.product_capacity_a);
            int product_capacity_b = productA.decodeInt(MmkvKey.product_capacity_b);
            int product_capacity_c = productA.decodeInt(MmkvKey.product_capacity_c);
            int product_capacity_d = productA.decodeInt(MmkvKey.product_capacity_d);
            int water_pump_capacity = productA.decodeInt(MmkvKey.water_pump_capacity);
            presenter.addCommands(Command.drink(product_capacity_a, product_capacity_b, product_capacity_c, product_capacity_d, water_pump_capacity));
        });
        b_btn.setOnClickListener(v -> {
            if (isShowErrWindow) {
                return;
            }
            MMKV productB = MMKV.mmkvWithID(MmkvKey.product_b);
            int product_capacity_a = productB.decodeInt(MmkvKey.product_capacity_a);
            int product_capacity_b = productB.decodeInt(MmkvKey.product_capacity_b);
            int product_capacity_c = productB.decodeInt(MmkvKey.product_capacity_c);
            int product_capacity_d = productB.decodeInt(MmkvKey.product_capacity_d);
            int water_pump_capacity = productB.decodeInt(MmkvKey.water_pump_capacity);
            presenter.addCommands(Command.drink(product_capacity_a, product_capacity_b, product_capacity_c, product_capacity_d, water_pump_capacity));
        });
    }

    @Override
    public void updateTemp(float temp) {
        runOnUiThread(() -> {
            tv_temperature.setText(getString(R.string.temperature_, temp + ""));
        });
    }

    @Override
    public void errToast(boolean errA, boolean errB, boolean errC, boolean errD) {
        if (!XXPermissions.hasPermission(this, Manifest.permission.SYSTEM_ALERT_WINDOW)) {
            LogUtils.e("还未拥有悬浮窗权限");
            return;
        }
        String msg = "";
        if (errA) msg += "第一缸饮料！  ";
        if (errB) msg += "第二缸饮料！  ";
        if (errC) msg += "第三缸饮料！  ";
        if (errD) msg += "第四缸饮料！  ";
        if (errPop != null && errPop.isShowing()) {
            if (msg.isEmpty()) {
                //之前有异常，现在没有异常了
                isShowErrWindow = false;
                errPop.dismiss();
            } else {
                //更新异常信息
                tv_other.setText(msg);
            }
            return;
        }
        errWindowView = LayoutInflater.from(this).inflate(R.layout.pop_layout, null);
        tv_other = errWindowView.findViewById(R.id.tv_other);
        tv_other.setText(msg);
        errPop = new PopupWindow(errWindowView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        errPop.setBackgroundDrawable(new BitmapDrawable());
        // 设置popWindow弹出窗体可点击，这句话必须添加，并且是true
        errPop.setTouchable(false);
        // true:设置触摸外面时消失
        errPop.setOutsideTouchable(false);
        errPop.setFocusable(false);
        errPop.showAtLocation(lock_view, Gravity.CENTER, 0, 0);

//        wm.addView(errWindowView,mParams);
        isShowErrWindow = true;
    }

    /**
     * 根据配置显示或隐藏相应产品视图
     */
    private void initViewByMmkv() {
        MMKV product_a = MMKV.mmkvWithID(MmkvKey.product_a);
        isOpenA = product_a.decodeBool(MmkvKey.product_open);
        a.setVisibility(isOpenA ? View.VISIBLE : View.GONE);
        a_content.setText(product_a.decodeString(MmkvKey.product_name));
        String aPath = product_a.decodeString(MmkvKey.product_img);
        if (FileUtils.isFileExists(aPath)) {
            Drawable drawable = Drawable.createFromPath(aPath);
            a.setBackground(drawable);
        }
        a_content.setTextSize(product_a.decodeInt(MmkvKey.product_name_size, 20));
        a_content.setTextColor(product_a.decodeInt(MmkvKey.product_name_color, Color.BLACK));

        MMKV product_b = MMKV.mmkvWithID(MmkvKey.product_b);
        isOpenB = product_b.decodeBool(MmkvKey.product_open);
        b.setVisibility(isOpenB ? View.VISIBLE : View.GONE);
        b_content.setText(product_b.decodeString(MmkvKey.product_name));
        String bPath = product_b.decodeString(MmkvKey.product_img);
        if (FileUtils.isFileExists(bPath)) {
            Drawable drawable = Drawable.createFromPath(bPath);
            b.setBackground(drawable);
        }
        b_content.setTextSize(product_b.decodeInt(MmkvKey.product_name_size, 20));
        b_content.setTextColor(product_b.decodeInt(MmkvKey.product_name_color, Color.BLACK));
    }


    /**
     * 密码错误锁头抖动动画
     */
    private void lockJitterAnim() {
        ObjectAnimator anim = ObjectAnimator.ofFloat(profile_image, "translationX", -20, 20, -10, 10, -5, 5, 0);
        anim.setDuration(300);
        anim.start();
    }

    @Override
    public void onBackPressed() {
    }

    public void jump2set(View view) {
        //展示输入密码弹窗后取消屏保倒计时
        stopScreenSaverTimer();
        dialog = new MaterialDialog.Builder(this)
                .iconRes(R.drawable.ic_admin_warning)
                .title(R.string.warring)
                .content(R.string.warring_conent)
                .cancelable(false)//设置点击外部不可隐藏弹框
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
                        if (dialog.getInputEditText().getText().toString().equals(mmkv.decodeString(MmkvKey.admin_pwd, MmkvKey.default_admin_pwd))) {
                            startActivity(new Intent(MainActivity.this, ConfigActivity.class));
                        } else {
                            Toasty.error(MainActivity.this, R.string.wrong_password, Toast.LENGTH_SHORT, true).show();
                        }
                    }
                })
                .negativeText(R.string.cancel)
                .show();
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                //隐藏输入密码弹窗后取消屏保倒计时
                startScreenSaverTimer();
            }
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                LogUtils.i("按下时取消屏保倒计时");
                //有按下动作时取消定时
                stopScreenSaverTimer();
                break;
            case MotionEvent.ACTION_UP:
                LogUtils.i("抬起时启动屏保倒计时");
                //抬起时启动定时
                startScreenSaverTimer();
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopScreenSaverTimer();
        stopQueryTempTimer();
    }

    @Override
    protected void onResume() {
        initViewByMmkv();
        startScreenSaverTimer();
        startQueryTempTimer();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        stopScreenSaverTimer();
        stopQueryTempTimer();
        super.onDestroy();
    }

    /**
     * 开始倒计时查询温度任务
     */
    private void startQueryTempTimer() {
        LogUtils.i("开始倒计时查询温度任务");
        if (queryTempTimer == null) {
            queryTempTimer = new Timer();
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    //10秒发送一次查询温度的指令
                    LogUtils.i("10秒发送一次查询温度的指令");
                    presenter.addCommands(Command.queryTemp());
                }
            };
            queryTempTimer.schedule(timerTask, 0, 10 * 1000);
        }
    }

    /**
     * 停止查询温度倒计时任务
     */
    private void stopQueryTempTimer() {
        if (queryTempTimer != null) {
            LogUtils.i("停止查询温度倒计时");
            queryTempTimer.cancel();
            queryTempTimer.purge();
            queryTempTimer = null;
        }
    }

    /**
     * 停止屏保倒计时任务
     */
    private void stopScreenSaverTimer() {
        if (screenSaverTimer != null) {
            LogUtils.i("停止屏保倒计时");
            screenSaverTimer.cancel();
            screenSaverTimer.purge();
            screenSaverTimer = null;
        }
    }

    /**
     * 是否可以开启屏保倒计时任务
     *
     * @return 返回true的条件：1.当前未锁屏;2.当前并不在管理员密码弹框界面
     */
    private boolean isCanCountdown() {
        if (dialog != null && dialog.isShowing()) {
            return false;
        } else {
            return !isLocked;
        }
    }

    /**
     * 开始屏保倒计时任务
     */
    private void startScreenSaverTimer() {
        if (!isCanCountdown()) {
            LogUtils.i("无法开启屏保倒计时 isLocked=" + isLocked);
            return;
        }
        LogUtils.i("开启屏保倒计时");
        if (screenSaverTimer == null) {
            screenSaverTimer = new Timer();
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    LogUtils.i("倒计时结束，进入屏保");
                    //清空掉屏保倒计时任务
                    stopScreenSaverTimer();
                    //进入屏保界面
                    startActivity(new Intent(MainActivity.this, PlayViewActivity.class));
                }
            };
            screenSaverTimer.schedule(timerTask, mmkv.decodeInt(MmkvKey.screen_saver_time, MmkvKey.default_screen_saver_time) * 1000);
        }
    }
}