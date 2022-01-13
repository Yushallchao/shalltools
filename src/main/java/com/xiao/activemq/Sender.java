package com.xiao.activemq;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class Sender {
    private static final int SEND_NUMBER = 10;

    public void connectQueue(String tcp, String queuename, String message){
        // ConnectionFactory  连接工厂是用户创建连接的对象.
        ConnectionFactory connectionFactory;
        // Connection 连接工厂创建一个jms connection
        Connection connection = null;
        // Session 是生产和消费的一个单线程上下文。会话用于创建消息的生产者，消费者和消息。会话提供了一个事务性的上下文。
        Session session;
        // Destination 目的地是客户用来指定他生产消息的目标还有他消费消息的来源的对象.
        Destination destination;
        MessageProducer producer;
        // TextMessage message;
        connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER,
                ActiveMQConnection.DEFAULT_PASSWORD, tcp);
        try {
            connection = connectionFactory.createConnection();
            // 开启连接
            connection.start();

            session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);

            destination = session.createQueue(queuename);

            producer = session.createProducer(destination);

            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
            //发送
            sendMessage(session, producer,message);
            session.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != connection)
                    connection.close();
            } catch (Throwable ignore) {
            }
        }

    }

    public static void sendMessage(Session session, MessageProducer producer, String msg){
        for (int i = 1; i <= SEND_NUMBER; i++) {
            TextMessage message = null;
            try {
                message = session.createTextMessage(msg);
                System.out.println("发送消息" + msg + i);
                producer.send(message);
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
    }
}
