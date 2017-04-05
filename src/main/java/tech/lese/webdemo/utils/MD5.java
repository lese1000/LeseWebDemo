package tech.lese.webdemo.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Copyright(C) 
 *
 * Module: 
 * @author  
 * @version
 * @see
 * @description: <MD5加密>
 * @log:
 */
public class MD5 {
   public static String hex(byte[] array)  {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < array.length; ++i) {
            sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
        }
        return sb.toString();
    }

    public static String md5Hex(String message) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            return hex(md.digest(message.getBytes("CP1252")));
        } catch (NoSuchAlgorithmException e) {

        } catch (UnsupportedEncodingException e) {
        
        }
        return message;
    }
	 
	public static void main(String args[]) {
		System.out.println("MD5(\"abcefg\"):" + MD5.md5Hex("150127007"));
	}
}
