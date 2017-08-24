package com.putaoteng.task6.utils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import net.rubyeye.xmemcached.GetsResponse;
import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.exception.MemcachedException;

public class Cache {
	private MemcachedClient client = null;

	public Cache(){
		@SuppressWarnings("resource")
		ApplicationContext context = new
				ClassPathXmlApplicationContext("xmemcached.xml");
		this.client = (MemcachedClient) context.getBean("memcachedClient");
	}
	
	
	// 添加方法
	public void set(String key, int exp, Object value) {
		try {
			if (!client.set(key, exp, value)) {
				System.err.println("set error, key is" + key + "value is" + value);
			}
		} catch (TimeoutException e) {
			Log.loggerCreate(LogLevel.ERROR, "MemcachedClient operation timeout-----(set)" + e.getMessage());
			e.printStackTrace();
		} catch (InterruptedException e) {
			Log.loggerCreate(LogLevel.ERROR, "MemcachedClient operation interrupted-----(set)" + e.getMessage());
			e.printStackTrace();
		} catch (MemcachedException e) {
			Log.loggerCreate(LogLevel.ERROR, "MemcachedClient operation failed-----(set)" + e.getMessage());
			e.printStackTrace();
		}
	}

	// 删除一个key
	public void delete(String key) {
		try {
			client.delete(key);
		} catch (TimeoutException e) {
			Log.loggerCreate(LogLevel.ERROR, "MemcachedClient operation timeout-----(delete)" + e.getMessage());
			e.printStackTrace();
		} catch (InterruptedException e) {
			Log.loggerCreate(LogLevel.ERROR, "MemcachedClient operation interrupted-----(delete)" + e.getMessage());
			e.printStackTrace();
		} catch (MemcachedException e) {
			Log.loggerCreate(LogLevel.ERROR, "MemcachedClient operation failed-----(delete)" + e.getMessage());
			e.printStackTrace();
		}
	}

	//原子更新,将比较和更新绑定为一个原子的操作
	public boolean update(String key, Object value) {
		try {
			GetsResponse<Integer> result =  client.gets(key);
			long cas = result.getCas();
			if (!client.cas(key, 0, value, cas)){
				return false;
			}
		} catch (TimeoutException e) {
			Log.loggerCreate(LogLevel.ERROR, "MemcachedClient operation timeout-----(update)" + e.getMessage());
			e.printStackTrace();
		} catch (InterruptedException e) {
			Log.loggerCreate(LogLevel.ERROR, "MemcachedClient operation interrupted-----(update)" + e.getMessage());
			e.printStackTrace();
		} catch (MemcachedException e) {
			Log.loggerCreate(LogLevel.ERROR, "MemcachedClient operation failed-----(update)" + e.getMessage());
			e.printStackTrace();
		}
		return true;
	}
	
	//根据key,获取值
	public Object get(String key){
		try {
			return client.get(key);
		} catch (TimeoutException e) {
			Log.loggerCreate(LogLevel.ERROR, "MemcachedClient operation timeout-----(get)" + e.getMessage());
			e.printStackTrace();
		} catch (InterruptedException e) {
			Log.loggerCreate(LogLevel.ERROR, "MemcachedClient operation interrupted-----(get)" + e.getMessage());
			e.printStackTrace();
		} catch (MemcachedException e) {
			Log.loggerCreate(LogLevel.ERROR, "MemcachedClient operation failed-----(get)" + e.getMessage());
			e.printStackTrace();
		}
		
		return null;
	}
	
	//额外添加服务
	public void addServer(String server, int port, int weight){
		try {
			client.addServer(server, port, weight);
		} catch (IOException e) {
			Log.loggerCreate(LogLevel.ERROR, "添加Memcacahed失败:" + e.getMessage());
			e.printStackTrace();
		}
	}
	
	//移除一个服务
	public void removeServer(String hostList){
		client.removeServer(hostList);
	}
}
