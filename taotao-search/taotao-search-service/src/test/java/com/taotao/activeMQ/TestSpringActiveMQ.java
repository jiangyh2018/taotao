package com.taotao.activeMQ;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;

/**
 * @description: 测试接收消息
 * @author:
 * @create: 2018-09-18 20:38
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring/applicationContext-*.xml"})
public class TestSpringActiveMQ{
    @Test
    public void onMessage() throws IOException {
        System.in.read();
    }
}