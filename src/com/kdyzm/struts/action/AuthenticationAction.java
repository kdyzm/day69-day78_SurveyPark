package com.kdyzm.struts.action;

import java.util.Collection;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.kdyzm.domain.User;
import com.kdyzm.domain.security.Role;
import com.kdyzm.service.RoleService;
import com.kdyzm.service.UserService;
import com.kdyzm.struts.action.base.BaseAction;
import com.opensymphony.xwork2.ActionContext;

/**
 * 授权Action
 * @author kdyzm
 *
 */
@Controller
@Scope("prototype")
public class AuthenticationAction extends BaseAction<User>{
	private static final long serialVersionUID = -1585766135317644994L;
	@Resource(name="userService")
	private UserService userService;
	@Resource(name="roleService")
	private RoleService roleService;
	private Integer[] ownRoles;
/***********************************华丽的分割线**************************************/	
	public String toAuthenticationManagementPage() throws Exception{
		Collection<User> userList=this.userService.findAllEntities();
		ActionContext.getContext().put("userList", userList);
		return "toAuthenticationManagementPage";
	}
	public Integer[] getOwnRoles() {
		return ownRoles;
	}
	public void setOwnRoles(Integer[] ownRoles) {
		this.ownRoles = ownRoles;
	}
	public String toUpdateAuthenticationPage() throws Exception{
		this.model=this.userService.getEntityById(this.model.getUserId());
		Collection<Role>noneOwnRoles=this.roleService.findAllNoneOwnRoles(this.model);
		ActionContext.getContext().put("noneOwnRoles", noneOwnRoles);
		return "toUpdateAuthenticationPage";
	}
	//保存或者更新权限的方法
	public String saveOrUpdateAuthentication() throws Exception{
		Set<Role>roles=roleService.getRolesByIds(this.ownRoles);
		this.model.setRoles(roles);
		this.userService.saveOrUpdateEntity(model);
		return "toAuthenticationManagementPageAction";
	}
	
	//直接清空某个用户授权的方法
	public String clearAuthentication() throws Exception{
		this.model=this.userService.getEntityById(this.model.getUserId());
		this.userService.clearAuthentication(this.model);
		return "toAuthenticationManagementPageAction";
	}
}
