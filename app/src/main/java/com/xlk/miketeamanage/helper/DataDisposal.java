package com.xlk.miketeamanage.helper;

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
     * 16进制转10进制数
     *
     * @param inHex 16进制字符
     * @return eg: A6 -> 166
     */
    public static int hexString2Int(String inHex) {
        return Integer.parseInt(inHex, 16);
    }

    /**
     * 10进制转16进制
     *
     * @param intHex 10进制数 一般是int型数据 eg:10
     * @return eg: 10 -> A
     */
    public static String int2HexString(int intHex) {
        return Integer.toHexString(intHex);
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
    public static String Byte2Hex(Byte inByte) {
        return String.format("%02x", new Object[]{inByte}).toUpperCase();
    }

    //字节数组转转hex字符串
    public static String ByteArrToHex(byte[] inBytArr) {
        StringBuilder strBuilder = new StringBuilder();
        for (byte valueOf : inBytArr) {
            strBuilder.append(Byte2Hex(Byte.valueOf(valueOf)));
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
        return int2HexString(num / 256);
    }

    public static String low8HexString(int num) {
        return int2HexString(num % 256);
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
