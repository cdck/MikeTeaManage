package com.xlk.miketeamanage.model;

import android.os.Bundle;

/**
 * @author Created by xlk on 2021/7/30.
 * @desc
 */
public class MmkvKey {
    /**
     * 产品一
     */
    public static final String product_a = "product_a";
    /**
     * 产品二
     */
    public static final String product_b = "product_b";
    /**
     * 管理员密码
     */
    public static final String admin_pwd = "admin_pwd";
    public static final String default_admin_pwd = "123456";
    /**
     * 默认密码最大长度
     */
    public static final int admin_pwd_length = 8;
    /**
     * 锁屏密码
     */
    public static final String pin_pwd = "pin_pwd";
    public static final String default_pin_pwd = "1234";
    /**
     * 默认密码最大长度
     */
    public static final int pin_pwd_length = 4;
    /**
     * 屏保倒计时
     */
    public static final String screen_saver_time = "screen_saver_time";
    /**
     * 默认值：单位秒
     */
    public static final int default_screen_saver_time = 10;
    /**
     * 制冷启动温度
     */
    public static final String refrigeration_launch_temp = "refrigeration_launch_temp";
    public static final int default_refrigeration_launch_temp = 0;
    /**
     * 制冷停止温度
     */
    public static final String refrigeration_stop_temp = "refrigeration_stop_temp";
    public static final int default_refrigeration_stop_temp = 0;
    /**
     * 清洗速度
     */
    public static final String clean_speed = "clean_speed";
    public static final int default_clean_speed = 0;
    /**
     * 出料速度
     */
    public static final String discharge_speed = "discharge_speed";
    public static final int default_discharge_speed = 0;
    /**
     * 产品名称（产品设置相关的key都是根据产品进行独立存储的）
     * MMKV.mmkvWithID()
     * 当前已固定的产品：{@link MmkvKey#product_a}.和{@link MmkvKey#product_b}.
     *
     */
    public static final String product_name = "product_name";
    /**
     * 当前产品是否开启
     */
    public static final String product_open = "product_open";
    /**
     * 产品图
     */
    public static final String product_img = "product_img";
    public static final String product_capacity_a = "product_capacity_a";
    public static final String product_capacity_b = "product_capacity_b";
    public static final String product_capacity_c = "product_capacity_c";
    public static final String product_capacity_d = "product_capacity_d";
    /**
     * 水泵容量
     */
    public static final String water_pump_capacity = "water_pump_capacity";
}
