package com.xiao.encryption.test;

import com.xiao.encryption.AESSecurity;
import com.xiao.encryption.MD5Security;

public class TestMd5Security {
    public static void main(String[] args){
        String md5Str = "你好！";

        MD5Security md5Security = new MD5Security();
        //加密
        System.out.println(md5Security.MD5Encrypt(md5Str));
        String encryptedStr = "0342B5AFF1E19BFAAA604E265278E317";
        //解密 MD5一次加密，两次解密
        System.out.println(md5Security.MD5Decrypt(md5Security.MD5Decrypt(md5Str)));

    }
}
