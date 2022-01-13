package com.xiao.jmeter.sdk;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.ewell.mq.queue.QueueTools;
import com.ibm.mq.MQException;
import com.ibm.mq.MQQueueManager;

public class SDK {

    public static String putReqAndGetResp(String qmgr,String queue) {

        QueueTools queueTools = new QueueTools();
        // 队列管理器实例
        MQQueueManager queueManager = null;
        // 消息Id
        String msgId = null;
        // 请求数据
        String reqMsg = "<ESBEntry>"+
                "test"+
                "</ESBEntry>";
        String ss = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss.SSS").format(new Date());
        System.out.println("开始请求消息："+ss);
        // 响应数据IO
        String respMsg = null;
        try {

            // 连接MQ，获取队列管理器实例，该实例如果不调用方法进行断开操作可长期保持连接。连接函数会自动读取配置文件标签名为“QMGR.SXX”下的配置信息进行连接。，
            queueManager = queueTools.connect(qmgr);
            String cs = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss.SSS").format(new Date());
            System.out.println("连接："+cs);
            // 发送请求消息
            msgId= queueTools.putMsg(queueManager, queue, reqMsg);
            // 获取响应消//
            //respMsg= queueTools.getMsgById(queueManager, "PS10039", msgId, 5); //3-waitInterval
            System.out.println("响应消息："+msgId);

        } catch (MQException e) {
            // 2033表示队列中没有消息
            if (e.reasonCode == 2033) {
            } else {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //断开MQ连接
            if (null != queueManager) {
                try {
                    queueManager.disconnect();
                } catch (MQException e) {
                    e.printStackTrace();
                }
            }
        }

        return msgId;
    }

}
