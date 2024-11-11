package com.phms.service;

import com.phms.model.ResultMap;
import com.phms.pojo.UserRole;

import java.util.List;

public interface UserRoleService {
	List<UserRole> getRoleByUserId(String userId);

	boolean delUserRoleByRoleId(int id);

	ResultMap delUserRoleByUserIdAndRoleId(String userId, Integer roleId);

	String addUserRole(Integer roleId, String[] userIds);

	UserRole getUserRole(String userId);
}
