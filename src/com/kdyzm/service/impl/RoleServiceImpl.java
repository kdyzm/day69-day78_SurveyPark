package com.kdyzm.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kdyzm.dao.base.BaseDao;
import com.kdyzm.dao.impl.RightDaoImpl;
import com.kdyzm.domain.User;
import com.kdyzm.domain.security.Right;
import com.kdyzm.domain.security.Role;
import com.kdyzm.service.RoleService;
import com.kdyzm.service.base.impl.BaseServiceImpl;

@Service("roleService")
public class RoleServiceImpl extends BaseServiceImpl<Role> implements RoleService{
	@Resource(name="rightDao")
	private RightDaoImpl rightDao;
	@Resource(name="roleDao")
	public void setBaseDao(BaseDao<Role> baseDao) {
		super.setBaseDao(baseDao);
	}

	@Override
	public Collection<Right> findAllNoneOwnRights(Role model) {
		Collection<Right>allRights=this.rightDao.findAllEntities();
		model=this.getEntityById(model.getRoleId());
		Collection<Right>ownRights=model.getRights();
		allRights.removeAll(ownRights);
		return allRights;
	}
	//找到所有没有的角色
	@Override
	public Collection<Role> findAllNoneOwnRoles(User user) {
		List<Role>roles=new ArrayList<Role>(user.getRoles());
		Collection<Role>allRoles=this.findAllEntities();
		allRoles.removeAll(roles);
		return allRoles;
	}
	//根据id获取所有Role对象列表的方法
	@Override
	public Set<Role> getRolesByIds(Integer[] ownRoles) {
		StringBuffer  hql;
		if(ownRoles.length>=1){
			hql=new StringBuffer("from Role where roleId in (");
			for(int i=0;i<ownRoles.length;i++){
				if(i==ownRoles.length-1){
					hql.append(ownRoles[i]);
				}else{
					hql.append(ownRoles[i]+",");
				}
			}
			hql.append(")");
			Collection<Role>roles=this.findEntityByHQL(hql.toString());
			return new HashSet<Role>(roles);
		}else{
			return new HashSet<Role>();
		}
	}
}
