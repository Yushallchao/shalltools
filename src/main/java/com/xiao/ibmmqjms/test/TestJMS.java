package com.xiao.ibmmqjms.test;

import com.xiao.ibmmqjms.JMSQueue;

import javax.jms.JMSException;
import javax.naming.NamingException;

public class TestJMS {
    public static void main(String[] args){
        String url = "file:/D:/JMSWorks"; //.bindings路径
        String iQueue = "PUT";
        String oQueue = "GET";
        String factoryName = "HISFactory";
        String message = "<a>你好</a>";

        JMSQueue jmsQueue = new JMSQueue();
        try {
            jmsQueue.connetQueue(url,iQueue,oQueue,factoryName,message);
        } catch (NamingException e) {
            e.printStackTrace();
        } catch (JMSException e) {
            e.printStackTrace();
        }

    }
}
