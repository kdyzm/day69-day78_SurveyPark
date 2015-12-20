package com.kdyzm.service.base.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;

import com.kdyzm.dao.base.BaseDao;
import com.kdyzm.service.base.BaseService;
/**
 * 该类也得是抽象类才行
 * @author kdyzm
 *
 * @param <T>
 */
public abstract class BaseServiceImpl<T> implements BaseService<T>{
	public BaseDao<T> baseDao;
	//注意注入Dao的方法是放到方法的前面，而不是加到字段的前面
	public BaseDao<T> getBaseDao() {
		return baseDao;
	}

	public void setBaseDao(BaseDao<T> baseDao) {
		this.baseDao = baseDao;
	}

	@Override
	public void saveEntity(T t) {
		this.baseDao.saveEntity(t);
	}

	@Override
	public void updateEntity(T t) {
		this.baseDao.updateEntity(t);
	}

	@Override
	public void saveOrUpdateEntity(T t) {
		this.baseDao.saveOrUpdateEntity(t);
	}

	@Override
	public void deleteEntiry(T t) {
		this.baseDao.deleteEntiry(t);
	}

	@Override
	public void batchEntityByHql(String hql, Object... objects) {
		this.baseDao.batchEntityByHql(hql, objects);
	}

	@Override
	public T getEntityById(Serializable id) {
		return this.baseDao.getEntityById(id);
	}

	@Override
	public T loadEntiryById(Serializable id) {
		return this.baseDao.loadEntiryById(id);
	}

	@Override
	public List<T> findEntityByHQL(String hql, Object... objects) {
		return this.baseDao.findEntityByHQL(hql, objects);
	}
	@Override
	public Collection<T> findAllEntities() {
		return this.baseDao.findAllEntities();
	}
	//执行ddl 类型的sql语句
	@Override
	public void executeSql(String sql, Object... objects) {
		this.baseDao.executeSql(sql, objects);
	}
	//执行查询类型的sql语句
	@Override
	public Collection<T> findAllEntitiesBySQl(String sql, Object... objects) {
		return this.baseDao.findAllEntitiesBySql(sql, objects);
	}
}
