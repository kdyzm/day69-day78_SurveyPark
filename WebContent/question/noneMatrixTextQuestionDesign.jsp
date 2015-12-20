<%@ page language="java" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<link rel="stylesheet"
	href="${pageContext.servletContext.contextPath}/css/header.css"
	type="text/css">
	<style type="text/css">
		table {
		border: 1px solid black;
		width: 80%;
		margin: 0 auto;
		margin-top: 20px;
		border-collapse: collapse;
	}
	table td{
		border:1px solid black;
		padding: 5px;
	}
	textarea,input {
		width: 80%;
	}
	</style>
</head>
<body>
	<%@ include file="/header.jsp" %>
	<div>
		<s:form action="QuestionAction_saveQuestion.action" namespace="/">
			<s:hidden name="questionId"></s:hidden>
			<s:hidden name="pageId"></s:hidden>
			<s:hidden name="surveyId"></s:hidden>
			<s:hidden name="questionType"/>
			<table>
				<tr>
					<td colspan="2">非矩阵式文本框类型问题设计</td>
				</tr>
				<tr>
					<td width="40%;">问题标题：</td>
					<td width="60%;">
						<s:textfield name="title"></s:textfield>
					</td>
				</tr>
				<tr>
					<td>
						<s:submit value="确定"></s:submit>
					</td>
					<td>
						<input type="button" value="返回" onclick="javascript:window.history.go(-1);">
					</td>
				</tr>
			</table>
		</s:form>	
	</div>
</body>
</html>