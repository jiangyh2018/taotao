package com.taotao.manager;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.Test;

import javax.jms.*;
import java.io.IOException;

/**
 **/
public class TestActionMQ {

    /**
     * 测试发送Queue队列消息
     *
     * @throws JMSException
     */
    @Test
    public void testProducerQueue() throws JMSException {
//      第一步：创建ConnectionFactory对象，需要指定服务端ip及端口号。
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.25.133:61616");
//      第二步：使用ConnectionFactory对象创建一个Connection对象。
        Connection connection = connectionFactory.createConnection();
//      第三步：开启连接，调用Connection对象的start方法。
        connection.start();
//      第四步：使用Connection对象创建一个Session对象。
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
//      第五步：使用Session对象创建一个Destination对象（topic、queue），此处创建一个Queue对象。
        Queue queue = session.createQueue("demo-queue");
//      第六步：使用Session对象创建一个Producer对象。
        MessageProducer producer = session.createProducer(queue);
//      第七步：创建一个Message对象，创建一个TextMessage对象。
        TextMessage message = session.createTextMessage("hello ActiveMQ,this is my 2 message.");
//      第八步：使用Producer对象发送消息。
        producer.send(message);
//      第九步：关闭资源。
        producer.close();
        session.close();
        connection.close();
    }

    /**
     * 测试接收Queue队列消息
     */
    @Test
    public void testConsumerQueue() throws JMSException, IOException {
//      第一步：创建一个ConnectionFactory对象。
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.25.133:61616");
//      第二步：从ConnectionFactory对象中获得一个Connection对象。
        Connection connection = connectionFactory.createConnection();
//      第三步：开启连接。调用Connection对象的start方法。
        connection.start();
//      第四步：使用Connection对象创建一个Session对象。
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
//      第五步：使用Session对象创建一个Destination对象。和发送端保持一致queue，并且队列的名称一致。
        Queue queue = session.createQueue("demo-queue");
//      第六步：使用Session对象创建一个Consumer对象。
        MessageConsumer consumer = session.createConsumer(queue);
//      第七步：接收消息。
        consumer.setMessageListener(message -> {
            //      第八步：打印消息。
            TextMessage textMessage = (TextMessage) message;
            try {
                System.out.println(textMessage.getText());
            } catch (JMSException e) {
                e.printStackTrace();
            }
        });
        //等待键盘输入
        System.in.read();
//      第九步：关闭资源
        consumer.close();
        session.close();
        connection.close();
    }

    /**
     * 测试发送Topic消息
     */
    @Test
    public void testProducerTopic() throws JMSException {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.25.133:61616");
        Connection connection = connectionFactory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //创建Destination
        Topic topic = session.createTopic("demo-topic");
        //创建Produce的对象
        MessageProducer producer = session.createProducer(topic);
        //创建消息
        TextMessage textMessage = session.createTextMessage("hello topic mq2");
        //发送消息
        producer.send(textMessage);
        //关闭资源
        producer.close();
        session.close();
        connection.close();
    }

    /**
     * 测试接收Topic消息
     */
    @Test
    public void testConsumerTopic() throws JMSException, IOException {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.25.133:61616");
        Connection connection = connectionFactory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //创建Destination
        Topic topic = session.createTopic("demo-topic");
        //创建Consumer
        MessageConsumer consumer = session.createConsumer(topic);
        //接收消息
        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                //打印消息
                TextMessage textMessage = (TextMessage) message;
                try {
                    System.out.println(textMessage.getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });
        //等待
        System.in.read();
        //关闭资源
        consumer.close();
        session.close();
        connection.close();
    }

    /**
     * 测试接收Topic消息
     */
    @Test
    public void testConsumerTopic2() throws JMSException, IOException {
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.25.133:61616");
        Connection connection = connectionFactory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //创建Destination
        Topic topic = session.createTopic("demo-topic");
        //创建Consumer
        MessageConsumer consumer = session.createConsumer(topic);
        //接收消息
        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                //打印消息
                TextMessage textMessage = (TextMessage) message;
                try {
                    System.out.println(textMessage.getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });
        //等待
        System.in.read();
        //关闭资源
        consumer.close();
        session.close();
        connection.close();
    }


}