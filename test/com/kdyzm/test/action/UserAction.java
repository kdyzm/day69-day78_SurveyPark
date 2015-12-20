package com.kdyzm.test.action;

import java.util.List;

import com.kdyzm.test.domain.User;
import com.opensymphony.xwork2.ActionSupport;

public class UserAction extends ActionSupport{
	private static final long serialVersionUID = 1612210354638451522L;
	private List<User> users;
	public List<User> getUsers() {
		return users;
	}
	public void setUsers(List<User> users) {
		this.users = users;
	}

	public String doReg() throws Exception{
		for(User user:users){
			System.out.println(user);
		}
		return "showReg";
	}
}
