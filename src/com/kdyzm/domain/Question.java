package com.kdyzm.domain;

import java.io.Serializable;

import com.kdyzm.utils.StringUtils;

/**
 * 该类是问题类
 * @author kdyzm
 *有五个数组属性的成员变量都没有配置到配置文件中，只需要在set方法中进行数组拆分即可
 */
public class Question implements Serializable{
	private static final long serialVersionUID = 9035567610697913247L;
	private transient Integer questionId;				//问题的ID
	/**
	 * 题型分为0-8一共九种类型
	 */
	private int questionType;			//问题的题型
	private String title;			//问题的标题
	private String optionText;			//问题的选项
	private String[]optionTextArr;												//问题选项的集合
	
	private boolean other;			//其他项
	//其他项可能是无、文本框、下拉列表框
	private String otherType;		//其他项的样式
	private String otherSelectOptions;	//其他项如果是下拉列表框的话使用该项作为内容
	private String[] otherSelectOptionArr;										//该字段对应着其他项是多选框的情况，这里存放着拆分之后的字符串数组
	
	private String matrixRowTitles;		//矩阵式行标题集
	private String[] matrixRowTitleArr;											//矩阵式行标题集数组
	private String matrixColTitles;		//矩阵式列标题集
	private String[] matrixColTitleArr;											//矩阵式列标题集数组
	private String matrixSelectOptions;		//矩阵式下拉选项集
	private String []matrixSelectOptionArr;										//矩阵式下拉列表
	
	//Question和Page之间是多对一的关系
	private Page page;					//该问题对应哪一页
/***********************************************************************/
	public int getQuestionType() {
		return questionType;
	}

	public String[] getOptionTextArr() {
		return optionTextArr;
	}
	
	public void setOptionTextArr(String[] optionTextArr) {
		this.optionTextArr = optionTextArr;
	}
	
	public String[] getMatrixRowTitleArr() {
		return matrixRowTitleArr;
	}
	
	public void setMatrixRowTitleArr(String[] matrixRowTitleArr) {
		this.matrixRowTitleArr = matrixRowTitleArr;
	}
	
	public String[] getMatrixColTitleArr() {
		return matrixColTitleArr;
	}
	
	public void setMatrixColTitleArr(String[] matrixColTitleArr) {
		this.matrixColTitleArr = matrixColTitleArr;
	}
	
	public String[] getMatrixSelectOptionArr() {
		return matrixSelectOptionArr;
	}
	
	public void setMatrixSelectOptionArr(String[] matrixSelectOptionArr) {
		this.matrixSelectOptionArr = matrixSelectOptionArr;
	}

	public String[] getOtherSelectOptionArr() {
		return otherSelectOptionArr;
	}
	
	public void setOtherSelectOptionArr(String[] otherSelectOptionArr) {
		this.otherSelectOptionArr = otherSelectOptionArr;
	}

	public Integer getQuestionId() {
		return questionId;
	}
	
	public void setQuestionId(Integer questionId) {
		this.questionId = questionId;
	}

	public void setQuestionType(int questionType) {
		this.questionType = questionType;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getOptionText() {
		return optionText;
	}

	public void setOptionText(String optionText) {
		this.optionText = optionText;
		this.optionTextArr=StringUtils.String2Arr(optionText);
	}
	//TODO boolean类型的变量值的get/set方法产生变异？？？
	public boolean isOther() {
		return other;
	}

	public void setOther(boolean other) {
		this.other = other;
	}

	public String getOtherType() {
		return otherType;
	}

	public void setOtherType(String otherType) {
		this.otherType = otherType;
	}

	public String getOtherSelectOptions() {
		return otherSelectOptions;
	}

	public void setOtherSelectOptions(String otherSelectOptions) {
		this.otherSelectOptions = otherSelectOptions;
		this.otherSelectOptionArr=StringUtils.String2Arr(otherSelectOptions);
	}

	public String getMatrixRowTitles() {
		return matrixRowTitles;
	}

	public void setMatrixRowTitles(String matrixRowTitles) {
		this.matrixRowTitles = matrixRowTitles;
		this.matrixRowTitleArr=StringUtils.String2Arr(matrixRowTitles);
	}

	public String getMatrixColTitles() {
		return matrixColTitles;
	}

	public void setMatrixColTitles(String matrixColTitles) {
		this.matrixColTitles = matrixColTitles;
		this.matrixColTitleArr=StringUtils.String2Arr(matrixColTitles);
	}

	public String getMatrixSelectOptions() {
		return matrixSelectOptions;
	}

	public void setMatrixSelectOptions(String matrixSelectOptions) {
		this.matrixSelectOptions = matrixSelectOptions;
		this.matrixSelectOptionArr=StringUtils.String2Arr(matrixSelectOptions);
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}
/*************常量定义区域***************************************************/
	//定义了问题的类型的常量
	public static class QuestionType{
		
	}
	//定义了其他项的选项值
	public static class OtherStyle{
		
	}
}
