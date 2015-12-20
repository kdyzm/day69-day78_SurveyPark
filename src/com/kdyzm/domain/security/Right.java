package com.kdyzm.domain.security;

import java.io.Serializable;

/**
 * 权限类
 * 权限类的属性设计是非常重要的
 * @author kdyzm
 *	另外，虽然Right和Role之间是多对多的关系，但是一般没有从Right到Role的关系查找的，所以只是建立
 *  Role到Right之间的多对多单向关系
 */
public class Right implements Serializable{
	private static final long serialVersionUID = 7690933329658416384L;
	private Integer rightId;					//权限唯一标识id
	private String rightName="未命名";					//权限名称
	private String rightUrl;					//将要过滤的URL
	private String rightDesc="默认描述";					//权限描述
	private Long rightCodes;					//权限码
	private int rightPos;						//权限位，实际上就是权限组的类别
	private Boolean common=true;						//标识是否为公共资源的标识字段
/*************************华丽的分割线**************************************************/
	public String getRightName() {
		return rightName;
	}
	public Boolean getCommon() {
		return common;
	}
	public void setCommon(Boolean common) {
		this.common = common;
	}
	public Integer getRightId() {
		return rightId;
	}
	public void setRightId(Integer rightId) {
		this.rightId = rightId;
	}
	public void setRightName(String rightName) {
		this.rightName = rightName;
	}
	public String getRightUrl() {
		return rightUrl;
	}
	public void setRightUrl(String rightUrl) {
		this.rightUrl = rightUrl;
	}
	public String getRightDesc() {
		return rightDesc;
	}
	public void setRightDesc(String rightDesc) {
		this.rightDesc = rightDesc;
	}
	public Long getRightCodes() {
		return rightCodes;
	}
	public void setRightCodes(Long rightCodes) {
		this.rightCodes = rightCodes;
	}
	public int getRightPos() {
		return rightPos;
	}
	public void setRightPos(int rightPos) {
		this.rightPos = rightPos;
	}
}
