/*
 * All rights Reserved, Copyright (C) Aisino LIMITED 2019
 * FileName: CustomRolesAuthorizationFilter.java
 */
package com.phms.shiro;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * class name: CustomRolesAuthorizationFilter <BR>
 * class description: please write your description <BR>
 */
//AuthorizationFilter抽象类事项了javax.servlet.Filter接口，它是个过滤器。 
public class CustomRolesAuthorizationFilter extends AuthorizationFilter {
	@Override
	protected boolean isAccessAllowed(ServletRequest req, ServletResponse resp, Object mappedValue) throws Exception {
		Subject subject = getSubject(req, resp);
		String[] perms = ((String[]) mappedValue);
		boolean isPermitted = true;
		if (null != perms && perms.length > 0) {
			if (perms.length == 1) {
				if (!isOneOfPermitted(perms[0], subject)) {
					isPermitted = false;
				}
			} else if (!isAllPermitted(perms, subject)) {
				isPermitted = false;
			}
		}
		return isPermitted;
	}

	/**
	 * 以“，”分割的权限为并列关系的权限控制，分别对每个权限字符串进行“|”分割解析 若并列关系的权限有一个不满足则返回false
	 *
	 * @param permStrArray 以","分割的权限集合
	 * @param subject      当前用户的登录信息
	 * @return 是否拥有该权限
	 */
	private boolean isAllPermitted(String[] permStrArray, Subject subject) {
		boolean isPermitted = true;
		for (int index = 0, len = permStrArray.length; index < len; index++) {
			if (!isOneOfPermitted(permStrArray[index], subject)) {
				isPermitted = false;
				break;
			}
		}
		return isPermitted;
	}

	/**
	 * 判断以“|”分割的权限有一个满足的就返回true，表示权限的或者关系
	 *
	 * @param permStr 权限数组种中的一个字符串
	 * @param subject 当前用户信息
	 * @return 是否有权限
	 */
	private boolean isOneOfPermitted(String permStr, Subject subject) {
		boolean isPermitted = false;
		String[] permArr = permStr.split("\\|");
		if (permArr.length > 0) {
			for (int index = 0, len = permArr.length; index < len; index++) {
				if (subject.hasRole(permArr[index])) {
					isPermitted = true;
					break;
				}
			}
		}
		return isPermitted;
	}
}
