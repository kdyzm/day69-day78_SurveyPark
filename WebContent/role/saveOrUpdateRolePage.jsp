<%@ page language="java" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<link rel="stylesheet" href="${pageContext.servletContext.contextPath}/css/header.css" type="text/css">
<script type="text/javascript" src="${pageContext.servletContext.contextPath}/js/jquery-1.4.1.js"></script>
<script type="text/javascript">
	$().ready(function(){
		$("button[id='toRight']").unbind("click");
		$("button[id='toRight']").bind("click",function(){
			var noneOwnRights=$("select[name='noneOwnRights']");
			var ownRights=$("select[name='ownRights']");
			$("select[name='ownRights'] option:selected").each(function(){
				noneOwnRights.prepend($(this).clone());
				$(this).remove();
			});
			return false;
		});
		$("button[id='toLeft']").unbind("click");
		$("button[id='toLeft']").bind("click",function(){
			var noneOwnRights=$("select[name='noneOwnRights']");
			var ownRights=$("select[name='ownRights']");
			$("select[name='noneOwnRights'] option:selected").each(function(){
				ownRights.prepend($(this).clone());
				$(this).remove();
			});
			return false;
		});
		$("button[id='allToRight']").unbind("click");
		$("button[id='allToRight']").bind("click",function(){
			var noneOwnRights=$("select[name='noneOwnRights']");
			var ownRights=$("select[name='ownRights']");
			$("select[name='ownRights'] option").each(function(){
				noneOwnRights.append($(this).clone());
				$(this).remove();
			});
			return false;
		});
		$("button[id='allToLeft']").unbind("click");
		$("button[id='allToLeft']").bind("click",function(){
			var noneOwnRights=$("select[name='noneOwnRights']");
			var ownRights=$("select[name='ownRights']");
			$("select[name='noneOwnRights'] option").each(function(){
				ownRights.append($(this).clone());
				$(this).remove();
			});
			return false;
		});
		$("#submit").unbind("click");
		$("#submit").bind("click",function(){
			$("select[name='ownRights'] option").each(function(){
				$(this).attr("selected","selected");
			});
			return true;
		});
	});
</script>
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
	button{
		width: 150px;
		height: 30px;
		text-align: center;
		line-height: 20px;
		font-size: 12px;
		margin-bottom: 5px;
	}
	select {
		height: 300px;
		width: 150px;
		text-align: center;
	}
</style>
</head>
<body>
	<%@ include file="/header.jsp" %>
	<div>
		<s:form action="RoleAction_saveOrUpdateRole.action" namespace="/">
			<s:hidden name="roleId"></s:hidden>
			<table>
				<tr>
					<td>
						角色名称
					</td>
					<td>
						<s:textfield name="roleName"></s:textfield>
					</td>
				</tr>
				<tr>
					<td>角色值</td>
					<td>
						<s:textfield name="roleValue"></s:textfield>
					</td>
				</tr>
				<tr>
					<td>角色描述</td>
					<td>
						<s:textarea name="roleDesc"></s:textarea>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<!-- select标签必须加上name属性，否则就会报错 -->
						<table style="border: 0px;padding: 0px;width: 200px;">
							<tr>
								<td style="border: 0px;">
									<s:select list="%{rights}" name="ownRights" multiple="true" size="20" listKey="rightId" listValue="rightName"></s:select>
								</td>
								<td style="border: 0px;">
									<div style="display: inline-block;">
										<button id="toRight">&gt;</button><br/>
										<button id="toLeft">&lt;</button><br/>
										<button id="allToRight">&gt;&gt;</button><br/>
										<button id="allToLeft">&lt;&lt;</button>
									</div>
								</td>
								<td style="border: 0px;">
									<s:select list="%{#noneOwnRights}" name="noneOwnRights" multiple="true" size="20" listKey="rightId" listValue="rightName"></s:select>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<s:submit value="确认" id="submit"></s:submit>
					</td>
				</tr>
			</table>
		</s:form>
	</div>
</body>
</html>