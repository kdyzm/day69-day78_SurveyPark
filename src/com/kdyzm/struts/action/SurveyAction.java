package com.kdyzm.struts.action;

import java.io.File;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.util.ServletContextAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.kdyzm.domain.Survey;
import com.kdyzm.domain.User;
import com.kdyzm.service.SurveyService;
import com.kdyzm.struts.action.aware.UserAware;
import com.kdyzm.struts.action.base.BaseAction;
import com.kdyzm.utils.FileUploadUtils;
import com.opensymphony.xwork2.ActionContext;
@Controller("surveyAction")
@Scope("prototype")
public class SurveyAction extends BaseAction<Survey> implements UserAware,ServletContextAware{
	private static final long serialVersionUID = -2866898387974853835L;
	private User user;
	@Resource(name="surveyService")
	private SurveyService surveyService;
	/**
	 * 创建新的Survey的方法
	 * @return
	 * @throws Exception
	 */
	public String createNewSurvey() throws Exception{
		//创建完成之后直接转到显示Survey的页面上
		this.surveyService.createNewSurvey(user);
		return "toMySurveyPageAction";
	}
	@Override
	public void setUser(User user) {
		this.user=user;
	}
	//跳转到我的调查页面
	public String toMySurveyPage() throws Exception{
		List<Survey>surveys=this.surveyService.findMySurveys(user);
		ActionContext.getContext().put("surveys", surveys);
		return "toMySurveyPage";
	}
	//跳转到设计Survey界面上
	public String designSurveyPage() throws Exception{
		HttpServletRequest request=ServletActionContext.getRequest();
		String surveyId=request.getParameter("surveyId");
		this.model=this.surveyService.getModelById(Integer.parseInt(surveyId));
		System.out.println(model.getPages().size());
		ActionContext.getContext().getValueStack().push(model);
		return "designSurveyPage";
	}
	
	//跳转到编辑调查的页面上去
	public String toEditSurveyPage() throws Exception{
		this.model=this.surveyService.getModelById(this.model.getSurveyId());
		return "toEditSurveyPage";
	}
	public String editSurvey() throws Exception{
		//这里由于Survey和User之间是单向关联关系，所以必须手动维护关系，但是和Pages之间的关系就不需要维护了
		this.model.setUser(user);
		this.surveyService.updateSurvey(this.model);
		return "toDesignSurveyPageAction";
	}
	//删除调查的方法
	public String deleteSurvey() throws Exception{
		this.surveyService.deleteSurvey(this.model);
		return "toMySurveyPageAction";
	}
	/**
	 * 清除调查的方法
	 * 清除调查：就是在正式将该调查发布之前，首先将自己之前的测试数据全部删除掉
	 * @return
	 * @throws Exception
	 */
	public String clearSurvey() throws Exception{
		this.surveyService.clearSurvey(this.model);
		return "toMySurveyPageAction";
	}
	/**
	 * 打开或者关闭调查的方法
	 * @return
	 * @throws Exception
	 */
	public String openOrCloseSurvey() throws Exception{
		System.out.println("访问了SurveyAction的openOrCloseSurvey方法！");
		this.surveyService.updateSurveyClosedState(this.model);
		System.out.println("访问完成SurveyAction的openOrCloseSurvey方法！");
		return "toMySurveyPageAction";
	}
	//跳转到上传logo的页面
	public String toUploadLogoPage() throws Exception{
		return "toUploadLogoPage";
	}
	//上传的logo图片
	private File logo;					//上传的logo的文件
	private String logoFileName ;		//上传的文件的名称
	private String logoContentType ;	//上传的文件的类型
	public String getLogoFileName() {
		return logoFileName;
	}
	public void setLogoFileName(String logoFileName) {
		this.logoFileName = logoFileName;
	}
	public String getLogoContentType() {
		return logoContentType;
	}
	public void setLogoContentType(String logoContentType) {
		this.logoContentType = logoContentType;
	}
	public File getLogo() {
		return logo;
	}
	public void setLogo(File logo) {
		this.logo = logo;
	}
	
	//实施上传logo动作的方法
	public String doUploadLogo() throws Exception{
		//首先需要保存住上传的文件！
		String fileName=FileUploadUtils.saveUploadFileToDestDir(logo, logoFileName);
		//接着需要将保存住的文件和Survey对象关联起来
		model=this.surveyService.getModelById(model.getSurveyId());
		model.setLogoPath(fileName);
		System.out.println(fileName);
		this.surveyService.updateSurvey(model);
		return "toDesignSurveyPageAction";
	}
	private ServletContext sc;
	@Override
	public void setServletContext(ServletContext context) {
		this.sc=context;
	}
	//一个方法专门判断上传的文件是否存在！
	public boolean isLogoImageExists() throws Exception{
		String fileName=this.model.getLogoPath();
		File file=new File(sc.getRealPath(fileName));
		return file.exists();
	}
}
