package com.kdyzm.service;

import com.kdyzm.domain.User;
import com.kdyzm.service.base.BaseService;

public interface UserService extends BaseService<User> {

	void saveUser(User user);

	boolean isEmailUnique(String trim);

	User checkEmailAndPassword(User model);

	void clearAuthentication(User model);
}
