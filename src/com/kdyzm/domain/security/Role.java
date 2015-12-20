package com.kdyzm.domain.security;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * 角色类
 * @author kdyzm
 *	TODO 暂时存档，先用不着
 *Role对象和Right对象之间是多对多关系
 *Role对象和User对象之间是多对多关系,但是只是建立User到Role的多对多单向关系
 */
public class Role implements Serializable{
	private static final long serialVersionUID = -1585936238538771703L;
	private Integer roleId;				//Role对象标识id
	private String roleName="未命名角色";			//Role对象名称
	private String roleValue="0";			//Role对象值，只是在判定是超级管理员的时候有用
	private String roleDesc="默认角色描述";			//Role对象描述
	//建立Role到Right之间的单向多多对关系
	private Set<Right>rights=new HashSet<Right>();//这里必须初始化一下，否则在重用保存/修改页的时候会报错
/*********************************华丽的分割线*****************************************/
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getRoleValue() {
		return roleValue;
	}
	public void setRoleValue(String roleValue) {
		this.roleValue = roleValue;
	}
	public String getRoleDesc() {
		return roleDesc;
	}
	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}
	public Set<Right> getRights() {
		return rights;
	}
	public void setRights(Set<Right> rights) {
		this.rights = rights;
	}
}
