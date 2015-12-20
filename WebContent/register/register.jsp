<%@ page language="java" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<link rel="stylesheet" href="${pageContext.servletContext.contextPath}/css/header.css" type="text/css">
<style type="text/css">
	#content{
		width: 100%;
		height: 100%;
		background-color: #CDC;
		padding-top: 10px;
	}
	table {
		width: 60%;
		border-collapse: collapse;
		margin: 0 auto;
	}
	table td{
		padding: 5px;
		text-align: right;
		padding-left: 10px;
	}
	input {
		width: 80%;
	}
	.error{
		color: red;
	}
	
</style>
</head>
<body>
	<%@ include file="/header.jsp" %>
	<div id="content">
		<s:form action="RegisterAction_register.action" namespace="/">
			<table>
				<tr>
					<td width="30%">电子邮箱</td>
					<td width="70%">
						<s:textfield name="email"></s:textfield>
						<s:fielderror cssClass="error">
							<s:param>email</s:param>
						</s:fielderror>
					</td>
				</tr>
				<tr>
					<td>昵称</td>
					<td>
						<s:textfield name="nickName"></s:textfield>
						<s:fielderror cssClass="error">
							<s:param>nickName</s:param>
						</s:fielderror>
					</td>
				</tr>
				<tr>
					<td>密码</td>
					<td>
						<s:password name="password"></s:password>
						<s:fielderror cssClass="error">
							<s:param>password</s:param>
						</s:fielderror>
					</td>
				</tr>
				<tr>
					<td>请再次输入密码</td>
					<td>
						<s:password name="repeatPassword"></s:password>
						<s:fielderror cssClass="error">
							<s:param>repeatPassword</s:param>
						</s:fielderror>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<s:submit name="register" value="注册"></s:submit>
					</td>
				</tr>
			</table>
		</s:form>
	</div>
</body>
</html>