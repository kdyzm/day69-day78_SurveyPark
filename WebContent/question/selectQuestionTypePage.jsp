<%@ page language="java" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<link rel="stylesheet" href="${pageContext.servletContext.contextPath}/css/header.css" type="text/css">
<style type="text/css">
	table{
		width: 50%;
		margin: 0 auto;
		margin-top: 20px;
	}
	select {
		width: 100%;
		font-size: 17px;
	}
</style>
</head>
<body>
	<%@ include file="/header.jsp" %>
	<div>
		<table>
			<tr>
				<td width="30%">请选择一种题型</td>
				<td width="70%">
					<s:form action="QuestionAction_toDesignQuestionPage.action" namespace="/">
						<s:hidden name="pageId"></s:hidden>
						<s:hidden name="surveyId"></s:hidden>
						<select name="questionType" onchange="javascript:this.form.submit();">
							<option value="-1">-----请选择一种题型-----</option>
							<option value="0">非矩阵式横向单选按钮</option>
							<option value="1">非矩阵式纵向单选按钮</option>
							<option value="2">非矩阵式横向复选按钮</option>
							<option value="3">非矩阵式纵向复选按钮</option>
							
							<option value="4">非矩阵式下拉列表</option>
							
							<option value="5">非矩阵式文本框</option>
							
							<option value="6">矩阵式单选按钮</option>
							<option value="7">矩阵式复选按钮</option>
							
							<option value="8">矩阵式下拉列表</option>
						</select>
					</s:form>
				</td>
			</tr>
		</table>
	</div>	
</body>
</html>