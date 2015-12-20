package com.kdyzm.domain.statistic;

import java.io.Serializable;

/**
 * 选项统计模型
 * 分析选项统计模型的设计的问题是一个比较难的问题
 * 实现该选项模型的设计的关键就是必须考虑到所有种类的问题
 * 		并不是每个属性都需要赋值，一定是只有部分属性用的到
 * @author kdyzm
 *
 */
public class OptionStatisticModel implements Serializable{
	private static final long serialVersionUID = -1193710788274579019L;
	//选项标签
	private String optionLabel;	
	//选项的索引值
	private int optionIndex;	
	//矩阵型的行标签
	private String matrixRowLabel;
	//矩阵型的航标签的索引值
	private int matirxRowIndex;
	//矩阵型的列标签
	private String matrixColLabel;
	//矩阵型问题的列标签的索引值
	private int matrixColIndex;
	//矩阵型下拉列表标签
	private String matrixSelectLabel;
	//矩阵型下拉列表标签的索引值
	private int matrixSelectIndex;
	//该选项有多少人选择
	private int count;
/*********************************华丽的分割线******************************************/
	public String getOptionLabel() {
		return optionLabel;
	}

	public void setOptionLabel(String optionLabel) {
		this.optionLabel = optionLabel;
	}

	public int getOptionIndex() {
		return optionIndex;
	}

	public void setOptionIndex(int optionIndex) {
		this.optionIndex = optionIndex;
	}

	public String getMatrixRowLabel() {
		return matrixRowLabel;
	}

	public void setMatrixRowLabel(String matrixRowLabel) {
		this.matrixRowLabel = matrixRowLabel;
	}

	public int getMatirxRowIndex() {
		return matirxRowIndex;
	}

	public void setMatirxRowIndex(int matirxRowIndex) {
		this.matirxRowIndex = matirxRowIndex;
	}

	public String getMatrixColLabel() {
		return matrixColLabel;
	}

	public void setMatrixColLabel(String matrixColLabel) {
		this.matrixColLabel = matrixColLabel;
	}

	public int getMatrixColIndex() {
		return matrixColIndex;
	}

	public void setMatrixColIndex(int matrixColIndex) {
		this.matrixColIndex = matrixColIndex;
	}

	public String getMatrixSelectLabel() {
		return matrixSelectLabel;
	}

	public void setMatrixSelectLabel(String matrixSelectLabel) {
		this.matrixSelectLabel = matrixSelectLabel;
	}

	public int getMatrixSelectIndex() {
		return matrixSelectIndex;
	}

	public void setMatrixSelectIndex(int matrixSelectIndex) {
		this.matrixSelectIndex = matrixSelectIndex;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

}
