<%@ page language="java" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" href="${pageContext.servletContext.contextPath}/css/header.css" type="text/css">
<title>Insert title here</title>
<style type="text/css">
	table {
		border: 1px solid black;
		border-collapse: collapse;
		width: 70%;
		margin: 0 auto;
		margin-top: 20px;
	}
	table td{
		border: 1px solid black;
		padding: 5px;
	}
	input,button{
		width: 60%;
	}
</style>
</head>
<body>
	<%@ include file="/header.jsp" %>
	<div>
		<s:form action="PageAction_updatePageInfo.action" namespace="/">
			<s:hidden name="pageId"></s:hidden>
			<s:hidden name="surveyId"></s:hidden>
			<table>
				<tr>
					<td>修改页面标题：</td>
					<td>
						<s:textfield name="title"></s:textfield>
					</td>
				</tr>
				<tr>
					<td>
						<s:submit value="确定"></s:submit>
					</td>
					<td>
						<button onclick="javascript:window.history.go(-1);">返回</button>
					</td>
				</tr>
			</table>
		</s:form>
	</div>
</body>
</html>