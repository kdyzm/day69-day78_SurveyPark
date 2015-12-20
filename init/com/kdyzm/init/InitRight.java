package com.kdyzm.init;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.kdyzm.domain.security.Right;
import com.kdyzm.service.RightService;

/**
 * 初始化权限的类
 * @author kdyzm
 *
 */
public class InitRight {
	public static void main(String[] args) throws Exception {
		ApplicationContext context=new ClassPathXmlApplicationContext("spring/applicationContext.xml");
		RightService rightService=(RightService) context.getBean("rightService");
		
//		InitRight.class.getClassLoader();
		URL url=ClassLoader.getSystemResource("com/kdyzm/struts/action");
		File dir=new File(url.toURI());
		File[] fiels=dir.listFiles();
		for(File file:fiels){
			if(file.getName().endsWith("class"))
			processClass(file,rightService);
		}
		System.out.println("完成初始化任务！");
	}
	/**
	 * 根据文件和rightService插入Action中的每个方法对应的权限标识
	 * @param file
	 * @param rightService
	 * @throws Exception 
	 */
	private static void processClass(File file, RightService rightService) throws Exception {
		String basePackage="com.kdyzm.struts.action.";
		String className=basePackage+file.getName().substring(0,file.getName().indexOf("."));
		Class clazz=Class.forName(className);
		Method[]methods=clazz.getDeclaredMethods();
		
		String methodName="";
		Class returnType=null;
		Class[] parameters=null;
		String url="";
		for(Method method:methods){
			methodName=method.getName();
			returnType=method.getReturnType();
			parameters=method.getParameterTypes();
			if(returnType.equals(String.class)
					&&(parameters==null||parameters.length==0)){
				url="/"+file.getName().substring(0,file.getName().indexOf("."))+"_"+methodName+".action";
				Right right=new Right();
				right.setRightUrl(url);
				rightService.saveOrUpateRight(right);
			}
		}
	}
}
