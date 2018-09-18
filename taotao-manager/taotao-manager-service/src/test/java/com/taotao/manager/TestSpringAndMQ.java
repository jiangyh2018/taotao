package com.taotao.manager;

import org.apache.activemq.command.ActiveMQTopic;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import javax.jms.*;

/**
 * @description: 测试spring整合ActiveMQ
 * @author:
 * @create: 2018-09-16 22:30
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/applicationContext-*.xml"})
public class TestSpringAndMQ {
    @Autowired
    private JmsTemplate jmsTemplate;
    @Autowired
    private Queue queueDestination;
    @Resource(name = "topicDestination")
    private ActiveMQTopic topic;

    /**
     * 测试发送topic消息
     */
    @Test
    public void testTopicProducer() {
        jmsTemplate.send(topic, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage("spring 整合 ActiveMQ 测试 topic producer");
            }
        });
    }
    /**
     * 测试发送Queue消息
     */
    @Test
    public void testQueueProducer() {
        jmsTemplate.send(queueDestination, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage("spring 整合 ActiveMQ 测试 queue producer");
            }
        });
    }

    /**
     * 测试接收Queue消息
     * 经测试：这种方式每次运行只能接收一条，还是得用监听事件。
     */
    @Test
    public void testQueueConsumer() throws JMSException {
        Message receive = jmsTemplate.receive(queueDestination);
        TextMessage textMessage = (TextMessage) receive;
        System.out.println(textMessage.getText());
    }

}