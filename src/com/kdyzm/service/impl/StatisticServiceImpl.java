package com.kdyzm.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kdyzm.dao.impl.AnswerDaoImpl;
import com.kdyzm.dao.impl.QuestionDaoImpl;
import com.kdyzm.domain.Question;
import com.kdyzm.domain.statistic.OptionStatisticModel;
import com.kdyzm.domain.statistic.QuestionStatisticModel;
import com.kdyzm.service.StatisticService;
/**
 * 统计服务的实现类
 * @author kdyzm
 *
 */
@Service("statisticService")
public class StatisticServiceImpl implements StatisticService{
	@Resource(name="answerDao")
	private AnswerDaoImpl answerDao;
	@Resource(name="questionDao")
	private QuestionDaoImpl questionDao;

	//进行统计分析，实现统计分析模型的获取,这是最核心的一个方法
	/**
	 * 该方法一定已经开启了事务，而且该事务已经回滚成功了
	 * 但是已经确定的是，在进入该方法之前已经确定好了数据源
	 * 进入事务方法的时候就已经选择好了数据源
	 * 
	 * 最后决定将没没有声明的方法全部关闭掉事务
	 * 这样这个方法就应该不带有事务了
	 */
	@Override
	public QuestionStatisticModel statics(Question question){
		System.out.println("访问了QuestionStatisticService的statics方法");
		
		
		/*question.setTitle("测试事务");
		questionDao.updateEntity(question);*/
		
		//该方法逻辑上分析是加上了事务的，但是需要进行测试是否真正加上了事务。
		QuestionStatisticModel questionStatisticModel=new QuestionStatisticModel();
		
		//设置Question对象
		questionStatisticModel.setQuestion(question);
		
		//获取问题回答人数
		String hql="select count(*) from Answer a where questionId=?";
		int qcount=answerDao.getQuestionResponseCount(hql,question.getQuestionId());
		questionStatisticModel.setCount(qcount);
		
		//最重要的一个问题就是获取每个选项的统计问题，即填充List<OptionStatisticsModel>列表
		List<OptionStatisticModel>options=questionStatisticModel.getOsms();
		
		int questionType=question.getQuestionType();
		//使用到的hql语句都只是同一个
		hql="select count(*) from Answer a where a.questionId=? and concat(',',a.answerIndexs,',') like ?";
		//每个选项的统计数据初始化为0
		int ocount=0;				
		switch(questionType){
			case 0:
			case 1:
			case 2:
			case 3:
			case 4:
				String []optionArr=question.getOptionTextArr();
				OptionStatisticModel optionStatisticModel=null;
				for(int i=0;i<optionArr.length;i++){
					String option=optionArr[i];
					//这里i就是索引值，而opiton就是标签
					optionStatisticModel=new OptionStatisticModel();
					optionStatisticModel.setOptionLabel(option);
					optionStatisticModel.setOptionIndex(i);
					ocount=answerDao.getOptionResponseAmount(hql,question.getQuestionId(),"%,"+i+",%");
					optionStatisticModel.setCount(ocount);
					options.add(optionStatisticModel);
				}
				//这里还需要考虑到有其它选项的情况
				if(question.isOther()){
					//option就是标签
					String option="other";
					optionStatisticModel=new OptionStatisticModel();
					optionStatisticModel.setOptionLabel(option);
					ocount=answerDao.getOptionResponseAmount(hql,question.getQuestionId(),"%other%");
					optionStatisticModel.setCount(ocount);
					options.add(optionStatisticModel);
				}
				break;
			/*
			 * 类型5是文本框类型，不做统计
			 * */
			case 6:
			case 7:
			case 8:
				String []rows=question.getMatrixRowTitleArr();
				String []cols=question.getMatrixColTitleArr();
				String []selectOptionArr=question.getMatrixSelectOptionArr();
				//这里至少需要两重循环，最多需要三重循环（是下拉列表框的类型）
				for(int i=0;i<rows.length;i++){
					for(int j=0;j<cols.length;j++){
						//这里分为两种情况，一种情况是radio/checkbox的类型，一种情况是select类型
						
						//radio/checkbox类型的
						if(question.getQuestionType()<8){
							optionStatisticModel=new OptionStatisticModel();
							optionStatisticModel.setMatirxRowIndex(i);
							optionStatisticModel.setMatrixColLabel(rows[i]);
							optionStatisticModel.setMatrixColIndex(j);
							optionStatisticModel.setMatrixColLabel(cols[j]);
							
							ocount=answerDao.getOptionResponseAmount(hql, question.getQuestionId(), "%,"+i+"_"+j+",%");
							optionStatisticModel.setCount(ocount);
							options.add(optionStatisticModel);
						}
						//select类型的
						if(question.getQuestionType()==8){
							for(int k=0;k<selectOptionArr.length;k++){
								if(i==0&&j==0&&k==1){
									System.out.println("继续检测");
								}
								optionStatisticModel=new OptionStatisticModel();
								optionStatisticModel.setMatirxRowIndex(i);
								optionStatisticModel.setMatrixColLabel(rows[i]);
								optionStatisticModel.setMatrixColIndex(j);
								optionStatisticModel.setMatrixColLabel(cols[j]);
								optionStatisticModel.setMatrixSelectIndex(k);
								optionStatisticModel.setMatrixSelectLabel(selectOptionArr[k]);
								
								ocount=answerDao.getOptionResponseAmount(hql, question.getQuestionId(), "%,"+i+"_"+j+"_"+k+",%");
								optionStatisticModel.setCount(ocount);
								options.add(optionStatisticModel);
							}
						}
					}
				}
				break;
			default:break;
		}
		return questionStatisticModel;
	}
}
