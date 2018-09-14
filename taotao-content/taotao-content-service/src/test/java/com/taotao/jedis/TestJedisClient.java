package com.taotao.jedis;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @description: 测试jedisClient的配置
 * @author:
 * @create: 2018-08-31 21:04
 **/
public class TestJedisClient {
    @Test
    public void demo(){
        ApplicationContext applicationContext=new ClassPathXmlApplicationContext("classpath:spring/applicationContext-jedis.xml");
        JedisClient jedisClient=applicationContext.getBean(JedisClient.class);
        System.out.println(jedisClient.set("age","25"));
        System.out.println(jedisClient.get("age"));
    }

}