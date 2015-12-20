package com.kdyzm.utils;

import java.util.UUID;

public class StringUtils {
	//String转换成String数组
	public static synchronized String[] String2Arr(String sourceStr){
		if(sourceStr!=null)
			return sourceStr.split("\r\n");
		return null;
	}
	//String数组转换成为String字符串
	public static synchronized String arr2String(String[]arr ) {
		if(arr==null){
			return "";
		}
		StringBuffer sb=new StringBuffer();
		for(int i=0;i<arr.length;i++){
			if(i==arr.length-1){
				sb.append(arr[i]);
			}else{
				sb.append(arr[i]);
				sb.append(",");
			}
		}
		return sb.toString();
	}
	public static String getUUIDString(){
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	
	//数组对象转换成为字符串的方法
	public static String arr2String(Object[] params) {
		StringBuffer sb=new StringBuffer();
		boolean flag=false;
		for(Object param:params){
			if(flag==false){
				sb.append(param);
				flag=true;
			}
			sb.append(","+param);
		}
		return sb.toString();
	}
	//通过jsp页面的静态调用可以直接调用某个类的某个方法
	public static String setTagContentLimitLength(String string){
		int length=15;
		System.out.println("访问了setTagContentLimitLength方法！"+string);
		if(string !=null){
			if(string.length()>length){
				return string.substring(0,length)+"......";
			}else{
				return string;
			}
		}
		return "";
	}
}
