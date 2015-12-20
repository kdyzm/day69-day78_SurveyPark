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
		text-align: right;
	}
</style>
</head>
<!-- 进行权限管理的界面 -->
<body>
	<%@ include file="/header.jsp" %>
	<div style="background-color:#CCC;height:30px;width: 100%;line-height: 30px;font-size: 15px;text-align: left;padding-left: 20px;">
		角色管理的界面
		<div style="width: 100px;float: right;margin-right: 20px;">
			<s:a action="RoleAction_toSaveOrUpdateRolePage.action" namespace="/">
				添加角色
			</s:a>
		</div>
	</div>
	<div>
		<table>
			<tr>	
				<td>序号</td>
				<td>ID</td>
				<td>角色名称</td>
				<td>修改</td>
				<td>删除</td>
			</tr>
			<s:iterator value="%{#roleList}" var="role" status="st">
				<tr>
					<td><s:property value="#st.count"/></td>
					<td><s:property value="#role.roleId"/></td>
					<td><s:property value="#role.roleName"/></td>
					<td>
						<s:a action="RoleAction_toSaveOrUpdateRolePage.action" namespace="/">
							<s:param name="roleId" value="%{#role.roleId}"></s:param>
							修改
						</s:a>
					</td>
					<td>
						<s:a action="RoleAction_deleteRole.action" namespace="/">
							<s:param name="roleId" value="%{#role.roleId}"></s:param>
							删除
						</s:a>
					</td>
				</tr>
			</s:iterator>
		</table>
	</div>
</body>
</html>