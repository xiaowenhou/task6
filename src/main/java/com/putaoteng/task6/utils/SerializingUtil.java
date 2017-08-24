package com.putaoteng.task6.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SerializingUtil {
	/**
	 * 对实体Bean进行序列化操作
	 * 
	 * @param source
	 *            待转换的实体
	 * @return 转换之后的字节数组
	 * @throws Exception
	 * 
	 */
	public static byte[] serialize(Object source) {
		ByteArrayOutputStream byteOut = null;
		ObjectOutputStream objOut = null;

		try {
			byteOut = new ByteArrayOutputStream();
			objOut = new ObjectOutputStream(byteOut);
			// 将对象source写入流中
			objOut.writeObject(source);
			// 刷新流
			objOut.flush();
		} catch (IOException e) {
			Log.loggerCreate(LogLevel.ERROR, source.getClass().getName() + "serialized error");
			Log.loggerCreate(LogLevel.ERROR, e.getMessage());
		} finally {

			try {
				if (null != objOut) {
					objOut.close();
				}
			} catch (IOException e) {
				objOut = null;
			}
		}
		return byteOut.toByteArray();
	}
	
	/**
	 * 将字节数组反序列化为实体Bean
	 * @param source 需要进行反序列化的字节数组
	 * @return 反序列化后的实体Bean
	 * @throws Exception 
	 * 
	 */
	public static Object deserialize(byte[] source){
		ObjectInputStream objIn = null;
		Object result = null;
		try {
			ByteArrayInputStream byteIn = new ByteArrayInputStream(source);
			objIn = new ObjectInputStream(byteIn);
			result = objIn.readObject();
		} catch (IOException | ClassNotFoundException e) {
			Log.loggerCreate(LogLevel.ERROR, "deserialized error. Cause by: "
							+ e.getMessage());
		} finally{
			try{
				if (null != objIn){
					objIn.close();
				}
			} catch (IOException e){
				objIn = null;
			}
		}
		return result;
	}
}
