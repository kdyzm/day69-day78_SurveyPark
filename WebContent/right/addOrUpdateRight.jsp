<%@ page language="java" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<link rel="stylesheet" href="${pageContext.servletContext.contextPath}/css/header.css" type="text/css">
<style type="text/css">
	table{
		border: 1px solid black;
		width: 90%;
		margin: 0 auto;
		margin-top: 20px;
		border-collapse: collapse;
	}
	table td{
		border: 1px solid black;
		padding: 5px;
		text-align: center;
	}
	input[type="text"]{
		border: 1px solid gray;
		width: 99%;
	}
	textarea {
		border: 1px solid gray;
		width: 99%;
		margin: 0 auto;
		height:150px;
		margin-top: 5px;
		margin-bottom: 5px;
	}
	input[type="submit"]{
		border:1px solid gray;
		width: 100px;
	}
	input[type="reset"]{
		border:1px solid gray;
		width: 100px;
	}
</style>
</head>
<body>
	<%@ include file="/header.jsp" %>
	<div>
		<s:form action="RightAction_saveOrUpdateRightPage.action" namespace="/">
			<s:hidden name="rightId"></s:hidden>
			<table>
				<tr>
					<td>
						权限名称
					</td>
					<td>
						<s:textfield name="rightName"></s:textfield>
					</td>
				</tr>
				<tr>
					<td>权限URL</td>
					<td>
						<s:textfield name="rightUrl"></s:textfield>
					</td>
				</tr>
				<tr>
					<td>权限位</td>
					<td>
						<s:textfield readonly="true" name="rightPos"></s:textfield>
					</td>
				</tr>
				<tr>
					<td>权限码</td>
					<td>
						<s:textfield readonly="true" name="rightCodes"></s:textfield>
					</td>
				</tr>
				<tr>
					<td>公共资源</td>
					<td>
						<s:checkbox name="common"></s:checkbox>
					</td>
				</tr>
				<tr>
					<td>权限描述</td>
					<td>
						<s:textarea name="rightDesc"></s:textarea>
					</td>
				</tr>
				<tr>
					<td>
						<s:submit value="确认"></s:submit>
					</td>
					<td>
						<input type="reset" value="重置"/>
					</td>
				</tr>
			</table>
		</s:form>
	</div>
</body>
</html>