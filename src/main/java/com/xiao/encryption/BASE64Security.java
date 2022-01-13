package com.xiao.encryption;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;

public class BASE64Security {

  /**
   * @description BASE64加密
   * @author Yxc
   * @date 2021/11/19 16:44
   * @param clearStr
   * @return java.lang.String
   */
    public String BASE64Encrypt(String clearStr){
        byte[] b = null;
        String s = null;
        try {
            b = clearStr.getBytes("utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (b != null) {
            s = new BASE64Encoder().encode(b);
        }
        return s;
    }

    /**
     * @description BASE64解密
     * @author Yxc
     * @date 2021/11/19 16:47
     * @param encodeStr
     * @return java.lang.String
     */
    public String BASE64Decrypt(String encodeStr){
        byte[] b = null;
        String result = null;
        if (encodeStr != null) {
            BASE64Decoder decoder = new BASE64Decoder();
            try {
                b = decoder.decodeBuffer(encodeStr);
                result = new String(b, "utf-8");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
