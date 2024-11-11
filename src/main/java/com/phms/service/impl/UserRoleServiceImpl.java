package com.phms.service.impl;

import com.phms.mapper.UserRoleMapper;
import com.phms.model.ResultMap;
import com.phms.pojo.User;
import com.phms.pojo.UserRole;
import com.phms.pojo.UserRoleExample;
import com.phms.service.UserRoleService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.Collections;
import java.util.List;

@Service
public class UserRoleServiceImpl implements UserRoleService {

	@Autowired
	private UserRoleMapper userRoleMapper;
	/** logback日志记录 */
	private static final Logger logger = LoggerFactory.getLogger(UserRoleServiceImpl.class);

	/**
	 *      Description: 根据用户id获取权限 <BR>
	 *      Remark: <BR>
	 * @param userId
	 * @return <BR>
	 */
	@Override
	public List<UserRole> getRoleByUserId(String userId) {
		UserRoleExample example = new UserRoleExample();
		example.createCriteria().andUserIdEqualTo(userId);
		try {
			return userRoleMapper.selectByExample(example);
		} catch (Exception e) {
			logger.error("根据用户id获取权限异常", e);
			return Collections.emptyList();
		}
	}

	/**
	 *      Description: 根据权限id删除用户权限表 <BR>
	 *      Remark: <BR>
	 * @param id
	 * @return <BR>
	 */
	@Override
	@Transactional
	public boolean delUserRoleByRoleId(int id) {
		UserRoleExample example = new UserRoleExample();
		example.createCriteria().andRoleIdEqualTo(id);
		try {
			userRoleMapper.deleteByExample(example);
			return true;
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			logger.error("根据权限id删除用户权限表异常", e);
			return false;
		}
	}

	/**
	 *      Description: 根据用户id权限id删除用户权限表 <BR>
	 *      Remark: <BR>
	 * @param userId
	 * @param roleId
	 * @return <BR>
	 */
	@Override
	@Transactional
	public ResultMap delUserRoleByUserIdAndRoleId(String userId, Integer roleId) {
		Subject subject = SecurityUtils.getSubject();
		User user = (User) subject.getPrincipal();

		if (userId.equals(user.getId())) {
			return new ResultMap().fail().message("DontOP");
		} else {
			UserRoleExample example = new UserRoleExample();
			example.createCriteria().andRoleIdEqualTo(roleId).andUserIdEqualTo(userId);
			try {
				userRoleMapper.deleteByExample(example);
				return new ResultMap().success().message("删除成功");
			} catch (Exception e) {
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				logger.error("根据用户id权限id删除用户权限表异常", e);
				return new ResultMap().fail().message("删除失败");
			}
		}
	}

	/**
	 *      Description: 添加用户角色 <BR>
	 *      Remark: <BR>
	 * @param roleId
	 * @param userIds
	 * @return <BR>
	 */
	@Override
	@Transactional
	public String addUserRole(Integer roleId, String[] userIds) {
		try {
			if (null != roleId && userIds != null && userIds.length > 0) {
				for (String userId : userIds) {
					UserRole userRole = new UserRole();
					userRole.setRoleId(roleId);
					userRole.setUserId(userId);
					userRoleMapper.insert(userRole);
				}
			}
			return "SUCCESS";
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			logger.error("添加用户角色 异常", e);
			return "ERROR";
		}
	}

	@Override
	public UserRole getUserRole(String userId) {
		UserRoleExample example = new UserRoleExample();
		example.createCriteria().andUserIdEqualTo(userId);
		return userRoleMapper.selectByExample(example).get(0);
	}
}
