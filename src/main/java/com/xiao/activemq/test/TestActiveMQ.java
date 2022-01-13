package com.xiao.activemq.test;

import com.xiao.activemq.Receiver;
import com.xiao.activemq.Sender;

public class TestActiveMQ {
    public static void main( String[] args )
    {
        String message = "<b>hello world!你好，世界！</b>";
        String tcp = "tcp://127.0.0.1:61616";
        String queuename = "QMGR.S01_1";
        Sender sender = new Sender();
        Receiver receiver = new Receiver();

        sender.connectQueue(tcp,queuename,message);

        receiver.receiveMessage(tcp,queuename);


    }
}
