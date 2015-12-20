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
		table{
			border: 1px solid black;
			border-collapse: collapse;
			width: 80%;
			margin: 0 auto;
			margin-top: 20px;
		}
		table td{
			border: 1px solid black;
			text-align: center;
			padding: 5px;
		}
		input,button {
	width: 70%;
}
	</style>
</head>
<body>
	<%@ include file="/header.jsp"%>
	<div>
		<s:form action="SurveyAction_editSurvey.action" namespace="/">
			<s:hidden name="surveyId"></s:hidden>
			<table>
				<tr>
					<td colspan="2">编辑调查页面</td>
				</tr>
				<tr>
					<td>调查标题：</td>
					<td><s:textfield name="title"></s:textfield></td>
				</tr>
				<tr>
					<td>"下一步"提示文本：</td>
					<td><s:textfield name="nextText"></s:textfield></td>
				</tr>
				<tr>
					<td>"上一步"提示文本：</td>
					<td><s:textfield name="preText"></s:textfield></td>
				</tr>
				<tr>
					<td>"完成"提示文本：</td>
					<td><s:textfield name="doneText"></s:textfield></td>
				</tr>
				<tr>
					<td>"退出"提示文本：</td>
					<td><s:textfield name="exitText"></s:textfield></td>
				</tr>
				<tr>
					<td><s:submit value="确定"></s:submit></td>
					<td>
						<button>返回</button>
					</td>
				</tr>
			</table>
		</s:form>
	</div>
</body>
</html>