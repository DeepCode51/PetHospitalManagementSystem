package com.phms.service;

import com.phms.model.ResultMap;
import com.phms.pojo.User;
import com.phms.pojo.UserParameter;

import java.util.List;

public interface UserService {
	User selectUserByUserId(Long userId);

	public ResultMap login(String username, String password);

	public boolean checkUserPassword(String password);

	public String updatePassword(String password);

	Object getAllUserByRoleId(Integer roleId, Integer page, Integer limit);

	Object getAllUserByNotRoleId(Integer roleId, Integer page, Integer limit);

	User selectByPrimaryKey(Long userId);

	List<User> getAdmins();

	Object getAllUserByLimit(UserParameter userParameter);

	void delUserById(Long id);

	void addUser(User user) throws Exception;

	String updateUser(Long oldId, User user);

	String updateUser(User user);

	List<User> getAllUser();

	User getAdminById(Long userId);

	Object getAllDelUserByLimit(UserParameter userParameter);

	List<User> selectAllUser();

	User getUserByPhoneAndName(String phone, String name);

    void save(User user);

	User getByIdCard(String idCard);

    List<User> listDoctor();
}
