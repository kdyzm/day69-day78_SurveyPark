<%@ page language="java" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" href="${pageContext.servletContext.contextPath}/css/header.css" type="text/css">
<style type="text/css">
	table{
		border: 1px solid black;
		border-collapse: collapse;
		width: 100%;
	}
	table tr{
		border-collapse: collapse;
	}
	tr td{
		border:1px solid black;
		border-collapse: collapse;
	}
	a{
		color: gray;
		text-decoration: none;
	}
	a:HOVER {
		color: red;
	}
	.tdHL{
		text-align: left;
		border-width: 0px;
	}
	.tdHR{
		text-align: right;
		border-width: 0px;
	}
	.pageTitle{
		background-color: #CCF;
	}
	.questionTitle{
		background-color: #CCC;
	}
</style>
<title>Insert title here</title>
</head>
<body>
	<%@ include file="/header.jsp" %>
	<div>
		<!-- 先设计一个变量保存住surveyid -->
		<s:set value="%{surveyId}" name="id"/>
		<table>
			<tr>
				<td colspan="2">设计调查</td>
			</tr>
			<tr>
				<td class="tdHL">
					<!-- 这里使用sturs2标签直接判断图片是否存在! -->
					<!-- 在这里加上一个logo标识 -->
					<s:if test="isLogoImageExists()">
						<img width="40px"  alt="这是logo标识" src="<s:url value='%{logoPath}'/>"/>
					</s:if>
					<s:else>
						<!-- 如果图片不存在，则什么都不显示 -->
					</s:else>
					<s:property value="title"/>
				</td>
				<td class="tdHR">
					<s:a action="SurveyAction_toUploadLogoPage.action" namespace="/">
						<s:param name="surveyId" value="%{#id}"></s:param>
					增加Logo
					</s:a>
					&nbsp;
					<s:a action="SurveyAction_toEditSurveyPage.action" namespace="/">
						<s:param name="surveyId" value="%{#id}"></s:param>
					编辑调查</s:a>&nbsp;
					<s:a action="PageAction_toAddPagePage.action" namespace="/">
						<s:param value="%{#id}" name="surveyId"></s:param>
					增加页</s:a>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<!-- 主干内容开始 -->
					<table>
						<tr>
							<td width="20px"></td>
							<td width="*">
								<!-- 迭代页面集合 -->
								<table>
									<s:iterator value="%{pages}" var="page">
										<s:set var="pId" value="%{#page.pageId}"></s:set>
										<tr>
											<td>
												<table>
													<tr class="pageTitle">
														<!-- 页面标题 -->
														<td width="40%" class="tdHL">
															<s:property value="%{#page.title}"/>
														</td>
														<td width="60%" class="tdHR">
															<s:a action="PageAction_toEditPageTitlePage.action" namespace="/">
																<s:param name="pageId" value="%{#pId}"></s:param>
																<s:param name="surveyId" value="%{#id}"></s:param>
															编辑页面标题</s:a>&nbsp;
															<s:a action="PageAction_toSelectTargetPage.action" namespace="/">
																<s:param name="pageId" value="%{#pId}"></s:param>
																<s:param name="surveyId" value="%{#id}"></s:param>
															移动/复制页
															</s:a>
															&nbsp;
															<s:a action="QuestionAction_toSelectQuestionTypePage.action" namespace="/">
																<s:param name="pageId" value="%{#pId}"></s:param>
																<s:param name="surveyId" value="%{#id}"></s:param>
															增加问题</s:a>&nbsp;
															<s:a action="PageAction_deletePage.action" namespace="/">
																<s:param name="pageId" value="%{#pId}"></s:param>
																<s:param name="surveyId" value="%{#id}"></s:param>
															删除页</s:a>
														</td>
													</tr>
													<tr>
														<td colspan="2">
															<table>
																<tr>
																	<td width="20px"></td>
																	<td>
																		<!-- 迭代问题的集合 -->
																		<table>
																			<s:iterator value="%{#page.questions}" var="question">
																				<s:set var="qid" value="%{#question.questionId}"></s:set>
																				<!-- 问题题干 -->
																				<tr class="questionTitle">
																					<td class="tdHL"><s:property value="%{#question.title}"/></td>
																					<td class="tdHR">
																						<s:a action="QuestionAction_editQuestion.action" namespace="/">
																							<s:param name="pageId" value="%{#pId}"></s:param>
																							<s:param name="surveyId" value="%{#id}"></s:param>
																							<s:param name="questionId" value="%{#qid}"></s:param>
																						编辑问题</s:a>&nbsp;
																						<s:a action="QuestionAction_deleteQuestion.action" namespace="/">
																							<s:param name="pageId" value="%{#pId}"></s:param>
																							<s:param name="surveyId" value="%{#id}"></s:param>
																							<s:param name="questionId" value="%{#qid}"></s:param>
																						删除问题</s:a>&nbsp;
																					</td>
																				</tr>
																				<!-- 问题主体，主要涉及的问题就是问题的分类 -->
																				<tr>
																					<td colspan="2">
																						<!-- 定义变量，为当前类型的题型 -->
																						<s:set value="%{#question.questionType}" var="qt"></s:set>
																						<!-- 第一种题型：带有单选框或者复选框的 
																							题目标识就是题号小于4，0-3
																						-->
																						<s:if test="#qt lt 4">
																							<s:iterator value="#question.optionTextArr">
																								<input type='<s:property value="#qt<2?'radio':'checkbox'"/>'>
																								<s:property/>
																								<s:if test="#qt==1 || #qt==3">
																									<br/>
																								</s:if>
																							</s:iterator>
																							<!-- 处理other的情况 -->
																							<s:if test="#question.other">
																								<input type='<s:property value="#qt<2?'radio':'checkbox'"/>'>其它
																								<s:if test="#question.otherType==1">
																									<input type="text">
																								</s:if>
																								<s:elseif test="#question.otherType==2">
																									<s:select list="#question.otherSelectOptionArr">
																									</s:select>
																								</s:elseif>
																							</s:if>
																						</s:if>
																						<!-- 第二种题型,是下拉列表类型的题型 -->
																						<s:elseif test="#qt==4">
																							<s:select list="#question.optionTextArr"></s:select>
																						</s:elseif>
																						<s:elseif test="#qt==5">
																							<s:textfield></s:textfield>
																						</s:elseif>
																						<!-- 第三种题型,矩阵问题，6,7,8 -->
																						<s:else>
																							<table>
																								<!-- 列头 -->
																								<tr>
																									<td></td>
																									<s:iterator value="#question.matrixColTitleArr">
																										<td><s:property/></td>
																									</s:iterator>
																								</tr>
																								<!-- 输出N多行 -->
																								<s:iterator value="#question.matrixRowTitleArr">
																									<tr>
																										<td><s:property/></td>
																										<s:iterator value="#question.matrixColTitleArr">
																											<td>
																												<s:if test="#qt==6">
																													<input type="radio">
																												</s:if>
																												<s:elseif test="#qt==7">
																													<input type="checkbox">
																												</s:elseif>
																												<s:elseif test="#qt==8">
																													<select>
																														<s:iterator value="#question.matrixSelectOptionArr">
																															<option>
																																<s:property/>
																															</option>
																														</s:iterator>
																													</select>
																												</s:elseif>
																											</td>
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
									</s:iterator>
								</table>
							</td>
						</tr>
					</table>				
				</td>
			</tr>
		</table>
	</div>
</body>
</html>