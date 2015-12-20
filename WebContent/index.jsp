<%@ page language="java" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<link rel="stylesheet" href="${pageContext.servletContext.contextPath}/css/header.css" type="text/css">
<style type="text/css">
	table {
	margin:0 auto;
		margin-top:20px;
		width: 50%; 
		border: 1px solid black;
		border-collapse: collapse;
}
	table td{
		padding: 5px;
		text-align: center;
		border:1px solid black;
	}
	input {
	width: 70%;
}
</style>
</head>
<body>
<!-- 这里将首页和登陆界面合并成一个页面了 -->
<%@ include file="/header.jsp" %>
	<s:if test="%{#session.user != null}">
		<div>
			欢迎你,<s:property value="%{#session.user.nickName}"/>
		</div>
	</s:if>
	<s:else>
		<div id="content">
			<div style="text-align: center;"><s:actionerror/></div>
			<s:form namespace="/" action="LoginAction_chekEmailAndPassword.action">
				<table>
					<thead>
						<tr>
							<td colspan="2">用户登陆界面</td>
							<s:fielderror>
							</s:fielderror>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>Email</td>
							<td>
								<s:textfield name="email" value="1234567@qq.com"></s:textfield>
							</td>
						</tr>
						<tr>
							<td>密码</td>
							<td>
								<s:textfield name="password" value="kdyzm"></s:textfield>
							</td>
						</tr>
						<tr>
							<td>
								<s:submit value="登陆"></s:submit>
							</td>
							<td>
								<s:reset value="重置"></s:reset>
							</td>
						</tr>
					</tbody>
				</table>
			</s:form>
		</div>
	</s:else>
</body>