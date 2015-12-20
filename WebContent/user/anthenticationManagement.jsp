<%@ page language="java" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<link rel="stylesheet" href="${pageContext.servletContext.contextPath}/css/header.css" type="text/css">
<style type="text/css">
	table{
		width: 89%;
		border: 1px solid balck;
		border-collapse: collapse;
		margin: 0 auto;
		margin-top: 20px;
	}
	table td{
		border:1px solid black;
		padding: 5px;
		text-align: center;
	}
</style>
</head>
<!-- 进行权限管理的界面 -->
<body>
	<%@ include file="/header.jsp" %>
	<div style="background-color:#CCC;height:30px;width: 100%;line-height: 30px;font-size: 15px;text-align: left;padding-left: 20px;">
		用户授权管理
	</div>
	<div>
		<table>
			<tr>	
				<td>序号</td>
				<td>ID</td>
				<td>email</td>
				<td>昵称</td>
				<td>修改授权</td>
				<td>清除授权</td>
			</tr>
			<s:iterator value="%{#userList}" var="user" status="st">
				<tr>
					<td><s:property value="#st.count"/></td>
					<td><s:property value="#user.userId"/></td>
					<td><s:property value="#user.email"/></td>
					<td><s:property value="#user.nickName"/></td>
					<td>
						<s:a action="AuthenticationAction_toUpdateAuthenticationPage.action" namespace="/">
							<s:param name="userId" value="%{#user.userId}"></s:param>
							修改授权
						</s:a>
					</td>
					<td>
						<s:a action="AuthenticationAction_clearAuthentication.action" namespace="/">
							<s:param name="userId" value="%{#user.userId}"></s:param>
							清除授权
						</s:a>
					</td>
				</tr>
			</s:iterator>
		</table>
	</div>
</body>
</html>