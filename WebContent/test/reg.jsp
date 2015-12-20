<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags"  prefix="s"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<body>
	<s:form action="UserActionx_doReg.action" namespace="/">
		<table>
			<tr>
				<td>id</td>
				<td>name</td>
			</tr>
			<s:iterator value="new int[4]" status="st">
				<tr>
					<td>
						<s:textfield name="%{'users['+#st.index+'].id'}"></s:textfield>
					</td>
					<td>
						<s:textfield name="%{'users['+#st.index+'].name'}"></s:textfield>
					</td>
				</tr>
			</s:iterator>
			<tr>
				<td colspan="2">
					<s:submit value="提交"></s:submit>
				</td>
			</tr>
		</table>
	</s:form>
</body>
</html>