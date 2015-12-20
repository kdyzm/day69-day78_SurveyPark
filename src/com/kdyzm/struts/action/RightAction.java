package com.kdyzm.struts.action;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.kdyzm.domain.security.Right;
import com.kdyzm.service.RightService;
import com.kdyzm.struts.action.base.BaseAction;
import com.opensymphony.xwork2.ActionContext;
@Controller
@Scope("prototype")
public class RightAction extends BaseAction<Right>{
	private static final long serialVersionUID = -7795563058273704791L;
	
	@Resource(name="rightService")
	private RightService rightService;
	
	private List<Right>rightList;
	
	public List<Right> getRightList() {
		return rightList;
	}

	public void setRightList(List<Right> rightList) {
		this.rightList = rightList;
	}

	public String toRightManagementPage() throws Exception{
		this.rightList=new ArrayList<Right>(this.rightService.getAllRights());
		ActionContext.getContext().put("rightList", this.rightList);
		return "toRightManagementPage";
	}
	
	public String toSaveOrUpdateRightPage() throws Exception{
		if(this.model.getRightId()!=null)
		this.model=this.rightService.getRightById(this.model.getRightId());
		return "toSaveOrUpdateRightPage";
	}
	public String saveOrUpdateRightPage() throws Exception{
		this.rightService.saveOrUpateRight(model);
		return "toRightManagementPageAction";
	}
	public String deleteRight() throws Exception{
		this.rightService.deleteRight(this.model);
		return "toRightManagementPageAction";
	}
	//批处理更新权限公共资源权限和名字的方法
	//TODO 使用这种批量更新，接收不到封装完成的List对象，OGNL封装错误。
	public String updateBatchRights() throws Exception{
		rightService.updateBatchRights(this.rightList);
		return "toRightManagementPageAction";
	}
}
