package com.kdyzm.cache;

import java.lang.reflect.Method;

import org.springframework.cache.interceptor.KeyGenerator;

/**
 * 自定义key生成器
 * @author kdyzm
 *
 */
public class SurveykeyGenerator implements KeyGenerator{

	//什么对象调用的什么方法，参数列表是什么
	@Override
	public Object generate(Object arg0, Method arg1, Object... arg2) {
		String targetCode=arg0.getClass().getSimpleName()+"["+arg0.hashCode()+"]";
		String methodName=arg1.getName();
		if(arg2!=null){
			StringBuffer stringBuffer=new StringBuffer();
			stringBuffer.append("(");
			for(int i=0;i<arg2.length;i++){
				stringBuffer.append(arg2[i].toString());
				stringBuffer.append(",");
			}
			stringBuffer.append(")");
			targetCode= targetCode+"."+methodName+stringBuffer.toString();
			System.out.println(targetCode);
			return targetCode;
		}
		targetCode= targetCode+"."+methodName+"()";
		System.out.println(targetCode);
		return targetCode;
	}
}
