package com.xiao.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class DosUtil {
    /**
     * @description 操作dos
     * @author Yxc
     * @date 2021/11/22 10:51
     * @param
     * @return void
     */
    public static void commond(){
        try {
            Runtime rt = Runtime.getRuntime();
            Process pr = rt.exec("cmd /c ping www.baidu.com && dir");
            //Process pr = rt.exec("D:\\xunlei\\project.aspx");
            BufferedReader input = new BufferedReader(new InputStreamReader(pr.getInputStream(), "GBK"));
            String line = null;
            while ((line = input.readLine()) != null) {
                System.out.println(line);
            }
            int exitVal = pr.waitFor();
            System.out.println("Exited with error code " + exitVal);

        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
        }
    }
}
