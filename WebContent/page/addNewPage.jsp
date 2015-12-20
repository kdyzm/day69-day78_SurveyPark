<%@ page language="java" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" href="${pageContext.servletContext.contextPath}/css/header.css" type="text/css">
<title>Insert title here</title>
<style type="text/css">
	table{
		border: 1px solid black;
		border-collapse: collapse;
		width:80%;
		margin: 0 auto;
		margin-top: 20px;
	}
	table td{
		border: 1px solid black;
		padding: 5px;
	}
	input,button{
		width: 70%;
	}
</style>
</head>
<!-- 给调查添加新的方法 -->
<body>
	<%@ include file="/header.jsp" %>
	<div>
		<table>
			<tr>
				<td colspan="2">创建新的页面</td>
			</tr>
			<tr>
				<td>&nbsp;</td>				
				<td>
					<s:form action="PageAction_saveNewPage.action" namespace="/">
						<s:hidden name="surveyId"></s:hidden>
						<table>
							<tr>
								<td>页面标题:</td>
								<td>
									<s:textfield name="title"></s:textfield>
								</td>
							</tr>
							<tr>
								<td>页面描述：</td>
								<td>
									<s:textfield name="description"></s:textfield>
								</td>
							</tr>
							<tr>
								<td>
									<s:submit value="提交"></s:submit>
								</td>
								<td>
									<button onclick="javascript:window.history.go(-1);">返回</button>
								</td>
							</tr>
						</table>
					</s:form>
				</td>				
			</tr>
		</table>
	</div>
</body>
</html>