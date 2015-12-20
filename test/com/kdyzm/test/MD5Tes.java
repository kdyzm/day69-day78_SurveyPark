package com.kdyzm.test;

import java.security.MessageDigest;

public class MD5Tes {
	public static void main(String args[]) throws Exception{
		StringBuffer sb=new StringBuffer();
		String arr[]={"0","1","2","3","4","5","6","7","8","9","A","B","C","D","E","F"};
		MessageDigest messageDigest=MessageDigest.getInstance("MD5");
		String input="nihao";
		byte []data=messageDigest.digest(input.getBytes());
		System.out.println(data.length);
		for(byte temp:data){
			//高四位
			sb.append(arr[(temp>>4)&0X0F]);
			//低四位
			sb.append(arr[temp&0X0F]);
		}
		System.out.println(sb.toString());
	}
}
