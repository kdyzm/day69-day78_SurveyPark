package com.kdyzm.struts.action;

import java.awt.Font;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

import javax.annotation.Resource;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.kdyzm.datasource.SurveyToken;
import com.kdyzm.domain.Question;
import com.kdyzm.domain.Survey;
import com.kdyzm.domain.statistic.OptionStatisticModel;
import com.kdyzm.domain.statistic.QuestionStatisticModel;
import com.kdyzm.service.QuestionService;
import com.kdyzm.service.StatisticService;
import com.kdyzm.service.SurveyService;
import com.kdyzm.struts.action.base.BaseAction;
import com.opensymphony.xwork2.ActionContext;

/**
 * 进行统计分析的Action
 * 
 * @author kdyzm
 *
 */
@Controller
@Scope("prototype")
public class StatisticAction extends BaseAction<Survey> {
	private static final long serialVersionUID = 7348499753359866614L;
	@Resource(name = "statisticService")
	private StatisticService statisticService;
	@Resource(name = "surveyService")
	private SurveyService surveyService;
	@Resource(name = "questionService")
	private QuestionService questionService;
	private JFreeChart chart;
	private Integer questionId; // 问题的唯一标识ID
	private Integer statisticType; // 统计图的类型

	public Integer getStatisticType() {
		return statisticType;
	}

	public void setStatisticType(Integer statisticType) {
		this.statisticType = statisticType;
	}

