package com.kdyzm.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import com.kdyzm.domain.security.Right;
import com.kdyzm.domain.security.Role;

/**
 * 该类是用户类
 * @author kdyzm
 *	这里设计的关键就是User和Survey之间建立的是单向关联的关系
 *	只是从Survey建立到User之间的关联，但是不建立User到Survey的关联
 */
public class User implements Serializable{
	private static final long serialVersionUID = 6023702953202573404L;
	private Integer userId;				//用户id
	private String password;		//用户密码
	private String nickName;		//用户昵称
	private String email;			//用户邮件
	private Date registerDate;		//用户注册日期
	private Set<Role>roles;
	private Boolean superAdmin;	//判定是否是超级管理员的标识字段
	private long[]rightSum;		//进行权限判定的关键,注意这里一定要使用基本数据类型，否则会有问题，因为包装类型的默认值不是0，是null
/*******************************************************************/
	public User() {
	}
	public long[] getRightSum() {
		return rightSum;
	}
	
	public void setRightSum(long[] rightSum) {
		this.rightSum = rightSum;
	}

	public Boolean getSuperAdmin() {
		return superAdmin;
	}
	public void setSuperAdmin(Boolean superAdmin) {
		this.superAdmin = superAdmin;
	}

	public Set<Role> getRoles() {
		return roles;
	}
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	public String getPassword() {
		return password;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getRegisterDate() {
		return registerDate;
	}
	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}
	//TODO 计算rightSum数组的方法，关键是调用该方法的时机是什么，即什么时候调用该方法：在验证登录的时候调用该方法
	public void calculateRightSum(){
		int pos=0;
		Long codes=0L;
		for(Role role:roles){
			if(role.getRoleValue().equals("-1")){
				//如果是超级管理员则不需要指定权限了，因为对于超级管理员判断依据不是这个东西
				return;
			}else{
				for(Right right:role.getRights()){
					pos=right.getRightPos();
					codes=right.getRightCodes();
					//使用|的计算方式获取该权限组上的权限值
					rightSum[pos]=codes|rightSum[pos];
				}
			}
		}
		//最后断掉和Role的链接
		roles=null;
	}
	//判断该用户是否有该权限的方法
	public boolean hasRight(Right right) {
		int pos=right.getRightPos();
		Long codes=right.getRightCodes();
		long result=rightSum[pos]&codes;
		if(result==0)return false;
		return true;
	}
	public boolean isSuperAdmin() {
		for(Role role:roles){
			if(role.getRoleValue().equals("-1"))
				return true;
		}
		return false;
	}
}
