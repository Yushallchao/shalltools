package com.xiao.encryption;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Security {
    /**
     * @description MD5加密
     * @author Yxc
     * @date 2021/11/19 16:56
     * @param
     * @return java.lang.String
     */
    public String MD5Encrypt(String clearStr){
        try {
            MessageDigest messageDigest =MessageDigest.getInstance("MD5");

            byte[] inputByteArray = clearStr.getBytes();

            messageDigest.update(inputByteArray);

            byte[] resultByteArray = messageDigest.digest();

            return byteArrayToHex(resultByteArray);

        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    /**
     * @description 转16进制 加密
     * @author Yxc
     * @date 2021/11/19 16:53
     * @param byteArray
     * @return java.lang.String
     */
    protected static String byteArrayToHex(byte[] byteArray) {

        // 字符补全
        char[] hexDigits = {'0','1','2','3','4','5','6','7','8','9', 'A','B','C','D','E','F' };

        char[] resultCharArray = new char[byteArray.length * 2];

        int index = 0;
        for (byte b : byteArray) {
            resultCharArray[index++] = hexDigits[b>>> 4 & 0xf];
            resultCharArray[index++] = hexDigits[b& 0xf];
        }
        return new String(resultCharArray);

    }

    /**
     * @description MD5解密
     * @author Yxc
     * @date 2021/11/19 16:57
     * @param encodeStr
     * @return java.lang.String
     */
    public String MD5Decrypt(String encodeStr) {
        char[] a = encodeStr.toCharArray();
        for (int i = 0; i < a.length; i++) {
            a[i] = (char) (a[i] ^ 't');
        }
        String s = new String(a);
        return s;
    }

}
