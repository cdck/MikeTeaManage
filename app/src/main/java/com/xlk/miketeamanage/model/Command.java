package com.xlk.miketeamanage.model;

import com.blankj.utilcode.util.LogUtils;
import com.xlk.miketeamanage.helper.DataDisposal;

/**
 * @author Created by xlk on 2021/8/5.
 * @desc 串口的命令 16进制数
 */
public class Command {
    //帧头
    public static final int header = 0xAF;
    // A8
    public static final int temp = 0xA81006;

    /**
     * @param startTemp 制冷启动温度
     * @param stopTemp  制冷停止温度
     */
    public static void temp(int startTemp, int stopTemp) {
        String a8 = DataDisposal.hexXORhex("A8", DataDisposal.IntToHex(startTemp));
        String s = DataDisposal.hexXORhex(a8, DataDisposal.IntToHex(stopTemp));
        String a = "A81006" + s + "CFFC";
        LogUtils.e("测试结果="+a);
    }
}
