<%@ page language="java" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<link rel="stylesheet" href="${pageContext.servletContext.contextPath}/css/header.css" type="text/css">
<style type="text/css">
	.survey{
		display: inline-block;
		width: 150px;
		text-align: center ;
		margin: 20px;
	}
</style>
</head> 
<body>
	<%@ include file="/header.jsp" %>
	<div style="width: 80%;margin: 0 auto;margin-top: 30px;">
		<s:iterator value="%{#surveys}" var="survey">
			<s:a cssClass="survey" action="EntrySurveyAction_toSurveyPage.action" namespace="/">
				<s:param name="surveyId" value="#survey.surveyId"></s:param>
				<img width="200px" alt="<s:property value='%{#survey.title}'/>" src='<s:property value="getLogoPath(#survey.logoPath)"/>'/><br/>
				<s:property value="#survey.title"/>
			</s:a>
		</s:iterator>
	</div>
</body>
</html>