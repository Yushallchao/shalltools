package com.xiao.encryption.test;

import com.xiao.encryption.BASE64Security;

public class TestBase64Security {
    public static void main(String[] args){
        String base64Str = "你好！";

        BASE64Security base64Security = new BASE64Security();
        //加密
        System.out.println(base64Security.BASE64Encrypt(base64Str));
        String encryptedStr = "5L2g5aW977yB";
        //解密
        System.out.println(base64Security.BASE64Decrypt(encryptedStr));

    }
}
