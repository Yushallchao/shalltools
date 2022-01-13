package com.xiao.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DBConn {

    public Connection getConn(String driver, String url, String user, String password) {
        Connection conn = null;
        try {
            Class.forName(driver);// 加载驱动程序
            System.out.println("开始尝试连接数据库！");
            //url = "jdbc:oracle:thin:@127.0.0.1:1521:DBname";// 127.0.0.1是本机地址
            //user = 用户名,系统默认的账户名
            //password =  你安装时选设置的密码
            conn = DriverManager.getConnection(url, user, password);//获取连接
            if(conn != null){
                String conMili = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss.SSS").format(new Date());
                System.out.println("连接"+conMili);
                System.out.println("连接成功！");
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return conn;

    }
}
