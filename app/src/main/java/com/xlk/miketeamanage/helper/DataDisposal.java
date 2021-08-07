package com.xlk.miketeamanage.helper;

import static top.keepempty.sph.library.DataConversion.hexToByte;

/**
 * @author Created by xlk on 2021/8/7.
 * @desc 数据处理工具类
 */
public class DataDisposal {

    /**
     * 16进制转10进制数
     *
     * @param inHex 16进制字符
     * @return eg: A6 -> 166
     */
    public static int HexToInt(String inHex) {
        return Integer.parseInt(inHex, 16);
    }

    /**
     * 10进制转16进制
     *
     * @param intHex 10进制数 一般是int型数据 eg:10
     * @return eg: 10 -> A
     */
    public static String IntToHex(int intHex) {
        return Integer.toHexString(intHex);
    }

    /**
     * 16进制数互相异或
     *
     * @param hex1 16进制字符
     * @param hex2 16进制字符
     * @return 结果也是16进制的数
     */
    public static String hexXORhex(String hex1, String hex2) {
        byte[] bytes1 = hexToByteArray(hex1);
        byte[] bytes2 = hexToByteArray(hex2);
        byte[] bResult = new byte[bytes1.length];
        for (int i = 0; i < bytes1.length; i++) {
            bResult[i] = (byte) (bytes1[i] ^ bytes2[i]);
        }
        return bytesToHexString(bResult);
    }

    /**
     * 将byte数组转为16进制字符串
     */
    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (byte aSrc : src) {
            int v = aSrc & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv.toUpperCase());
        }
        return stringBuilder.toString();
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
}
