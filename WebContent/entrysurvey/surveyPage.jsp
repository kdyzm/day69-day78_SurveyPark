<%@ page language="java" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<!-- 
	天哪，简直不能认了，必须将struts2标签全部换成html标签才能正常
	否则不能正常解析动态指定的name属性，怪不得那个老师不使用struts2标签，我还以为他是傻X呢
	
	最后一个问题：下拉列表类型的问题，是不是必须要有个默认值才行，不是默认选择第一个吗。
	但是通过统计答案的过程中发现在下拉列表框中如果没有给他一个默认值的话，就默认没有选择。
 -->
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<link rel="stylesheet"
	href="${pageContext.servletContext.contextPath}/css/header.css"
	type="text/css">
<style type="text/css">
table {
	border: 1px solid black;
	border-collapse: collapse;
	width: 100%;
}
tr td {
	border: 1px solid black;
	border-collapse: collapse;
}

a {
	color: gray;
	text-decoration: none;
}

a:HOVER {
	color: red;
}

.tdHL {
	text-align: left;
	border-width: 0px;
}

.tdHR {
	text-align: right;
	border-width: 0px;
}

.pageTitle {
	background-color: #CCF;
}

.questionTitle {
	background-color: #CCC;
}
</style>
</head>
<body>
	<%@ include file="/header.jsp"%>
	这里是答题界面！
	<s:form namespace="/" action="EntrySurveyAction_doEntrySurvey.action">
		<div>
			<s:set value="%{#page.survey.surveyId}" name="id" />
			<!-- 当前页的页码 -->
			<s:hidden name="pageId" value="%{#page.pageId}"></s:hidden>
			<table>
				<s:set var="pId" value="%{#page.pageId}"></s:set>
				<tr>
					<td>
						<table style="display: inline-table;">
							<tr class="pageTitle">
								<!-- 页面标题 -->
								<td colspan="2" width="40%" class="tdHL"><s:property
										value="%{#page.title}" /></td>
							</tr>
							<tr>
								<td colspan="2">
									<table style="display: inline-table;">
										<tr>
											<td width="20px"></td>
											<td>
												<!-- 迭代问题的集合 -->
												<table style="display: inline-table;">
													<s:iterator value="%{#page.questions}" var="question">
														<s:set var="qid" value="%{#question.questionId}"></s:set>
														<!-- 问题题干 -->
														<tr class="questionTitle">
															<td colspan="2" class="tdHL"><s:property
																	value="%{#question.title}" /></td>
														</tr>
														<!-- 问题主体，主要涉及的问题就是问题的分类 -->
														<tr>
															<td colspan="2">
																<!-- 定义变量，为当前类型的题型 --> <s:set
																	value="%{#question.questionType}" var="qt"></s:set> <!-- 第一种题型：带有单选框或者复选框的 
																题目标识就是题号小于4，0-3
															--> <s:if test="#qt lt 4">
																	<s:iterator value="#question.optionTextArr" status="st">
																		<input name="q<s:property value='#qid'/>"
																			value='<s:property value="#st.index"/>'
																			type='<s:property value="#qt<2?'radio':'checkbox'"/>'
																			<s:property value="setTag(#pId+'','q'+#qid,#st.index,'checked')"/>
																			>
																		<s:property />
																		<s:if test="#qt==1 || #qt==3">
																			<br />
																		</s:if>
																	</s:iterator>
																	<!-- 处理other的情况 -->
																	<s:if test="#question.other">
																		<input name="q<s:property value='#qid'/>"
																			value="other"
																			<s:property value="setTag(#pId+'','q'+#qid,'other','checked')"/>
																			type='<s:property value="#qt<2?'radio':'checkbox'"/>'>其它
																		<s:if test="#question.otherType==1">
																			<input type="text" name='q<s:property value="#qid"/>other'
																				<s:property value="setText(#pId,'q'+#qid+'other')"/>
																			/>
																		</s:if>
																		<s:elseif test="#question.otherType==2">
																			<select name="q<s:property value='#qid'/>other">
																				<s:iterator value="#question.otherSelectOptionArr" status="st">
																					<option 
																						<s:property value="setTag(#pId+'','q'+#qid+'other',#st.index,'selected')"/>
																						value='<s:property value="#st.index"/>'>
																						<s:property/>
																					</option>
																				</s:iterator>
																			</select>
																		</s:elseif>
																	</s:if>
																</s:if> 
																<!-- 第二种题型,是下拉列表类型的题型 --> 
																<s:elseif test="#qt==4">
																	<select name="q<s:property value='#qid'/>">
																		<s:iterator value="#question.optionTextArr" status="st">
																			<option
																				<s:property value="setTag(#pId+'','q'+#qid,#st.index,'selected')"/>
																			 value='<s:property value="#st.index"/>'><s:property/></option>
																		</s:iterator>
																	</select>
																</s:elseif> 
																<s:elseif test="#qt==5">
																	<input type="text" name="q<s:property value='#qid'/>" 
																	<s:property value="setText(#pId,'q'+#qid)"/>
																	/>
																</s:elseif> <!-- 第三种题型,矩阵问题，6,7,8 --> <s:else>
																	<table style="display: inline-table;">
																		<!-- 列头 -->
																		<tr>
																			<td></td>
																			<s:iterator value="#question.matrixColTitleArr">
																				<td><s:property /></td>
																			</s:iterator>
																		</tr>
																		<!-- 输出N多行 -->
																		<s:iterator value="#question.matrixRowTitleArr"
																			status="rowst">
																			<tr>
																				<td><s:property /></td>
																				<s:iterator value="#question.matrixColTitleArr"
																					status="colst">
																					<td><s:if test="#qt==6">
																							<!-- 对于矩阵式单选按钮的问题，必须将每一行视为一个单独的问题 -->
																							<input type="radio"
																								value='<s:property value="#rowst.index+'_'+#colst.index"/>'
																								name="q<s:property value='#qid+"_"+#rowst.index'/>"
																								<s:property value="setTag(#pId+'','q'+#qid+'_'+#rowst.index,#rowst.index+'_'+#colst.index,'checked')"/>
																								>
																						</s:if> <s:elseif test="#qt==7">
																							<input type="checkbox"
																								value='<s:property value="#rowst.index+'_'+#colst.index"/>'
																								name="q<s:property value='#qid'/>"
																								<s:property value="setTag(#pId+'','q'+#qid,#rowst.index+'_'+#colst.index,'checked')"/>
																								>
																						</s:elseif> <s:elseif test="#qt==8">
																							<select name="q<s:property value='#qid'/>">
																								<s:iterator
																									status="selst"
																									value="#question.matrixSelectOptionArr">
																									<option value='<s:property value="#rowst.index+'_'+#colst.index+'_'+#selst.index"/>'
																										<s:property value="setTag(#pId+'','q'+#qid,#rowst.index+'_'+#colst.index+'_'+#selst.index,'selected')"/>
																									>
																										<s:property />
																									</option>
																								</s:iterator>
																							</select>
																						</s:elseif></td>
																				</s:iterator>
																			</tr>
																		</s:iterator>
																	</table>
																</s:else>
															</td>
														</tr>
													</s:iterator>
												</table>
											</td>
										</tr>
									</table>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
			<!-- 导航栏界面的问题，包括上一题、下一题、退出按钮的设计 -->
			<div
				style="width: 30%; margin: 0 auto; text-align: center; margin-top: 30px; font-size: 20px;">
				<s:if test="#page.orderNo!=#page.survey.minOrderNo">
					<input type="submit"
						value="<s:property value='#page.survey.submit_pre'/>"
						name="submit_pre" />
				</s:if>
				<s:if test="#page.orderNo!=#page.survey.maxOrderNo">
					<input type="submit"
						value="<s:property value='#page.survey.submit_next'/>"
						name="submit_next">
				</s:if>
				<s:if test="#page.orderNo==#page.survey.maxOrderNo">
					<%-- <input type="submit"
						value="<s:property value='#page.survey.submit_done'/>"
						name="submit_done"> --%>
					<s:submit name="submit_done" value="%{#page.survey.submit_done}"></s:submit>
				</s:if>
				<input type="submit"
					value="<s:property value='#page.survey.submit_exit'/>"
					name="submit_exit">
			</div>
		</div>
	</s:form>
</body>
</html>