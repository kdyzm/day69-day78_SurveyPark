package com.kdyzm.dao.base;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

public interface BaseDao <T>{
	//写操作
	public void saveEntity(T t);
	public void updateEntity(T t);
	public void saveOrUpdateEntity(T t);
	public void deleteEntiry(T t);
	
	//按照hql批处理
	public void batchEntityByHql(String hql,Object ...objects);
	
	//查询方法
	public T getEntityById(Serializable id);
	public T loadEntiryById(Serializable id);
	public List<T> findEntityByHQL(String hql,Object ...objects);
	public Object findUniqueResult(String hql,Object ...objects);
	public Collection<T> findAllEntities();
	public void executeSql(String sql,Object ...objects);
	public Collection<T> findAllEntitiesBySql(String sql,Object ...objects);
}
