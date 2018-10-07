package com.taotao;

import org.junit.Test;

/**
 * @description:测试强制转换String和基本数据类型
 * @author:
 * @create: 2018-09-18 21:53
 **/
public class TestNull {

    @Test
    public void testDemo() {
        Object obj = null;
        String str = (String) obj;
        System.out.println(str);

        Long l = (Long) obj;
        System.out.println(l);

        Integer i = (Integer) obj;
        System.out.println(i);

        long ll = (Long) obj;
        System.out.println(ll);

        int ii = (Integer) obj;
        System.out.println(ii);
    }
}