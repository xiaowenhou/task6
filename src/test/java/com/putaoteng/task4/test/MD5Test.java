package com.putaoteng.task4.test;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import org.junit.Test;

import com.putaoteng.task6.utils.MD5Encryption;

public class MD5Test {
	String encryString = null;
	
	@Test
	public void testEncoderByMd5() throws NoSuchAlgorithmException, UnsupportedEncodingException {
		String str = "username";
		
		this.encryString = MD5Encryption.EncoderByMd5(str);
		System.out.println(encryString);
	}

	@Test
	public void testCheckpassword() throws NoSuchAlgorithmException, UnsupportedEncodingException {
		boolean isEquals = MD5Encryption.checkpassword("username", "FMSwa4JOxZMjk2JRf1OLKQ==");
		
		System.out.println(isEquals);
	}

}
