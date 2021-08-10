package com.xlk.miketeamanage.model;

import com.blankj.utilcode.util.LogUtils;
import com.xlk.miketeamanage.helper.DataDisposal;

import static com.xlk.miketeamanage.helper.DataDisposal.addZero;
import static com.xlk.miketeamanage.helper.DataDisposal.hexXOR;
import static com.xlk.miketeamanage.helper.DataDisposal.high8HexString;
import static com.xlk.miketeamanage.helper.DataDisposal.int2HexString;
import static com.xlk.miketeamanage.helper.DataDisposal.low8HexString;

/**
 * @author Created by xlk on 2021/8/5.
 * @desc 串口的命令 16进制数
 */
public class Command {
    //帧尾
    private static final String commandEnd = "CFFC";

    /**
     * 点击来一杯，成功后返回该值
     */
    public static final String success_drink_one = "AFAA05" + commandEnd;
    /**
     * 发送制冷启动/停止温度，成功后返回该值
     */
    public static final String success_temperature_temp = "A8AA02" + commandEnd;
    /**
     * 发送清洗指令，成功后返回该值
     */
    public static final String success_product = "AEAA04" + commandEnd;
    /**
     * 发送启动/停止制冷指令后，如果成功则返回该值
     */
    public static final String success_temperature = "ADAA07" + commandEnd;
    /**
     * 设置清洗速度成功
     */
    public static final String success_clean_speed = "A7AA0D" + commandEnd;
    /**
     * 设置出料速度成功
     */
    public static final String success_discharge_speed = "A5AA0F" + commandEnd;


    /**
     * 设置制冷温度指令
     *
     * @param startTemp 制冷启动温度
     * @param stopTemp  制冷停止温度
     * @return 16进制的指令
     */
    public static String temperatureSetting(int startTemp, int stopTemp) {
        String hexValue1 = addZero(DataDisposal.int2HexString(startTemp));
        String hexValue2 = addZero(DataDisposal.int2HexString(stopTemp));
        //前三位的异或值
        String xor = hexXOR(hexXOR("A8", hexValue1), hexValue2);
        String result = "A8" + hexValue1 + hexValue2 + xor + commandEnd;
        LogUtils.e("设置温度指令 制冷启动温度=" + startTemp + ",制冷停止温度=" + stopTemp + ",最后拼接得到=" + result);
        return result;
    }

    /**
     * 启动或关闭制冷
     *
     * @param isOpen "AA"启动，"00"关闭
     * @return 16进制的指令
     */
    public static String temperature(String isOpen) {
        String xorResult = hexXOR("AD", isOpen);
        String result = "AD" + isOpen + xorResult + commandEnd;
        LogUtils.e("启动或关闭制冷 isOpen=" + isOpen + ",最后拼接得到=" + result);
        return result;
    }

    /**
     * 清洗速度
     *
     * @param speed 清洗速度
     * @return 16进制的指令
     */
    public static String cleanSpeed(int speed) {
        String hexValue1 = addZero(DataDisposal.int2HexString(speed));
        String value1 = hexXOR("A7", hexValue1);
        String result = "A7" + hexValue1 + value1 + commandEnd;
        LogUtils.e("设置清洗速度指令 speed=" + speed + ",hexValue1=" + hexValue1 + ",最后拼接得到=" + result);
        return result;
    }

    /**
     * 出料速度
     *
     * @param speed 出料速度
     * @return 16进制的指令
     */
    public static String dischargeSpeed(int speed) {
        String hexValue1 = addZero(DataDisposal.int2HexString(speed));
        String value1 = hexXOR("A5", hexValue1);
        String result = "A5" + hexValue1 + value1 + commandEnd;
        LogUtils.e("设置出料速度指令 speed=" + speed + ",hexValue1=" + hexValue1 + ",最后拼接得到=" + result);
        return result;
    }

    /**
     * 清洗水缸
     * 前三个字节异或结果
     *
     * @param number "11"第一缸，"22"第二缸，"33"第三缸，"44"第四缸， "55"水泵,"AA"全部缸不包括水泵
     * @param isOpen "AA"开启清洗，"00"停止清洗
     * @return 16进制指令
     */
    public static String cleanWaterTank(String number, String isOpen) {
        String xor = hexXOR(hexXOR("AE", number), isOpen);
        String result = "AE" + number + isOpen + xor + commandEnd;
        LogUtils.e("清洗指令 number=" + number + ",isOpen=" + isOpen + ",最后拼接得到=" + result);
        return result;
    }

    /**
     * 点击来一杯
     * 控制版返回：AF+AA+前两字节异或结果+CF+FC;
     *
     * @return 来一杯的16进制指令
     */
    public static String drink(int capacity1, int capacity2, int capacity3, int capacity4, int waterPumpCapacity) {
        String high1 = high8HexString(capacity1);
        String low1 = low8HexString(capacity1);
        String high2 = high8HexString(capacity2);
        String low2 = low8HexString(capacity2);
        String high3 = high8HexString(capacity3);
        String low3 = low8HexString(capacity3);
        String high4 = high8HexString(capacity4);
        String low4 = low8HexString(capacity4);
        String highWaterPump = high8HexString(waterPumpCapacity);
        String lowWaterPump = high8HexString(waterPumpCapacity);
        //前11位数的异或结果
        String xor = hexXOR(hexXOR(hexXOR(hexXOR(hexXOR(hexXOR(hexXOR(hexXOR(hexXOR(hexXOR("AF", high1), low1), high2), low2), high3), low3), high4), low4), highWaterPump), lowWaterPump);
        String result = "AF"
                + high1 + low1
                + high2 + low2
                + high3 + low3
                + high4 + low4
                + highWaterPump + lowWaterPump
                + xor
                + commandEnd;
        LogUtils.i("点击来一杯指令：" + result);
        return result;
    }


    /**
     * 查询温度命令
     *
     * @return 16进制字符串指令
     */
    public static String queryTemp() {
        return "A19736CFFC";
    }

}
