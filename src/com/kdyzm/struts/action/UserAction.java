package com.kdyzm.struts.action;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.interceptor.SessionAware;

import com.kdyzm.domain.User;
import com.kdyzm.service.UserService;
import com.kdyzm.struts.action.base.BaseAction;

/**
 * TODO 泛型不能加上真是奇葩，加上就会报空指针异常
 * Action中的泛型不能加上，否则会出现问题（在BaseAction中的构造方法中会出现空指针异常！）
 * 杜绝UserAction<User>这种形式的出现
 * @author kdyzm
 *
 */
public class UserAction extends BaseAction<User> implements SessionAware{

	@Override
	public void setSession(Map<String, Object> session) {
	}
	
}
