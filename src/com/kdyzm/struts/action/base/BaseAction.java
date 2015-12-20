package com.kdyzm.struts.action.base;

import java.lang.reflect.ParameterizedType;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;

public abstract class BaseAction<T> extends ActionSupport implements ModelDriven<T>,Preparable{
	private static final long serialVersionUID = -2094279976985931191L;
	//该方法不必非要实现，所有不使用abstract关键字来实现
	public T model;
	public void prepare() throws Exception {
	};
	//公国构造方法获取真实的泛型类型
	public BaseAction(){
		try {
			ParameterizedType parameterizedType=(ParameterizedType) this.getClass().getGenericSuperclass();
			Class<T> clazz=(Class<T>)parameterizedType.getActualTypeArguments()[0];
			model=(T) clazz.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//该方法必须实现，所有使用抽象的关键字来修饰
	public T getModel() {
		return model;
	};
	
}
