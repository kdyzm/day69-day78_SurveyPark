package com.kdyzm.struts.action.aware;

import com.kdyzm.domain.User;
/**
 * 用户关注接口，用于Action获取Session中的User对象
 * @author kdyzm
 *
 */
public interface UserAware {
	public void setUser(User user);
}
