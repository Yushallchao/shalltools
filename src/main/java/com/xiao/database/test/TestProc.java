package com.xiao.database.test;

import com.xiao.database.DBConn;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import oracle.jdbc.OracleTypes;

public class TestProc {
    public static void main(String[] args) {
        // TODO Auto-generated method stub

        testProduct();
    }

    public static void testProduct() {

        String url = "jdbc:oracle:thin:@192.168.150.199:1521:dbsrv2pdb";

        String userName = "his_all";

        String driver = "oracle.jdbc.driver.OracleDriver";

        String userPwd = "HIS_ALL";
        try {
            //加载驱动
            //Class.forName(driver);
            DBConn dbConn = new DBConn();
            //获得连接
            Connection conn = dbConn.getConn(driver,url,userName,userPwd);

            //创建存储过程的对象
            CallableStatement cstmt = conn.prepareCall("{call BS15035P (?,?,?,?,?,?,?,?,?,?)}");
            String value = " '4928759-1' , ' @' , '  @' , '  @' , 0 , ' @ ' , ' @ ' , 37 , ' @ ' ,";
            value= value.replace("'", "").replace(" ", "");
            String[] setvalue = value.split(",");
            int valueNum = setvalue.length;
            System.out.println(valueNum);

            for (int i = 0; i < valueNum; i++) {
                System.out.println(setvalue[i]);
                cstmt.setString(i+1, setvalue[i].replace("@", ""));
            }


            //对out参数，声明
            cstmt.registerOutParameter(10, OracleTypes.CURSOR);

            cstmt.execute();//

            ResultSet rs = (ResultSet) cstmt.getObject(10);

            //***************************************//
            //cstmt = conn.prepareCall("{call selePro}");
            //ResultSet rs=cstmt.executeQuery();

            while(rs.next()){
                ResultSetMetaData rsmd = rs.getMetaData();
                System.out.println(rs.getString(1));
                System.out.println(rsmd.getColumnCount());
/*			     String Stu = rs.getString("StuID");
			     String name = rs.getString("StuName");
			     String add = rs.getString("StuAddress");

			     System.out.println ("学号:"+"     "+"姓名:"+"     "+"地址");
			     System.out.println (Stu+"     "+name+"   "+add);

*/			    }
            cstmt.close();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
