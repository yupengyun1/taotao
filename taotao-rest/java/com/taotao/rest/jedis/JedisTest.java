package com.taotao.rest.jedis;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class JedisTest {

	@Test
	public void testJedisSingle() {
		Jedis jedis = new Jedis("192.168.253.129",6379);
		jedis.set("key1", "jedis test");
		String string = jedis.get("key1");
		System.out.println(string);
		jedis.close();
	}
	@Test
	public void testJedisPool() {
		JedisPool jedis = new JedisPool("192.168.253.129",6379);
		Jedis resource = jedis.getResource();
		String string = resource.get("key1");
		System.out.println(string);
		resource.close();
	}
	@Test
	public void testJedisSpringPool() {
		ClassPathXmlApplicationContext jedis = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
		JedisPool resource = (JedisPool) jedis.getBean("redisClient");
		Jedis string = resource.getResource();
		String string2 = string.get("key1");
		System.out.println(string2);
		resource.close();
	}
}
