<%@ page language="java" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet"
	href="${pageContext.servletContext.contextPath}/css/header.css"
	type="text/css">
<title>Insert title here</title>
<style type="text/css">
	table {
		border: 1px solid black;
		border-collapse: collapse;
		width: 80%;
		margin: 0 auto;
		margin-top: 20px;
	}
	table td{
		border: 1px solid black;
		padding: 5px;
	}
	input,button,select,textarea{
		width: 80%;
	}
	textarea {
	height: 100px;
}
</style>
</head>
<body>
	<!-- 针对矩阵式单选按钮和矩阵式复选按钮 -->
	<%@ include file="/header.jsp" %>
	<div>
		<s:form action="QuestionAction_saveQuestion.action" namespace="/">
			<s:hidden name="questionId"></s:hidden>
			<s:hidden name="questionType"></s:hidden>
			<s:hidden name="pageId"></s:hidden>
			<s:hidden name="surveyId"></s:hidden>
			<table>
				<tr>
					<td colspan="2">下拉列表类型矩阵型问题设计</td>
				</tr>
				<tr>
					<td>问题标题</td>
					<td>
						<s:textfield name="title"></s:textfield>
					</td>
				</tr>
				<tr>
					<td>行标题标签组</td>
					<td>
						<s:textarea name="matrixRowTitles">
							
						</s:textarea>
					</td>
				</tr>
				<tr>
					<td>列标题标签组</td>
					<td>
						<s:textarea name="matrixColTitles">
							
						</s:textarea>
					</td>
				</tr>
				<tr>
					<td>[下拉列表选项集合]</td>
					<td>
						<s:textarea name="matrixSelectOptions">
							
						</s:textarea>
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