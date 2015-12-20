package com.kdyzm.struts.action;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.kdyzm.domain.User;
import com.kdyzm.service.UserService;
import com.kdyzm.struts.action.base.BaseAction;

@Controller("registerAction")
@Scope("prototype")
public class RegisterAction extends BaseAction<User>{
	private static final long serialVersionUID = 969730751500864591L;
	@Resource(name="userService")
	private UserService userService;
	//跳转到注册界面
	public String toRegisterPage() throws Exception{
		System.out.println("即将跳转到用户注册界面！");
		return "toRegisterPage";
	}
	//接收表单提交的信息，并进行判断是否需要真正的进行保存
	public String register() throws Exception{
		User user=new User();
		BeanUtils.copyProperties(model, user);
		userService.saveUser(user);
		return "toIndexPage";
	}
	//使用struts2的验证方式
	/**
	 * 使用这种方法的验证，必须要在配置文件中声明一个input类型的标签转到指定的错误页面！
	 * 使用的方法就是addFieldError方法加入键值对，将错误信息保存到值栈中。
	 * 如果值栈中有相应的错误信息，将会自动跳转到input标签中，跳转到错误的页面
	 * 但是这种方式并不是很常用，一般使用js进行表单的验证。
	 * 可以使用@SkipValidate注解进行标识不需要验证的方法
	 * 也可以使用validate[Do]MethodName的方法单独标识出需要进行校验的方法
	 */
	public void validateDoRegister() {
		//首先验证几个字段值是否为空
		boolean flag=true;
		if(model.getEmail()==null||"".equals(model.getEmail().trim())){
			addFieldError("email", "emial是必填项！");
			flag=false;
		}
		if(model.getNickName()==null||"".equals(model.getNickName().trim())){
			addFieldError("nickName", "昵称是必填项");
			flag=false;
		}
		if(model.getPassword()==null||"".equals(model.getPassword().trim())){
			addFieldError("password", "密码字段是必填项");
			flag=false;
		}
		//判断两个密码是否一致
		String repeatPassword=ServletActionContext.getRequest().getParameter("repeatPassword");
		if(repeatPassword==null||"".equals(repeatPassword.trim())){
			addFieldError("repeatPassword", "重复密码字段是必填项！");
			flag=false;
		}
		if(!flag){
			return;
		}
		if(!repeatPassword.equals(model.getPassword())){
			addFieldError("repeatPassword", "请确认两个密码一致！");
			return;
		}
		//判断用户邮箱是否唯一
		if(this.userService.isEmailUnique(this.model.getEmail().trim())){
			addFieldError("email", "用户邮箱必须唯一！");
			return;
		}
	}
}
