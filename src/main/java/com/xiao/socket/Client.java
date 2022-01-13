package com.xiao.socket;

import java.io.*;
import java.net.Socket;

public class Client {
    public void socketClient() throws IOException {
        // 向服务器发出请求建立连接
        Socket socket = new Socket("localhost", 4700);
        // 从socket中获取输入输出流
        InputStream inputStream = socket.getInputStream();
        OutputStream outputStream = socket.getOutputStream();

        PrintWriter pw = new PrintWriter(outputStream);
        String line = "<a>hello</a>";
        pw.println(line);
        pw.flush();

        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        String result = br.readLine();
        System.out.println(result);

        //输入
        BufferedReader sin = new BufferedReader(new InputStreamReader(System.in));
        line = sin.readLine();
        //如果该字符串为 "bye"，则停止循环
        while(!line.equals("bye")){
            //向客户端输出该字符串
            pw.println(line);
            //刷新输出流，使Client马上收到该字符串
            pw.flush();
            //在系统标准输出上打印读入的字符串
            System.out.println("Client:"+line);
            //从Server读入一字符串，并打印到标准输出上
            System.out.println("Server:"+br.readLine());
            //从系统标准输入读入一字符串
            line=sin.readLine();
        }


        inputStream.close();
        outputStream.close();
        socket.close();

    }
}
