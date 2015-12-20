package com.kdyzm.struts.action;

import java.util.Collection;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.kdyzm.domain.security.Right;
import com.kdyzm.domain.security.Role;
import com.kdyzm.service.RightService;
import com.kdyzm.service.RoleService;
import com.kdyzm.struts.action.base.BaseAction;
import com.opensymphony.xwork2.ActionContext;
@Controller
@Scope("prototype")
public class RoleAction extends BaseAction<Role>{
	private static final long serialVersionUID = -1501135489285555314L;
	@Resource(name="roleService")
	private RoleService roleService;
	@Resource(name="rightService")
	private RightService rightService;
	private Integer[] ownRights;
/*****************************华丽的分割线*********************************************/
	//跳转到角色管理界面上去
	public String toRoleManagementPage() throws Exception{
		Collection<Role> roleList=this.roleService.findAllEntities();
		ActionContext.getContext().put("roleList", roleList);
		return "toRoleManagementPage";
	}
	public Integer[] getOwnRights() {
	return ownRights;
}
public void setOwnRights(Integer[] ownRights) {
	this.ownRights = ownRights;
}
	//跳转到添加角色或者更新角色信息的界面上去
	public String toSaveOrUpdateRolePage() throws Exception{
		if(this.model.getRoleId()!=null){
			this.model=this.roleService.getEntityById(this.model.getRoleId());
			Collection<Right>noneOwnRights=this.roleService.findAllNoneOwnRights(this.model);
			ActionContext.getContext().put("noneOwnRights", noneOwnRights);
		}else{
			Collection<Right>allRights=this.rightService.getAllRights();
			ActionContext.getContext().put("noneOwnRights", allRights);
		}
		return "toSaveOrUpdateRolePage";
	}
	
	public String saveOrUpdateRole() throws Exception{
		Set<Right>rights=this.rightService.getRightsByIds(this.ownRights);
		this.model.setRights(rights);
		this.roleService.saveOrUpdateEntity(model);
		return "toRoleManagementPageAction";
	}
	public String deleteRole() throws Exception{
		this.roleService.deleteEntiry(model);
		return "toRoleManagementPageAction";
	}
}
