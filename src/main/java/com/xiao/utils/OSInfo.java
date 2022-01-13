package com.xiao.utils;

import java.net.InetAddress;
import java.util.Map;
import java.util.Properties;

public class OSInfo {

    public static void getOSinfo() {
        Runtime runtime = Runtime.getRuntime();
        Properties props = System.getProperties();
        try {
            Map<String,String> map = System.getenv();
            InetAddress address = InetAddress.getLocalHost();
            System.out.println(address.getHostAddress());
            System.out.println(address.getHostName());
            System.out.println(props.getProperty("os.name"));
            System.out.println(runtime.totalMemory());
            System.out.println(map.get("COMPUTERNAME"));
            System.out.println(props.getProperty("os.version"));
            System.out.println(props.getProperty("user.dir")); //用户路径


        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
