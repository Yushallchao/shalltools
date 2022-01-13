package com.xiao.activemq;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class Receiver {
    public void receiveMessage(String tcp, String queuename){

        ConnectionFactory connectionFactory;
        Connection connection = null;
        Session session;
        Destination destination;
        //消费方
        MessageConsumer consumer;
        connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER,
                ActiveMQConnection.DEFAULT_PASSWORD, tcp);
        try {
            connection = connectionFactory.createConnection();
            connection.start();

            session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);

            destination = session.createQueue(queuename);
            consumer = session.createConsumer(destination);
            while (true) {
                TextMessage message = (TextMessage) consumer.receive(100000);
                if (null != message) {
                    System.out.println("接收消息：" + message.getText());
                } else {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != connection) {
                    connection.close();
                }
            } catch (Throwable ignore) {
            }
        }
    }

}
