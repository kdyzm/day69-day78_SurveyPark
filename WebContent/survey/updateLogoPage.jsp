<%@ page language="java" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<link rel="stylesheet" href="${pageContext.servletContext.contextPath}/css/header.css" type="text/css">
<style type="text/css">
	table{
		border:1px solid black;
		border-collapse: collapse;
		width: 40%;
		margin: 0 auto;
		margin-top: 30px;
	}
	table td{
		border:1px solid black;
		padding: 5px;
	}
</style>
</head>
<!-- 上传logo图片的界面 -->
<body>
	<%@ include file="/header.jsp" %>
	<div>
		<div style="width: 100%;text-align: center;"><s:fielderror></s:fielderror></div>
		<s:form enctype="multipart/form-data" method="POST" action="SurveyAction_doUploadLogo.action" namespace="/">
			<s:hidden name="surveyId" value="%{surveyId}"></s:hidden>
			<table>
				<tr>
					<td>
						请选择上传的logo图片：
					</td>
					<td>
						<s:file name="logo"></s:file>
					</td>
				</tr>
				<tr>
					<td>
						<s:submit value="上传"></s:submit>
					</td>
					<td>
						<input value="返回上一步" type="button" onclick="javascript:window.history.go(-1);">
					</td>
				</tr>
			</table>
		</s:form>
	</div>
</body>
</html>