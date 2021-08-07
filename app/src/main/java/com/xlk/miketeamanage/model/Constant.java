package com.xlk.miketeamanage.model;

/**
 * @author Created by xlk on 2021/8/4.
 * @desc
 */
public class Constant {
    /**
     * 串口地址
     */
    public static String path = "dev/ttyS1";
    /**
     * 波特率
     */
    public static final int baudRate = 115200;
    /**
     * 数据位 取值 位 7或 8
     */
    public static final int dataBits = 8;
    /**
     * 停止位 取值 1 或者 2
     */
    public static final int stopBits = 1;
    /**
     * 校验类型
     * O 奇校验位
     * E 偶校验位
     * N 清除校验位
     */
    public static final char parity = 'n';

    public static final int bus_send = 1;
    public static final int bus_receive = 2;
    public static final int bus_complete = 3;
}
