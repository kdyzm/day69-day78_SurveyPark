package com.kdyzm.dao.base.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;

import com.kdyzm.dao.base.BaseDao;
/***
 * BaseDaoImpl类必须是抽象类,实现已经定义好的接口
 * @author kdyzm
 *
 * @param <T>
 */
@SuppressWarnings("unchecked")
public abstract class BaseDaoImpl<T> implements BaseDao<T> {
	//手下需要两个成员变量，这两个成员变量的赋值，一个是通过spring容器管理，一个是通过泛型动态获取
	@Resource(name="sessionFactory")
	public SessionFactory sessionFactory;
	private Class<T> clazz;
	
	//在默认构造方法中调用相关程序获取真实的泛型类型
	public BaseDaoImpl() {
		ParameterizedType parameterizedType=(ParameterizedType) this.getClass().getGenericSuperclass();
		clazz=(Class<T>) parameterizedType.getActualTypeArguments()[0];
	}
	@Override
	public void saveEntity(T t) {
		System.out.println("将要保存"+t);
		this.sessionFactory.getCurrentSession().save(t);
	}

	@Override
	public void updateEntity(T t) {
		this.sessionFactory.getCurrentSession().update(t);
	}

	@Override
	public void saveOrUpdateEntity(T t) {
		this.sessionFactory.getCurrentSession().saveOrUpdate(t);
	}

	@Override
	public void deleteEntiry(T t) {
		this.sessionFactory.getCurrentSession().delete(t);
	}

	//批量处理更新的方法重点是使用Query对象
	@Override
	public void batchEntityByHql(String hql, Object... objects) {
		Query query=this.sessionFactory.getCurrentSession().createQuery(hql);
		for(int i=0;i<objects.length;i++){
			query.setParameter(i, objects[i]);
		}
		query.executeUpdate();
	}

	@Override
	public T getEntityById(Serializable id) {
		return (T) this.sessionFactory.getCurrentSession().get(clazz,id);
	}

	@Override
	public T loadEntiryById(Serializable id) {
		return (T) this.sessionFactory.getCurrentSession().load(clazz, id);
	}

	@Override
	public List<T> findEntityByHQL(String hql, Object... objects) {
		Query query=this.sessionFactory.getCurrentSession().createQuery(hql);
		for(int i=0;i<objects.length;i++){
			query.setParameter(i, objects[i]);
		}
		return query.list();
	}
	@Override
	public Object findUniqueResult(String hql, Object... objects) {
		Query query=this.sessionFactory.getCurrentSession().createQuery(hql);
		for(int i=0;i<objects.length;i++){
			query.setParameter(i, objects[i]);
		}
		return query.uniqueResult();
	}
	@Override
	public Collection<T> findAllEntities(){
		String hql="from "+clazz.getSimpleName();
		return this.sessionFactory.getCurrentSession().createQuery(hql).list();
	}
	//直接执行sql语句的方法
	@Override
	public void executeSql(String sql, Object... objects) {
		SQLQuery sqlQuery=this.sessionFactory.getCurrentSession().createSQLQuery(sql);
		for(int i=0;i<objects.length;i++){
			sqlQuery.setParameter(i, objects[i]);
		}
		sqlQuery.executeUpdate();
	}
	//根据sql语句得到List集合的方法
	@Override
	public Collection<T> findAllEntitiesBySql(String sql, Object... objects) {
		SQLQuery sqlQuery=this.sessionFactory.getCurrentSession().createSQLQuery(sql);
		for(int i=0;i<objects.length;i++){
			sqlQuery.setParameter(i, objects[i]);
		}
		sqlQuery.addEntity(clazz);
		return sqlQuery.list();
	}
}
