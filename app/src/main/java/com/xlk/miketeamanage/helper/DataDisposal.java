package com.xlk.miketeamanage.helper;

import com.blankj.utilcode.util.LogUtils;

/**
 * @author Created by xlk on 2021/8/7.
 * @desc 数据处理工具类
 */
public class DataDisposal {

    private static final char[] HEX_DIGITS_UPPER =
            {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
    private static final char[] HEX_DIGITS_LOWER =
            {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    /**
     * 16进制字符串转10进制数
     *
     * @param inHex 16进制字符
     * @return eg: 0x0A -> 10
     */
    public static int hexString2Int(String inHex) {
        return Integer.parseInt(inHex, 16);
    }

    /**
     * 10进制数转16进制字符串
     *
     * @param intHex 10进制数 一般是int型数据 eg:10
     * @return eg: 10 -> 0x0A
     */
    public static String int2HexString(int intHex) {
        String st = Integer.toHexString(intHex).toUpperCase();
        if(st.length()==1){
            return "0"+st;
        }
        return String.format("%2s",st).replaceAll(" ","0");
    }

    /**
     * 16进制数互相异或
     *
     * @param hex1 16进制字符
     * @param hex2 16进制字符
     * @return 结果也是16进制的数
     */
    public static String hexXOR(String hex1, String hex2) {
        byte[] bytes1 = hexToByteArray(hex1);
        byte[] bytes2 = hexToByteArray(hex2);
        byte[] bResult = new byte[bytes1.length];
        for (int i = 0; i < bytes1.length; i++) {
            bResult[i] = (byte) (bytes1[i] ^ bytes2[i]);
        }
        return bytes2HexString(bResult);
    }

    /**
     * 将byte数组转为16进制字符串
     */
    public static String bytes2HexString(byte[] bytes) {
        return bytes2HexString(bytes, true);
    }

    public static String bytes2HexString(final byte[] bytes, boolean isUpperCase) {
        if (bytes == null) return "";
        char[] hexDigits = isUpperCase ? HEX_DIGITS_UPPER : HEX_DIGITS_LOWER;
        int len = bytes.length;
        if (len <= 0) return "";
        char[] ret = new char[len << 1];
        for (int i = 0, j = 0; i < len; i++) {
            ret[j++] = hexDigits[bytes[i] >> 4 & 0x0f];
            ret[j++] = hexDigits[bytes[i] & 0x0f];
        }
        return new String(ret);
    }

    /**
     * hex字符串转byte数组
     * <p>e.g. hexString2Bytes("00A8") returns { 0, (byte) 0xA8 }</p>
     *
     * @param hexString The hex string.
     * @return the bytes
     */
    public static byte[] hexString2Bytes(String hexString) {
        if (hexString == null || hexString.isEmpty()) return new byte[0];
        int len = hexString.length();
        if (len % 2 != 0) {
            hexString = "0" + hexString;
            len = len + 1;
        }
        char[] hexBytes = hexString.toUpperCase().toCharArray();
        byte[] ret = new byte[len >> 1];
        for (int i = 0; i < len; i += 2) {
            ret[i >> 1] = (byte) (hex2Dec(hexBytes[i]) << 4 | hex2Dec(hexBytes[i + 1]));
        }
        return ret;
    }

    private static int hex2Dec(final char hexChar) {
        if (hexChar >= '0' && hexChar <= '9') {
            return hexChar - '0';
        } else if (hexChar >= 'A' && hexChar <= 'F') {
            return hexChar - 'A' + 10;
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * hex字符串转byte数组
     *
     * @param inHex 待转换的Hex字符串
     * @return 转换后的byte数组结果
     */
    public static byte[] hexToByteArray(String inHex) {
        int hexlen = inHex.length();
        byte[] result;
        if (hexlen % 2 == 1) {
            //奇数
            hexlen++;
            result = new byte[(hexlen / 2)];
            inHex = "0" + inHex;
        } else {
            //偶数
            result = new byte[(hexlen / 2)];
        }
        int j = 0;
        for (int i = 0; i < hexlen; i += 2) {
            result[j] = hexToByte(inHex.substring(i, i + 2));
            j++;
        }
        return result;
    }

    //Hex字符串转byte
    public static byte hexToByte(String inHex) {
        return (byte) Integer.parseInt(inHex, 16);
    }

    //1字节转2个Hex字符
    public static String byte2Hex(Byte inByte) {
        return String.format("%02x", new Object[]{inByte}).toUpperCase();
    }

    //字节数组转转hex字符串
    public static String byteArr2Hex(byte[] inBytArr) {
        StringBuilder strBuilder = new StringBuilder();
        for (byte valueOf : inBytArr) {
            strBuilder.append(byte2Hex(Byte.valueOf(valueOf)));
            strBuilder.append(" ");
        }
        return strBuilder.toString();
    }


    /**
     * 如果当前数是一个个位数，则在前面添加一个'0'
     *
     * @param hexString 16进制数
     * @return 最少两位数的16进制的值
     */
    public static String addZero(String hexString) {
        if (hexString.length() == 1) {
            return "0" + hexString;
        }
        return hexString;
    }


    /* **** ** 高8位和低8位计算： https://www.zhihu.com/question/454843238  ** **** */
    public static String high8HexString(int num) {
//        int high8 = num / 256;
        int high8 = (num & 0xff00) >> 8;
//        int high8 = num >> 8;
        /* **** **  https://blog.csdn.net/u014141461/article/details/116856369  ** **** */
//        int high8 = (byte) (num & 0xff);
//        LogUtils.e(num+"高8位："+high8);
        return int2HexString(high8);
    }

    public static String low8HexString(int num) {
//        int low8 = num % 256;
        int low8 = num & 0x00ff;
//        int low8 = num & 0x00ff;
        /* **** **  https://blog.csdn.net/u014141461/article/details/116856369  ** **** */
//        int low8 = (byte) ((num & 0xff00) >> 8);
//        LogUtils.e(num+"低8位："+low8);
        return int2HexString(low8);
    }

    /**
     * 将两个16进制的高8位和低8位还原成float类型的实际数值
     *
     * @param high8 高8位字符 e.g: "01"
     * @param low8  低8位字符 e.g: "2C"
     * @return float类型的实际数值
     */
    public static float combineFloat(String high8, String low8) {
        int high = DataDisposal.hexString2Int(high8);
        int low = DataDisposal.hexString2Int(low8);
        return ((float) (high * 256) + low) / 10;
    }
    /**
     * 将两个16进制的高8位和低8位还原成float类型的实际数值
     *
     * @param high8 高8位字符 e.g: "01"
     * @param low8  低8位字符 e.g: "2C"
     * @return int类型的实际数值
     */
    public static int combineInt(String high8, String low8) {
        int high = DataDisposal.hexString2Int(high8);
        int low = DataDisposal.hexString2Int(low8);
        return ((high * 256) + low) / 10;
    }

    /**
     * 将有小数位的数*10
     *
     * @param n float类型数据 （该项目中小数位最多固定为1位）
     * @return int型数
     */
    public static int float2int(float n) {
        if (hasDecimals(n)) {
            return (int) (n * 10);
        }
        return (int) n;
    }

    public static boolean hasDecimals(float value) {
        //如果n减去它的整数部分不等于0，那么n有小数
        return value - (int) value != 0;
    }
}
