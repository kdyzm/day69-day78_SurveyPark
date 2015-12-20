package com.kdyzm.struts.action;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.interceptor.SessionAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.kdyzm.domain.User;
import com.kdyzm.service.RightService;
import com.kdyzm.service.RoleService;
import com.kdyzm.service.UserService;
import com.kdyzm.struts.action.base.BaseAction;
@Controller("loginAction")
@Scope("prototype")
public class LoginAction extends BaseAction<User> implements SessionAware{
	private static final long serialVersionUID = 879952397314349337L;
	@Resource(name="userService")
	private UserService userService;
	private Map<String,Object>session;
	@Resource(name="rightService")
	private RightService rightService;
	@Resource(name="roleService")
	private RoleService roleService;
	//验证用户名和密码的方法
	public String chekEmailAndPassword() throws Exception{
		User user=userService.checkEmailAndPassword(this.model);
		if(user==null){
			addActionError("用户名或者密码错误！");
			System.out.println("用户名或者密码错误！");
			return "input";
		}else{
			//关于怎么将Session直接注入Action中的方法是一个比较难的题目
			System.out.println("用户登陆成功！");
			//在登陆成功的时候计算权限码
			int maxPos=rightService.getMaxPost();
			user.setRightSum(new long[maxPos+1]);
			if(user.isSuperAdmin()){
				user.setSuperAdmin(true);
			}else{
				user.setSuperAdmin(false);
			}
			//TODO 一定要把计算权限总和的语句放在最后，否则一旦将roles置为null，其它方法调用的时候就会出现空指针异常！
			user.calculateRightSum();
			
			session.put("user", user);
		}
		return "toIndexPage";
	}
	@Override
	public void setSession(Map<String, Object> session) {
		this.session=session;
	}
	
}
