package com.xiao.ibmmqjms;

import javax.jms.*;
import javax.naming.NamingException;

public class JMSQueue {

    public static String icf = "com.sun.jndi.fscontext.RefFSContextFactory";

    /**
     *功能描述
     * @author Yxc
     * @date 2021/11/19
     * @param  * @param url .bindings文件路径
     * @param inQueue 发送队列
     * @param outQueue 接收队列
     * @param factoryName .bindings文件中的工厂名称
     * @return void
     */
    public void connetQueue(String url,String inQueue,String outQueue,String factoryName,String message) throws NamingException, JMSException {
        QueueSession session = null;
        QueueConnection connection = null;
        QueueConnectionFactory factory = null;

        QueueSender queueSender = null;
        QueueReceiver queueReceiver= null;

        Queue oQueue = null; // A queue to send messages to
        Queue iQueue = null; // A queue to receive messages from

        try
        {
            JNDIUtil jndiUtil= new JNDIUtil(icf,url);

            factory= jndiUtil.getQueueConnectionFactory(factoryName);//工厂名称
            connection = factory.createQueueConnection();

            // Starts (or restarts) a connection's delivery of incoming messages. Messages will not be
            // received without this call.
            connection.start();

            // Indicate a non-transactional session
            boolean transacted = false;
            session = connection.createQueueSession( transacted, Session.AUTO_ACKNOWLEDGE);

            iQueue= jndiUtil.getQueue(inQueue);//发送队列
            queueSender = session.createSender(iQueue);

            TextMessage oMsg = session.createTextMessage();
            oMsg.setText(message); //发送String消息

            // You can set other message properties as well

            queueSender.send(oMsg);

            //
            oQueue= jndiUtil.getQueue(outQueue);//接收队列
            queueReceiver = session.createReceiver(oQueue);

            Message iMsg = queueReceiver.receive(1000);

            if ( iMsg != null ) {
                System.out.println(((TextMessage) iMsg).getText());
            }
            else {
                System.out.println("No messages in queue ");
            }
        }
        finally
        {
            // Always release resources

            if ( queueReceiver!= null )
                queueReceiver.close();

            if ( queueSender!= null )
                queueSender.close();

            if ( session!= null )
                session.close();

            if ( connection!= null )
                connection.close();


        }


    }
}