	public Integer getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Integer questionId) {
		this.questionId = questionId;
	}
	public void setChart(JFreeChart chart) {
		this.chart = chart;
	}
	// 只需要提供一个getChart方法即可
	public JFreeChart getChart() {
		return chart;
	}

	// 显示所有调查内容的页面
	public String toShowSurveyPage() throws Exception {
		this.model = this.surveyService.getModelById(this.model.getSurveyId());
		ActionContext.getContext().put("survey", this.model);
		return "toShowSurveyPage";
	}

	// 矩阵式问题统计
	public String statistic() throws Exception {
		chart = null;
		/**
		 * 在统计之前先绑定数据
		 */
		System.out.println("访问了StatisticAction的statistic方法！");
		Question question=questionService.getQuestion(questionId);
		Survey survey=question.getPage().getSurvey();
		SurveyToken surveyToken=new SurveyToken();
		surveyToken.setSurvey(survey);
		SurveyToken.bind(surveyToken);
		System.out.println("即将访问statisticService的统计方法！");
		QuestionStatisticModel questionStatisticModel = this.statisticService.statics(question);
		//解除绑定，实际上已经解除绑定过一次了但是好像并不管用
		SurveyToken.unbind();
		DefaultPieDataset pieDataset = null;
		DefaultCategoryDataset categoryDataset = null;
		try {
			if (statisticType < 2) {// 饼图
				pieDataset = new DefaultPieDataset();
				for (OptionStatisticModel option : questionStatisticModel.getOsms()) {
					pieDataset.setValue(option.getOptionLabel(), option.getCount());
				}
			} else { // 其它
				categoryDataset = new DefaultCategoryDataset();
				for (OptionStatisticModel option : questionStatisticModel.getOsms()) {
					categoryDataset.setValue(option.getCount(), option.getOptionLabel(), "");
				}
			}
			switch (this.statisticType) {
			case 0:
				// 平面饼图
				chart = ChartFactory.createPieChart(questionStatisticModel.getQuestion().getTitle(), pieDataset, true,
						false, false);
				((PiePlot)chart.getPlot()).setLabelGenerator(new StandardPieSectionLabelGenerator("{0}/{1}/{2}/{3}"));
				break;
			case 1:
				// 立体饼图
				chart = ChartFactory.createPieChart3D(questionStatisticModel.getQuestion().getTitle(), pieDataset, true,
						true, true);
				chart.getPlot().setForegroundAlpha(0.7F); // 设置透明度
				((PiePlot)chart.getPlot()).setLabelGenerator(new StandardPieSectionLabelGenerator("{0}/{1}/{2}/{3}"));
				break;
			case 2:
				// 横向平面柱状图
				chart = ChartFactory.createBarChart(questionStatisticModel.getQuestion().getTitle(),  "选项","单位（人）",
						categoryDataset, PlotOrientation.HORIZONTAL, true, true, true);
				break;
			case 3:
				// 纵向平面柱状图
				chart = ChartFactory.createBarChart(questionStatisticModel.getQuestion().getTitle(),  "选项","单位（人）",
						categoryDataset, PlotOrientation.VERTICAL, true, true, true);
				break;
			case 4:
				chart = ChartFactory.createBarChart3D(questionStatisticModel.getQuestion().getTitle(),  "选项","单位（人）",
						categoryDataset, PlotOrientation.HORIZONTAL, true, true, true);
				break;
			// 横向立体柱状图
			case 5:
				chart = ChartFactory.createBarChart3D(questionStatisticModel.getQuestion().getTitle(),  "选项","单位（人）",
						categoryDataset, PlotOrientation.VERTICAL, true, true, true);
				break;
			// 纵向立体柱状图
			case 6:
				/**
				 * 折线图的生成发生了问题
				 * TODO 这里的折线图如果都是同一个组中的，那么就不会显示折线图了
				 * 必须至少有两个组才行
				 */
				chart = ChartFactory.createLineChart(questionStatisticModel.getQuestion().getTitle(), "", "",
						categoryDataset, PlotOrientation.VERTICAL, true, false, false);
				break;
			// 平面折线图
			case 7:
				// 立体折线图 break; default:
				chart = ChartFactory.createLineChart3D(questionStatisticModel.getQuestion().getTitle(), "", "",
						categoryDataset, PlotOrientation.VERTICAL, true, false, false);
				break;
			}
			// 集中解决中文乱码问题
			chart.getTitle().setFont(new Font("宋体", Font.BOLD, 30));
			chart.getLegend().setItemFont(new Font("宋体", Font.PLAIN, 16));
			if (chart.getPlot() instanceof PiePlot) {
				PiePlot piePlot = (PiePlot) chart.getPlot();
				piePlot.setLabelFont(new Font("宋体", Font.PLAIN, 20));
			} else {
				Font font = new Font("宋体", Font.PLAIN, 15);
				chart.getCategoryPlot().getDomainAxis().setLabelFont(font);
				chart.getCategoryPlot().getDomainAxis().setTickLabelFont(font);
				chart.getCategoryPlot().getRangeAxis().setLabelFont(font);
				chart.getCategoryPlot().getRangeAxis().setTickLabelFont(font);
				chart.getPlot().setForegroundAlpha(0.7F);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("StatisticAction的统计方法结束");
		return SUCCESS;
	}
	private Question question;
	public Question getQuestion() {
		return question;
	}
	public void setQuestion(Question question) {
		this.question = question;
	}
	private QuestionStatisticModel questionStatisticModel;
	public QuestionStatisticModel getQuestionStatisticModel() {
		return questionStatisticModel;
	}
	public void setQuestionStatisticModel(QuestionStatisticModel questionStatisticModel) {
		this.questionStatisticModel = questionStatisticModel;
	}
	// 矩阵式问题统计图
	public String statisticMatrix() throws Exception {
		question=this.questionService.getQuestion(this.questionId);
		questionStatisticModel=this.statisticService.statics(question);
		ActionContext.getContext().getValueStack().push(questionStatisticModel);
		ActionContext.getContext().put("colors", colors);
		return ""+question.getQuestionType();
	}
	
	public String getPercent(int row,int col) throws Exception{
		int qcount=this.questionStatisticModel.getCount();
		int ocount=0;
		for(OptionStatisticModel osm:this.questionStatisticModel.getOsms()){
			if(osm.getMatirxRowIndex()==row&&osm.getMatrixColIndex()==col){
				ocount=osm.getCount();
				break;
			}
		}
		double result=(double)ocount/(double)qcount;
		result=result*100;
		DecimalFormat format=new DecimalFormat("#,###.00");
		return ""+ocount+"{"+format.format(result)+"%}";
	}
	String colors[]={
		"aqua",
		"black",
		"blue",
		"fuchsia",
		"fuchsia",
		"gray",
		"green",
		"lime",
		"maroon",
		"navy",
		"olive",
		"orange",
	};
	public String[] getColors() {
		return colors;
	}
	public void setColors(String[] colors) {
		this.colors = colors;
	}
	public String getWidth(int row,int col,int op) throws Exception{
		int qcount=this.questionStatisticModel.getCount();
		int ocount=0;
		for(OptionStatisticModel osm:this.questionStatisticModel.getOsms()){
			if(osm.getMatirxRowIndex()==row&&osm.getMatrixColIndex()==col&&osm.getMatrixSelectIndex()==op){
				ocount=osm.getCount();
				break;
			}
		}
		double result=(double)ocount/(double)qcount;
		result=result*100;
		return (int)result*2+"";
	}
	public String getPercent(int row,int col,int op) throws Exception{
		int qcount=this.questionStatisticModel.getCount();
		int ocount=0;
		for(OptionStatisticModel osm:this.questionStatisticModel.getOsms()){
			if(osm.getMatirxRowIndex()==row&&osm.getMatrixColIndex()==col&&osm.getMatrixSelectIndex()==op){
				ocount=osm.getCount();
				break;
			}
		}
		double result=(double)ocount/(double)qcount;
		result=result*100;
		DecimalFormat format=new DecimalFormat("#,###.00");
		return ""+ocount+"("+format.format(result)+"%)";
	}
}
