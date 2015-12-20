package com.kdyzm.service;

import java.util.Collection;
import java.util.Set;

import com.kdyzm.domain.User;
import com.kdyzm.domain.security.Right;
import com.kdyzm.domain.security.Role;
import com.kdyzm.service.base.BaseService;

public interface RoleService extends BaseService<Role>{

	Collection<Right> findAllNoneOwnRights(Role model);

	Collection<Role> findAllNoneOwnRoles(User model);

	Set<Role> getRolesByIds(Integer[] ownRoles);

}
