package com.joker.ClownUnino.util;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class BASE64 {

	/**
	 * BASE64 Ω‚√‹
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] decryptBASE64(String key) throws Exception{
		return(new BASE64Decoder()).decodeBuffer(key);
	}
	
	/**
	 * BASE64 º”√‹
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String encryptBASE64(byte[] key) throws Exception{
		return (new BASE64Encoder()).encodeBuffer(key);
	}
}
