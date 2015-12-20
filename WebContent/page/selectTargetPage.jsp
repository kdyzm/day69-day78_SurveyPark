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
		width: 80%;
		margin: 0 auto;
		border-collapse: collapse;
	}
	table td{
		border:1px solid black;
		padding:5px;
	}
	.innertable{
		width:100%;
	}
	.surveyTitle{
		background-color: #CCC;
	}
	.sourcePage{
		background-color: gray;
	}
</style>
</head>
<body>
	<%@ include file="/header.jsp" %>
	<br/>
	<br/>
	<div style="text-align: center;margin-bottom: 10px;">同一个调查内是移动，不同调查中是复制;灰色背景的部分是源页面</div>
	<table>
		<!-- 遍历所有调查 -->
		<s:iterator value="%{surveys}" var="survey">
			<tr class="surveyTitle">
				<td colspan="2">
					<s:property value="%{#survey.title}"/>
				</td>
			</tr>
			<tr>
				<td></td>
				<td>
					<table class="innertable">
						<s:iterator value="%{pages}" var="page">
							<tr>
								<s:if test="%{#page.pageId==#sourcePage.pageId}">
									<td class="sourcePage">
										<s:property value="%{#page.title}"/>
									</td>
								</s:if>
								<s:else>
									<td>
										<s:property value="%{#page.title}"/>
									</td>
								</s:else>
								<td>
									<s:form action="PageAction_copyOrRemovePage.action" namespace="/">
										<s:hidden name="oldPageId" value="%{#sourcePage.pageId}"></s:hidden>
										<s:hidden name="newPageId" value="%{#page.pageId}"></s:hidden>
										<s:hidden name="newSurveyId" value="%{#survey.surveyId}"></s:hidden>
										<s:radio name="position" list="#{0:'之前',1:'之后'}" value="0"></s:radio>
										<s:submit value="确定"></s:submit>
									</s:form>
								</td>
							</tr>
						</s:iterator>
					</table>
				</td>
			</tr>
			
		</s:iterator>
	</table>
</body>
</html>