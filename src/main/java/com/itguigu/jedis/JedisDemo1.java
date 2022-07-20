package com.itguigu.jedis;

import org.junit.Test;
import redis.clients.jedis.Jedis;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapters;
import java.util.List;
import java.util.Set;

public class JedisDemo1 {
    //连接redis测试
    public static void main(String[] args) {
        Jedis jedis = new Jedis("192.168.122.3",6379);
        String ping = jedis.ping();
        System.out.println(ping);
    }

    //操作key，string
    @Test
    public void demo01(){
        Jedis jedis = new Jedis("192.168.122.3",6379);
        //添加key
        jedis.set("name", "ZLP");
        //获取key
        String name = jedis.get("name");
        System.out.println(name);//ZLP


        //操作字符串类型
        //mset() 同时添加多个key，value
        jedis.mset("k1","v1","k2","v2");
        List<String> mget = jedis.mget("k1", "k2");
        System.out.println(mget);//[v1,v2]

        //相当于keys * 获取所有键信息
        Set<String> keys = jedis.keys("*");
        for (String key : keys) {
            System.out.println(key);
        }
    }

    //操作list集合
    @Test
    public void demo02(){
        Jedis jedis = new Jedis("192.168.122.3",6379);

        //向list集合当中存储，lpush代表从左边开始添加，要注意添加的顺序
        jedis.lpush("key1", "p1","p2","p3");//key1:p3,p2,p1
        //list： 0，-1将所有值都取出
        List<String> key1 = jedis.lrange("key1", 0, -1);
        for (String s : key1) {
            System.out.println(s);
        }
    }

    //操作set集合
    @Test
    public void demo03(){
        Jedis jedis = new Jedis("192.168.122.3",6379);

        jedis.sadd("name1","p1","p2");
        Set<String> name = jedis.smembers("name1");
        for (String s : name) {
            System.out.println(s);
        }
    }

    //操作hash集合
    @Test
    public void demo04(){
        Jedis jedis = new Jedis("192.168.122.3",6379);
        jedis.hset("users", "age","20");
        String hget = jedis.hget("users", "age");
        System.out.println(hget);
    }

    //操作zset集合
    @Test
    public void demo05(){
        Jedis jedis = new Jedis("192.168.122.3",6379);

        //根据score来排序
        jedis.zadd("china", 100d, "shanghai");
        Set<String> china = jedis.zrange("china", 0, -1);
        System.out.println(china);
    }


}
