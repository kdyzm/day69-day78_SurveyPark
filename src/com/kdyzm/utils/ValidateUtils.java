package com.kdyzm.utils;

import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.kdyzm.domain.User;
import com.kdyzm.domain.security.Right;
import com.kdyzm.struts.action.aware.UserAware;
import com.opensymphony.xwork2.Action;

public class ValidateUtils {
	// 验证是否有权限的验证方法
	public static boolean hasRight(String namespace, String actionName, HttpServletRequest request,Action action) {
		String url = namespace + "/"
				+ (actionName.contains("?") ? actionName.substring(0, actionName.indexOf("?")) : actionName)
				+ ".action";
		// TODO 将权限列表放入到ServletContext中的方法
		HttpSession session = request.getSession();
		ServletContext sc = session.getServletContext();
		Map<String, Right> allRights = (Map<String, Right>) sc.getAttribute("all_rights_map");
		Right right = allRights.get(url);
		// 如果是公共资源直接方放过
		if (right == null || right.getCommon()) {
//			System.out.println("访问公共资源，即将放行！");
			return true;
		} else {
			User user = (User) session.getAttribute("user");
			// 判断是否已经登陆
			if (user == null) {
				return false;
			} else {
				// 如果实现了UserAware接口
				if (action != null && action instanceof UserAware) {
					UserAware userAware = (UserAware) action;
					userAware.setUser(user);
				}
				// 如果是超级管理员直接放行
				if (user.getSuperAdmin()) {
					return true;
					// 否则先检查是否有权限
				} else {
					if (user.hasRight(right)) {
						return true;
					} else {
						return false;
					}
				}
			}
		}
	}
}
