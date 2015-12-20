package com.kdyzm.service.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kdyzm.dao.impl.RightDaoImpl;
import com.kdyzm.domain.security.Right;
import com.kdyzm.service.RightService;
import com.kdyzm.service.base.impl.BaseServiceImpl;
/**
 * 保存权限对象的算法
 * 需要着重考虑空指针的问题和边界值的问题
 * @author kdyzm
 *
 */
@Service("rightService")
public class RightServiceImpl extends BaseServiceImpl<Right> implements RightService{
	@Resource(name="rightDao")
	private RightDaoImpl rightDao;
	//添加新权限的过程
	public void saveOrUpateRight(Right right){
		Integer rightPos;
		Long rightCodes;
		//首先是针对新创建的情况
		if(right.getRightId()==null){
			//第一步查找最大权限位和最大权限码
			String hql="select max(r.rightPos),max(r.rightCodes) from Right r where r.rightPos=(select max(rr.rightPos) from Right rr)";
			Object[] arr=(Object[]) this.rightDao.findUniqueResult(hql);
			Integer topRightPos=(Integer) arr[0];
			Long topRightCodes=(Long) arr[1];
			if(topRightPos==null){
				rightPos=0;
				rightCodes=1L;
			}else{
				if(topRightCodes>=(1L<<60)){
					rightPos=topRightPos+1;
					rightCodes=1L;
				}else{
					rightPos=topRightPos;
					rightCodes=topRightCodes<<1;
				}
			}
			right.setRightPos(rightPos);
			right.setRightCodes(rightCodes);
		}
		this.rightDao.saveOrUpdateEntity(right);
	}
	@Override
	public Collection<Right> getAllRights() {
		return this.rightDao.findAllEntities();
	}
	@Override
	public void deleteRight(Right model) {
		this.rightDao.deleteEntiry(model);
	}
	@Override
	public Right getRightById(Integer rightId) {
		return this.rightDao.getEntityById(rightId);
	}
	@Override
	public void appendRightByUrl(String url) {
		String hql="select count(*) from Right where rightUrl=?";
		Long count=(Long) this.rightDao.findUniqueResult(hql, url);
		if(count==0){
			Right right=new Right();
			right.setRightUrl(url);
			this.saveOrUpateRight(right);
		}else{
			System.out.println(url+" 已经存在！");
		}
	}
	@Override
	public Set<Right> getRightsByIds(Integer[] ownRights) {
		StringBuffer hql;
		if(ownRights.length>=1){
			hql=new StringBuffer("from Right where rightId in(");
			for(int i=0;i<ownRights.length;i++){
				if(i==ownRights.length-1){
					hql.append(ownRights[i]);
				}else{
					hql.append(ownRights[i]+",");
				}
			}
			hql.append(")");
			return new HashSet<Right>(this.rightDao.findEntityByHQL(hql.toString()));
		}else{
			return new HashSet<Right>();
		}
	}
	@Override
	public void updateBatchRights(List<Right> rightList) {
		String hql="update Right r set rightName=?,common=? where rightId=?";
		for(Right right:rightList){
			this.rightDao.batchEntityByHql(hql, right.getRightName(),right.getCommon(),right.getRightId());
		}
	}
	@Override
	public int getMaxPost() {
		String hql="select max(rightPos) from Right";
		Object obj=this.rightDao.findUniqueResult(hql);
		return (Integer)obj;
	}
}
