package com.joker.ClownUnino.util;

import java.math.BigInteger;
import java.security.MessageDigest;

public class MD5 {

public static final String KEY_MD5 = "MD5";
	
	public static String getResult(String inputStr) {
		
		 //System.out.println("=======����ǰ������:"+inputStr);
	        BigInteger bigInteger=null;

	        try {
	         MessageDigest md = MessageDigest.getInstance(KEY_MD5);   
	         byte[] inputData = inputStr.getBytes(); 
	         md.update(inputData);   
	         bigInteger = new BigInteger(md.digest());   
	        } catch (Exception e) {e.printStackTrace();}
	       // System.out.println("MD5���ܺ�:" + bigInteger.toString(16));   
	        return bigInteger.toString(16);
	}
}
