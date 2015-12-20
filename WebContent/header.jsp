<%@ page language="java" pageEncoding="utf-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<div id="header">
	<div class="header_top">欢迎使用SurveyDoor调查系统!</div>
	<div class="header_content">
		<s:a action="IndexAction_toIndexPage.action" namespace="/">[首页]</s:a>
		<s:a action="SurveyAction_createNewSurvey.action" namespace="/">[新建调查]</s:a>
		<s:a action="SurveyAction_toMySurveyPage.action" namespace="/">[我的调查]</s:a>
		<s:a action="EntrySurveyAction_toEntrySurveyPage.action" namespace="/">[参与调查]</s:a>
		<s:a action="RegisterAction_toRegisterPage.action" namespace="/">[用户注册]</s:a>
		<s:a action="AuthenticationAction_toAuthenticationManagementPage.action" namespace="/">[用户授权管理]</s:a>
		<s:a action="RoleAction_toRoleManagementPage.action" namespace="/">[角色管理]</s:a> 
		<s:a action="RightAction_toRightManagementPage.action" namespace="/">[权限管理]</s:a>
		<s:a action="LogAction_findAllLogs.action" namespace="/">[日志管理]</s:a>
	</div>
	<div class="header_bottom">
		<marquee>
			<s:if test="%{#session.user!=null}">
				欢迎你，<s:property value="%{#session.user.nickName}"/>
			</s:if>
			<s:else>
				欢迎你，未登录用户
			</s:else>
		</marquee>
	</div>
</div>
