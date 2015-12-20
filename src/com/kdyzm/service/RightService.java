package com.kdyzm.service;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import com.kdyzm.domain.security.Right;

/**
 * 权限管理的服务
 * @author kdyzm
 *
 */
public interface RightService {
	public void saveOrUpateRight(Right right);

	public Collection<Right> getAllRights();

	public void deleteRight(Right model);

	public Right getRightById(Integer rightId);

	public void appendRightByUrl(String url);

	public Set<Right> getRightsByIds(Integer[] ownRights);

	public void updateBatchRights(List<Right> rightList);

	public int getMaxPost();
}
