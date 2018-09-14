package com.taotao.jedis;

import org.junit.Test;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

import java.util.HashSet;
import java.util.Set;

/**
 * @description: 测试jedis
 * @author:
 * @create: 2018-08-28 21:14
 **/
public class TestJedis {

    @Test
    public void testJedis() {
        //创建连接
        Jedis jedis = new Jedis("192.168.25.133", 6379);

        String str = jedis.get("name3");
        System.out.println("str: " + str);

        String hstr = jedis.hget("name", "name1");
        System.out.println("hstr: " + hstr);

        jedis.close();
    }

    @Test
    public void testJedisPool() {
        //创建连接池
        JedisPool jedisPool = new JedisPool("192.168.25.133", 6379);
        //从连接池中获取Jedis
        Jedis jedis = jedisPool.getResource();

        String str=jedis.get("name3");
        System.out.println("str: " + str);

        jedis.close();
        jedisPool.close();
    }

    @Test
    public void testJedisCluster(){
        Set<HostAndPort> nodes=new HashSet<>();
        nodes.add(new HostAndPort("192.168.25.133",7001));
        nodes.add(new HostAndPort("192.168.25.133",7002));
        nodes.add(new HostAndPort("192.168.25.133",7003));
        nodes.add(new HostAndPort("192.168.25.133",7004));
        nodes.add(new HostAndPort("192.168.25.133",7005));
        nodes.add(new HostAndPort("192.168.25.133",7006));
        JedisCluster jedisCluster=new JedisCluster(nodes);

        String name1=jedisCluster.get("name1");
        String name=jedisCluster.get("name");
        System.out.println("name1:"+name1);
        System.out.println("name:"+name);

        jedisCluster.hset("school","class","安徽建筑大学-1班");
        String str=jedisCluster.hget("school","class");
        System.out.println("str:"+str);

        jedisCluster.close();
    }

}