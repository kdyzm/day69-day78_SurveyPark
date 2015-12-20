<%@ page language="java" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<link rel="stylesheet"
	href="${pageContext.servletContext.contextPath}/css/header.css"
	type="text/css">
<style type="text/css">
table {
	width: 89%;
	border: 1px solid balck;
	border-collapse: collapse;
	margin: 0 auto;
	margin-top: 20px;
}

table td {
	border: 1px solid black;
	padding: 5px;
	text-align: center;
}
</style>
</head>
<!-- 进行权限管理的界面 -->
<body>
	<%@ include file="/header.jsp"%>
	<div
		style="background-color: #CCC; height: 30px; width: 100%; line-height: 30px; font-size: 15px; text-align: left; padding-left: 20px;">
		日志管理的界面</div>
	<div>
		<table>
			<tr>
				<td>操作人</td>
				<td>操作名称</td>
				<td>操作参数</td>
				<td>操作结果</td>
				<td>结果消息</td>
				<td>操作时间</td>
			</tr>
			<s:iterator value="%{#logList}" status="st">
				<tr>
					<td><s:property value="operator" /></td>
					<td><s:property value="operatorName" /></td>
					<td><s:property value="@com.kdyzm.utils.StringUtils@setTagContentLimitLength(operateParams)" /></td>
					<td><s:property value="operateResult" /></td>
					<td><s:property value="resultMessage" /></td>
					<td><s:date name="operatorDate" format="yyyy/MM/dd HH:mm:ss" /></td>
				</tr>
			</s:iterator>
		</table>
	</div>
</body>
</html>