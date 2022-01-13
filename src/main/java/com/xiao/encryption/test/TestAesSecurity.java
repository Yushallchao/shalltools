package com.xiao.encryption.test;

import com.xiao.encryption.AESSecurity;

public class TestAesSecurity {
    public static void main(String[] args){
        String aesStr = "你好！";
        //AES 为16bytes. DES 为8bytes
        String aesKey = "1234567812345678";
        AESSecurity aesSecurity = new AESSecurity();
        //加密
        System.out.println(aesSecurity.aesEncrypt(aesStr,aesKey));
        String encryptedStr = "GPgtghjxwE03FDeI+sOxzg==";
        //解密
        System.out.println(aesSecurity.aesDecrypt(encryptedStr,aesKey));

    }
}
