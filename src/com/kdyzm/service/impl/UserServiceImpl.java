package com.kdyzm.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kdyzm.dao.base.BaseDao;
import com.kdyzm.domain.User;
import com.kdyzm.service.UserService;
import com.kdyzm.service.base.impl.BaseServiceImpl;
import com.kdyzm.utils.DataUtils;
@Service("userService")
public class UserServiceImpl extends BaseServiceImpl<User>  implements UserService {
	@Resource(name="userDao")
	public void setUserDao(BaseDao<User> baseDao) {
		super.setBaseDao(baseDao);
	}

	@Override
	public void saveUser(User user) {
		Date registerDate=new Date();
		user.setRegisterDate(registerDate);
		user.setPassword(DataUtils.md5(user.getPassword()));
		saveEntity(user);
	}
	/**
	 * 判断邮箱是否唯一
	 */
	@Override
	public boolean isEmailUnique(String email) {
		String hql="from User where email=?";
		List<User> users=this.findEntityByHQL(hql, email);
		return users.isEmpty()?false:true;
	}

	@Override
	public User checkEmailAndPassword(User model) {
		String hql="from User where email=? and password=?";
		List<User> users=this.findEntityByHQL(hql, model.getEmail(),DataUtils.md5(model.getPassword()));
		User user=null;
		if(!users.isEmpty()){
			user=users.get(0);
			user.getRoles().size();//这里特别重要
		}
		return user;
	}
	//清空某个用户所有角色的方法
	@Override
	public void clearAuthentication(User user) {
		user.getRoles().clear();
		this.saveOrUpdateEntity(user);
	}
}
