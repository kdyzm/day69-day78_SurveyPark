package com.kdyzm.utils;

import java.security.MessageDigest;

public class DataUtils {
	
	//TODO md5加密工具
	public static synchronized String md5(String input){
		try {
			StringBuffer sb=new StringBuffer();
			String arr[]={"0","1","2","3","4","5","6","7","8","9","A","B","C","D","E","F"};
			MessageDigest messageDigest=MessageDigest.getInstance("MD5");
			byte []data=messageDigest.digest(input.getBytes());
			System.out.println(data.length);
			for(byte temp:data){
				//高四位
				sb.append(arr[(temp>>4)&0X0F]);
				//低四位
				sb.append(arr[temp&0X0F]);
			}
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
}
