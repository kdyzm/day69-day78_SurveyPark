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
		width: 95%;
		text-align: center;
		margin:5px auto;		
	}
	table td{
		border: 1px solid black;
		padding: 5px;
	}
</style>
<title>Insert title here</title>
</head>
<body>
	<%@ include file="/header.jsp" %>
	<div>
	<table>
		<thead>
			<tr>
				<td>ID</td>
				<td>调查标题</td>
				<td>创建时间</td>
				<td>状态</td>
				<td>设计</td>
				<td>收集信息</td>
				<td>分析</td>
				<td>打开/关闭</td>
				<td>清除调查</td>
				<td>删除</td>
			</tr>
		</thead>
		<tbody>
			<s:iterator value="%{#surveys}">
				<tr>
					<td>
						<s:property value="surveyId"/>
					</td>
					<td>
						<s:property value="title"/>
					</td>
					<td>
						<s:date name="createDate" format="yyyy/MM/dd HH:mm:ss"/>
					</td>
					<td>
						<s:if test="%{closed}">
							打开
						</s:if>
						<s:else>
							关闭
						</s:else>
					</td>
					<td>
						<s:a action="SurveyAction_designSurveyPage.action" namespace="/">
							<s:param name="surveyId" value="%{surveyId}"></s:param>
						设计</s:a>
					</td>
					<td>收集信息</td>
					<td>
						<s:a action="StatisticAction_toShowSurveyPage.action" namespace="/">
							<s:param name="surveyId" value="%{surveyId}"></s:param>
							分析
						</s:a>
					</td>
					<td>
						<s:a action="SurveyAction_openOrCloseSurvey.action" namespace="/">
							<s:param name="surveyId" value="%{surveyId}"></s:param>
							打开/关闭
						</s:a>
					</td>
					<td>
					<s:a action="SurveyAction_clearSurvey.action" namespace="/">
						<s:param name="surveyId" value="%{surveyId}"></s:param>
					清除调查</s:a>
					</td>
					<td>
					<s:a action="SurveyAction_deleteSurvey.action" namespace="/">
						<s:param name="surveyId" value="%{surveyId}"></s:param>
						删除
					</s:a>
					</td>
				</tr>
			</s:iterator>
		</tbody>
	</table>
	</div>
</body>
</html>